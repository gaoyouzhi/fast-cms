package jisu.sql;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class DbHtml {
	public static String url = "jdbc:mysql://waiwangout.mysql.rds.aliyuncs.com:3306/case_ecshop?useUnicode=true&characterEncoding=UTF-8";
	public static String username = "heyewei";
	public static String password = "wan989610";
	public static String driver = "com.mysql.jdbc.Driver";

	public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
		Class.forName(driver);
		Connection connection = DriverManager.getConnection(url,username,password);
		Statement statement = connection.createStatement();
		List<String> sqls = FileUtils.readLines(new File("c:/abc.sql"));
		for(String sql : sqls){
			System.out.println(sql);
			try{
				statement.executeUpdate(sql);
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
}
