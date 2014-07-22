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

(defn number-of-requests-per-second [filepath]
  (let [dataset (read-csv filepath)
        epochs (incanter/sel dataset :cols 0)
        epoch-map (reduce-kv (fn [c k v] (assoc c k (count v))) {} (group-by #(quot % 1000) epochs))] epoch-map))

(defn graph-requests-per-second [filepath title])

;;; Examples
;;; (-main :graph "response" :filepath "test/jmeter_graphs/aggregate-report.csv" :title "20rps")
(defn -main [& args]
  (let [{:keys [graph filepath title]} args]
    (when (= graph "response")
      (graph-response-report filepath title))))
