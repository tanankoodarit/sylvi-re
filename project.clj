(defproject sylvi-re "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.229"]
                 [reagent "0.6.0"]
                 [re-frame "0.8.0"]
                 [org.clojure/core.async "0.2.391"]
                 [re-com "0.8.3"]
                 [secretary "1.2.3"]
                 [funcool/beicon "2.6.1"]
                 [re-frisk "0.3.2"]
                 [cljs-http "0.1.42"]
                 [figwheel-sidecar "0.5.0"]]

  :plugins [[lein-cljsbuild "1.1.4"]
            [lein-less "1.7.5"]
            [lein-s3-static-deploy "0.1.0"]
            [org.clojure/data.json "0.2.6"]
            ]

  :min-lein-version "2.5.3"

  :source-paths ["src/clj"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"
                                    "test/js"]

  :figwheel {:css-dirs ["resources/public/css"]}

  :less {:source-paths ["less"]
         :target-path  "resources/public/css"}

  :profiles
  {:dev
   {:dependencies [[binaryage/devtools "0.8.2"]
                   ]

    :plugins      [[lein-figwheel "0.5.7"]
                   [lein-doo "0.1.7"]
                   ]
    }}

  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src/cljs"]
     :figwheel     {:on-jsload "sylvi-re.core/mount-root"}
     :compiler     {:main                 sylvi-re.core
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/out"
                    :asset-path           "js/compiled/out"
                    :source-map-timestamp true
                    :preloads             [devtools.preload]
                    :external-config      {:devtools/config {:features-to-install :all}}
                    :externs              ["externs.js"]
                    }}

    {:id           "min"
     :source-paths ["src/cljs"]
     :compiler     {:main            sylvi-re.core
                    :output-to       "resources/public/js/compiled/app.js"
                    :optimizations   :advanced
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false
                    :externs         ["externs.js"]
                    }}

    {:id           "test"
     :source-paths ["src/cljs" "test/cljs"]
     :compiler     {:main          sylvi-re.runner
                    :output-to     "resources/public/js/compiled/test.js"
                    :output-dir    "resources/public/js/compiled/test/out"
                    :optimizations :none
                    :externs       ["externs.js"]
                    }}
    ]}
  :aws {:access-key       ~(System/getenv "AWS_ACCESS_KEY_ROO")
        :secret-key       ~(System/getenv "AWS_SECRET_ACCESS_KEY_ROO")
        :s3-static-deploy {:bucket     "s3-website-prod.sylvi.saatanankoodarit.fi"
                           :local-root "resources/public/"}}

  )
