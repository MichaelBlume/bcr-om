(ns om-stuff
  (:require [om.core :as om :include-macros true]
            [clojure.string :as string]
            [om.dom :as dom :include-macros true]))

(enable-console-print!)

(def app-state (atom {:text "Hello world!"}))

(comment

(do


(reset! app-state
  {:people
     [{:type :student :first "Ben" :last "Bitdiddle" :email "benb@mit.edu"}
      {:type :student :first "Alyssa" :middle-initial "P" :last "Hacker"
       :email "aphacker@mit.edu"}
      {:type :professor :first "Gerald" :middle "Jay" :last "Sussman"
       :email "metacirc@mit.edu" :classes [:6001 :6946]}
      {:type :student :first "Eva" :middle "Lu" :last "Ator" :email "eval@mit.edu"}
      {:type :student :first "Louis" :last "Reasoner" :email "prolog@mit.edu"}
      {:type :professor :first "Hal" :last "Abelson" :email "evalapply@mit.edu"
       :classes [:6001]}]
   :classes
     {:6001 "The Structure and Interpretation of Computer Programs"
      :6946 "The Structure and Interpretation of Classical Mechanics"
      :1806 "Linear Algebra"}})

(defn display [show]
  (if show
    #js {}
    #js {:display "none"}))

(defn handle-change [e text owner]
  (om/transact! text (fn [_] (.. e -target -value))))

(defn commit-change [text owner]
  (om/set-state! owner :editing false))

(extend-type js/String
  om/IValue
  (-value [s] (str s))
  ICloneable
  (-clone [s] (js/String. s)))

(def newline-code 13)

(defn editable [text owner]
  (reify
    om/IInitState
    (init-state [_]
      {:editing false})
    om/IRenderState
    (render-state [_ {:keys [editing]}]
      (dom/li nil
        (dom/span #js {:style (display (not editing))}
                  (om/value text))
        (dom/input
          #js {:style (display editing)
               :value (om/value text)
               :onChange #(handle-change % text owner)
               :onKeyPress #(when (== (.-keyCode %) newline-code)
                              (commit-change text owner))
               :onBlur (fn [e] (commit-change text owner))})
        (dom/button
          #js {:style (display (not editing))
               :onClick #(om/set-state! owner :editing true)}
          "Edit")))))

(swap! app-state update-in [:classes :1806] (constantly "Intro QM"))

(defn middle-name [{:keys [middle middle-initial]}]
  (cond
    middle (str " " middle)
    middle-initial (str " " middle-initial ".")))

(defn display-name [{:keys [first last] :as contact}]
  (str last ", " first (middle-name contact)))

(defmulti entry-view (fn [person _] (:type person)))

(defmethod entry-view :student
  [student owner]
  (reify
    om/IRender
    (render [_]
      (dom/li nil (display-name student)))))

(defmethod entry-view :professor
  [professor owner]
  (reify
    om/IRender
    (render [_]
      (dom/li nil
        (dom/div nil (display-name professor))
        (dom/label nil "Classes")
        (apply dom/ul nil
          (for [cl (:classes professor)]
            (dom/li nil (om/value cl))))))))

(defn people [app]
  (->>
    app
    :people
    (map (fn [x]
           (if (:classes x)
             (update-in x [:classes]
               (fn [cs] (mapv (:classes app) cs)))
             x)))
    (into [])))

(people @app-state)

(defn registry-view [app owner]
  (reify
    om/IRenderState
    (render-state [_ state]
      (dom/div #js {:id "registry"}
        (dom/h2 nil "Registry")
        (apply dom/ul nil
          (om/build-all entry-view (people app)))))))

(defn classes-view [app owner]
  (reify
    om/IRender
    (render [_]
      (dom/div #js {:id "classes"}
        (dom/h2 nil "Classes")
        (apply dom/ul nil
          (for [[_ cname] (:classes app)]
            (dom/li nil cname)))))))

(om/root classes-view app-state
  {:target (. js/document (getElementById "classes"))})

(om/root registry-view app-state
  {:target (. js/document (getElementById "registry"))})

))
