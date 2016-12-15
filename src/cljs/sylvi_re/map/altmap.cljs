(ns sylvi-re.map.altmap
  (:require [re-frame.core :as re-frame]
            [re-com.core :as re-com]
            [reagent.core :as reagent]
            [beicon.core :as rx]))

(defn markerPlace [google-map location]
  (js/google.maps.Marker. (clj->js {"position" (google.maps.LatLng. (:lat location), (:lng location))
                                    "map"      google-map
                                    "icon"     "https://maps.google.com/mapfiles/kml/shapes/info-i_maps.png"
                                    "title"    "Route 1"}))
  )

(defn route-places [google-map routes]
  (doseq [location routes]
    (markerPlace google-map location)))

(defn route-lines [google-map routes]
  (let [lines (js/google.maps.Polyline. (clj->js {:path         routes
                                                  :strokeColor  "#FF0000" :strokeOpacity 1.0,
                                                  :strokeWeight 2}))]
    (.setMap lines google-map)
    )
  )

(defn gmap-inner []
  (let [gmap    (atom nil)
        options (clj->js {"zoom" 7})
        update  (fn [comp]
                  (let [{:keys [latitude longitude routes]} (reagent/props comp)
                        latlng (js/google.maps.LatLng. latitude longitude)]
                    (comment  (.setPosition (:marker @gmap) latlng))
                    (.panTo (:map @gmap) latlng)
                    (route-places (:map @gmap) routes)
                    (route-lines (:map @gmap) routes)))]

    (reagent/create-class
      {:reagent-render (fn []
                         [:div
                          [:h4 "Map"]
                          [:div#map-canvas {:style {:height "600px"}}]])

       :component-did-mount (fn [comp]
                              (let [canvas  (.getElementById js/document "map-canvas")
                                    gm      (js/google.maps.Map. canvas options)
                                    marker  (js/google.maps.Marker. (clj->js {:map gm :title "Drone"}))]
                                (reset! gmap {:map gm :marker marker}))
                              (update comp))

       :component-did-update update
       :display-name "gmap-inner"})))



(defn google-map-component []
  (let [pos (re-frame/subscribe [:map])]   ;; obtain the data
    (fn []
      [gmap-inner @pos])))

;;---------------------

(def routes
  [{:lat 61.387595 :lng 24.5059}
   {:lat 60.8806011 :lng 24.4371625}
   {:lat 60.6477312 :lng 24.7697714}
   {:lat 60.5027627 :lng 24.9842778}
   {:lat 60.3300189 :lng 24.2560676}
   ]
  )

(defn stream []
  (doseq [location routes]
    (re-frame/dispatch [:new-route-location location])
    ))

;; (js/setInterval #((stream)) 1000)