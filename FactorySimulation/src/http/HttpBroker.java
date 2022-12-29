package http;

import factory.Factory;
import factory.FactoryLine;
import factory.Machine;
import factory.Unit;

public class HttpBroker {
	
	
	private String data ;
	private String PostBody;;
	
	public void sendFactory(Factory factory, long timeStamp) {
		data ="";
		
		for(FactoryLine fl  :factory.getFactoryLines()) {
			for(Unit u : fl.getUnits()) {
				for(Machine m : u.getMachines()) {
					addBody(m.getId(), m.getStatus(), m.getCurrentPower(), m.getWorkingOn(),timeStamp);
					
				}
			}
		}
		sendRequest();
		
		
		
		
	}
	
	
	private void addBody(int id, int s, float p , int w, long t) {
		if(data.equals("")) {
			String st="{id: "+ id+", " +"s: " + s +", " + "p: " +p +", " +"w: " +w+ ", " +"t: " +t +"}";
			
			data +=st;
		//	System.out.println(data);
		}else {
			data +=(" ,{"+ "id: "+ id+", " +"s: " + s +", " + "p: " +p +", " +"w: " +w+ ", " +"t: " +t+"}" ); 
		}
		
		
		
	}
	
	private void sendRequest() {
		
		
		PostBody = "[ " +data +"]";
		System.out.println( PostBody);
		data ="";
		PostBody ="";
	}

}
