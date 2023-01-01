package factory;


import java.util.List;

public class Unit {
	
	
	List<Machine> machines;
	Unit nextUnit;
	boolean lastUnit;
	
	
	public void update() {
		
		if(machines.size() ==1) {
			Machine m = machines.get(0);
			
			if(m.getStatus() ==1) {
				if(!lastUnit && nextUnit.getReady()) {
					
					if(m.getWorkingOn() <=0) {
						m.setStatus(0);
					}else {
						nextUnit.pushProduct(m.getWorkingOn());
						m.setStatus(0);
					}
				
				}else if(lastUnit) {
					m.setStatus(0);
				}
			}
			
			m.update();
			
			
		}else {
			Machine m1 = machines.get(0);
			Machine m2 = machines.get(1);
			
			if(m1.getStatus() ==1 && m2.getStatus() ==1) {
				if(!lastUnit && nextUnit.getReady()) {
					nextUnit.pushProduct(m1.getWorkingOn());
					m1.setStatus(0);
					m2.setStatus(0);
					
					
				}else if(lastUnit) {
					m1.setStatus(0);
					m2.setStatus(0);
				}
			}
			
			
			m1.update();
			m2.update();
			
			
		}
		
		
	
		
	}


	public List<Machine> getMachines() {
		return machines;
	}


	public void setMachines(List<Machine> machines) {
		this.machines = machines;
	}
	
	public void pushProduct(int productId) {
		for(Machine m : machines) {
			m.setWorkingOn(productId);
			
		}
	}
	
	public boolean getReady() {
		
		for(Machine m : machines) {
			if(m.getStatus() !=0) {
				return false;
			}
			
		}
		return true;
	}


	public Unit getNextUnit() {
		return nextUnit;
	}


	public void setNextUnit(Unit nextUnit) {
		this.nextUnit = nextUnit;
	}


	public boolean isLastUnit() {
		return lastUnit;
	}


	public void setLastUnit(boolean lastUnit) {
		this.lastUnit = lastUnit;
	}
	

}
