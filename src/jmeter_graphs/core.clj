(ns jmeter-graphs.core
(:require [incanter.core :as incanter]
          [incanter.io :as io]
          [incanter.charts :as charts]))

(defn read-csv [filepath]
  (io/read-dataset filepath))

(defn graph [dataset title]
  (incanter/view (charts/time-series-plot
                  (incanter/sel dataset :cols 0)
                  (incanter/sel dataset :cols 1)
                  :x-label "minutes"
                  :y-label "response time (ms)"
                  :title title)))

(defn graph-report [filepath title]
  (let [dataset (read-csv filepath)]
    (graph dataset title)))


























