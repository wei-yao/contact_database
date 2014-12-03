package com.example.database;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class TestSql {
	private static void testCreateTable() throws SQLException {
		System.out.println("is table exists :"
				+ DbOperate.isTableExists("testTable"));
		String sql = "CREATE TABLE   testTable (a number,b varchar(20))";
		DBCon.getInstance().update(sql, null);
	}

	/**
	 * 证明数字也能以string类型的参数插入.
	 * 
	 * @throws SQLException
	 */
	private static void testInsert() throws SQLException {
		String sql = "insert into   testTable (a  ,b ) values (?,?)";
		String[] params = new String[] { 123 + "", "hello" };
		DBCon.getInstance().update(sql, params);
	}

	public static void main(String[] args) throws SQLException {
		// testSelect();
		// testCreateTable();
		// testSelect();
		// testInsert();
//		ContactDatabaseAdapter.insertContactGroup(2, 2);
//		ContactDatabaseAdapter.deleteAll();
		ContactDatabaseAdapter.setUp();
		System.out.println("origin");
		showContacters();
//		ContactDatabaseAdapter.updateContacter(new Contacter("123456789", "小花", null, 6));
//		showContacters();
		ContactDatabaseAdapter.deleteContacter(2);
		System.out.println("\n\n");
		showContacters();
//	
//		System.out.println("hello");
	}

	private static void showContacters() {
		try {
			ArrayList<Contacter> contacterList = ContactDatabaseAdapter
					.getContacterList();
			for (Contacter c : contacterList) {
				System.out.print(c.id+" "+c.name + " " + c.phone + "  "
						);
				for(String name:c.groupList){
					System.out.print(name+"\t");
				}
				System.out.println("");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("获取联系人失败");
		}

	}

	private static void testSelect() throws SQLException {
		String sql = "select * from tab";
		// String
		// sql="create table if not exsits testTable(a number,b varchar(20));";
		ResultSet rs = DBCon.getInstance().query(sql, null);
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			// rs.beforeFirst();
			final int columnCount = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					System.out.print(rsmd.getColumnName(i) + " "
							+ rs.getString(i) + " ");
				}
				System.out.println("");
			}
		} finally {
			if (rs != null)
				rs.close();
		}

	}
}
