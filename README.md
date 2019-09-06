This is just a small project to test and design a Schema based API running in the Grizzly web server.


This project is to test and design GraphQL Schema based service using the Grizzly app engine. We want two endpoint:
  - one english(default)
  - and one norwegian(no)
  
We want only ONE binding to the internal model - this is the code that is complex, so sharing this would ensure both 
apis stay in sync.

The Goal it to use the design in OTP to provide two similar APIs, but with different terminology and a few optional
elements: the Transmodel API and the GTFS API.