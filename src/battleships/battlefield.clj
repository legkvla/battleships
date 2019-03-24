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


(defrecord ShipTile [x y wounded? killed?])

;Example of ships list [[[1 1] [1 2]] [3 3]]

(defn get-ship [battle-map x y]
  (get battle-map (str x "_" y))
  )

(defn render-ship [battle-map ship-size]
  (loop [
    tile (ShipTile. (rand-int (- map-size ship-size)) (rand-int map-size) false false)
    count 0
    res {}
    ]
    (if (< count ship-size)
      (recur
        (ShipTile. (inc (:x tile)) (:y tile) false false)
        (inc count)
        (assoc battle-map
          (str (:x tile) "_" (:y tile))
          tile
          )
        )
      (assoc battle-map
        (str (:x tile) "_" (:y tile))
        tile
        )
      )
    )
  )

(defn create-open-map []
  (-> {}
    (render-ship 3)
    (render-ship 2)
    (render-ship 2)
    (render-ship 1)
    (render-ship 1)
    (render-ship 1)
    )
  )

(defn fight-ship [battle-map x y]
  (if-let [ship (get-ship battle-map x y)]
    (assoc-in battle-map [(str x "_" y) :wounded?] true)
    )
  )
