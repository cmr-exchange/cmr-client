(ns ^:unit cmr.client.tests.common.const
  (:require
   [clojure.test :refer :all]
   [cmr.client.common.const :as const]))

(deftest default-environment-type
  (is (= :prod const/default-environment-type)))
