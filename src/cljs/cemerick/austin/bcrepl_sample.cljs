(ns cemerick.austin.bcrepl-sample
  (:require [clojure.browser.repl]))

(defn hello
  []
  (js/alert "hihi"))

(defn whoami
  []
  (.-userAgent js/navigator))

(comment
  (whoami))
