/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao.impl;

import in.gadgethub.dao.DemandDao;
import in.gadgethub.pojo.DemandPojo;
import in.gadgethub.pojo.demand;
import in.gadgethub.utility.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class DemandImpl implements DemandDao{
    public Boolean addProduct(DemandPojo demandPojo){
         boolean status=false;
        String updateSQl = "update userdemand set useremail=? and prodid=? and quantity=quantity+?";
        String insertSQl="insert into userdemand values(?,?,?)";
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps1 = null;
        PreparedStatement ps2=null;
        try {
            ps1 = conn.prepareStatement(updateSQl);
            ps1.setString(1, demandPojo.getUseremail());
            ps1.setString(2, demandPojo.getProdId());
            ps1.setInt(3, demandPojo.getDemandQuntity() );
            int count = ps1.executeUpdate();
            if (count == 0) {
                ps2=conn.prepareStatement(insertSQl);
                ps2.setString(1,demandPojo.getUseremail());
                ps2.setString(2,demandPojo.getProdId());
                ps2.setInt(3, demandPojo.getDemandQuntity() );
                ps2.executeUpdate();
            }
            status=true;
        } catch (SQLException ex) {
            System.out.println("Exception occured in addProduct method"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps1);
        DBUtil.closeStatement(ps2);
        return status;
    }
    public Boolean removeProduct(String userId, String ProdId){
       boolean status=false;
       Connection conn=DBUtil.provideConnection();
       PreparedStatement ps=null;
       try{
           ps=conn.prepareStatement("delete from userdemand where useremail=? and prodId=?");
           ps.setString(1, userId);
           ps.setString(2, ProdId);
           status=ps.executeUpdate()>0;
           
       }catch (SQLException ex) {
            System.out.println("Exception occured in removeProduct method"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status; 
    }
    public List<DemandPojo> haveDemanded(String prodId){
      List<DemandPojo> demandList = new ArrayList<>();
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps=conn.prepareStatement("select * from userdemand where prodid=?");
            ps.setString(1, prodId);
            rs=ps.executeQuery();
            while (rs.next()) {
                DemandPojo demandPojo = new DemandPojo();
                demandPojo.setUseremail(rs.getString("useremail"));
                demandPojo.setProdId(rs.getString("prodid"));
                demandPojo.setDemandQuntity(rs.getInt("quantity"));
                demandList.add(demandPojo);
            }
        } catch (SQLException ex) {
            System.out.println("Exception occured in haveDemanded method............................"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultset(rs);
        DBUtil.closeStatement(ps);
        return demandList;  
    }

    @Override
    public boolean addProduct(demand demandPojo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
