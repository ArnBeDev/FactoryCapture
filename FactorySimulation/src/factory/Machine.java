package factory;

import java.util.Random;

public class Machine {
	
	private int id;
	
	private float currentPower;
	private float idlePower;
	private float maxPower;
	
	
	private float[] idlePowers;
	private float[] maxPowers;
	private int pIndex;
	
	
	
	//status0 = ready; status 1 besetzt , status 2= arbeiten , rest errors
	private int status;
	
	private int workingOn;
	
	private int workDuration;
	private int[] workDurations;
	int durationIndex;
	
	private int errorTime;// in seconds
	private int weakErrorTime;

	
	
	public Machine(int id) {
		this.id =id;
		
	}
	


	
	
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
	
	
	
	public void setWorkDuration(int min,int max) {
		Random r = new Random();
		workDurations = new int[5];
		for(int i =0; i<workDurations.length;i++) {
			workDurations[i] = r.nextInt(min, max+1); 
		}
		durationIndex =0;
		
	}
	
	


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}





	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
		
		if(status ==0) {
			workingOn =0;
		}
	}


	public int getWorkingOn() {
		return workingOn;
	}


	public void setWorkingOn(int workingOn) {
		this.workingOn = workingOn;
		status =2;
		createWorkDuration();
	}


	public void getWorkDuration() {
		workDuration = workDurations[durationIndex];
		durationIndex++;
		if(durationIndex>= workDurations.length) {
			durationIndex=0;
		}
		
	}


	public void setWorkDuration(int workDuration) {
		this.workDuration = workDuration;
	}


	public int getErrorTime() {
		return errorTime;
	}


	public void setError(int errorTime, int errorState) {
		this.errorTime = errorTime;
		this.status = errorState;
		if(errorState< 2) {
			this.status=3;
		}
	}
	
	
	private void createWorkDuration() {
		workDuration = workDurations[durationIndex];
		durationIndex++;
		
		if(durationIndex >= workDurations.length) {
			durationIndex=0;
		}
	}


	public float getCurrentPower() {
		return currentPower;
	}


	public void setCurrentPower(float currentPower) {
		this.currentPower = currentPower;
	}


	public void setIdlePower(float min , float max) {
		Random r = new Random();
		idlePowers =new float[15];
		for(int i =0; i<idlePowers.length;i++) {
			idlePowers[i] = r.nextFloat(min,max+1f);
		}
	}


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
