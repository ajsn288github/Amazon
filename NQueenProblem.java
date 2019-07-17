package com.Amazon;

/**
 * @author PL64640
 * @since 6/19/2019
 * <p>
 * Java program to solve boardSize Queen Problem using backtracking
 */
public class NQueenProblem {

    private int boardSize = 8;
    private int numOfSolutions = 1;

    private NQueenProblem(int boardSize) {
        this.boardSize = boardSize;
    }

    /* A utility function to print solution */
    private void printSolution(int[][] board) {
        System.out.printf("%d-\n", numOfSolutions++);
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++)
                System.out.printf(" %d ", board[i][j]);
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    /* A utility function to check if a queen can
    be placed on board[row][col]. Note that this
    function is called when "col" queens are
    already placed in columns from 0 to col -1.
    So we need to check only left side for
    attacking queens */
    private boolean isSafe(int[][] board, int row, int col) {
        int i, j;

        /* Check this row on left side */
        for (i = 0; i < col; i++)
            if (board[row][i] == 1)
                return false;

        /* Check upper diagonal on left side */
        for (i = row, j = col; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] == 1)
                return false;

        /* Check lower diagonal on left side */
        for (i = row, j = col; j >= 0 && i < boardSize; i++, j--)
            if (board[i][j] == 1)
                return false;

        return true;
    }

    /* A recursive utility function
    to solve boardSize Queen problem */
    private boolean solveNQUtil(int[][] board, int col) {
        /* base case: If all queens are placed
        then return true */
        if (col == boardSize) {
            printSolution(board);
            return true;
        }

        /* Consider this column and try placing
        this queen in all rows one by one */
        boolean res = false;
        for (int i = 0; i < boardSize; i++) {
            /* Check if queen can be placed on board[i][col] */
            if (isSafe(board, i, col)) {
                /* Place this queen in board[i][col] */
                board[i][col] = 1;

                // Make result true if any placement
                // is possible
                res = solveNQUtil(board, col + 1) || res;

                /* If placing queen in board[i][col]
                doesn't lead to a solution, then
                remove queen from board[i][col] */
                board[i][col] = 0; // BACKTRACK
            }
        }

	/* If queen can not be place in any row in
		this column col then return false */
        return res;
    }

    /* This function solves the boardSize Queen problem using
    Backtracking. It mainly uses solveNQUtil() to
    solve the problem. It returns false if queens
    cannot be placed, otherwise return true and
    prints placement of queens in the form of 1s.
    Please note that there may be more than one
    solutions, this function prints one of the
    feasible solutions.*/
    private void solveNQ() {
        int[][] board = new int[boardSize][boardSize];

        if (!solveNQUtil(board, 0)) {
            System.out.print("Solution does not exist");
        }
    }

    // Driver code
    public static void main(String[] args) {
        new NQueenProblem(8).solveNQ();
    }
}