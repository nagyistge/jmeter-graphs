# jmeter-graphs

## Description
A utility to graph from JMeter CSV logs. The currently supported graphs are response times, requests per second and throughput per second.

### Example Graphs
* [Response times](./examples/sample-response-times.png)
* [Requests per second]( ./examples/sample-requests.png)
* [Throughput per second]( ./examples/sample-throughput.png)

## JMeter Set-up
Create a JMeter listener and enter a filepath in the edit box labelled, "Write results to file". I use the Aggegrate Report listener and input './aggregate-report.csv'.

You can use the default log configuration, but the prerequisites are that the epoch time and elapsed time are in the first and second columns respectively, a header is not used and the logs are in CSV format. 

```log
1406193755956,202, ...
```

## Installation and Example Run
There is a standalone uberjar placed in bin. This first parameter is the type of graph you require and the second argument is the filepath of the JMeter report.

```bash 
$ git clone git@github.com:AidyLewis/jmeter-graphs.git
$ cd bin
$ java -jar jmeter-graphs-0.1.1.jar "responses" "../test/jmeter_graphs/fixtures/aggregate-report.csv"
$ java -jar jmeter-graphs-0.1.1.jar "requests" "../test/jmeter_graphs/fixtures/aggregate-report.csv"
$ java -jar jmeter-graphs-0.1.1.jar "throughput" "../test/jmeter_graphs/fixtures/aggregate-report.csv"
```

## Implementation 

### Calculations

* **Response Times** => Direct reading of epoch and elapsed times
* **Requests per second** => Grouped and counted the number of samples per second 
* **Throughput per second** => Similar method to requests per second, but added epoch time to the elapsed time

### Graphing Library
I have used Clojure's [Incanter](https://github.com/incanter/incanter) as a graphing library.

## License

Copyright © 2014 Adrian Lewis. Distributed under the Eclipse Public License.
