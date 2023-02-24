using System;
using System.Collections.Generic;


namespace FactorySimulation
{
    class Simulation
    {
		
		private DateTime date;
		private int _productId;
		private Factory _factory;
		private int _hourStart;
		private int _hourLastPart;
		public static bool FASTMODE = true;// fastmode for better debugging
		private HttpBroker _broker;
		public void StartSimulation()
		{
			Console.WriteLine("Simliato startet");
			CreateFactory();

			date = DateTime.Now;
			
			long lastTime = 0;
			long lastErrorDecision = 0;

			while (true)
			{
				UpdateTime();

				if ((_hourStart > date.Hour || _hourLastPart <= date.Hour && _factory.IsFactoryEmpty()))
				{
						//factory is not producing
					continue;
				}
			
				if (DateTimeOffset.Now.ToUnixTimeMilliseconds() - lastTime > 1000 )
				{
					

					lastTime = DateTimeOffset.Now.ToUnixTimeMilliseconds();

					if (lastTime - lastErrorDecision > 300000)
					{
						CreateErrorEvent();
						lastErrorDecision = DateTimeOffset.Now.ToUnixTimeMilliseconds();
					}


					foreach (Line factoryLine in _factory.GetFactoryLines())
					{

						for (int i = 0; i < factoryLine.GetUnits().Count; i++)
						{

							if (i == 0)
							{
								if (date.Hour < _hourLastPart && factoryLine.GetUnits()[0].GetReady())
								{
									factoryLine.GetUnits()[0].PushProduct(_productId++);
								}
							}

							factoryLine.GetUnits()[i].Update();

						}


					}

				
					_broker.SendFactory(_factory, lastTime);



                }
                else
                {
					
					//waiting
				}


			}



		}



