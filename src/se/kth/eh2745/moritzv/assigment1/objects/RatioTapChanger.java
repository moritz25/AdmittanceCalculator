package se.kth.eh2745.moritzv.assigment1.objects;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.w3c.dom.Element;

import se.kth.eh2745.moritzv.assigment1.XmlParser;
import se.kth.eh2745.moritzv.assigment1.db.MysqlField;
import se.kth.eh2745.moritzv.assigment1.db.MysqlFieldTypes;
import se.kth.eh2745.moritzv.assigment1.exception.XmlStructureNotAsAssumedException;

public class RatioTapChanger extends IdentifiedObject {
	protected double step;

	public RatioTapChanger(Element element) throws XmlStructureNotAsAssumedException {
		super(element);

	}

	@Override
	public void updateFromXML(Element element) throws XmlStructureNotAsAssumedException {
		super.updateFromXML(element);
		this.step = XmlParser.ParseDoubleNodeContent(element, "cim:TapChanger.step");
	}

	public static String getCimName() {
		return "cim:RatioTapChanger";

	}

	public static boolean hasDbTable() {
		return true;
	}

	public static ArrayList<MysqlField> getTabelFields() {

		ArrayList<MysqlField> list = EquipmentContainer.getTabelFields();
		list.add(new MysqlField("step", MysqlFieldTypes.FLOAT));
		return list;

	}

	@Override
	public int insertData(PreparedStatement statment) throws SQLException {
		int index = super.insertData(statment);
		statment.setDouble(++index, getStep());
		return index;
	}

	public double getStep() {
		return step;
	}

}
