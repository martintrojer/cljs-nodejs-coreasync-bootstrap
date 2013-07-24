(ns cljs-nodejs-coreasync-bootstrap.fs
  (:require [cljs.core.async :as async :refer [<! >! chan close! timeout]])
  (:require-macros [cljs.core.async.macros :as m :refer [go alt!]]))

(defn test [filename]

  (let [fs (js/require "fs")
        open-ch (chan)
        read-ch (chan)

        handle-open (fn [fd]
                      (let [buf (js/Buffer. 100)]
                        (fs/read fd buf 0 (.-length buf) 0
                                 (fn [_ _ _] (go (>! read-ch [fd buf]))))))
        handle-read (fn [fd buf]
                      (println "read:" buf)
                      (fs/close fd))]

    (fs/open filename "r"
             (fn [_ fd] (go (>! open-ch fd))))

    (go
     (let [fd (<! open-ch)]
       (handle-open fd)))

    (go
     (let [[fd buf] (<! read-ch)]
       (handle-read fd buf)))))
