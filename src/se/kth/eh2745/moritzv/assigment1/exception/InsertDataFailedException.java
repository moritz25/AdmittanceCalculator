package se.kth.eh2745.moritzv.assigment1.exception;

public class InsertDataFailedException extends MysqlException {

	private static final long serialVersionUID = 3588299366456202475L;

	public InsertDataFailedException(Throwable cause){
		super(cause);
	}
}
