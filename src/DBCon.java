  
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
  
import javax.naming.Context;  
import javax.naming.InitialContext;  
import javax.naming.NamingException;  
import javax.sql.DataSource;  
  
public class DBCon {  
    //���ݿ���������  
    public static final String DRIVER="oracle.jdbc.driver.OracleDriver";  
    //���ݿ����ӵ�ַ(���ݿ���)  
    public static final String URL="jdbc:oracle:thin:@localhost:1521:orcl";  
    //��½��  
    public static final String USER="SCOTT";  
    //��½����  
    public static final String PWD="123456";  
    //�������ݿ����Ӷ���  
    private Connection con=null;  
    //�������ݿ�Ԥ�������  
    private PreparedStatement ps=null;  
    //���������  
    private ResultSet rs=null;  
    //��������Դ����  
    public static DataSource source=null;  
//    private static Object lock=new Object();
    private static DBCon dbCon;
    private DBCon(){
    	con=getCon();
    }
    public static DBCon getInstance(){
    	if(dbCon==null){
    		dbCon=new DBCon();
    	}
    	return dbCon;
    }
    
        /**  
         * ��ȡ���ݿ�����  
         */  
        public Connection getCon(){  
            try {  
                Class.forName(DRIVER);  
            } catch (ClassNotFoundException e) {  
                  
                e.printStackTrace();  
            }  
            try {  
                con=DriverManager.getConnection(URL,USER,PWD);  
            } catch (SQLException e) {  
                  
                e.printStackTrace();  
            }  
              
            return con;  
        }  
//  /**  
//   * ��ȡ���ݿ�����  
//   */  
//  public Connection getCon(){  
//  
//      try {  
//          con=source.getConnection();  
//      } catch (SQLException e) {  
//            
//          e.printStackTrace();  
//      }  
//  
//      return con;  
//  }  
  
  //��ѯ�Ļ�ֻ�ر���rs
    /**  
     * �ر�������Դ  
     */  
    public void closeRes(){  
        if(rs!=null)  
            try {  
                rs.close();  
            } catch (SQLException e) {  
                  
                e.printStackTrace();  
            }  
            if(ps!=null)  
                try {  
                    ps.close();  
                } catch (SQLException e) {  
                      
                    e.printStackTrace();  
                }  
//                if(con!=null)  
//                    try {  
//                        con.close();  
//                    } catch (SQLException e) {  
//                        e.printStackTrace();  
//                    }  
  
  
    }  
    public void closeAll(){
    	closeRes();
    	   if(con!=null)  
             try {  
                 con.close();  
             } catch (SQLException e) {  
                 e.printStackTrace();  
             }  
    	  dbCon=null;
    }
//   public void closeConnectionSafely(){
//	   if(con)
//   }
    /**  
     * @param sql���ݿ����(����ɾ����) ���      
     * @param pras�����б��ɴ����ɲ���������ΪNULL����������ʽ���ڣ�  
     * @return ������Ӱ�춼����  
     * @throws SQLException 
     */  
    public int update(String sql,String... pras) throws SQLException{  
        int resu=0;  
//         con =getCon();  
        try {  
            ps=con.prepareStatement(sql);  
            if(null!=pras){
            for(int i=0;i<pras.length;i++){  
                ps.setString(i+1,pras[i]);  
            }  
            }
            resu=ps.executeUpdate();
        } 
//        catch (SQLException e) {  
//              
//            e.printStackTrace();  
//        }  
        finally{  
            closeRes();  
        }  
        return resu;  
    }  
  
    /**  
     * @param sql���ݿ��ѯ���  
     * @param pras�����б��ɴ����ɲ���������ΪNULL����������ʽ���ڣ�  
     * @return ���ؽ����  
     * @throws SQLException 
     */  
    public ResultSet query(String sql,String... pras) throws SQLException{  
//    	con=getCon();  
//        try {  
            ps=con.prepareStatement(sql);  
  
            if(pras!=null)  
                for(int i=0;i<pras.length;i++){  
                    ps.setString(i+1, pras[i]);  
                }  
            rs=ps.executeQuery();  
//        }finally{
//        	closeAll();
//        } 
//        catch (SQLException e) {  
//            e.printStackTrace();  
//        }  
        return rs;  
    }  
  
  
}  