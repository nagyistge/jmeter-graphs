(ns jmeter-graphs.core
(:require [incanter.core :as incanter]
          [incanter.io :as io]
          [incanter.charts :as charts])
(:gen-class))

;;; response graph
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

(defn convert-seconds-to-milliseconds [filepath]
  (let [seconds-map (number-of-requests-in-a-second filepath)] 
    (apply merge 
           (map (fn [[k v]] {(* k 1000) v}) seconds-map))))

(defn request-dataset [filepath] 
  (let [sorted-dataset (sort (convert-seconds-to-milliseconds filepath))]
    (incanter/dataset ["time" "requests"] 
                      sorted-dataset)))

(defn graph-request [filepath]
  (let [dataset (request-dataset filepath)]
    (graph-dataset dataset "Requests" "Requests Per Second" )))

;;; Examples
;;; (-main :graph "response" :filepath "test/jmeter_graphs/fixtures/aggregate-report.csv" :title "20rps")
;;; (-main :graph "requests" :filepath "test/jmeter_graphs/fixtures/aggregate-report.csv")
(defn -main [& args]
  (let [{:keys [graph filepath title]} args]
    (cond 
     (= graph "response") (graph-response filepath title)
     (= graph "requests") (graph-request filepath))))

