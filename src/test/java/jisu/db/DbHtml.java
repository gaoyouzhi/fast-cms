package jisu.db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.jfinal.kit.Kv;
import com.jfinal.template.Engine;

public class DbHtml {
	public static String url = "jdbc:mysql://waiwangout.mysql.rds.aliyuncs.com:3306/cms?useUnicode=true&characterEncoding=UTF-8";
	public static String username = "heyewei";
	public static String password = "wan989610";
	public static String driver = "com.mysql.jdbc.Driver";
	public static String dbname = "cms";

	public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
		Class.forName(driver);
		Connection connection = DriverManager.getConnection(url,username,password);
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("select table_name,table_comment from information_schema.tables where table_schema = '"+dbname+"' order by table_name asc;");
		List<DbTable> tables = new ArrayList<>();
		while(resultSet.next()){
			if(StringUtils.isBlank(resultSet.getString(2))){
				continue;
			}
			List<DbColumn> dbColumns = new ArrayList<>();
			DbTable dbTable = new DbTable();
			dbTable.setTable(resultSet.getString(1));
			dbTable.setComment(resultSet.getString(2));
			
			
			Statement statement2 = connection.createStatement();
			
			ResultSet resultSet2 =  statement2.executeQuery("select column_name,column_type,column_key,is_nullable,column_comment from information_schema.columns where table_schema = '"+dbname+"'  and table_name ='"+resultSet.getString(1)+"'");
			
			while(resultSet2.next()){
				DbColumn dbColumn = new DbColumn();
				dbColumn.setComment(resultSet2.getString(5));
				dbColumn.setIsNullable(resultSet2.getString(4).equals("YES")? "是":"否");
				dbColumn.setKey(resultSet2.getString(3));
				dbColumn.setName(resultSet2.getString(1));
				dbColumn.setType(resultSet2.getString(2));
				
				dbColumns.add(dbColumn);
			}
			dbTable.setColumns(dbColumns);
			tables.add(dbTable);
			
			resultSet2.close();
			statement2.close();
		}
		resultSet.close();
		statement.close();
		connection.close();
		
		Kv kv = Kv.create().set("dbName", dbname).set("tables", tables);
		String databaseHtml = Engine.create("db").getTemplate("E:\\我的代码\\jrecms\\src\\test\\jisu\\db\\database.html").renderToString(kv);
		FileUtils.writeStringToFile(new File("c:/index.html"), databaseHtml);
//		
//			List<String> tables = Db.query("show tables");
//	        for(String table : tables){
//	        	List<Object[]> columnDatas = Db.query("show full columns from "+table);
//	            StringBuilder insertColumn = new StringBuilder();
//	            StringBuilder selectColumn = new StringBuilder();
//	            Map<Integer,String> columnMap = new HashMap<>();
//	            for(int i=0;i<columnDatas.size();i++){
//	                Object[] columnData = columnDatas.get(i);
//	                String field = String.valueOf(columnData[0]);
//	                insertColumn.append(",`"+field+"`");
//	                selectColumn.append(","+field);
//	                columnMap.put(i, String.valueOf(columnData[1]));
//	            }
//	        }
	}
}
