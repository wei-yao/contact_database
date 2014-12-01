  
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
  
//  //��̬�����  
//  static{  
//  
//      //��ʼ�������ļ�context  
//      try {  
//          Context context=new InitialContext();  
//          source=(DataSource)context.lookup("java:comp/env/jdbc/webmessage");  
//      } catch (Exception e) {  
//          // TODO Auto-generated catch block  
//          e.printStackTrace();  
//      }  
//  
//  
//  }  
  
        /**  
         * ��ȡ���ݿ�����  
         */  
        public Connection getCon(){  
            try {  
                Class.forName(DRIVER);  
            } catch (ClassNotFoundException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
            try {  
                con=DriverManager.getConnection(URL,USER,PWD);  
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
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
//          // TODO Auto-generated catch block  
//          e.printStackTrace();  
//      }  
//  
//      return con;  
//  }  
  
  
    /**  
     * �ر�������Դ  
     */  
    public void closeAll(){  
        if(rs!=null)  
            try {  
                rs.close();  
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
            if(ps!=null)  
                try {  
                    ps.close();  
                } catch (SQLException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
                if(con!=null)  
                    try {  
                        con.close();  
                    } catch (SQLException e) {  
                        e.printStackTrace();  
                    }  
  
  
    }  
    /**  
     * @param sql���ݿ����(����ɾ����) ���      
     * @param pras�����б��ɴ����ɲ���������ΪNULL����������ʽ���ڣ�  
     * @return ������Ӱ�춼����  
     */  
    public int update(String sql,String... pras){  
        int resu=0;  
        con=getCon();  
        try {  
            ps=con.prepareStatement(sql);  
            for(int i=0;i<pras.length;i++){  
                ps.setString(i+1,pras[i]);  
            }  
            resu=ps.executeUpdate();  
        } catch (SQLException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        finally{  
            closeAll();  
        }  
        return resu;  
    }  
  
    /**  
     * @param sql���ݿ��ѯ���  
     * @param pras�����б��ɴ����ɲ���������ΪNULL����������ʽ���ڣ�  
     * @return ���ؽ����  
     */  
    public ResultSet query(String sql,String... pras){  
        con=getCon();  
        try {  
            ps=con.prepareStatement(sql);  
  
            if(pras!=null)  
                for(int i=0;i<pras.length;i++){  
                    ps.setString(i+1, pras[i]);  
                }  
            rs=ps.executeQuery();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
        return rs;  
    }  
  
  
}  