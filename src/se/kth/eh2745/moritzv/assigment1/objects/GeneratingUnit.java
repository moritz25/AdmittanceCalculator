package se.kth.eh2745.moritzv.assigment1.objects;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.w3c.dom.Element;

import se.kth.eh2745.moritzv.assigment1.XmlParser;
import se.kth.eh2745.moritzv.assigment1.db.MysqlField;
import se.kth.eh2745.moritzv.assigment1.db.MysqlFieldTypes;
import se.kth.eh2745.moritzv.assigment1.exception.XmlStructureNotAsAssumedException;

public class GeneratingUnit extends Equipment {
	protected double maxP;
	protected double minP;

	public GeneratingUnit(Element element) throws XmlStructureNotAsAssumedException {
		super(element);

		this.maxP = XmlParser.ParseDoubleNodeContent(element, "cim:GeneratingUnit.maxOperatingP");
		this.minP = XmlParser.ParseDoubleNodeContent(element, "cim:GeneratingUnit.minOperatingP");

	}

	public static String getCimName() {
		return "cim:GeneratingUnit";

	}

	public static boolean hasDbTable() {
		return true;
	}

	public static ArrayList<MysqlField> getTabelFields() {

		ArrayList<MysqlField> list = Equipment.getTabelFields();
		list.add(new MysqlField("maxP", MysqlFieldTypes.FLOAT));
		list.add(new MysqlField("minP", MysqlFieldTypes.FLOAT));
		return list;

	}

	@Override
	public int insertData(PreparedStatement statment) throws SQLException {
		int index = super.insertData(statment);
		statment.setDouble(++index, getMaxP());
		statment.setDouble(++index, getMinP());
		return index;
	}

	public double getMaxP() {
		return maxP;
	}

	public double getMinP() {
		return minP;
	}

}
