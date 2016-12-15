(ns sylvi-re.services.pois
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))

;; https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=62.1332752,25.0954844&type=store|storage&radius=50000&sensor=false&key=AIzaSyARxMs5p0tZEXC6wD7ZPFTOWx_0jewi39w

(def url1 "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=AIzaSyARxMs5p0tZEXC6wD7ZPFTOWx_0jewi39w&radius=50000&sensor=false&location=62.1332752%2C25.0954844&type=store%7Cstorage")
(def url2 "http://programs.api.yle.fi/v1/id/1-3133398.json?app_id=SANDBOX&app_key=SANDBOX")
(def url3 "https://api.github.com/users?since=135")
(def url4 "https://maps.googleapis.com/maps/api/place/details/json?placeid=ChIJN1t_tDeuEmsRUsoyG83frY4&key=AIzaSyARxMs5p0tZEXC6wD7ZPFTOWx_0jewi39w")

(defn mun []
  (go (let [response (<! (http/get url2
                                     {:timeout 3000
                                      :callback-name "callback"
                                      :query-params  {}}))]
        (prn (:status response))
        (prn response))))