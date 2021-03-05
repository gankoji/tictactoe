(ns tictactoe.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))



(defn checkRange
  "Checks that move inputs are on the board."
  [input]
  (and (> input -1) (< input 3)))

(defn getMoveInRange
  []
  (let [move (Integer/parseInt (read-line))]
    (if (checkRange move)
      move
      (do
        (println "Out of range. Try again.")
        (getMoveInRange)))))

(defn getUserMove
  "Asks user for input. Expects two integers."
  []
  (println "Please input your move.")
  (println "Row:")
  (let [row (getMoveInRange)]
    (println "Col:")
    (let [column (getMoveInRange)]
      (vec [row column]))))

(def gameBoard ["-" "-" "-" "-" "-" "-" "-" "-" "-"])
(println gameBoard)
(defn updateBoard
  [move currentBoard token]
  (let [[row col] move]
  (assoc currentBoard (+ (* 3 row) col) token)))

(def firstMove (updateBoard (getUserMove) gameBoard "X"))
(println firstMove)
(println (updateBoard (getUserMove) firstMove "O"))
