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
  [move board]
  (let [[row col] move
        curr (nth board (+ (* 3 row) col))]
    (if (= 0 (compare curr "-"))
     move
     (do
      (println "That spot is taken. Try again!")
      (validateMove (getUserMove) board)))))

(def threeInARow  [[0 1 2] [3 4 5] [6 7 8] [0 3 6] [1 4 7] [2 5 8] [0 4 8] [2 4 6]])

(defn tokensMatch?
  "Finds the tokens at matchPositions on the board. If all three are the
   same, returns true, else false."
  [playerToken matchPositions board]
  (let [pieces (map #(nth board %) matchPositions)
        truthy (map #(= 0 (compare % playerToken)) pieces)]
        (every? identity truthy)))

(defn hasWon?
  "Checks for the win condition"
  [playerToken board]
  (let [truthy (map #(tokensMatch? playerToken % board) threeInARow)]
  (.contains truthy true)))

(defn spacesLeft?
  [board]
  (.contains board "-"))

(defn printRow
  [board pair]
  (let [[start end] pair
    row (subvec board start end)]
    (println row)))

(def printerRows [[0 3] [3 6] [6 9]])
(defn printBoard
  [board]
    (printRow board (nth printerRows 0))
    (printRow board (nth printerRows 1))
    (printRow board (nth printerRows 2))
    )

(def gameBoard ["-" "-" "-" "-" "-" "-" "-" "-" "-"])

(defn otherPlayer
  [player]
  (if (= player "X")
  "O"
  "X"))

(defn gameLoop
  [board player]
  (let [done (hasWon? player board)
        lost (hasWon? (otherPlayer player) board)
        stuck (not (spacesLeft? board))]
    (printBoard board)
    (if (or done lost)
      (do
        (println "Congratulations, you won!")
        board)
      (if stuck
        (println "Game over, no moves left.")
        (gameLoop (updateBoard (validateMove (getUserMove) board) board player) (otherPlayer player))))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (gameLoop gameBoard "X")
)
