package util;

import java.sql.*;
/**
* <strong>
* --------------------------------------------------------------- <br>
* | ISYS6197 - BUSINESS APPLICATION DEVELOPMENT | <br>
* --------------------------------------------------------------- <br>
* </strong>
* <br>
* Connect.java | This class is used for connection to MySQL database
* <br>
* Copyright 2019 - Bina Nusantara University
* <br>
* Software Laboratory Center | Laboratory Center Alam Sutera
* <br>
* Kevin Surya Wahyudi (SW16-2), All rights reserved.
* <br>
*/
public final class Connect {
	
	private final String USERNAME = "root"; // change with your MySQL username, the default username is 'root'
	private final String PASSWORD = ""; // change with your MySQL password, the default password is empty
	private final String DATABASE = "waste_bank"; // change with the database name that you use
	private final String HOST = "localhost:3306"; // change with your MySQL host, the default port is 3306
	private final String CONECTION = String.format("jdbc:mysql://%s/%s?enabledTLSProtocols=TLSv1.2", HOST, DATABASE);
	
	private Connection con;
	private Statement st;
	private static Connect connect;
	
	//result set
	public ResultSet rs;
	public PreparedStatement ps;
	public ResultSetMetaData rsm;
	
	//Login SELECT Query 
	private String SELECT_QUERY = "SELECT * FROM user WHERE UserName = ? AND UserPassword = ? AND UserRole = ?";
	String role_query = "SELECT UserRole FROM user WHERE UserName = ? AND UserPassword = ?";
	
	
	/**
	* Constructor for Connect class
	* <br>
	* This class is used singleton design pattern, so this class only have one instance
	*/
    private Connect() {
    	try {  
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(CONECTION,USERNAME, PASSWORD);  
            st = con.createStatement(); 
            System.out.println("server connected !s");
        } catch(Exception e) {
        	e.printStackTrace();
        	System.out.println("Failed to connect the database, the system is terminated!");
        	System.exit(0);
        }  
    }
    
    
    //SELECT data 
    public ResultSet selectQuery(String query)
    {
    	try {
			rs = st.executeQuery(query);
			System.out.println(rs);
			rsm = rs.getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return rs;
    }
    
	/**
	* This method is used for get instance from Connect class
	* @return Connect This returns instance from Connect class
	*/
    public static synchronized Connect getConnection() {
		/**
		* If the connect is null then:
		*   - Create the instance from Connect class
		*   - Otherwise, just assign the previous instance of this class
		*/
		return connect = (connect == null) ? new Connect() : connect;
    }

    /**
	* This method is used for SELECT SQL statements.
	* @param String This is the query statement
	* @return ResultSet This returns result data from the database
	*/
    public ResultSet executeQuery(String query) {
        ResultSet rs = null;
    	try {
            rs = st.executeQuery(query);
        } catch(Exception e) {
        	e.printStackTrace();
        }
        return rs;
    }

	/**
	* This method is used for INSERT, UPDATE, or DELETE SQL statements.
	* @param String This is the query statement
	*/
    public void executeUpdate(String query) {
    	try {
			st.executeUpdate(query);
			System.out.println(st);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
	/**
	* This method is used for SELECT, INSERT, UPDATE, or DELETE SQL statements using prepare statement.
	* @param String This is the query statement
	*/
    public PreparedStatement prepareStatement(String query) {
    	PreparedStatement ps = null;
    	try {
			ps = con.prepareStatement(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return ps;
    }
    
    //validate login
    public boolean valdiateLogin(String username, String password, String userrole)
    {
    	try {
			PreparedStatement ps = con.prepareStatement(SELECT_QUERY);
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, userrole);
			
			System.out.println(ps);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
			{
				return true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return false;
    }
    
    public Integer getIdUsers(String username, String password)
    {
    	String Query = "SELECT UserId FROM user WHERE UserName = ? AND UserPassword = ?";
    	Integer id;
    	
    	try {
			PreparedStatement ps = con.prepareStatement(Query);
			ps.setString(1, username);
			ps.setString(2, password);
			
			System.out.println(ps);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
			{
				id = rs.getInt("UserId");
				return id;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        return null;
    }
    
    public String getUserRoles(String username, String password) {
    	try {
			PreparedStatement ps = con.prepareStatement(role_query);
			ps.setString(1, username);
			ps.setString(2, password);
			
			System.out.println(ps);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				String roles = rs.getString("UserRole");
				return roles;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return null;
    }
    

    
    
}
