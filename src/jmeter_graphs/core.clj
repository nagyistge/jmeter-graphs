(ns jmeter-graphs.core
(:require [incanter.core :as incanter]
          [incanter.io :as io]
          [incanter.charts :as charts])
(:gen-class))

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

(defn count-samples-by-second [samples]
  (frequencies (map #(quot % 1000) samples)))

(defn number-of-requests-in-a-second [filepath]
  (let [dataset (read-csv filepath)
        epochs (incanter/sel dataset :cols 0)
        seconds-map (count-samples-by-second epochs)] 
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

(defn sum-epoch-and-elapsed-times [filepath]
  (let [dataset (read-csv filepath)
        col0 (incanter/sel dataset :cols 0)
        col1 (incanter/sel dataset :cols 1)]
    (map + col0 col1)))

(defn throughput-by-second [filepath]
  (let [throughput (sum-epoch-and-elapsed-times filepath)
     seconds-map (count-samples-by-second throughput)]
    seconds-map))

(defn throughput-dataset [filepath] 
  (let [throughput-per-second (throughput-by-second filepath)
        sorted-dataset (sort (convert-seconds-to-milliseconds throughput-per-second))]
    (incanter/dataset ["time" "throughput"] 
                      sorted-dataset)))

(defn graph-throughput [filepath]
  (let [dataset (throughput-dataset filepath)]
    (graph-dataset dataset "Throughput" "Throughout Per Second")))

;;; Examples
;;; (-main "response" "test/jmeter_graphs/fixtures/aggregate-report.csv" "20rps")
;;; (-main "requests" "test/jmeter_graphs/fixtures/aggregate-report.csv")
;;; (-main "throughput" "test/jmeter_graphs/fixtures/aggregate-report.csv")
(defn -main [& args]
  (cond
   (= (first args) "response") (graph-response (second args) (nth args 2))
   (= (first args) "requests") (graph-request (second args))
   (= (first args) "throughput") (graph-throughput (second args))
   :else (println "You did not pass a valid graph")))




