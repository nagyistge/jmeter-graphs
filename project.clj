(defproject jmeter-graphs "0.1.1"
  :description "A utility to graph from JMeter CSV logs"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [incanter "1.5.5"]]
  :main jmeter-graphs.core
  :aot [jmeter-graphs.core])
