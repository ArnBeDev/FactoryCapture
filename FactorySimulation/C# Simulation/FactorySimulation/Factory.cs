using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FactorySimulation
{
    class Factory
    {

		private List<Line> _factoryLines;

		public List<Line> GetFactoryLines()
		{
			return _factoryLines;
		}

		public void SetFactoryLines(List<Line> factoryLines)
		{
			this._factoryLines = factoryLines;
		}


		public bool IsFactoryEmpty()
		{
			foreach (Line l in _factoryLines)
			{
				foreach (Unit u  in l.GetUnits())
				{
					foreach (Machine m in u.GetMachines())
					{
						if (m.GetWorkingOn() > 0)
						{

							return false;
						}
					}
				}
			}


			return true;
		}


	}
}
