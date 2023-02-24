package factory;

import java.util.List;
/**
 * 
 * @author Arnbedev
 *
 */

public class Factory {
	
	//Each factory consist of factoryLines
	private List<FactoryLine> factoryLines;
/**
 * 
 * @return FactoryLine, List of all factoryLines
 */
	public List<FactoryLine> getFactoryLines() {
		return factoryLines;
	}
/**
 * 
 * @param factoryLines, all FactoryLines the Factory should have
 */
	public void setFactoryLines(List<FactoryLine> factoryLines) {
		this.factoryLines = factoryLines;
	}
	
	/**
	 * 
	 * @return true,  all machines have workingOn =0
	 * 			false, at least one machines has workingOn > 0
	 * 	
	 */
	public boolean isFactoryEmpty() {
		for(FactoryLine l: factoryLines) {
			for(Unit u  : l.getUnits()) {
				for(Machine m : u.getMachines()) {
					if(m.getWorkingOn() >0) {
						
						return false;
					}
				}
			}
		}
		
		
		return true;
	}
	
	

}
