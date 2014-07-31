(defproject bcr-om "NOT_DEPLOYED"
  :source-paths ["src/clj" "src/cljs"]
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2268"]
                 [org.clojure/core.async "0.1.267.0-0d7780-alpha"]
                 [om "0.6.5"] 
                 [ring "1.2.1"]
                 [compojure "1.1.6"]
                 [enlive "1.1.5"]]

  :profiles {:dev {:plugins [[com.cemerick/austin "0.1.3"]
                             [lein-cljsbuild "1.0.4-SNAPSHOT"]]
                   :cljsbuild {:builds [{:source-paths ["src/cljs"]
                                         :compiler {:output-to "target/classes/public/app.js"
                                                    :optimizations :simple
                                                    :pretty-print true}}]}}})

