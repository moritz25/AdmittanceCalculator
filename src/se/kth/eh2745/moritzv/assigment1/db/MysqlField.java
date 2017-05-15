package se.kth.eh2745.moritzv.assigment1.db;

public class MysqlField {
	String name;
	MysqlFieldTypes type;
	int length = -1;
	boolean canNull=false;
	boolean primaryKey = false;

	public MysqlField(String name, MysqlFieldTypes type) {
		this.name = name;
		this.type = type;

	}

	public MysqlField(String name, MysqlFieldTypes type, int length) {
		this.name = name;
		this.type = type;
		this.length = length;
	}

	public MysqlField(String name, MysqlFieldTypes type, int length, boolean primaryKey) {
		this.name = name;
		this.type = type;
		this.length = length;
		this.primaryKey = primaryKey;
	}
	
	public String getCreateString() {
		String str=getQuotedName()+ " "+ type.mysqlCommand;
		if(length != -1){
			str +="("+length+")";
		}
		if(!canNull){
			str += " NOT NULL";
		}
		
		
		return  str; 
	}
	
	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public String getName() {
		return name;
	}
	public String getQuotedName() {
		return "`"+name+"`";
	}

}
