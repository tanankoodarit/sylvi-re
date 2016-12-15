(ns sylvi-re.core-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [sylvi-re.core :as core]))

(deftest fake-test
  (testing "fake description"
    (is (= 1 2))))
