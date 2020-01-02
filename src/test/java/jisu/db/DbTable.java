package jisu.db;

import java.util.List;

public class DbTable {

	private String table;
	private String comment;
	
	private List<DbColumn> columns;
	
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public List<DbColumn> getColumns() {
		return columns;
	}
	public void setColumns(List<DbColumn> columns) {
		this.columns = columns;
	}
}
