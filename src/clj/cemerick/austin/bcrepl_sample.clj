(ns cemerick.austin.bcrepl-sample
  (:require [cemerick.austin.repls :refer (browser-connected-repl-js)]
            [net.cgrand.enlive-html :as enlive]
            [compojure.route :refer (resources)]
            [compojure.core :refer (GET defroutes)]  
            ring.adapter.jetty
            [clojure.java.io :as io]))                                 

(defn repage []
  (enlive/deftemplate page
    (io/resource "index.html")
    []
    [:body] (enlive/append
              (enlive/html [:script (browser-connected-repl-js)]))))


(defroutes site
  (resources "/") 
  (GET "/*" req (do (repage) (page))))

(defn run
  []
  (defonce ^:private server
    (ring.adapter.jetty/run-jetty #'site {:port 8080 :join? false}))
  server)

