package factory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import http.HttpBroker;


public class FactorySimulation {
	
	Calendar cl;
	Date date;
	int productId;
	Factory factory;
	int hourStart;
	int hourHardEnd;
	int hourLastPart;
	HttpBroker broker;

	public void startSimulation() {
		createFactory();
		
		Date date = new Date(System.currentTimeMillis()); 
		Calendar cl = new GregorianCalendar();
		cl.setTimeZone(TimeZone.getTimeZone("GMT+1"));
		cl.setTime(date);
		long lastTime=0;
		long lastErrorDecision = System.currentTimeMillis();
		
		while(true) {
			updateTime();
			
			if((hourStart >cl.get(Calendar.HOUR_OF_DAY) ||hourLastPart <=cl.get(Calendar.HOUR_OF_DAY) ) && factory.isFactoryEmpty()) {
				continue;
			}
			
			if(System.currentTimeMillis() -lastTime >1000) {
				lastTime = System.currentTimeMillis();
				
				
				for(FactoryLine factoryLine : factory.getFactoryLines()) {
					
					for(int i =0; i< factoryLine.getUnits().size();i++) {
						
						if(i==0) {
							if(cl.get(Calendar.HOUR_OF_DAY )<hourLastPart && factoryLine.getUnits().get(0).getReady()) {
								factoryLine.getUnits().get(0).pushProduct(productId++);
							}
						}
					
						factoryLine.getUnits().get(i).update();
						
					}
					
					
				}
				
				
				
				broker.sendFactory(factory,lastTime);
				
				
			}
		
			
		}
		
		
		
	}
	
	
	
	private void createFactory() {
		factory = new Factory();
		FactoryLine fLine1 = new FactoryLine();
		FactoryLine fLine2 = new FactoryLine();
		
		Unit u1 = new Unit();
		Unit u2 = new Unit();
		Unit u3 = new Unit();
		Unit u4 = new Unit();
		Unit u5 = new Unit();
		
		
		Machine m1 = new Machine(1,2);
		Machine m2 = new Machine(2,1);
		m2.setWorkDuration(15, 17);
		m1.setWorkDuration(15, 17);
		m1.setIdlePower(0.5f, 3f);
		m2.setIdlePower(0.5f, 3f);
		m1.setMaxPower(175, 205);
		m2.setMaxPower(175, 205);
		
		u1.setMachines(List.of(m1,m2));
	
		
		Machine m3 = new Machine(3,0);
		m3.setWorkDuration(35, 42);
		m3.setIdlePower(1f, 5.5f);
		m3.setMaxPower(200, 350);
		u2.setMachines(List.of(m3));
		
		
		Machine m4 = new Machine(4,0);
		m4.setWorkDuration(65, 75);
		m4.setIdlePower(2.5f, 10.5f);
		m4.setMaxPower(150, 180);
		u3.setMachines(List.of(m4));
		
		Machine m5 = new Machine(5,6);
		Machine m6 = new Machine(6,5);
		m5.setWorkDuration(25, 28);
		m6.setWorkDuration(25, 30);
		m5.setIdlePower(1f, 20f);
		m5.setMaxPower(350, 400);
		m6.setMaxPower(360, 390);
		m6.setIdlePower(1f, 20f);
		u4.setMachines(List.of(m5,m6));
		
		
		
		Machine m7 = new Machine(7,0);
		m7.setWorkDuration(29, 37);
		m7.setMaxPower(150, 160);
		m7.setIdlePower(0.1f, 25);
		
		
		u5.setMachines(List.of(m7));
		
		u1.setNextUnit(u2);
		u2.setNextUnit(u3);
		u3.setNextUnit(u4);
		u4.setNextUnit(u5);
		u5.setLastUnit(true);
		
		
		
		fLine1.setUnits(List.of(u1,u2,u3,u4,u5));
		factory.setFactoryLines(List.of(fLine1));
		
		productId = 1;
		
	}
	
	private void updateTime() {
		 date = new Date(System.currentTimeMillis()); 
		
		
		cl.setTime(date);
		
		
	}
	
	
	public FactorySimulation() {
		 broker = new HttpBroker();
		 hourStart =8;
		 hourLastPart =21;
		 cl = new GregorianCalendar();
		 cl.setTimeZone(TimeZone.getTimeZone("GMT+1"));
		 createFactory();
		 
		createErrorEvents();
	}
	
	
	
	private void createErrorEvents() {
		long time = System.currentTimeMillis();
		Random r = new Random();
		// 33% chance that an error will occure
		
		int i= r.nextInt(1,4);
		if(i ==3) {
			int amount = r.nextInt(1,11);
			if(amount %2==0) {
					// factoryLine 1
			}
			
			if(amount<6) {
				// one machine will get an error
				//factory.getFactoryLines().get(0).getUnits().get(1).getMachines().get(0).setError(120, 3);
				
				
			}else if(amount <9) {
				//two machines will get an error
				
			}else {
				//three machines will get an error
				
			}
			
		}
		
		//System.out.println(System.currentTimeMillis() -time  + "     i: " +i);
		
		
		
	}
	

}
