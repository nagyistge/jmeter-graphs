# jmeter-graphs

## Description
A utility to graph from JMeter CSV logs. The currently supported graphs are response times, requests per second and throughput per second.

### Example Graphs
* [Response times](./example-graphs/sample-response-times.png)
* [Requests per second]( ./example-graphs/sample-requests.png)
* [Throughput per second]( ./example-graphs/sample-throughput.png)

## JMeter Set-up
Create a JMeter listener and enter a filepath in the edit box labelled, "Write results to file". I use the Aggegrate Report listener and input './aggregate-report.csv'.

You can use the default log configuration, but the prerequisites are that the Unix time and elapsed time are in the first and second columns respectively, a header is not used and the logs are in CSV format. 

```log
1406193755956,202, ...
```

## Installation and Example Run
There is a standalone uberjar placed in bin. This first parameter is the type of graph you require and the second argument is the filepath of the JMeter report.

```bash 
$ git clone git@github.com:aidylewis/jmeter-graphs.git
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

## Questions
**Why is my throughput per second sometimes greater than my requests per second?**

JMeter has a constant throughput timer that fixes requests per minute. We can assure 20 requests per second(rps) with
(* 60 20) => 1200. 

We cannot assure a fixed throughput (+ epoch-times elapsed-times) as elapsed time is variable. At 20rps we may have a throughput of 15 for one second and 25 the subsequent second. 

Running the below function through a Clojure REPL:
```clojure
(throughput-dataset "test/jmeter_graphs/fixtures/aggregate-report.csv")
````

Will give us an Incanter dataset:
```log
| 1406194240000 |         20 |
| 1406194241000 |         20 |
| 1406194242000 |         15 |
| 1406194243000 |         25 |
| 1406194244000 |         20 |
```

Which show us the time period the throughput per second occurred. In one second we have 15, whilst the next second returns 25.
