package GeographicArea;

import GeographicArea.Utilities.UtilitieType;
import GrafoStruct.Node;

public class Lote extends Node{
	
	private GroundType groundType;
	private GroundInclination groundInclination;
	private int price;
	private int dimensions;
	private UtilitieType utilitieType;

	public Lote(String _name, String _groundClass, String _groundInclin, int _price, int _dimensions, UtilitieType _utlitie){
		
		super(_name);
		
		setGroundType(_groundClass);
		setGroundInclination(_groundInclin);
		
		price = _price;
		dimensions = _dimensions;
		utilitieType = _utlitie;
	}
	
	private void setGroundType(String groundClassification){
		groundType = GroundType.getType(groundClassification);
	}
	private void setGroundInclination(String groundInclin){
		groundInclination = GroundInclination.getInclination(groundInclin);
	}
	
	@Override
	public void printInfo(){
		super.printInfo();
		
		groundType.printInfo();
		groundInclination.printInfo();
		utilitieType.printInfo();
		
		System.out.println("\tPrice: " + price + "�");
		System.out.println("\tDimensions: " + dimensions + "m�");
		
		System.out.println();
	}
}


