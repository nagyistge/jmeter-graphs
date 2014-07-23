(ns jmeter-graphs.core
(:require [incanter.core :as incanter]
          [incanter.io :as io]
          [incanter.charts :as charts])
(:gen-class))

(defn read-csv [filepath]
  (io/read-dataset filepath))

(defn graph-response [dataset title y-label]
  (incanter/view (charts/time-series-plot
                  (incanter/sel dataset :cols 0)
                  (incanter/sel dataset :cols 1)
                  :x-label "minutes"
                  :y-label y-label
                  :title title)))

(defn graph-response-report [filepath title]
  (let [dataset (read-csv filepath)]
    (graph-response dataset title)))

(defn number-of-requests-in-a-second [filepath]
  (let [dataset (read-csv filepath)
        epochs (incanter/sel dataset :cols 0)
        epoch-map (reduce-kv (fn [c k v] (assoc c k (count v))) {} (group-by #(quot % 1000) epochs))] 
    (sort epoch-map)))

(defn request-dataset [filepath] 
   (incanter/dataset ["time" "requests"] 
                     (number-of-requests-in-a-second filepath)))

(defn graph-request-report [filepath title])

;;; Examples
;;; (-main :graph "response" :filepath "test/jmeter_graphs/aggregate-report.csv" :title "20rps")
(defn -main [& args]
  (let [{:keys [graph filepath title]} args]
    (when (= graph "response")
      (graph-response-report filepath title "response time (ms)"))))
