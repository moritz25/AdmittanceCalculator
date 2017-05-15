package se.kth.eh2745.moritzv.assigment1;

import se.kth.eh2745.moritzv.assigment1.objects.ACLineSegment;
import se.kth.eh2745.moritzv.assigment1.objects.BaseVoltage;
import se.kth.eh2745.moritzv.assigment1.objects.Breaker;
import se.kth.eh2745.moritzv.assigment1.objects.BusbarSection;
import se.kth.eh2745.moritzv.assigment1.objects.ConnectivityNode;
import se.kth.eh2745.moritzv.assigment1.objects.EnergyConsumer;
import se.kth.eh2745.moritzv.assigment1.objects.GeneratingUnit;
import se.kth.eh2745.moritzv.assigment1.objects.GeographicalRegion;
import se.kth.eh2745.moritzv.assigment1.objects.Line;
import se.kth.eh2745.moritzv.assigment1.objects.PowerTransformer;
import se.kth.eh2745.moritzv.assigment1.objects.PowerTransformerEnd;
import se.kth.eh2745.moritzv.assigment1.objects.RatioTapChanger;
import se.kth.eh2745.moritzv.assigment1.objects.RdfObject;
import se.kth.eh2745.moritzv.assigment1.objects.RegulatingControl;
import se.kth.eh2745.moritzv.assigment1.objects.SubGeographicalRegion;
import se.kth.eh2745.moritzv.assigment1.objects.Substation;
import se.kth.eh2745.moritzv.assigment1.objects.SynchronousMachine;
import se.kth.eh2745.moritzv.assigment1.objects.Terminal;
import se.kth.eh2745.moritzv.assigment1.objects.VoltageLevel;

public enum RdfObjectClasses {

	ACLINESEGMENT(ACLineSegment.class), 
	BASE_VOLTAGE(BaseVoltage.class), 
	BREAKER(Breaker.class), 
	BUSBAR_SECTION(BusbarSection.class),
	CONNECTIVITY_NODE(ConnectivityNode.class),
	ENERGY_CONSUMER(EnergyConsumer.class),
	GENERATING_UNIT(GeneratingUnit.class),
	GEOGRAPHICAL_REGION (GeographicalRegion.class),
	LINE(Line.class),
	POWER_TRANSFORMER(PowerTransformer.class), 
	POWER_TRANSFORMER_END(PowerTransformerEnd.class), 
	RATIO_TAP_CHANGER(RatioTapChanger.class), 
	REGULATING_CONTROL(RegulatingControl.class), 
	SUB_GEOGRAPHICAL_REGION (SubGeographicalRegion.class),
	SUBSTATION(Substation.class), 
	SYNCHRONOUS_MACHINE(SynchronousMachine.class), 
	TERMINAL(Terminal.class),
    VOLTAGE_LEVEL(VoltageLevel.class);

	private final Class<? extends RdfObject> classvar;

	private RdfObjectClasses(Class<? extends RdfObject> classvar) {
		this.classvar = classvar;
	}

	public Class<? extends RdfObject> getClassvar() {
		return classvar;
	}

}
