(ns sylvi-re.auth.login
  (:require [re-frame.core :as re-frame]
            [re-com.core :as re-com]))


(defn login-title []
  (let [name (re-frame/subscribe [:name])]
    (fn []
      [re-com/title
       :label "Authenticate now"
       :level :level1])))

(defn login-button-authenticate []
  [re-com/button
   :label "Login"
   :on-click #(re-frame/dispatch [:login-clicked])])

(defn login-panel []
  [re-com/v-box
   :gap "1em"
   :children [[login-title] [login-button-authenticate]]])


(defn logout-panel []
  [re-com/button
   :label "Logout"
   :on-click #(re-frame/dispatch [:logout-clicked])])