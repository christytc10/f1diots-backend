####  Get sessions
```/raceData?limit=10&offset=0```
Use limit and offset for pagination. Default sort is by descending timestamp (i.e. 
the latest session would be page 1, result 1)

####  Get specific session
```/raceData/210422_233453_FP```
Pass in the ID from the raceData. Returns the (almost) full results payload