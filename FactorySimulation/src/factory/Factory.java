package factory;

import java.util.List;

public class Factory {
	
	List<FactoryLine> factoryLines;

	public List<FactoryLine> getFactoryLines() {
		return factoryLines;
	}

	public void setFactoryLines(List<FactoryLine> factoryLines) {
		this.factoryLines = factoryLines;
	}
	
	
	public boolean isFactoryEmpty() {
		for(FactoryLine l: factoryLines) {
			for(Unit u  : l.getUnits()) {
				for(Machine m : u.getMachines()) {
					if(m.getWorkingOn() >0) {
						System.out.println("Factory not empty");
						return false;
					}
				}
			}
		}
		
		
		return true;
	}
	
	

}
