package se.kth.eh2745.moritzv.assigment1.exception;

public class XmlStructureNotAsAssumedException extends XmlParsingException {



	public XmlStructureNotAsAssumedException(String tagName) {
		super(tagName);
	}
	

	private static final long serialVersionUID = 910609683490559241L;

}
