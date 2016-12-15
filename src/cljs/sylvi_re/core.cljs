(ns sylvi-re.core
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [sylvi-re.events]
              [sylvi-re.subs]
              [sylvi-re.routes :as routes]
              [sylvi-re.views :as views]
              [sylvi-re.config :as config]
              [re-frisk.core :refer [enable-re-frisk!]]))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (enable-re-frisk!)
    (println "dev mode")))

(defn mount-root []
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (routes/app-routes)
  (re-frame/dispatch-sync [:initialize-db])
  (dev-setup)
  (mount-root))
