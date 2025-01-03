/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao.impl;
import in.gadgethub.dao.DemandDao;
import in.gadgethub.pojo.demand;
import in.gadgethub.utility.DBUtil;
import in.gadgethub.utility.IDutil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Asus
 */
public class demandDaoImpl implements DemandDao{

    @Override
    public boolean addProduct(demand demandPojo) {
        boolean status=false;
        String updateSQL="Update userdemand set quantity=quantity+? where useremail=? and prodid=?";
        String insertSQL="Insert into userdemand values(?,?,?)";
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps1=null;
        PreparedStatement ps2=null;
        try{
            ps1=conn.prepareStatement(updateSQL);
            ps1.setInt(1, demandPojo.getDemandQuantity());
            ps1.setString(2, demandPojo.getUseremail());
            ps1.setString(3, demandPojo.getProdid());
            int k=ps1.executeUpdate();
            if(k==0){
                ps2=conn.prepareStatement(insertSQL);
                ps2.setString(1, demandPojo.getUseremail());
                ps2.setString(2, demandPojo.getProdid());
                ps2.setInt(3, demandPojo.getDemandQuantity());
                ps2.executeUpdate();
                
            }
            status=true;            
        }catch(SQLException ex){
            System.out.println("Error in addProduct:"+ex);
            ex.printStackTrace();
        } 
        DBUtil.closeStatement(ps1);
        DBUtil.closeStatement(ps2);
        return status;
        
    }
  
    public boolean removeProduct(String userId, String prodId) {
        boolean result=false;
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        try{
            ps=conn.prepareStatement("delete from userdemand where useremail=? and prodid=?");
            ps.setString(1,userId);
            ps.setString(2,prodId);
            result=ps.executeUpdate()>0;
        }catch(SQLException ex){
            System.out.println("Error in removeProduct:"+ex);
            ex.printStackTrace();
        } 
        DBUtil.closeStatement(ps);
         return result;
    }
    @Override
    public List<demand> haveDemanded(String prodId) {
        List <demand> demandList = new ArrayList<>();
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement("select * from userdemand where prodid=?");
            ps.setString(1,prodId);
            rs=ps.executeQuery();
            while(rs.next()){
                demand demandPojo=new demand();
                demandPojo.setUseremail(rs.getString("useremail"));
                demandPojo.setProdid(rs.getString("prodid"));
                demandPojo.setDemandQuantity(rs.getInt("quantity"));
                demandList.add(demandPojo);                
            }            
        }catch(SQLException ex){
            System.out.println("Error in haveDemanded:"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return demandList;
    }
    

}