# F1diots backend

The website is deployed at [f1diots.com](https://f1diots.com). It's tracking race sessions from our community server on Assetto Corsa Competizione and showing the best lap times.

## Endpoints

####  Get sessions
```/raceData?pageSize=2&page=0```
page is 0 indexed. Default sort is by descending timestamp

####  Get specific session
```/raceData/210422_233453_FP```
Pass in the ID from the raceData. Returns the (almost) full results payload
