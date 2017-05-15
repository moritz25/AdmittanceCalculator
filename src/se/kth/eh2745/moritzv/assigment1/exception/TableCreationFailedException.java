package se.kth.eh2745.moritzv.assigment1.exception;

public class TableCreationFailedException extends MysqlException {

	private static final long serialVersionUID = 42634170401696869L;
	
	public TableCreationFailedException(Throwable cause){
		super(cause);
	}

}
