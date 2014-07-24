# jmeter-graphs

## Description
A utility to graph from JMeter CSV logs. The currently supported graphs are response times, requests per second and throughput per second.

### Example Graphs

## JMeter Set-up
Create a JMeter listener and enter a filepath in the edit box labelled, "Write results to file". I use the Aggegrate Report listener and input './aggregate-report.csv'.

You can use the default log configuration, but the prerequisites are that the epoch time and elapsed time are in the first and second columns respectively, there is no header used and the logs are in CSV format. 

```log
1406193755956,202, ...
```

## Installation

## Implementation 

### Calculations

* Response Times => Direct reading of epoch and elapsed times
* Requests per second => Grouped and counted the number of samples per second 
* Throughput per second => Similar method to requests per second, but added epoch times to the elapsed time

### Graphing Library
I have used Clojure's [Incanter](https://github.com/incanter/incanter) as a graphing library.

## License

Copyright © 2014 Adrian Lewis. Distributed under the Eclipse Public License.
