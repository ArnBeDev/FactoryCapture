using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FactorySimulation
{
    class Unit
    {


		private List<Machine> _machines;
		private Unit _nextUnit;
		private bool _lastUnit;


		public void Update()
		{

			if (_machines.Count == 1)
			{
				Machine m = _machines[0];

				if (m.GetStatus() == 1)
				{
					if (!_lastUnit && _nextUnit.GetReady())
					{

						if (m.GetWorkingOn() <= 0)
						{
							m.SetStatus(0);
						}
						else
						{
							_nextUnit.PushProduct(m.GetWorkingOn());
							m.SetStatus(0);
						}

					}
					else if (_lastUnit)
					{
						m.SetStatus(0);
					}
				}

				m.Update();


			}
			else
			{
				Machine m1 = _machines[0];
				Machine m2 = _machines[1];

				if (m1.GetStatus() == 1 && m2.GetStatus() == 1)
				{
					if (!_lastUnit && _nextUnit.GetReady())
					{
						_nextUnit.PushProduct(m1.GetWorkingOn());
						m1.SetStatus(0);
						m2.SetStatus(0);


					}
					else if (_lastUnit)
					{
						m1.SetStatus(0);
						m2.SetStatus(0);
					}
				}


				m1.Update();
				m2.Update();


			}




		}


		public List<Machine> GetMachines()
		{
			return _machines;
		}


		public void SetMachines(List<Machine> machines)
		{
			this._machines = machines;
		}

		public void PushProduct(int productId)
		{
			foreach(Machine m in _machines)
			{
				m.SetWorkingOn(productId);

			}
		}

		public bool GetReady()
		{

			foreach(Machine m in _machines)
			{
				if (m.GetStatus() != 0)
				{
					return false;
				}

			}
			return true;
		}


		public Unit GetNextUnit()
		{
			return _nextUnit;
		}


		public void SetNextUnit(Unit nextUnit)
		{
			this._nextUnit = nextUnit;
		}


		public bool IsLastUnit()
		{
			return _lastUnit;
		}


		public void SetLastUnit(bool lastUnit)
		{
			this._lastUnit = lastUnit;
		}


	}
}
