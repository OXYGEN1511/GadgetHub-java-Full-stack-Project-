package in.gadgethub.dao.impl;

import in.gadgethub.dao.UserDao;
import in.gadgethub.pojo.UserPojo;
import in.gadgethub.utility.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class UserDAOImpl implements UserDao { 
    
// Interface mein public default hoti hai;
    
    public boolean isRegistered(String emailId){
        Connection conn = DBUtil.provideConnection();
        boolean isEmail= false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try{
            //Connection Strt
          
          String sql = " Select 1 from users where useremail = ? ";
          pstmt = conn.prepareStatement(sql);
          
          pstmt.setString(1,emailId);
          rs = pstmt.executeQuery();
          if(rs.next()){
              isEmail = true;
              
          }
        }
        catch(SQLException e){
        e.printStackTrace();
        }
          DBUtil.closeStatement(pstmt);
          DBUtil.closeResultSet(rs);
          
           return isEmail;
    
    }

    @Override
    public String registerUser(UserPojo user) {
         String status = "Register failed";
         boolean isUserRegistered = isRegistered(user.getUseremail());
      
         if(isUserRegistered){
             status = "Email Already Register. Try Agai";
             return status;
         }
         
         Connection conn = DBUtil.provideConnection();
        PreparedStatement pstmt = null;
        try{
            pstmt = conn.prepareStatement("Insert into users values (?,?,?,?");
            pstmt.setString(1,user.getUseremail());
               pstmt.setString(2,user.getUsername());
                  pstmt.setString(3,user.getMobile());
                     pstmt.setString(4,user.getAddress());
                        pstmt.setInt(5,user.pincode());
                           pstmt.setString(6,user.getPassword());
                              int count = pstmt.executeUpdate();
                              
                              if(count ==1){
                                  status = "Resistered Succefully";
            }
        }
          catch(SQLException e){
        e.printStackTrace();
        }
          DBUtil.closeStatement(pstmt);
          return status;
         
    }

    @Override
    public String isValidCredentials(String emailId, String password) {
     
         Connection conn = DBUtil.provideConnection();
         ResultSet rs = null;
        PreparedStatement pstmt = null;
         
        String status = "Login Denied.Invalid UserName or Password";
        try{
            pstmt = conn.prepareStatement("select 1 from users where emailId = ? and  password = ?");
            pstmt.setString(1,emailId);
            pstmt.setString(2,password);
            rs  = pstmt.executeQuery();
            if(rs.next()){
                status = "Login Succesful";
            }
            
        }catch(SQLException e){
       status = "Error:"+e.getMessage();
       System.out.println("error in iisValidCrendential:"+e);
       e.printStackTrace();
        }
          DBUtil.closeStatement(pstmt);
          DBUtil.closeResultSet(rs);
          return status;
    }
    
    

    @Override
    public UserPojo getUserDetails(String emailId) {
        UserPojo user = null;
         ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection conn = DBUtil.provideConnection();

try{
    pstmt  = conn.prepareStatement("Select * from users where useremail = ?");
    pstmt.setString(1, emailId);
     rs =  pstmt.executeQuery();
    if(rs.next()){
        user =  new UserPojo();
        user.setUseremail( rs.getString("useremail"));
        user.setUsername(rs.getString("username"));
        user.setUserAddress(rs.getString("address"));
        user.setMobile(rs.getString("mobile"));
        user.getPincode(rs.getInt("pincode"));
        user.getPassword(rs.getString("password"));
   }
    
}catch(SQLException e){
       System.out.println("error in getUserDetail:"+e);
       e.printStackTrace();
        }
          DBUtil.closeStatement(pstmt);
          DBUtil.closeResultSet(rs);
          return user;
    }
         
        
    

    @Override
    public String getUserFirstName(String emailId) {
           String fName =" ";
           ResultSet rs = null;
           PreparedStatement pstmt = null;
            Connection conn = DBUtil.provideConnection();
            try{
                pstmt  = conn.prepareStatement("Select username from users where useremail = ?");
                 pstmt.setString(1, emailId);
                 rs =  pstmt.executeQuery();
                 if(rs.next()){
                     String fullName = rs.getString(1);
                     fName = fullName.split("")[0];
                     
                     }
            }
     catch(SQLException e){
          System.out.println("error in getUserDetail:"+e);
        e.printStackTrace();
        }
          DBUtil.closeStatement(pstmt);
          DBUtil.closeResultSet(rs);
          return fName;
}
    
    
    
//
    @Override
    public String getUserAddr(String emailId) {
        
           String address = null;
           ResultSet rs = null;
           PreparedStatement pstmt = null;
            Connection conn = DBUtil.provideConnection();
            try{
                pstmt  = conn.prepareStatement("Select username from users where useremail = ?");
                 pstmt.setString(1, emailId);
                 rs =  pstmt.executeQuery();
                 if(rs.next()){
                     address = rs.getString(1);
                     
                     
        
    }}
     catch(SQLException e){
          System.out.println("error in getUserDetail:"+e);
        e.printStackTrace();
        }
          DBUtil.closeStatement(pstmt);
          DBUtil.closeResultSet(rs);
          return address;
                                                //To change body of generated methods, choose Tools | Templates.
    }
}
  //Cascade 