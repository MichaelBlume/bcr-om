(ns start-bcr
  (:require cemerick.austin.bcrepl-sample))

(comment
  "This will get this tutorial running in your browser with vim sending
  clojurescript to your browser window"
  
  "I don't think it works in chrome though."

  "start the ring server"

  (cemerick.austin.bcrepl-sample/run)
  
  "next eval this"
  
  (def repl-env (reset! cemerick.austin.repls/browser-repl-env
                        (cemerick.austin/repl-env)))

  "now a repl env exists! Now open localhost:8080 in your browser as requested"

  "Almost done, now eval this, open one of the cljs files in this repo, and
  start working from there"

  (cemerick.piggieback/cljs-repl :repl-env repl-env))