		private void CreateFactory()
		{
			
			_factory = new Factory();
			Line fLine1 = new Line();
			Line fLine2 = new Line();

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
			m2.SetWorkDuration(15, 17);
			m1.SetWorkDuration(15, 17);
			m1.SetIdlePower(0.5f, 3f);
			m2.SetIdlePower(0.5f, 3f);
			m1.SetMaxPower(175, 205);
			m2.SetMaxPower(175, 205);

			u1.SetMachines(new List<Machine> { m1, m2 });


			Machine m3 = new Machine(3);
			m3.SetWorkDuration(35, 42);
			m3.SetIdlePower(1f, 5.5f);
			m3.SetMaxPower(200, 350);
			u2.SetMachines(new List<Machine> { m3 });


			Machine m4 = new Machine(4);
			m4.SetWorkDuration(65, 75);
			m4.SetIdlePower(2.5f, 10.5f);
			m4.SetMaxPower(150, 180);
			u3.SetMachines(new List<Machine> { m4 });

			Machine m5 = new Machine(5);
			Machine m6 = new Machine(6);
			m5.SetWorkDuration(25, 28);
			m6.SetWorkDuration(25, 30);
			m5.SetIdlePower(1f, 20f);
			m5.SetMaxPower(350, 400);
			m6.SetMaxPower(360, 390);
			m6.SetIdlePower(1f, 20f);
			u4.SetMachines(new List<Machine> { m5, m6 });



			Machine m7 = new Machine(7);
			m7.SetWorkDuration(29, 37);
			m7.SetMaxPower(150, 160);
			m7.SetIdlePower(0.1f, 25);


			u5.SetMachines(new List<Machine> { m7 });


			Machine m8 = new Machine(8);
			m8.SetWorkDuration(5, 6);
			m8.SetMaxPower(33, 45);
			m8.SetIdlePower(0.1f, 1.5f);
			u6.SetMachines(new List<Machine> { m8 });



			Machine m9 = new Machine(9);
			m9.SetWorkDuration(5, 8);
			m9.SetMaxPower(45, 65);
			m9.SetIdlePower(3, 4);
			u7.SetMachines(new List<Machine> { m9 });

			Machine m10 = new Machine(10);
			m10.SetWorkDuration(10, 12);
			m10.SetMaxPower(300, 330);
			m10.SetIdlePower(0.1f, 10);
			u8.SetMachines(new List<Machine> { m10 });


			Machine m11 = new Machine(11);
			Machine m12 = new Machine(12);
			m11.SetWorkDuration(30, 32);
			m12.SetWorkDuration(30, 32);
			m11.SetMaxPower(200, 210);
			m12.SetMaxPower(200, 213);
			m11.SetIdlePower(1, 5);
			m12.SetIdlePower(2, 4);
			u9.SetMachines(new List<Machine> { m11, m12 });




			Machine m13 = new Machine(13);
			Machine m14 = new Machine(14);
			m13.SetWorkDuration(24, 27);
			m14.SetWorkDuration(25, 28);
			m13.SetMaxPower(300, 310);
			m14.SetMaxPower(295, 313);
			m13.SetIdlePower(1, 5);
			m14.SetIdlePower(2, 4);
			u10.SetMachines(new List<Machine> { m13, m14 });


			Machine m15 = new Machine(15);
			m15.SetWorkDuration(5, 35);
			m15.SetMaxPower(100, 300);
			m15.SetIdlePower(3, 15);

			Unit uLastL1 = new Unit();
			uLastL1.SetMachines(new List<Machine> { m15 });



			u1.SetNextUnit(u2);
			u2.SetNextUnit(u3);
			u3.SetNextUnit(u4);
			u4.SetNextUnit(u5);
			u5.SetNextUnit(u6);
			u6.SetNextUnit(u7);
			u7.SetNextUnit(u8);
			u8.SetNextUnit(u9);
			u9.SetNextUnit(u10);
			u10.SetNextUnit(uLastL1);
			uLastL1.SetLastUnit(true);



			fLine1.SetUnits(new List<Unit> { u1, u2, u3, u4, u5, u6, u7, u8, u9, u10, uLastL1 });





			Machine m16 = new Machine(16);
			Machine m17 = new Machine(17);
			Machine m18 = new Machine(18);
			Machine m19 = new Machine(19);
			Machine m20 = new Machine(20);
			Machine m21 = new Machine(21);
			Machine m22 = new Machine(22);
			Machine m23 = new Machine(23);
			Machine m24 = new Machine(24);
			Machine m25 = new Machine(25);
			Machine m26 = new Machine(26);
			Machine m27 = new Machine(27);
			Machine m28 = new Machine(28);
			Machine m29 = new Machine(29);


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


			m16.SetWorkDuration(15, 18);
			m17.SetWorkDuration(14, 18);
			m16.SetMaxPower(180, 195);
			m17.SetMaxPower(180, 195);
			m17.SetIdlePower(1, 4);
			m16.SetIdlePower(1, 6);


			u11.SetMachines(new List<Machine> { m16, m17 });


			m18.SetWorkDuration(24, 29);
			m19.SetWorkDuration(24, 30);
			m18.SetMaxPower(379, 385);
			m19.SetMaxPower(390, 395);
			m19.SetIdlePower(1, 7);
			m18.SetIdlePower(1, 11);

			u12.SetMachines(new List<Machine> { m18, m19 });


			m20.SetWorkDuration(33, 39);
			m20.SetIdlePower(1, 9);
			m20.SetMaxPower(235, 313);

			u13.SetMachines(new List<Machine> { m20 });



			m21.SetWorkDuration(64, 74);
			m21.SetMaxPower(165, 183);
			m21.SetIdlePower(2, 13);

			u14.SetMachines(new List<Machine> { m21 });

			m22.SetWorkDuration(4, 9);
			m22.SetMaxPower(36, 51);
			m22.SetIdlePower(0.5f, 2.8f);

			u15.SetMachines(new List<Machine> { m22 });



			m23.SetWorkDuration(29, 31);
			m23.SetIdlePower(4, 9);
			m23.SetMaxPower(205, 222);
			m24.SetWorkDuration(29, 31);
			m24.SetIdlePower(1, 23);
			m24.SetMaxPower(202, 226);

			u16.SetMachines(new List<Machine> { m23, m24 });

			m25.SetWorkDuration(15, 30);
			m25.SetIdlePower(1, 6);
			m25.SetMaxPower(115, 150);

			u17.SetMachines(new List<Machine> { m25 });

			m26.SetWorkDuration(25, 30);
			m26.SetMaxPower(115, 200);
			m26.SetIdlePower(3, 13);

			u18.SetMachines(new List<Machine> { m26 });


			m27.SetWorkDuration(21, 25);
			m27.SetIdlePower(0.4f, 3);
			m27.SetMaxPower(315, 322);
			m28.SetWorkDuration(21, 26);
			m28.SetIdlePower(0.1f, 3.3f);
			m28.SetMaxPower(311, 319);

			u19.SetMachines(new List<Machine> { m27, m28 });


			m29.SetWorkDuration(5, 25);
			m29.SetMaxPower(100, 333);
			m29.SetIdlePower(1, 5);

			u20.SetMachines(new List<Machine> { m29 });



			u11.SetNextUnit(u12);
			u12.SetNextUnit(u13);
			u13.SetNextUnit(u14);
			u14.SetNextUnit(u15);
			u15.SetNextUnit(u16);
			u16.SetNextUnit(u17);
			u17.SetNextUnit(u18);
			u18.SetNextUnit(u19);
			u19.SetNextUnit(u20);
			u20.SetLastUnit(true);

			fLine2.SetUnits(new List<Unit> { u11, u12, u13, u14, u15, u16, u17, u18, u19, u20 });


			_factory.SetFactoryLines(new List<Line> { fLine1, fLine2 });

			_productId = 1;

			Console.WriteLine(" factory Created");

		}

