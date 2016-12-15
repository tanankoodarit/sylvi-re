(ns sylvi-re.views
  (:require [re-frame.core :as re-frame]
            [re-com.core :as re-com]
            [sylvi-re.auth.login :refer [login-panel logout-panel]]
            [sylvi-re.map.altmap :refer [google-map-component]]))


;; home

(defn home-title []
  (let [name (re-frame/subscribe [:name])]
    (fn []
      [re-com/title
       :label (str "Hello from " @name ". This is the Home Page.")
       :level :level1])))

(defn link-to-about-page []
  [re-com/hyperlink-href
   :label "go to About Page"
   :href "#/about"])

(comment defn home-panel []
         [re-com/v-box
          :gap "1em"
          :children [[home-title] [link-to-about-page]]])

(defn home-panel []
  (let [routes (re-frame/subscribe [:routes])]
    (fn []
      [google-map-component])))


;; about

(defn about-title []
  [re-com/title
   :label "This is the About Page."
   :level :level1])

(defn link-to-home-page []
  [re-com/hyperlink-href
   :label "go to Home Page"
   :href "#/"])

(defn about-panel []
  [re-com/v-box
   :gap "1em"
   :children [[about-title] [link-to-home-page]]])

;; Navi

(defn navi-title []
  (let [title (re-frame/subscribe [:navi-title])]
    (fn []
      [re-com/title
       :label @title
       :level :level1]
      )))

(defn navi []
  (let [authenticated (re-frame/subscribe [:authenticated])]
    (fn []
      [re-com/v-box
       :gap "1em"
       :children [
                  [re-com/hyperlink-href
                   :label "Map"
                   :href "#/"]
                  [re-com/hyperlink-href
                   :label "About"
                   :href "#/about"]
                  (when @authenticated [logout-panel])]]
      )))


;; main

(defmulti panels identity)
(defmethod panels :home-panel [] [home-panel])
(defmethod panels :about-panel [] [about-panel])
(defmethod panels :default [] [:div])

(defn show-panel [panel-name]
  [panels panel-name])

(defn authenticated-panel []
  (let [active-panel (re-frame/subscribe [:active-panel])]
    (fn []
      [re-com/h-split
       :height "100%"
       :initial-split 15
       :panel-1 [re-com/scroller
                 ;:size  "none"
                 :v-scroll :auto
                 :h-scroll :off
                 :child [re-com/v-box
                         :size "1"
                         :children [[navi-title] [navi]]]]
       :panel-2 [re-com/scroller
                 ;:size  "none"
                 :height "100%"
                 :v-scroll :auto
                 :h-scroll :off
                 :child [re-com/v-box
                         :size "1"
                         :children [[panels @active-panel]]]]]
      )))


(defn main-panel []
  (let [authenticated (re-frame/subscribe [:authenticated])]
    [re-com/box
     :height "100%"
     :child (if (true? @authenticated)
              [authenticated-panel]
              [login-panel])
     ]

    ))
