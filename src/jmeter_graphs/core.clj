(ns jmeter-graphs.core
(:require [incanter.core :as incanter]
          [incanter.io :as io]
          [incanter.charts :as charts])
(:gen-class))

;;; response
(defn read-csv [filepath]
  (io/read-dataset filepath))

(defn graph-dataset [dataset title y-label]
  (incanter/view (charts/time-series-plot
                  (incanter/sel dataset :cols 0)
                  (incanter/sel dataset :cols 1)
                  :x-label "Test Duration (mins)"
                  :y-label y-label
                  :title title)))

(defn graph-response [filepath title]
  (let [dataset (read-csv filepath)]
    (graph-dataset dataset title "Response Times (ms)")))

;;; request graph
(defn number-of-requests-in-a-second [filepath]
  (let [dataset (read-csv filepath)
        epochs (incanter/sel dataset :cols 0)
        seconds-map (reduce-kv (fn [c k v] (assoc c k (count v))) {} (group-by #(quot % 1000) epochs))] 
    seconds-map))

(defn convert-seconds-to-milliseconds [seconds]
  (apply merge 
         (map (fn [[k v]] {(* k 1000) v}) seconds)))

(defn request-dataset [filepath] 
  (let [requests-per-second (number-of-requests-in-a-second filepath)
        sorted-dataset (sort (convert-seconds-to-milliseconds requests-per-second))]
    (incanter/dataset ["time" "requests"] 
                      sorted-dataset)))

(defn graph-request [filepath]
  (let [dataset (request-dataset filepath)]
    (graph-dataset dataset "Requests" "Requests Per Second" )))

;;; throughput
(defn sum-epoch-and-elapsed-times [filepath]
  (let [dataset (read-csv filepath)
        col0 (incanter/sel dataset :cols 0)
        col1 (incanter/sel dataset :cols 1)]
    (map + col0 col1)))

(defn throughput-by-second [filepath]
  (let [throughput (sum-epoch-and-elapsed-times filepath)
     seconds-map (reduce-kv (fn [c k v] (assoc c k (count v))) {} (group-by #(quot % 1000) throughput))]
    seconds-map))

(defn throughput-dataset [filepath] 
  (let [requests-per-second (throughput-by-second filepath)
        sorted-dataset (sort (convert-seconds-to-milliseconds requests-per-second))]
    (incanter/dataset ["time" "throughput"] 
                      sorted-dataset)))

(defn graph-throughput [filepath]
  (let [dataset (throughput-dataset filepath)]
    (graph-dataset dataset "Throughput" "Throughout Per Second")))

;;; Examples
;;; (-main :graph "response" :filepath "test/jmeter_graphs/fixtures/aggregate-report.csv" :title "20rps")
;;; (-main :graph "requests" :filepath "test/jmeter_graphs/fixtures/aggregate-report.csv")
;;; (-main :graph "throughput" :filepath "test/jmeter_graphs/fixtures/aggregate-report.csv")
(defn -main [& args]
  (let [{:keys [graph filepath title]} args]
    (cond 
     (= graph "response") (graph-response filepath title)
     (= graph "requests") (graph-request filepath)
     (= graph "throughput") (graph-throughput filepath))))

