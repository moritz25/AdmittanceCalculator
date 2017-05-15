package se.kth.eh2745.moritzv.assigment1.objects;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.w3c.dom.Element;

import se.kth.eh2745.moritzv.assigment1.XmlParser;
import se.kth.eh2745.moritzv.assigment1.db.MysqlField;
import se.kth.eh2745.moritzv.assigment1.db.MysqlFieldTypes;
import se.kth.eh2745.moritzv.assigment1.exception.XmlStructureNotAsAssumedException;

public class RegulatingControl extends IdentifiedObject {

	protected double targetValue;

	public double getTargetValue() {
		return targetValue;
	}
	public  static String getCimName() {
		return "cim:RegulatingControl";

	}

	public static boolean hasDbTable() {
		return true;
	}


	public static ArrayList<MysqlField> getTabelFields() {

		ArrayList<MysqlField> list = EquipmentContainer.getTabelFields();
		list.add(new MysqlField("targetValue", MysqlFieldTypes.FLOAT));
		return list;

	}

	public int insertData(PreparedStatement statment) throws SQLException {
		int index = super.insertData(statment);
		statment.setDouble(++index, getTargetValue());
		return index;
	}
	
	public RegulatingControl(Element element) throws XmlStructureNotAsAssumedException  {
		super(element);
	
	}
	
	public void updateFromXML(Element element) throws XmlStructureNotAsAssumedException{
		super.updateFromXML(element);
		this.targetValue = XmlParser.ParseDoubleNodeContent(element, "cim:RegulatingControl.targetValue");
	}

	
	
	

}
