using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FactorySimulation
{
    class Line
    {

		private List<Unit> _units;



		public List<Unit> GetUnits()
		{
			return _units;
		}

		public void SetUnits(List<Unit> units)
		{
			this._units = units;
		}

	}
}
