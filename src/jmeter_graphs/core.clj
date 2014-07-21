(ns jmeter-graphs.core
(:require [incanter.core :as incanter]
          [incanter.io :as io]
          [incanter.charts :as charts])
(:gen-class))

(defn read-csv [filepath]
  (io/read-dataset filepath :header true))

(defn graph-response [dataset title]
  (incanter/view (charts/time-series-plot
                  (incanter/sel dataset :cols 0)
                  (incanter/sel dataset :cols 1)
                  :x-label "minutes"
                  :y-label "response time (ms)"
                  :title title)))

(defn graph-response-report [filepath title]
  (let [dataset (read-csv filepath)]
    (graph-response dataset title)))

(defn -main [& args]
  (when (= (first args) "response") 
    (graph-response-report (second args) (nth args 2))))


;;;
(def epochs '(1405060620301 1405060625735 1405060626141
                                 1405060625559 1405060627285 1405060626927 1405060625174 1405060626962 140506062616))


