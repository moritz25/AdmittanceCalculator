package se.kth.eh2745.moritzv.assigment1.objects;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.w3c.dom.Element;

import se.kth.eh2745.moritzv.assigment1.XmlParser;
import se.kth.eh2745.moritzv.assigment1.db.MysqlField;
import se.kth.eh2745.moritzv.assigment1.db.MysqlFieldTypes;
import se.kth.eh2745.moritzv.assigment1.exception.XmlStructureNotAsAssumedException;

public class EnergyConsumer extends Equipment {

	protected double p;
	protected double q;

	public EnergyConsumer(Element element) throws XmlStructureNotAsAssumedException {
		super(element);

	}

	@Override
	public void updateFromXML(Element element) throws XmlStructureNotAsAssumedException {
		super.updateFromXML(element);

		this.p = XmlParser.ParseDoubleNodeContent(element, "cim:EnergyConsumer.p");
		this.q = XmlParser.ParseDoubleNodeContent(element, "cim:EnergyConsumer.q");
	}

	public static String getCimName() {
		return "cim:EnergyConsumer";
	}

	public static boolean hasDbTable() {
		return true;
	}

	public static ArrayList<MysqlField> getTabelFields() {

		ArrayList<MysqlField> list = Equipment.getTabelFields();
		list.add(new MysqlField("P", MysqlFieldTypes.FLOAT));
		list.add(new MysqlField("Q", MysqlFieldTypes.FLOAT));

		return list;

	}

	@Override
	public int insertData(PreparedStatement statment) throws SQLException {
		int index = super.insertData(statment);
		statment.setDouble(++index, getP());
		statment.setDouble(++index, getQ());
		return index;
	}

	public double getP() {
		return p;
	}

	public double getQ() {
		return q;
	}

}