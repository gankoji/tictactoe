(ns tictactoe.core
  (:gen-class))

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


(defn updateBoard
  [move currentBoard token]
  (let [[row col] move]
  (assoc currentBoard (+ (* 3 row) col) token)))

(defn validateMove 
  "This function takes a move input and the current gameboard, and returns a
  validated move. It will recurse on itself until a valid move is found. "
  [move gameBoard]
  (let [[row col] move
        curr (nth gameBoard (+ (* 3 row) col))]
    (if (= 0 (compare curr "-"))
     move
     (do
      (println "That spot is taken. Try again!")
      (validateMove (getUserMove) gameBoard)))))

(def threeInARow  [[0 1 2] [3 4 5] [6 7 8] [0 3 6] [1 4 7] [2 5 8] [0 4 8] [2 4 6]])

(defn tokensMatch?
  "Finds the tokens at matchPositions on the board. If all three are the
   same, returns true, else false."
  [playerToken matchPositions gameBoard]
  (let [pieces (map #(nth gameBoard %) matchPositions)
        truthy (map #(= 0 (compare % playerToken)) pieces)]
        (every? identity truthy)))

(defn hasWon?
  "Checks for the win condition"
  [playerToken gameBoard]
  (let [truthy (map #(tokensMatch? playerToken % gameBoard) threeInARow)]
  (println truthy)
  (.contains truthy true)))

(def gameBoard ["-" "-" "-" "-" "-" "-" "-" "-" "-"])

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (def firstMove (updateBoard (validateMove (getUserMove) gameBoard) gameBoard "X"))
  (def second (updateBoard (validateMove (getUserMove) firstMove) firstMove "X"))
  (def third (updateBoard (validateMove (getUserMove) second) second "X"))


  (println (hasWon? "X" third))
)
