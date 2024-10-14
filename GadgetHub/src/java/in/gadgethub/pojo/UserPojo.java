/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.pojo;

/**
 *
 * @author Asus
 */
public class UserPojo {
    
    private String username;
    private String useremail;
    private String mobile;
    private String address;
    private int pincode;

    public UserPojo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    private String password;

    public UserPojo(String username, String useremail, String mobile, String address, String password) {
        this.username = username;
        this.useremail = useremail;
        this.mobile = mobile;
        this.address = address;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getUseremail() {
        return useremail;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public void setUserAddress(String address) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int pincode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void getPincode(int aInt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void getPassword(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
