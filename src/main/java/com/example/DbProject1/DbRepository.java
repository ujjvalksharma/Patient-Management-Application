package com.example.DbProject1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Value;

import redis.clients.jedis.Jedis;

/**
 * This is database repository which will construct the database object for Mysql and Redis.
 *  A single object is constructed by the class to reduce the connection load 
 *  and connection leak. To implement this we are using singleton design pattern.
 *
 */
public class DbRepository {

	
	private static String mySqlDatabaseName="DbProject";  // mysql database name
	private static String mySqlPort="3306";  // mysql port number
	private static String mySqlHost="localhost"; //  mysql host name
	private static   int redisPort=6379; // redis port number
	private static 	String redisHost="localhost"; //  redis host name
	private static 	String mysqlUser="root";  // mysql username
	private static 	String mysqlPass="ujjval123"; // mysql password
	private static Connection con = null; 
	 private static Jedis jedis =null;
	 private static	Statement statement=null;
	 
	 private static String url = "jdbc:mysql://"+mySqlHost+":"+mySqlPort+"/"+mySqlDatabaseName; 	

	    static
	    { 

	        try { 
	        	
				con = DriverManager.getConnection(url, mysqlUser, mysqlPass); 
	        
				jedis = new Jedis(redisHost,redisPort); 
	        	   statement = con.createStatement(); 
	            Class.forName("com.mysql.jdbc.Driver"); 
	            
	        } 
	        catch (ClassNotFoundException | SQLException e) { 
	            e.printStackTrace(); 
	        } 
	    } 
	    /**
	     * This returns the mysql connection object.
	     * @return mysql connection object
	     */
	    public static Connection getMySQlConn() 
	    { 
	        return con; 
	    } 
	    /**
	     * This returns Redis connection object.
	     * @return redis connection object
	     */
	    public static Jedis getRedisConn() 
	    { 
	        return jedis; 
	    } 
	    /**
	     * This returns the mysql connection object's statement to write queries.
	     * @return mysql connection object's statement
	     */
	    public static Statement getMySqlStatement() 
	    { 
	        return statement; 
	    } 
}
