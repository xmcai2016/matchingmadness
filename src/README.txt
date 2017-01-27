=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: ____cax___
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an approprate use of the concept. You may copy and paste from your proposal
  document if you did not change the features you are implementing.

====================
=: Core Concept 1 :=
====================

Concept 1: _2D array______

- What specific feature of your game will be implemented using this concept?
I will line up my tiles using a 2D array.

- Why does it make sense to implement this feature with this concept? Justify
  why this is a non-trivial application of the concept in question.
The game board is composed of a 2D array of tiles. 

====================
=: Core Concept 2 :=
====================

Concept 2: __Complex search ___

- What specific feature of your game will be implemented using this concept?
When given two selected tiles, the game has to know if the two tiles are within a one turn distance.  

- Why does it make sense to implement this feature with this concept? Justify
  why this is a non-trivial application of the concept in question.
Implementing this concept requires intensive search on the optimal path. It has to know if the paths are valid.

====================
=: Core Concept 3 :=
====================

Concept 3: ___ File I/O____

- What specific feature of your game will be implemented using this concept?
The game will keep track of a list of top ten user names and high scores. The “high score” will be represented by the amount of time used for a player to complete the game. The lower the time spent, the better the score is. 

- Why does it make sense to implement this feature with this concept? Justify
  why this is a non-trivial application of the concept in question.
Whenever a player achieves a time that’s lower than the 10th on the high score list, his or her score will be recorded in the file. This is an all-time top chart. 

====================
=: Core Concept 4 :=
====================

Concept 4: ___Testable Component___

- What specific feature of your game will be implemented using this concept?
I will test the correctness of my optimal path finding algorithm. 

- Why does it make sense to implement this feature with this concept? Justify
  why this is a non-trivial application of the concept in question.

This is really important because it determines whether or not my game is playable.



=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  The CoverImage class extends JPanel and is a very short class I made just for the convenience 
  to display the cover image on the firstFrame.
  The Game class builds the entire look of the game and have JButtons that trigger other parts of 
  the program.
  The GameCourt class is an extension of JPanel as well, and it contains the major state of the game.
  The HighScores class reads and writes high scores. It is an implementation of the IO.
  The Tile Class contains mainly of the JButtons that are displayed on the GameCourt. It also stores 
  the states of the tiles.
  The MyTest Class contains tests that test my implementation of the Highscores calss.

- Revisit your proposal document. What components of your plan did you end up
  keeping? What did you have to change? Why?
I kept everything in terms of the core concepts, with a minor change in the testable component. 
I originally intended to test my optimal path algorithm, but during the process of implementing the 
game I realized it was easier to test out the algorithm through playing the game. On the other
hand, I found that testing the correctness of writing of the high score file extremely imperative 
and decided to write test cases for that. 

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
WRITING THE HIGH SCORE FILE!! Took me so long to figure out what was wrong with my code (2 days!!)

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
Yes, there is a good seperation of functionality, as described in detail above.
Private states are carefully encapsulated--
the "get" methods return copies, not alias, of instance variables. 


========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
Swing library documentations
images of my fruits are from google
There is also some reference to the code I wrote for the spellchecker homework, for the IO part of the game. 

