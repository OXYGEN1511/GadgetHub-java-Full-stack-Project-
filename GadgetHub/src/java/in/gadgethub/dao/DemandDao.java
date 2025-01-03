/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao;
import in.gadgethub.pojo.demand;
import java.util.List;

/**
 *
 * @author Asus
 */
public interface DemandDao {
    public boolean addProduct(demand demandPojo);

    public boolean removeProduct(String userId, String prodId);

     public List<demand>haveDemanded(String prodId);
}
