using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace FactorySimulation
{
    class HttpBroker
    {

	

		private String _data;
		private String _backendAddress = "http://192.168.2.102:8080/sendmachine/";
		private  HttpClient client;

		public static int _counter;

		public void SendFactory(Factory factory, long timeStamp)
		{
			_data = "";

			
			foreach (Line fl  in factory.GetFactoryLines())
			{
				foreach (Unit u in fl.GetUnits())
				{
					foreach (Machine m in u.GetMachines())
					{
						AddBody(m.GetId(), m.GetStatus(), m.GetCurrentPower(), m.GetWorkingOn(), timeStamp);

					}
				}
			}



			// For each PUT a new Thread will be created
			Thread t = new Thread(SendRequest);
			t.Start();

		}


		private void AddBody(int id, int s, float p, int w, long t)
		{

			if (_data.Equals(""))
			{
				String st = "{\"machineId\": " + id + ", " + "\"stateCode\": " + s + ", " + "\"power\": " + p + ", " + "\"workingOn\": " + w + ", " + "\"timestamp\": " + t + "}";

				_data += st;

			}
			else
			{
				_data += (" ,{" + "\"machineId\": " + id + ", " + "\"stateCode\": " + s + ", " + "\"power\": " + p + ", " + "\"workingOn\": " + w + ", " + "\"timestamp\": " + t + "}");
			}



		}



		private async void  SendRequest()
		{
			String putBody = "["+ _data + "]";
			Console.WriteLine("SEND:"+  putBody);
			StringContent content = new StringContent(putBody, Encoding.UTF8,"application/json");

			
		

            try
			{
				using HttpResponseMessage message = await client.PutAsync(_backendAddress, content);
				message.EnsureSuccessStatusCode();

            }
            catch(Exception)
            {
				Console.Write("SENDING FAILED");
            }
			
		

		}

		
		public HttpBroker()
        {
			client = new HttpClient();
			
		}


	    

	}
}
