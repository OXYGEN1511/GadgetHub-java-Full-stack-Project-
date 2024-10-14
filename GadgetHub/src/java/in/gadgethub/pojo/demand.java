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
public class demand {
    private String useremail;
    private String prodid;
    private int demandQuantity;
    
    
    

    @Override
    public String toString() {
        return "demand{" + "useremail=" + useremail + ", prodid=" + prodid + ", demandQuantity=" + demandQuantity + '}';
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getProdid() {
        return prodid;
    }

    public void setProdid(String prodid) {
        this.prodid = prodid;
    }

    public int getDemandQuantity() {
        return demandQuantity;
    }

    public void setDemandQuantity(int demandQuantity) {
        this.demandQuantity = demandQuantity;
    }

    public demand(String useremail, String prodid, int demandQuantity) {
        this.useremail = useremail;
        this.prodid = prodid;
        this.demandQuantity = demandQuantity;
    }

    public demand() {
    }
    
    
}
