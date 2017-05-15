package se.kth.eh2745.moritzv.assigment1.objects;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.w3c.dom.Element;

import se.kth.eh2745.moritzv.assigment1.XmlParser;
import se.kth.eh2745.moritzv.assigment1.db.MysqlField;
import se.kth.eh2745.moritzv.assigment1.db.MysqlFieldTypes;
import se.kth.eh2745.moritzv.assigment1.exception.XmlStructureNotAsAssumedException;

public class BaseVoltage extends RdfObject {
	protected double nominalVoltage;

	public BaseVoltage(Element element) throws XmlStructureNotAsAssumedException {
		super(element);

		this.nominalVoltage = XmlParser.ParseDoubleNodeContent(element, "cim:BaseVoltage.nominalVoltage");

	}

	public static boolean hasDbTable() {
		return true;
	}

	public static ArrayList<MysqlField> getTabelFields() {

		ArrayList<MysqlField> list = RdfObject.getTabelFields();
		list.add(new MysqlField("nominalVoltage", MysqlFieldTypes.FLOAT));
		return list;

	}

	@Override
	public int insertData(PreparedStatement statment) throws SQLException {
		int index = super.insertData(statment);
		statment.setDouble(++index, getNominalVoltage());
		return index;
	}

	public double getNominalVoltage() {
		return nominalVoltage;
	}

	public static String getCimName() {
		return "cim:BaseVoltage";

	}

	@Override
	public String toString() {
		return "BaseVoltage [nominalVoltage=" + nominalVoltage + ", rdfId=" + rdfId + "]";
	}
}
