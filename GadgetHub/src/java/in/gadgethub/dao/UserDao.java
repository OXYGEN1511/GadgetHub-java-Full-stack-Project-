/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao;

import in.gadgethub.pojo.UserPojo;

/**
 *
 * @author Asus
 */
public interface UserDao {
    String registerUser(UserPojo user);
    boolean isRegistered(String emailId);
    String isValidCredentials(String emailId, String password);
    UserPojo getUserDetails(String emailId);
    String getUserFirstName(String emailId);
    String getUserAddr(String emailId);
    
    
}
