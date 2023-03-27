# FactoryCapture
Software that provides statistic data for machines of a factory.  

Software is currently in development but will be build up as followed:  

#### Backend:  
 - Java Spring Application  
 - Connected to NoSQL Database Couchbase  
 - receives machine data via HTTP Requests  
 - aggregates machine data and stores them in the database  
 - these data can be accessed over a web-api  
 
 #### Frontend:  
  - written in Javascript with Vue.js as Frontend framework  
  - live overview over the factory  
  - allows to analyse the productivity of the machines  
  - allows machine error backtracing  
  - allows production backtracing  

#### Factory Simulation:  
 - written in C# and Java  
 - sends every seconds a HTTP Request to the backend with machinedata  
 - is a good option as long as you do not have your own factory  
