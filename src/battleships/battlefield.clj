(ns battleships.battlefield)

(def map-size 9)

(defrecord FoggedMapTile [x y wounded? killed? opened?])

(defn create-fogged-map []
  (let [
    rows (range 1 map-size)
    cols (range 1 map-size)
    tiles (map
      (fn [row col] (FoggedMapTile. row col false false false))
      rows cols
      )
    ]
    (zipmap
      (map #(str (:x %) "_" (:y %)) tiles)
      tiles
      )
    )
  )
