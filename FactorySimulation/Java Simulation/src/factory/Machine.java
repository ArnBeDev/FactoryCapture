package factory;

import java.util.Random;

/**
 * Machine Class
 * 
 * @author Arnbedev
 *
 */

public class Machine {
	
	
	
	private int id;
	
	private float currentPower;
	private float idlePower;
	private float maxPower;
	
	
	private float[] idlePowers;
	private float[] maxPowers;
	private int pIndex;
	
	
	
	// 0=ready, 1= work done , 2= working , 3/4 errors
	private int status;
	
	private int workingOn;
	
	//work and error times are in seconds
	
	private int workDuration;
	private int[] workDurations;
	private int durationIndex;
	
	private int errorTime;
	
	
	/**
	 * Machine Constructor
	 * 
	 * @param id int, id of machine
	 */
	
	public Machine(int id) {
		this.id =id;
		
	}
	


	
	/**
	 * simulates one second of the machine
	 * 
	 */
	public void update() {
		changePower();
		
		switch(status) {
		case 0:
			
			break;
		case 1:
			break;
		case 2:
			workDuration--;
			if(workDuration <=0) {
				setStatus(1);
			}
			
			break;
		case 3:
			errorTime--;
			if(errorTime<0) {
				if(workingOn > 0) {
					setStatus(2);
				}else {
					setStatus(0);
				}
			}
			break;
		case 4:
			errorTime--;
			if(errorTime<0) {
				if(workingOn > 0) {
					setStatus(2);
				}else {
					setStatus(0);
				}
			}
			default:
				setStatus(0);
				break;
		}
		
		
		
		
	}
	
	/**
	 * Method that  creates random work times between the given range
	 * 
	 * @param min int, minimum workDurtation in seconds
	 * @param max int, max workDuration in seconds
	 */
	
	public void setWorkDuration(int min,int max) {
		Random r = new Random();
		workDurations = new int[5];
		for(int i =0; i<workDurations.length;i++) {
			workDurations[i] = r.nextInt(min, max+1); 
			if(FactorySimulation.FASTMODE) {
				workDurations[i] =2;
			}
			
		}
		durationIndex =0;
		
	}
	
	
	/**
	 * 
	 * @return int, id of machine
	 */

	public int getId() {
		return id;
	}

/**
 * 
 * @param id int, sets the id of machine
 */
	public void setId(int id) {
		this.id = id;
	}




/**
 * 
 * @return int status of machine, 0 - Idle, wait for next part
 * 								  1 - Idle, wait for next machine
 * 								  2 -Working 
 *  						      3 -Error 
 *   							  4 -Fatal Error 
 */
	public int getStatus() {
		return status;
	}
	
/**
 * 
 * @param status returns the current state of machine
 * 
 */

	public void setStatus(int status) {
		this.status = status;
		
		if(status ==0) {
			workingOn =0;
		}
	}

	/**
	 * 
	 * @return returns the id of the part, 0 if machine is not occupied
	 */

	public int getWorkingOn() {
		return workingOn;
	}


	/**
	 * 
	 * @param workingOn int, sets the id of the part that machine is working on
	 */
	public void setWorkingOn(int workingOn) {
		this.workingOn = workingOn;
		status =2;
		createWorkDuration();
	}




/**Creates an error
 * 
 * @param errorTime int, error time in seconds
 * @param errorState int,  Error state code: 3- normal error, 4- fatal error
 */
	public void setError(int errorTime, int errorState) {
		this.errorTime = errorTime;
		this.status = errorState;
		if(errorState< 3) {
			this.status=3;
		}
		if(errorState >4) {
			this.status =4;
		}
	}
	
	
	private void createWorkDuration() {
		workDuration = workDurations[durationIndex];
		durationIndex++;
		
		if(durationIndex >= workDurations.length) {
			durationIndex=0;
		}
	}

/**
 * 
 * 
 * @return the current power consumption
 */
	public float getCurrentPower() {
		return currentPower;
	}


	

/**
 * Creates random power consumption during idle
 * 
 * @param min float, min power 
 * @param max float, max power 
 */
	public void setIdlePower(float min , float max) {
		Random r = new Random();
		idlePowers =new float[15];
		for(int i =0; i<idlePowers.length;i++) {
			idlePowers[i] = r.nextFloat(min,max+1f);
		}
	}

/**
 * Creates random power consumption during work
 * 
 * @param min, min power 
 * @param max, max power
 */
	public void setMaxPower(float min,float max) {
			Random r = new Random();
			maxPowers =new float[15];
			for(int i =0; i<maxPowers.length;i++) {
				maxPowers[i] = r.nextFloat(min,max+1f);
			}
			pIndex =0;
			
	}
	

	private void changePower() {
		idlePower = idlePowers[pIndex];
		maxPower = maxPowers[pIndex];
		
		pIndex++;
		if(pIndex >=maxPowers.length) {
			pIndex=0;
		}
		
		if(status==2) {
			currentPower = maxPower;
			
		}else {
			currentPower = idlePower;
		}
		
	}

	

}
