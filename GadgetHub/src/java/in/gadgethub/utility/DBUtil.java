/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Asus
 */
public class DBUtil {
    
    private static Connection conn;
    public static void openConnection(String dbUrl,String username, String password){
        if(conn==null){
            try{
                conn = DriverManager.getConnection(dbUrl,username,password);
                System.out.println("Connection Opened!");
            }catch(SQLException ex){
                System.out.println("Error in closing");
                ex.printStackTrace();
            }
        }
    }
    
    public static void closeConnection(Statement st){
        if(st!=null){
            try{
                conn.close();
                System.out.println("Connection closed");
            }catch(SQLException ex){
                System.out.println("Error in closing");
                ex.printStackTrace();
            }
        }
    }
    
    public static Connection provideConnection(){
        return conn;
        }
    
     public static void closeResultSet(ResultSet rs){
        if(rs!=null){
            try{
                rs.close();
                System.out.println("Connection closed");
            }catch(SQLException ex){
                System.out.println("Error in closing");
                ex.printStackTrace();
            }
        }
    }

    public static void closeStatement(Statement st) {
        if(st!=null){
            try{
                st.close();
            }catch(SQLException ex){
                System.out.println("Error in closing");
                ex.printStackTrace();
            }
        }
        //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}
