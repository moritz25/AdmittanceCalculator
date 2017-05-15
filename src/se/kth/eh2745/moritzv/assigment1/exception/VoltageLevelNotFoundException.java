package se.kth.eh2745.moritzv.assigment1.exception;

public class VoltageLevelNotFoundException extends XmlParsingException {

	private static final long serialVersionUID = 9221887207671762641L;

	public VoltageLevelNotFoundException(String message) {
		super(message);
	}

}
