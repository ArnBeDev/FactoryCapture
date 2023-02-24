package factory;

import java.util.List;
/**
 * 
 * Class that represents a Line of A factory,
 * A line is working indepently from other lines
 * it consist of units
 * 
 * @author Arnbedev
 *
 */

public class FactoryLine {

	
	private List<Unit> units;
	
	
	/**
	 * 
	 * @return All units of this line
	 */
	public List<Unit> getUnits() {
		return units;
	}
	
/**
 * 
 * @param units All units of this line
 */
	public void setUnits(List<Unit> units) {
		this.units = units;
	}
	
	
	

}