		private void UpdateTime()
		{


			date = DateTime.Now;
			



		}


		public Simulation()
		{

			_hourStart = 3;
			_hourLastPart = 23;
			_broker = new HttpBroker();

		}



		private void CreateErrorEvent()
		{

			Random r = new Random();
			// 33% chance that an error will occure

			int i = r.Next(1, 4);
			if (i == 3)
			{
				i = r.Next(1, 101);

				//9% chance that it will be a longer error
				if (i > 91)
				{
					i = r.Next(1, 101);
					// longer error have a higher chance to be in line2

					if (i > 37)
					{
						i = r.Next(16, 39);
						{

							// m19 and m23 have a higher chance for longer error
							if (i > 29)
							{
								if (i % 2 == 0)
								{
									SetError(19, r.Next(120, 555), 4);

								}
								else
								{
									SetError(23, r.Next(120, 555), 4);
								}

							}
							else
							{
								SetError(i, r.Next(120, 450), 4);
							}




						}



					}
					else
					{
						SetError(r.Next(1, 15), r.Next(90, 390), 4);

					}


				}
				else if (i > 65)
				{



					i = r.Next(1, 101);

					if (i > 43)
					{
						//line2, m20 and m27 have higher chance for mid error
						i = r.Next(16, 37);
						if (i > 29)
						{
							if (i % 2 == 2)
							{
								SetError(20, r.Next(70, 105), 3);
							}
							else
							{
								SetError(27, r.Next(70, 105), 3);
							}

						}
						else
						{
							SetError(r.Next(16, 30), r.Next(70, 105), 3);
						}


					}
					else
					{
						SetError(r.Next(1, 16), r.Next(65, 95), 3);
					}

				}
				else
				{
					// all machines have same Chance for short error
					SetError(r.Next(1, 30), r.Next(10, 63), 3);


				}


			}



		}

		private void SetError(int machineId, int time, int errorCode)
		{
			foreach(Line l in _factory.GetFactoryLines())
			{
				foreach (Unit u in l.GetUnits())
				{
					foreach(Machine m  in u.GetMachines())
					{
						if (m.GetId() == machineId)
						{
							Console.WriteLine("ERROR CREATED on : " + machineId + " with ErrorCode: " + errorCode);
							m.SetError(time, errorCode);
						}
					}
				}
			}
		}

	}
}
