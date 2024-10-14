
package in.gadgethub.pojo;


public class CartPojo {
    
    private String useremail;
    private String prodid;
    private int quantity;
    
    public CartPojo() {
         
    }

    public CartPojo(String useremail, String prodid, int quantity) {
        this.useremail = useremail;
        this.prodid = prodid;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
