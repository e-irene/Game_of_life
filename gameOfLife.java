/*
 * @author: Eirene Mehta
 * Program Description: Conway's game of life. User has option to replay if they'd like.
 * Uses an integer array where 0 (default value for int array elements) represents a dead cell, and 1 represents a live cell
 */
import java.util.Scanner;

class gameOfLife {
    private static Scanner uI = new Scanner (System.in); 
    private static int gameBoard[][] = new int [20][20]; //creates gameboard
    private static int nextGen[][] = new int [20][20]; //temporary board to hold updated values
    private static int numAlive = 0; //user choice for alive cells at beginning
    private static int genCounter = 0; //counts generations
    public static void main (String[] args) {
        System.out.println("Welcome to the game.");
        System.out.println("Please enter how many cells you would like to start the game with");
        numAlive = assureInt(numAlive); //sets the numAlive variable used in fillArray() method
        fillArray();
        do {
            printBoard();
            findNeighbours();
            updateBoard(); // will update board but won't print until replay() is true
            genCounter++; // increments the generation
        } while (replay() == true);
    } // end of main method

// Fill Array with Live Cells
private static void fillArray() {
    int aliveCounter = 0;
    do {
        int randRow = (int)(Math.random() * (gameBoard.length)); //random row index and casted as an int
        int randCol = (int)(Math.random() * (gameBoard.length)); //random column index and casted as an int
        if ((gameBoard[randRow][randCol] == 0)){
            gameBoard[randRow][randCol] = 1; //make random indices '1' aka live
            aliveCounter ++;
        }
    } while (aliveCounter < numAlive);  //loops and adds random alive cells at places until counter meets requirement (1 less because do-while)   
} // end of method fillArray()

// Assure input
private static int assureInt (int assureVar) {
    do {
        while (!uI.hasNextInt()) {
            System.out.println("Please enter a number");
            uI.next(); //collects user input
        }
        assureVar = uI.nextInt();
        if (assureVar < 0 || assureVar > 400) {
            System.out.println("Please enter a number between 1 and 400."); //max value for live cells is 400 in a 20 x 20 grid, and cannot be 0 or less. 
        }
    } while (assureVar < 0 || assureVar > 400); //continues loop until valid input is entered
    return assureVar;
} // end of method assureInt()

// Find Neighbours
private static void findNeighbours() {
    int[][] directions = { { 0, 1 },  { 1, 0 }, { 0, -1 }, { -1, 0 }, { 1, 1 },  { -1, -1 }, { 1, -1 }, { -1, 1 } };
    int foundRow = 0; //row index found
    int foundCol = 0; //column index found
    int aliveNeighbours = 0; //number of alive neighbours foumd
    for (int i = 0; i < 20; i++) { //int i iterates rows in (i, j) coorindate system to describe position of neighbour
        for (int j = 0; j < 20; j++) { //int j iterates columns
            aliveNeighbours = 0; // reset alive neighbours
            for (int[] k : directions) { //for loop to iterate through different directions to check neighbours
                foundRow = i + k[0]; //row index at which it was found
                foundCol = j + k[1]; //column index at which it was found

                if ((foundRow >= 0 && foundRow < 20) && (foundCol >= 0 && foundCol < 20)) { //check if the neighbour has an index out of bounds
                    if (gameBoard[foundRow][foundCol] == 1) {
                        aliveNeighbours++; //increment if neighbour is alive
                        } //end nested if
                    } // end if statement
                } 
            // Conditions for Determining if the new generation will be alive or dead
            if (gameBoard[i][j] == 1) {
                if (aliveNeighbours == 2 || aliveNeighbours == 3) { //if 2 or 3 neighbours, cell continues to live
                    nextGen[i][j] = 1;
                }
            else {
                    nextGen[i][j] = 0; //otherwise it dies
                }
            }
            if (gameBoard[i][j] == 0) { //if dead with 3 neighbours it becomes alive
                if (aliveNeighbours == 3) {
                    nextGen[i][j] = 1;
                }
                else {
                    nextGen[i][j] = 0; //else remains dead
                }
            }
        } // end for loop for columns
    } // end for loop for rows
} //end of method findNeighbours()

// Update Board
private static void updateBoard() {
    for (int i = 0; i < 20; i++) { //iterate rows
        for (int j = 0; j < 20; j++) { //iterate columns
            gameBoard [i][j] = nextGen [i][j]; //updates gameBoard to continue the game to allow to play
        } // end nested for
    } // end for loop
} // end method updateBoard()

private static void printBoard() {
    System.out.printf("----Generation %d--------------------\n", genCounter);
    for (int i = 0; i < 20; i++) { //iterate rows
        for (int j = 0; j < 20; j++) { //iterate columns
            if (gameBoard[i][j] == 1){
            System.out.print("O"); //prints O if alive (int value is 1)
            }
            else {
                System.out.print("."); //prints . if alive (int alive is 0)
            }
        } // end of nested for loop
    System.out.println(); //space between rows
    } // end of for loop
} //end of printBoard()

private static boolean replay() {
    String userChoice = "";
    boolean playAgain = false;
    do { // do while to assure input
        System.out.println("Advance Generation: 1 \nExit Game: Exit");
        userChoice = uI.next();
        if (! ((userChoice.equals("1")) || (userChoice.equalsIgnoreCase("exit")))){ //assures for input
            System.out.println("Please enter 1 or exit.");
        }
    } while (! ((userChoice.equals("1")) || (userChoice.equalsIgnoreCase("exit"))) ); //loops until correct input is entered
    
    if (userChoice.equals("1")) {
        playAgain = true; //user wants to play again (returns true)
    }
    if (userChoice.equalsIgnoreCase("exit")) {
        playAgain = false; //user does not want to play again (returns false)
    }
    return playAgain; //returns boolean
    } // end of method replay()
} // end of class