# Connect4
Connect4 is two-player game,which consists of a game board of seven columns and six rows,color discs. In this game there are two players who take turns to drop the disc in a board  which falls straight down in the column where a player wants to place it.The first to form a horizontal or vertical or diagonal of its own disc wins the game.

Explanation of Java code:

●	Programming language: Java 8
●	Tool : Eclipse Luna 4.2
●	Strategy used : Minimax Search Algorithm with heuristic evaluation 

Components of Java code:

Our java program consists of following Java classes

1.	GamePanel.java
GamePanel class extends JPanel. Jpanel is a generic lightweight container provided by Java and this class will hold our 2D array of type color. Gamepanel will listen to mouse click events and also paints the changes on user interface.
2.	GraphicalUserInterface.java
a.	Is a custom class which holds and operates on all the user-interface components such as  gameFrame, gameStatusPanel, gamePanel , gameStatusLabel , gameBoard and currentPlayer. GameFrame is the main UI component which holds all the UI components.
b.	This class contains methods responsible for starting the game, creating UI components, checking the winner and updating status on the panel.
3.	Connect4MouseAdaptor.java 
a.	This class is extension of Java’s MouseAdpater and it is responsible for detecting the column to drop disk based on the mouse click by User.
4.	Connect4Utils.java
a.	This class contains all the major methods which are responsible for dropping disk, validating the move, determine whether the Panel is full, un-dropping disk, to figure out the opposite player, Checking the Winner , getting the Next Move computer needs to make using “Minimax Search Algorithm with heuristic evaluation”



5.	RunConnect4.java
Is a main class from where the execution starts. Main method in this class creates an 2D array of color. In this 2D array white represents empty disk, Red represents Human and Black Represents Computer. 2D array is passed to GraphicalUserInterface class which create a UI frame to play the game and calls all the required methods to play the game from start to end.

Minimax Search Algorithm with heuristic evaluation:

Heuristic Evaluation Function: Connect4Utils.getHeuristicScore(Color[][] field)

This function evaluates the state of the 2D Color Array as following 

1.	Score is calculated for row, column and diagonal for both Human and Computer 
2.	4 disks of Human/Computer are in line then Score of 4 is calculated  
3.	3 disks of Human/Computer are in line then Score of 3 is calculated  
4.	2 disks of Human/Computer are in line then Score of 2 is calculated  
5.	1 disks of Human/Computer are in line then Score of 1 is calculated  
6.	Finally if Human Score is more than Computer score then a negative Human ELSE Computer score is returned.

Minimax Search Function: Connect4Utils.minmax(Color[][] board, int depth, Color player)

1.	Core principle of minimax is to minimize the maximum possible loss
2.	getMoveForComputer methods calls minimax function to determine the best column for computer 
3.	Minimax function has a local variable bestScore which will be assigned a big negative number if the player is computer or a big positve value if the player is Human.
4.	If the depth is zero or panel is full we use above heuristic function to calculate best score. Based on the best score for the moves we determine the best column for the computer
5.	Minimax is a recursive function which calls itself to determine the best move
6.	If the player is computer then we maximize the score to determine best column i.e., we compare score for the move with the bestScore and if it is greater than or equal to currentScore then we change the bestColumn value and bestScore value. This is done for all possible moves.
7.	If the player is human then we minimize the score to determine best column i.e., we compare score for the move with the bestScore and if it is less than currentScore then we change the bestColumn value and bestScore value. This is done for all possible moves.
8.	In the end based on minimax algorithm and heurstic evaluation computer can predict the move. 

