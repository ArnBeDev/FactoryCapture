using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FactorySimulation
{
    class Machine
    {


		private int _id;

		private float _currentPower;
		private float _idlePower;
		private float _maxPower;


		private float[] _idlePowers;
		private float[] _maxPowers;
		private int _pIndex;



		// 0=ready, 1= work done , 2= working , 3/4 errors
		private int _status;

		private int _workingOn;

		//work and error times are in seconds

		private int _workDuration;
		private int[] _workDurations;
		private int _durationIndex;

		private int _errorTime;




		public Machine(int id)
		{
			this._id = id;

		}





		public void Update()
		{
			ChangePower();

			switch (_status)
			{
				case 0:

					break;
				case 1:
					break;
				case 2:
					_workDuration--;
					if (_workDuration <= 0)
					{
						SetStatus(1);
					}

					break;
				case 3:
					_errorTime--;
					if (_errorTime < 0)
					{
						if (_workingOn > 0)
						{
							SetStatus(2);
						}
						else
						{
							SetStatus(0);
						}
					}
					break;
				case 4:
					_errorTime--;
					if (_errorTime < 0)
					{
						if (_workingOn > 0)
						{
							SetStatus(2);
						}
						else
						{
							SetStatus(0);
						}
					}
					break;
				default:
					SetStatus(0);
					break;
			}




		}



		public void SetWorkDuration(int min, int max)
		{
			Random r = new Random();
			_workDurations = new int[5];
			for (int i = 0; i < _workDurations.Length; i++)
			{
				_workDurations[i] = r.Next(min, max + 1);
				if (Simulation.FASTMODE)
				{
					_workDurations[i] = 2;
				}

			}
			_durationIndex = 0;

		}




		public int GetId()
		{
			return _id;
		}


		public void SetId(int id)
		{
			this._id = id;
		}





		public int GetStatus()
		{
			return _status;
		}


		public void SetStatus(int status)
		{
			this._status = status;

			if (status == 0)
			{
				_workingOn = 0;
			}
		}


		public int GetWorkingOn()
		{
			return _workingOn;
		}


		public void SetWorkingOn(int workingOn)
		{
			this._workingOn = workingOn;
			_status = 2;
			CreateWorkDuration();
		}


		public void GetWorkDuration()
		{
			_workDuration = _workDurations[_durationIndex];
			_durationIndex++;
			if (_durationIndex >= _workDurations.Length)
			{
				_durationIndex = 0;
			}

		}




		public int getErrorTime()
		{
			return _errorTime;
		}


		public void SetError(int errorTime, int errorState)
		{
			this._errorTime = errorTime;
			this._status = errorState;
			if (errorState < 2)
			{
				this._status = 3;
			}
		}


		private void CreateWorkDuration()
		{
			_workDuration = _workDurations[_durationIndex];
			_durationIndex++;

			if (_durationIndex >= _workDurations.Length)
			{
				_durationIndex = 0;
			}
		}


		public float GetCurrentPower()
		{
			return _currentPower;
		}


		public void SetCurrentPower(float currentPower)
		{
			this._currentPower = currentPower;
		}


		public void SetIdlePower(float min, float max)
		{
			Random r = new Random();
			_idlePowers = new float[15];
			for (int i = 0; i < _idlePowers.Length; i++)
			{
				_idlePowers[i] = r.Next((int)min,(int) max + 1) + (float)r.NextDouble();

			}
		}


		public void SetMaxPower(float min, float max)
		{
			Random r = new Random();
			_maxPowers = new float[15];
			for (int i = 0; i < _maxPowers.Length; i++)
			{
				_maxPowers[i] = r.Next((int)min, (int)max + 1) + (float)r.NextDouble();
			}
			_pIndex = 0;

		}


		private void ChangePower()
		{
			_idlePower = _idlePowers[_pIndex];
			_maxPower = _maxPowers[_pIndex];

			_pIndex++;
			if (_pIndex >= _maxPowers.Length)
			{
				_pIndex = 0;
			}

			if (_status == 2)
			{
				_currentPower = _maxPower;

			}
			else
			{
				_currentPower = _idlePower;
			}

		}
	}
}
