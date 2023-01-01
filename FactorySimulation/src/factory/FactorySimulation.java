package factory;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import http.HttpBroker;


public class FactorySimulation {
	
	private Calendar cl;
	private Date date;
	private int productId;
	private Factory factory;
	private int hourStart;
	private int hourLastPart;
	

	public void startSimulation() {
		createFactory();
		
		date = new Date(System.currentTimeMillis()); 
		cl = new GregorianCalendar();
		cl.setTimeZone(TimeZone.getTimeZone("GMT+1"));
		cl.setTime(date);
		long lastTime=0;
		long lastErrorDecision =0;
		
		while(true) {
			updateTime();
			
			if((hourStart >cl.get(Calendar.HOUR_OF_DAY) ||hourLastPart <=cl.get(Calendar.HOUR_OF_DAY) ) && factory.isFactoryEmpty()) {
				continue;
			}
			
			if(System.currentTimeMillis() -lastTime >1000) {
				lastTime = System.currentTimeMillis();
				
				if(lastTime -lastErrorDecision >300000) {
					createErrorEvent();
					lastErrorDecision = System.currentTimeMillis();
				}
				
				
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
				
				HttpBroker broker = new HttpBroker();
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
		
		Unit u6 = new Unit();
		Unit u7 = new Unit();
		Unit u8 = new Unit();
		Unit u9 = new Unit();
		Unit u10 = new Unit();
		
		
		Machine m1 = new Machine(1);
		Machine m2 = new Machine(2);
		m2.setWorkDuration(15, 17);
		m1.setWorkDuration(15, 17);
		m1.setIdlePower(0.5f, 3f);
		m2.setIdlePower(0.5f, 3f);
		m1.setMaxPower(175, 205);
		m2.setMaxPower(175, 205);
		
		u1.setMachines(List.of(m1,m2));
	
		
		Machine m3 = new Machine(3);
		m3.setWorkDuration(35, 42);
		m3.setIdlePower(1f, 5.5f);
		m3.setMaxPower(200, 350);
		u2.setMachines(List.of(m3));
		
		
		Machine m4 = new Machine(4);
		m4.setWorkDuration(65, 75);
		m4.setIdlePower(2.5f, 10.5f);
		m4.setMaxPower(150, 180);
		u3.setMachines(List.of(m4));
		
		Machine m5 = new Machine(5);
		Machine m6 = new Machine(6);
		m5.setWorkDuration(25, 28);
		m6.setWorkDuration(25, 30);
		m5.setIdlePower(1f, 20f);
		m5.setMaxPower(350, 400);
		m6.setMaxPower(360, 390);
		m6.setIdlePower(1f, 20f);
		u4.setMachines(List.of(m5,m6));
		
		
		
		Machine m7 = new Machine(7);
		m7.setWorkDuration(29, 37);
		m7.setMaxPower(150, 160);
		m7.setIdlePower(0.1f, 25);
		
		
		u5.setMachines(List.of(m7));
		
		
		Machine m8 = new Machine(8);
		m8.setWorkDuration(5,6);
		m8.setMaxPower(33, 45);
		m8.setIdlePower(0.1f, 1.5f);
		u6.setMachines(List.of(m8));
		
		
		
		Machine m9 = new Machine(9);
		m9.setWorkDuration(5,8);
		m9.setMaxPower(45, 65);
		m9.setIdlePower(3, 4);
		u7.setMachines(List.of(m9));
		
		Machine m10 = new Machine(10);
		m10.setWorkDuration(10,12);
		m10.setMaxPower(300, 330);
		m10.setIdlePower(0.1f, 10);
		u8.setMachines(List.of(m10));
		
		
		Machine m11 = new Machine(11);
		Machine m12 = new Machine(12);
		m11.setWorkDuration(30,32);
		m12.setWorkDuration(30,32);
		m11.setMaxPower(200, 210);
		m12.setMaxPower(200, 213);
		m11.setIdlePower(1, 5);
		m12.setIdlePower(2, 4);
		u9.setMachines(List.of(m11,m12));
		
		
		
		
		Machine m13 = new Machine(13);
		Machine m14 = new Machine(14);
		m13.setWorkDuration(24,27);
		m14.setWorkDuration(25,28);
		m13.setMaxPower(300, 310);
		m14.setMaxPower(295, 313);
		m13.setIdlePower(1, 5);
		m14.setIdlePower(2, 4);
		u10.setMachines(List.of(m13,m14));
		
		
		Machine m15 = new Machine(15);
		m15.setWorkDuration(5,35);
		m15.setMaxPower(100, 300);
		m15.setIdlePower(3, 15);
		
		Unit uLastL1 = new Unit();
		uLastL1.setMachines(List.of(m15));
		
		
		
		u1.setNextUnit(u2);
		u2.setNextUnit(u3);
		u3.setNextUnit(u4);
		u4.setNextUnit(u5);
		u5.setNextUnit(u6);
		u6.setNextUnit(u7);
		u7.setNextUnit(u8);
		u8.setNextUnit(u9);
		u9.setNextUnit(u10);
		u10.setNextUnit(uLastL1);
		uLastL1.setLastUnit(true);
		
		
		
		fLine1.setUnits(List.of(u1,u2,u3,u4,u5,u6,u7,u8,u9,u10,uLastL1));
		
		
		
		
		
		Machine m16 =new Machine(16);
		Machine m17 =new Machine(17);
		Machine m18 =new Machine(18);
		Machine m19 =new Machine(19);
		Machine m20 =new Machine(20);
		Machine m21 =new Machine(21);
		Machine m22 =new Machine(22);
		Machine m23 =new Machine(23);
		Machine m24 =new Machine(24);
		Machine m25 =new Machine(25);
		Machine m26 =new Machine(26);
		Machine m27 =new Machine(27);
		Machine m28 =new Machine(28);
		Machine m29 =new Machine(29);
	
		
		Unit u11 = new Unit();
		Unit u12 = new Unit();
		Unit u13 = new Unit();
		Unit u14 = new Unit();
		Unit u15 = new Unit();
		Unit u16 = new Unit();
		Unit u17 = new Unit();
		Unit u18 = new Unit();
		Unit u19 = new Unit();
		Unit u20 = new Unit();
		
		
		m16.setWorkDuration(15,18);
		m17.setWorkDuration(14,18);
		m16.setMaxPower(180, 195);
		m17.setMaxPower(180, 195);
		m17.setIdlePower(1, 4);
		m16.setIdlePower(1, 6);
		
		
		u11.setMachines(List.of(m16,m17));
		
		
		m18.setWorkDuration(24, 29);
		m19.setWorkDuration(24, 30);
		m18.setMaxPower(379, 385);
		m19.setMaxPower(390, 395);
		m19.setIdlePower(1, 7);
		m18.setIdlePower(1, 11);
		
		u12.setMachines(List.of(m18,m19));
		
		
		m20.setWorkDuration(33,39);
		m20.setIdlePower(1, 9);
		m20.setMaxPower(235, 313);
		
		u13.setMachines(List.of(m20));
		
		
		
		m21.setWorkDuration(64, 74);
		m21.setMaxPower(165, 183);
		m21.setIdlePower(2, 13);
		
		u14.setMachines(List.of(m21));
		
		m22.setWorkDuration(4,9);
		m22.setMaxPower(36, 51);
		m22.setIdlePower(0.5f, 2.8f);
		
		u15.setMachines(List.of(m22));
		
		
		
		m23.setWorkDuration(29, 31);
		m23.setIdlePower(4, 9);
		m23.setMaxPower(205, 222);
		m24.setWorkDuration(29, 31);
		m24.setIdlePower(1, 23);
		m24.setMaxPower(202, 226);
		
		u16.setMachines(List.of(m23,m24));
		
		m25.setWorkDuration(15,30);
		m25.setIdlePower(1, 6);
		m25.setMaxPower(115, 150);
		
		u17.setMachines(List.of(m25));
		
		m26.setWorkDuration(25,30);
		m26.setMaxPower(115, 200);
		m26.setIdlePower(3, 13);
		
		u18.setMachines(List.of(m26));
		
		
		m27.setWorkDuration(21, 25);
		m27.setIdlePower(0.4f, 3);
		m27.setMaxPower(315, 322);
		m28.setWorkDuration(21, 26);
		m28.setIdlePower(0.1f, 3.3f);
		m28.setMaxPower(311, 319);
		
		u19.setMachines(List.of(m27,m28));
		
		
		m29.setWorkDuration(5,25);
		m29.setMaxPower(100, 333);
		m29.setIdlePower(1,5);
		
		u20.setMachines(List.of(m29));
		
		
		
		u11.setNextUnit(u12);
		u12.setNextUnit(u13);
		u13.setNextUnit(u14);
		u14.setNextUnit(u15);
		u15.setNextUnit(u16);
		u16.setNextUnit(u17);
		u17.setNextUnit(u18);
		u18.setNextUnit(u19);
		u19.setNextUnit(u20);
		u20.setLastUnit(true);
		
		fLine2.setUnits(List.of(u11,u12,u13,u14,u15,u16,u17,u18,u19,u20));
		
		
		factory.setFactoryLines(List.of(fLine1,fLine2));
		
		productId = 1;
		
	}
	
	private void updateTime() {
		 date = new Date(System.currentTimeMillis()); 
		
		
		cl.setTime(date);
		
		
	}
	
	
	public FactorySimulation() {
	
		 hourStart =3;
		 hourLastPart =23;
		 cl = new GregorianCalendar();
		 cl.setTimeZone(TimeZone.getTimeZone("GMT+1"));
		 createFactory();
		 
		
	}
	
	
	
	private void createErrorEvent() {
		
		
		Random r = new Random();
		// 33% chance that an error will occure
		
		int i= r.nextInt(1,4);
		if(i ==3) {
			i = r.nextInt(1,101);
			
			//9% chance that it will be a longer error
			if(i>91) {
				i = r.nextInt(1,101);
				// longer error have a higher chance to be in line2
				
				if(i>37) {
					i= r.nextInt(16,39);{
						
						// m19 and m23 have a higher chance for longer error
						if(i>29) {
							if(i%2==0) {
								setError(19, r.nextInt(120,555), 4);
								
							}else {
								setError(23, r.nextInt(120,555), 4);
							}
							
						}else {
							setError(i, r.nextInt(120,450), 4);
						}
						
						
						
						
					}
					
					
					
				}else {
					setError( r.nextInt(1,15),r.nextInt(90,390), 4);
					
				}
				
				
			}else if(i>65) {
				
				
				
				i = r.nextInt(1,101);
				
				if(i>43) {
					//line2, m20 and m27 have higher chance for mid error
					i = r.nextInt(16,37);
					if(i>29) {
						if(i%2==2) {
							setError(20, r.nextInt(70,105), 3);
						}else {
							setError(27, r.nextInt(70,105), 3);
						}
						
					}else {
						setError(r.nextInt(16,30), r.nextInt(70,105), 3);
					}
							
					
				}else {
					setError(r.nextInt(1,16), r.nextInt(65,95), 3);
				}
				
			}else {
				// all machines have same Chance for short error
				setError(r.nextInt(1,30), r.nextInt(10,63), 3);
				
				
			}
			
			
		}
		
		
		
	}
	
	private void setError(int machineId, int time, int errorCode) {
		for(FactoryLine l :factory.getFactoryLines()) {
			for(Unit u : l.getUnits()) {
				for(Machine m :u.getMachines()) {
					if(m.getId() ==machineId) {
						System.out.println("ERROR CREATED on : " +machineId  + " with ErrorCode: " +errorCode);
						m.setError(time, errorCode);
					}
				}
			}
		}
	}
	

}
