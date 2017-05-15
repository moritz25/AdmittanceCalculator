package se.kth.eh2745.moritzv.assigment1.objects;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.w3c.dom.Element;

import se.kth.eh2745.moritzv.assigment1.RdfLibary;
import se.kth.eh2745.moritzv.assigment1.XmlParser;
import se.kth.eh2745.moritzv.assigment1.db.MysqlField;
import se.kth.eh2745.moritzv.assigment1.db.MysqlFieldTypes;
import se.kth.eh2745.moritzv.assigment1.exception.XmlStructureNotAsAssumedException;

public class Substation extends EquipmentContainer {

	protected RdfLink<GeographicalRegion> region;

	public GeographicalRegion getRegion() {
		return region.getObj();
	}

	public String getRegionRdfId() {
		return region.getRdfId();
	}

	public static String getCimName() {
		return "cim:Substation";

	}

	public static boolean hasDbTable() {
		return true;
	}


	public static ArrayList<MysqlField> getTabelFields() {

		ArrayList<MysqlField> list = EquipmentContainer.getTabelFields();
		list.add(new MysqlField("region_rdf:ID", MysqlFieldTypes.VARCHAR, 50));
		return list;

	}

	public int insertData(PreparedStatement statment) throws SQLException {
		int index = super.insertData(statment);
		statment.setString(++index, getRegionRdfId());
		return index;
	}
	
	@Override
	public RdfObject[] getLinks() {
		RdfObject[] objs = { getRegion() };
		return objs;
	}

	public Collection<VoltageLevel> getVoltageLevels() {
		Collection<VoltageLevel> allVoltageLevel = RdfLibary.getAllofType(VoltageLevel.class);
		Collection<VoltageLevel> voltageLevelsAtSub = new ArrayList<VoltageLevel>();
		for (VoltageLevel voltageLevel : allVoltageLevel) {
			if (voltageLevel.getSubstation().equals(this)) {
				voltageLevelsAtSub.add(voltageLevel);
			}
		}
		return voltageLevelsAtSub;
	}

	public Collection<BusbarSection> getBusbarSections() {
		Collection<VoltageLevel> allVoltageLevel = getVoltageLevels();
		Collection<BusbarSection> busbarSectionsAtSub = new ArrayList<BusbarSection>();
		for (VoltageLevel voltageLevel : allVoltageLevel) {
			busbarSectionsAtSub.addAll(voltageLevel.getEqipmentOfType(BusbarSection.class));
		}
		return busbarSectionsAtSub;
	}

	@SuppressWarnings("unchecked")
	public Substation(Element element) throws XmlStructureNotAsAssumedException {
		super(element);
		this.region = (RdfLink<GeographicalRegion>) XmlParser.ParseRdfLink(element, "cim:Substation.Region", this);

	}

}
