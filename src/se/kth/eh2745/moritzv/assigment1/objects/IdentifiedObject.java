package se.kth.eh2745.moritzv.assigment1.objects;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.w3c.dom.Element;

import se.kth.eh2745.moritzv.assigment1.XmlParser;
import se.kth.eh2745.moritzv.assigment1.db.MysqlField;
import se.kth.eh2745.moritzv.assigment1.db.MysqlFieldTypes;
import se.kth.eh2745.moritzv.assigment1.exception.XmlStructureNotAsAssumedException;

public abstract class IdentifiedObject extends RdfObject {
	protected String name;

	public IdentifiedObject(Element element) throws XmlStructureNotAsAssumedException {
		super(element);
		this.name = XmlParser.ParseStringNodeContent(element, "cim:IdentifiedObject.name");

	}

	protected IdentifiedObject() {
		super();
	}

	public static ArrayList<MysqlField> getTabelFields() {

		ArrayList<MysqlField> list = RdfObject.getTabelFields();
		list.add(new MysqlField("name", MysqlFieldTypes.VARCHAR, 32));
		return list;

	}

	@Override
	public int insertData(PreparedStatement statment) throws SQLException {
		int index = super.insertData(statment);
		statment.setString(++index, getName());
		return index;
	}

	public String getName() {
		return name;
	}

	@Override
	public int compareTo(RdfObject other) {
		int classCompare = this.getClass().getName().compareTo(other.getClass().getName());

		if (classCompare == 0) {
			int nameCompare = this.getName().compareTo(((IdentifiedObject) other).getName());
			if (nameCompare == 0) {
				return this.rdfId.compareTo(other.rdfId);
			} else {
				return nameCompare;
			}

		} else {
			return classCompare;
		}
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [name=" + name + ", rdfId=" + rdfId + "]";
	}

}
