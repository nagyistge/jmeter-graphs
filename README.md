# jmeter-graphs

A utility to graph from JMeter CSV logs.

## Usage

Create a JMeter listener and enter a filepath in the edit box labelled, "Write results to file". I use the Aggegrate Report listener and input './aggregate-report.csv'.

You can use the default log configuration, but the prerequisites are that the epoch time and elapsed time are in the first and second columns respectively, there is no header used and the logs are in CSV format. 

```log
1406193755956,202, ...
```

## Implementation 

I have used 



## License

Copyright © 2014 Adrian Lewis. Distributed under the Eclipse Public License.
