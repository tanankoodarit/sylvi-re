(ns sylvi-re.events
  (:require [re-frame.core :as re-frame]
            [sylvi-re.db :as db]
            [secretary.core :as secretary]))

(re-frame/reg-event-db
  :initialize-db
  (fn [_ _]
    db/default-db))

(re-frame/reg-event-db
  :set-active-panel
  (fn [db [_ active-panel]]
    (assoc db :active-panel active-panel)))

(re-frame/reg-event-db
  :login-clicked
  (fn [db [_ _]]
    (assoc db :isauthenticated true)))

(re-frame/reg-event-db
  :logout-clicked
  (fn [db [_ _]]
    (assoc db :isauthenticated false)))

(re-frame/reg-event-db
  :new-route-location
  (fn [db [_ location]]
    (do
      (println location)
      (assoc-in db [:map :routes] (conj (get-in [:map :routes] db) location)))))
