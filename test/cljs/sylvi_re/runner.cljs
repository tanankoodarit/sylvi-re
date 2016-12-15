(ns sylvi-re.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [sylvi-re.core-test]))

(doo-tests 'sylvi-re.core-test)
