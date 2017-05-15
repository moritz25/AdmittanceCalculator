package se.kth.eh2745.moritzv.assigment1.db;

public enum MysqlFieldTypes {

	VARCHAR("varchar"),
	INTEGER("INTEGER"),
	FLOAT("FLOAT"), 
	BOOLEAN("BOOLEAN");
	
	String mysqlCommand;

	private MysqlFieldTypes(String mysqlCommand) {
		this.mysqlCommand = mysqlCommand;
	}
	
	
}
