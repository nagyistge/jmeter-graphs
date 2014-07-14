(ns jmeter-graphs.core
(:require [incanter.core :as incanter]
          [incanter.io :as io]
          [incanter.charts :as charts]))

(defn read-csv [filepath]
  (io/read-dataset filepath))

(def data (read-csv filepath))

(defn graph [title]
  (incanter/view (charts/time-series-plot
                  (incanter/sel data :cols 0)
                  (incanter/sel data :cols 1)
                  :x-label "minutes"
                  :y-label "response time (ms)"
                  :title title)))

(defn graph-report [filepath title]
  (read-csv filepath)
  (graph title))


























