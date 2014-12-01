  
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
    //数据库驱动对象  
    public static final String DRIVER="oracle.jdbc.driver.OracleDriver";  
    //数据库连接地址(数据库名)  
    public static final String URL="jdbc:oracle:thin:@localhost:1521:orcl";  
    //登陆名  
    public static final String USER="SCOTT";  
    //登陆密码  
    public static final String PWD="123456";  
    //创建数据库连接对象  
    private Connection con=null;  
    //创建数据库预编译对象  
    private PreparedStatement ps=null;  
    //创建结果集  
    private ResultSet rs=null;  
    //创建数据源对象  
    public static DataSource source=null;  
  
//  //静态代码块  
//  static{  
//  
//      //初始化配置文件context  
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
         * 获取数据库连接  
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
//   * 获取数据库连接  
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
     * 关闭所有资源  
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
     * @param sql数据库更新(增、删、改) 语句      
     * @param pras参数列表（可传，可不传，不传为NULL，以数组形式存在）  
     * @return 返回受影响都行数  
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
     * @param sql数据库查询语句  
     * @param pras参数列表（可传，可不传，不传为NULL，以数组形式存在）  
     * @return 返回结果集  
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