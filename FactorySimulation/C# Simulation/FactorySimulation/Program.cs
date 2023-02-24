using System;

namespace FactorySimulation
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Hello World!");
            Simulation simulation = new Simulation();
            simulation.StartSimulation();
        }
    }
}
