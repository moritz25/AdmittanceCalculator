package se.kth.eh2745.moritzv.assigment1.objects;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.w3c.dom.Element;

import se.kth.eh2745.moritzv.assigment1.RdfLibary;
import se.kth.eh2745.moritzv.assigment1.XmlParser;
import se.kth.eh2745.moritzv.assigment1.db.MysqlField;
import se.kth.eh2745.moritzv.assigment1.db.MysqlFieldTypes;
import se.kth.eh2745.moritzv.assigment1.exception.NoCimNamException;
import se.kth.eh2745.moritzv.assigment1.exception.XmlStructureNotAsAssumedException;

public abstract class RdfObject implements Comparable<RdfObject> {

	protected String rdfId;

	public RdfObject(Element element) throws XmlStructureNotAsAssumedException {
		this.rdfId = XmlParser.ParseRdfID(element);
		RdfLibary.addToList(this);
	}

	protected RdfObject() {
		super();
	}

	public void updateFromXML(Element element) throws XmlStructureNotAsAssumedException {
		if (this.rdfId.equals(XmlParser.ParseRdfID(element))) { // Security
																// check that
																// the right
																// object is
																// updated
			throw new XmlStructureNotAsAssumedException(this.toString());
		}
	}

	public static String getCimName() throws NoCimNamException {
		throw new NoCimNamException();

	}

	public static String getCimName(Object a) throws NoCimNamException {
		return getCimName();

	}

	public static boolean hasDbTable() {
		return false;
	}

	public static ArrayList<MysqlField> getTabelFields() {
		ArrayList<MysqlField> list = new ArrayList<MysqlField>();
		list.add(new MysqlField("rdfId", MysqlFieldTypes.VARCHAR, 50, true));
		return list;

	}

	public static String getPrimaryKeyName() {
		for (MysqlField field : getTabelFields()) {
			if (field.isPrimaryKey()) {
				return field.getName();
			}
		}
		return null;

	}

	/**
	 *
	 * @param statment
	 *            Mysql PreparedStatment to add Data to for execution
	 * @return number of last added Data
	 * @throws SQLException
	 */
	public int insertData(PreparedStatement statment) throws SQLException {

		statment.setString(1, getRdfId());
		return 1;
	}

	public String getRdfId() {
		return rdfId;
	}

	public RdfObject[] getLinks() {
		RdfObject[] objs = {};
		return objs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rdfId == null) ? 0 : rdfId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		RdfObject other = (RdfObject) obj;
		if (rdfId == null) {
			if (other.rdfId != null) {
				return false;
			}
		} else if (!rdfId.equals(other.rdfId)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [rdfId=" + rdfId + "]";
	}

	@Override
	public int compareTo(RdfObject other) {
		int classCompare = this.getClass().getName().compareTo(other.getClass().getName());

		if (classCompare == 0) {
			return this.rdfId.compareTo(other.rdfId);
		} else {
			return classCompare;
		}
	}

}