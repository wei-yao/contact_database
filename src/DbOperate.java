import java.sql.ResultSet;
import java.sql.SQLException;



public class DbOperate {
	public static boolean isTableExists(String tableName) throws SQLException{
		if(tableName==null||"".equals(tableName))
			return false;
		ResultSet rs=null;
		try{
		String sql="select * from tab where TNAME = ?  and TABTYPE= 'TABLE'";
		rs=DBCon.getInstance().query(sql, tableName);
		return rs.next();
		}finally{
			if(rs!=null)
				rs.close();
		}
		}
	public static boolean isSequenceExists(String name) throws SQLException{
		if(name==null||"".equals(name))
			return false;
		ResultSet rs=null;
		try{
		String sql="select * from ALL_SEQUENCES where SEQUENCE_NAME = ?  ";
		rs=DBCon.getInstance().query(sql, name);
		return rs.next();
		}finally{
			if(rs!=null)
				rs.close();
		}
		
	}
	public static long getCurrSeq(String name) throws SQLException{
//		String sql="select last_number from user_sequences where sequence_name=?";
		String sql="select  "+name+".CURRVAL from DUAL";
		ResultSet rs=null;
		long ret=0;
		try{
		rs=DBCon.getInstance().query(sql,null);
		if(rs.next()){
			ret=rs.getLong(1);
		}
		}finally{
			if(rs!=null)
				rs.close();
		}
		return ret;
	}
}
