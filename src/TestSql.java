import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class TestSql {
	public static void main(String[] args) throws SQLException {
		DBCon dbCon = new DBCon();
		String sql = "select * from tab";

		ResultSet rs = dbCon.query(sql, null);
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
//			rs.beforeFirst();
			final int columnCount = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <=columnCount; i++) {
					System.out.print(rsmd.getColumnName(i) + " "
							+ rs.getString(i)+" ");
				}
				System.out.println("");
			}
		} finally {
			rs.close();
			dbCon.closeAll();
		}
	}
}
