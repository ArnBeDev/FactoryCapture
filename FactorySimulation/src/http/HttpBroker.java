package http;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import factory.Factory;
import factory.FactoryLine;
import factory.Machine;
import factory.Unit;

public class HttpBroker extends Thread {
	
	

	private String data;
	private String backendAddress ="http://localhost:8080/sendmachine/";
	private String PostBody;
	
	public static int counter;
	
	public void sendFactory(Factory factory, long timeStamp) {
		data ="";

		PostBody ="";
		for(FactoryLine fl  :factory.getFactoryLines()) {
			for(Unit u : fl.getUnits()) {
				for(Machine m : u.getMachines()) {
					addBody(m.getId(), m.getStatus(), m.getCurrentPower(), m.getWorkingOn(),timeStamp);
					
				}
			}
		}
		
		this.start();
		
		
		
	}
	
	
	private void addBody(int id, int s, float p , int w, long t) {
	
		if(data.equals("")) {
			String st="{\"machineId\": "+ id+", " +"\"stateCode\": " + s +", " + "\"power\": " +p +", " +"\"workingOn\": " +w+ ", " +"\"timestamp\": " +t +"}";
			
			data +=st;

		}else {
			data +=(" ,{"+ "\"machineId\": "+ id+", " +"\"stateCode\": " + s +", " + "\"power\": " +p +", " +"\"workingOn\": " +w+ ", " +"\"timestamp\": " +t+"}" ); 
		}
		
		
		
	}
	
	
	
	private void sendRequest() {
		
		 PostBody = "[ " +data +"]";
		
		
		if(counter %1 == 0) {
			System.out.println( PostBody);
			counter =0;
			
		}
			counter++;
			
		try {
		
			URL url = new URL(backendAddress);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setRequestProperty("Accept-Encoding", "application/json");
			httpCon.setRequestProperty("Content-Type", "application/json");
			 httpCon.setDoInput(true);
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("PUT");
			OutputStreamWriter out = new OutputStreamWriter(
			    httpCon.getOutputStream());
			out.write(PostBody);
			out.flush();
			
		
			httpCon.getInputStream();
			
		}catch(Exception e) {
			System.out.println("SENDING FAILED");
		}
	
		
		

		PostBody ="";
	}
	
	@Override	
	public void run() {
		
		sendRequest();
	
	
	}

}
