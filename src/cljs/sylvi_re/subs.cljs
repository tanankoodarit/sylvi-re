(ns sylvi-re.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
  :name
  (fn [db]
    (:name db)))

(re-frame/reg-sub
  :active-panel
  (fn [db _]
    (:active-panel db)))

(re-frame/reg-sub
  :navi-title
  (fn [db]
    (:navi-title db)))

(re-frame/reg-sub
  :authenticated
  (fn [db]
    (:isauthenticated db)))

(re-frame/reg-sub
  :routes
  (fn [db]
    (:routes db)))

(re-frame/reg-sub
  :map
  (fn [db]
    (:map db)))