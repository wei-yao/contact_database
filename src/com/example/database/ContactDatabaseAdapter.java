package com.example.database;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 用于联系人和数据库的操作.
 * 
 * @author zhaolu
 * 
 */
public class ContactDatabaseAdapter {

	public abstract static class ContactEntry {
		public static final String TABLE_NAME = "CONTACTS";
		public static final String _ID = "ID";
		public static final String NAME = "NAME";
		public static final String PHONE = "PHONE";
		private static final String CREATE_TABLE = "create table "
				+ ContactEntry.TABLE_NAME + " ( " + ContactEntry._ID
				+ " NUMBER(7) PRIMARY KEY, " + ContactEntry.NAME
				+ " VARCHAR2(20) not null, " + ContactEntry.PHONE
				+ " VARCHAR2(30) not null ) ";
		private static final String SEQ_NAME = "SEQ_CONTACTS";
	}
	/**
	 * 删除所有的表和数据.
	 */
	private static void deleteAll(){
		deleteAllTable();
		deleteAllSequences();
	}
	private static void deleteAllSequences() {
		String sql1="DROP SEQUENCE "+ContactEntry.SEQ_NAME;
		String sql2="DROP SEQUENCE "+GroupEntry.SEQ_NAME;
		String sql3="DROP SEQUENCE "+ContactGroupEntry.SEQ_NAME;
		try {
			DBCon.getInstance().update(sql1, null);
			DBCon.getInstance().update(sql2, null);
			DBCon.getInstance().update(sql3,null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private static void deleteAllTable() {
		String sql1="drop table  "+ContactEntry.TABLE_NAME;
		String sql2="drop table "+GroupEntry.TABLE_NAME;
		String sql3="drop table "+ContactGroupEntry.TABLE_NAME;
		try {
			DBCon.getInstance().update(sql3, null);
			DBCon.getInstance().update(sql2, null);
			DBCon.getInstance().update(sql1,null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public abstract static class GroupEntry {
		public static final String TABLE_NAME = "GROUP_TABLE";
		public static final String _ID = "ID";
		public static final String NAME = "NAME";
		private static final String CREATE_TABLE = "create table " + TABLE_NAME
				+ " (" + _ID + " NUMBER(7) PRIMARY KEY," + NAME
				+ " VARCHAR2(20) not null, CONSTRAINT name_unique UNIQUE ("+NAME+") )";
		private static final String SEQ_NAME = "SEQ_GROUP";
	}

	public abstract static class ContactGroupEntry {
		public static final String TABLE_NAME = "CONTACTS_GROUP_TABLE";
		public static final String _ID = "ID";
		public static final String USER_ID = "USER_ID";
		public static final String GROUP_ID = "GROUP_ID";
		public static final String ROLE = "ROLE";
		// private static final String CREATE_TABLE = "create table " +
		// TABLE_NAME
		// + " (" + _ID + " NUMBER(7) PRIMARY KEY," + USER_ID
		// + " NUMBER(5) FOREIGN KEY (" + ContactEntry._ID
		// + ") REFERENCES " + ContactEntry.TABLE_NAME + "("
		// + ContactEntry._ID + ")," + GROUP_ID
		// + " NUMBER(5) FOREIGN KEY (" + GroupEntry._ID + ") REFERENCES "
		// + GroupEntry.TABLE_NAME + "(" + GroupEntry._ID + ")," + ROLE
		// + " varchar2(20))";
		private static final String CREATE_TABLE = "create table " + TABLE_NAME
				+ " (" + _ID + " NUMBER(7) PRIMARY KEY," + USER_ID
				+ " NUMBER(7) , " + GROUP_ID + " NUMBER(7) , " + ROLE
				+ " varchar2(20)," + " FOREIGN KEY (" + USER_ID + ")"
				+ " REFERENCES " + ContactEntry.TABLE_NAME + " ("
				+ ContactEntry._ID + " ), FOREIGN KEY (" + GROUP_ID
				+ ") REFERENCES " + GroupEntry.TABLE_NAME + " ( "
				+ GroupEntry._ID + " ))";
		private static final String SEQ_NAME = "SEQ_CONTACTS_GROUP";
	}

	private static void createTableIfnotExists(String tableName, String sql)
			throws SQLException {
		if (!DbOperate.isTableExists(tableName)) {
			// String sql = "create table " + ContactEntry.TABLE_NAME + " ("
			// + ContactEntry._ID + " NUMBER(5) PRIMARY KEY, "
			// + ContactEntry.NAME + " VARCHAR2(20), "
			// + ContactEntry.PHONE + " VARCHAR2(30 )";
			DBCon.getInstance().update(sql, null);
		}
	}
/**
 *  删除旧表,初始化表和数据.
 * @throws SQLException
 */
	public static void setUp() throws SQLException {
		deleteAll();
		createTables();
		createAllSequence();
		initializeData();

	}

	private static void createSequence(String name) throws SQLException {
		if (!DbOperate.isSequenceExists(name)) {
			String sql = "CREATE SEQUENCE " + name
					+ " MINVALUE 1 START WITH 1 INCREMENT BY 1  CACHE 10";
			DBCon.getInstance().update(sql, null);
		}
	}

	private static void createAllSequence() throws SQLException {
		createSequence(ContactEntry.SEQ_NAME);
		createSequence(GroupEntry.SEQ_NAME);
		createSequence(ContactGroupEntry.SEQ_NAME);
	}

	private static void createTables() throws SQLException {
		createContactsTable();
		createGroupTable();
		createContactsGroupTable();
	}

	private static void createContactsGroupTable() throws SQLException {
		createTableIfnotExists(ContactGroupEntry.TABLE_NAME,
				ContactGroupEntry.CREATE_TABLE);
	}

	private static void createGroupTable() throws SQLException {
		createTableIfnotExists(GroupEntry.TABLE_NAME, GroupEntry.CREATE_TABLE);
	}

	/**
	 * 返回了插入条目的id,失败返回-1
	 * 
	 * @param name
	 * @param phone
	 * @return
	 * @throws SQLException
	 */
	public static long insertContact(String name, String phone)
			throws SQLException {
		String sql = "insert into " + ContactEntry.TABLE_NAME + " ("
				+ ContactEntry._ID + " , " + ContactEntry.NAME + ","
				+ ContactEntry.PHONE + ") values ( " + ContactEntry.SEQ_NAME
				+ ".nextval, " + "?,?)";
		String[] params = new String[] { name, phone };
		long ret = -1;
		if (DBCon.getInstance().update(sql, params) > 0) {
			ret = DbOperate.getCurrSeq(ContactEntry.SEQ_NAME);
		}
		return ret;
	}

	public static long insertGroup(String name) throws SQLException {
		String sql = "insert into " + GroupEntry.TABLE_NAME + " ("
				+ GroupEntry._ID + "," + GroupEntry.NAME + ") values ( "
				+ GroupEntry.SEQ_NAME + ".nextval ,?)";
		String[] params = new String[] { name };
		long ret = -1;
		if (DBCon.getInstance().update(sql, params) > 0) {
			ret = DbOperate.getCurrSeq(GroupEntry.SEQ_NAME);
		}
		return ret;
	}
	/**
	 * 修改联系人
	 * @param contacter 联系人结构体，只修改值不为null的域.
	 * @throws SQLException 
	 */
	public static void updateContacter(Contacter contacter) throws SQLException{
		ArrayList<String> columns=new ArrayList<String>();
		if(contacter.name!=null)
//			columns.add(ContactEntry.NAME);
		{
			updateName(contacter);
		}
			if(contacter.phone!=null)
			{
				updatePhone(contacter);
			}
			if(contacter.groupList!=null){
				updateContactGroup(contacter);
			}
//			columns.add(ContactEntry.PHONE);
			{
				
			}
				//		if(!columns.isEmpty())
//		{
//			String sql="update "+ContactEntry.TABLE_NAME+" set "
//		}
	}
	private static void updateContactGroup(Contacter contacter) throws SQLException {
//		ArrayList<Long> originGroupIds=getGroupsId(contacter.id);
		deleteOldRelations(contacter.id);
		for(long groupId:contacter.groupIdList){
//			if(originGroupIds.contains(groupId)){
//				updateContactGroup(contacter.id,groupId);
//			}else{
				insertContactGroup(contacter.id, groupId);
//			}
		}
	}
	private static void deleteOldRelations(long id) throws SQLException {
		String sql="delete from "+ContactGroupEntry.TABLE_NAME+" where "+ContactGroupEntry.USER_ID+"=?";
		DBCon.getInstance().update(sql, id+"");
	}
	public static void deleteContacter(long id) throws SQLException{
		deleteOldRelations(id);
		deleteContactRows(id);
		
	}
	private static void deleteContactRows(long id) throws SQLException {
		String sql="delete from "+ContactEntry.TABLE_NAME+" where "+ContactEntry._ID+"=?";
		DBCon.getInstance().update(sql, id+"");
	}
	private static void updatePhone(Contacter contacter) throws SQLException {
		String sql="update "+ContactEntry.TABLE_NAME+" set "+ContactEntry.PHONE+"=? where "+ContactEntry._ID+"=?";
		DBCon.getInstance().update(sql, new String[]{contacter.phone,contacter.id+""});
	}
	private static void updateName(Contacter contacter) throws SQLException {
		String sql="update "+ContactEntry.TABLE_NAME+" set "+ContactEntry.NAME+"=? where "+ContactEntry._ID+"=?";
		DBCon.getInstance().update(sql, new String[]{contacter.name,contacter.id+""});
	}
	/**
	 * 插入联系人 群组关系表.
	 * 
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	private static long insertContactGroup(long userId, long groupId)
			throws SQLException {
		String sql = "insert into " + ContactGroupEntry.TABLE_NAME + " ("
				+ ContactGroupEntry._ID + "," + ContactGroupEntry.USER_ID
				+ ", " + ContactGroupEntry.GROUP_ID + ") values ( "
				+ ContactGroupEntry.SEQ_NAME + ".nextval ,?,?)";
		String[] params = new String[] { userId + "", groupId + "" };
		long ret = -1;
		if (DBCon.getInstance().update(sql, params) > 0) {
			ret = DbOperate.getCurrSeq(ContactGroupEntry.SEQ_NAME);
		}
		return ret;
	}
	/**
	 * 初始化插入数据.
	 * @throws SQLException 
	 */
	private static void initializeData() throws SQLException{
		String[] groupNames =new String[]{"同学","实验室","同乡"};
		ArrayList<Long> groupIds=new ArrayList<Long>();
		for(String groupName:groupNames){
			groupIds.add(insertGroup(groupName));
		}
		insertContact("小强", "18400001234", new long[]{groupIds.get(0),groupIds.get(1)});
		insertContact("小红", "13400005875", new long[]{groupIds.get(0),groupIds.get(1)});
		insertContact("小明", "18700007584", new long[]{groupIds.get(0),groupIds.get(2)});
		insertContact("小刚", "13700003785", new long[]{groupIds.get(0),groupIds.get(2)});
	}

	public static ArrayList<Contacter> getContacterList() throws SQLException {
		ResultSet rs = null;
		String name;
		String phone;
		long userId;
		ArrayList<Contacter> ret=new ArrayList<Contacter>();
		try {
			rs = getContacters();
			while (rs.next()) {
				name = rs.getString(ContactEntry.NAME);
				phone = rs.getString(ContactEntry.PHONE);
				userId = rs.getLong(ContactEntry._ID);
				ArrayList<String> groups = getCroups(userId);
				ret.add(new Contacter(name, phone, groups,userId));
			}
		} finally {
			if (rs != null)
				rs.close();
		}
		return ret;
	}

	private static ArrayList<String> getCroups(long userId) throws SQLException {
		ArrayList<Long> groupIds = getGroupsId(userId);
		ArrayList<String >ret=new ArrayList<String>();
		for(long id:groupIds){
			ret.add(getGroupName(id));
		}
		return ret;
	}

	private static String getGroupName(long id) throws SQLException {
		String ret="";
		String sql="select "+GroupEntry.NAME+" from "+GroupEntry.TABLE_NAME+" where "+GroupEntry._ID+"=?";
		ResultSet rs=null;
		try{
			rs=DBCon.getInstance().query(sql, id+"");
			if(rs.next()){
				ret=rs.getString(1);
			}
		}finally{
			if(rs!=null)
			rs.close();
		}
		return ret;
	}

	private static ArrayList<Long> getGroupsId(long userId) throws SQLException {
		ArrayList<Long> ret = new ArrayList<Long>();
		String sql = "select " + ContactGroupEntry.GROUP_ID + " from "
				+ ContactGroupEntry.TABLE_NAME + " where "
				+ ContactGroupEntry.USER_ID + "=?";
		ResultSet rs = null;
		try {
			rs = DBCon.getInstance().query(sql, userId + "");
			while (rs.next()) {
				ret.add(rs.getLong(1));
			}
			return ret;
		} finally {
			if (rs != null)
				rs.close();
		}
	}

	private static ResultSet getContacters() throws SQLException {
		String sql = "select * from " + ContactEntry.TABLE_NAME;
		return DBCon.getInstance().query(sql, null);
	}

	/**
	 * 插入新联系人,附带组信息. group
	 * 
	 * @param name
	 * @param phone
	 * @param groupId
	 *            组id的数组. 假设输入合法.
	 * @return
	 * @throws SQLException
	 */
	public static void insertContact(String name, String phone, long[] groupId)
			throws SQLException {
		if (groupId == null || groupId.length == 0)
			return;
		// 插入联系人，获取联系人id，并更新contactgroup表.
		// 以后再加trasaction
		long id = insertContact(name, phone);
		if (id != -1) {
			for (long tmp : groupId)
				insertContactGroup(id, tmp);
		}
	}

	private static void createContactsTable() throws SQLException {
		createTableIfnotExists(ContactEntry.TABLE_NAME,
				ContactEntry.CREATE_TABLE);
	}
}
