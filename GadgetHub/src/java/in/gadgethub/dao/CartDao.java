
package in.gadgethub.dao;
import in.gadgethub.pojo.CartPojo;
import java.util.List;

//The CartDao interface in your project serves as a contract for data access
//operations related to the cart. Its purpose is to define the operations (methods)
//that any implementing class must provide without dictating how those operations should be implemented.


public interface CartDao {
//    Adds a product to the cart for the specified user.
    public String addProductToCart(CartPojo cart);
    
//    Updates the quantity of a product in the cart.
    public String updateProductInCart(CartPojo cart);
    
//    Retrieves all cart items for a given user.
    public List<CartPojo> getAllCartItems(String userId);
    
//    Gets the quantity of a specific product in the cart for the given user
    public int getCartItemsCount(String userId, String itemId);
    
//    Removes all quantities of a product from the cart for the specified user.
    public String removeProductFromCart(String userId, String prodId);
    
//    Removes only one instance (or specific quantity) of a product from the cart.
    public Boolean removeAProduct(String userId, String prodId);
    
    
//    An interface defines a contract that implementing classes must follow.
//    It contains method signatures without implementations.
//This allows flexibility to change the underlying implementation (e.g., switching databases)
//    without changing the method signatures.
    
    
    
}
