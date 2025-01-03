
package in.gadgethub.dao.impl;

import in.gadgethub.dao.TransactionDao;
import in.gadgethub.pojo.transaction;
import in.gadgethub.utility.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionDaoImpl implements TransactionDao {
    
    public String getUserId(String transId) {
        String userId="";
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement("Select useremail from transactions where transid=?");
            ps.setString(1,transId);
            rs=ps.executeQuery();
            if(rs.next()){
                userId=rs.getString(1);
            }
        }catch(SQLException ex){
            System.out.println("Error in haveDemanded:"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return userId;
    }
    
    
}
