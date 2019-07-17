package com.Amazon;// IMPORT LIBRARY PACKAGES NEEDED BY YOUR PROGRAM
// SOME CLASSES WITHIN A PACKAGE MAY BE RESTRICTED
// DEFINE ANY CLASS AND METHOD NEEDED

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * You are in charge of preparing a recently purchased lot of one Amazon's new building. The lot is covered with trenches and has a single obstacles that needs to be taken down before the foundation can be processed for the building. The demolition robot must remove the obstacle before progress can be made on the building.
 * <p>
 * Write an algorithm to determine the minimum distance required for the demolition robot to remove the obstacle.
 * <p>
 * <p>
 * Assumptions:
 * --> The lot is flat, except for the trenches and can be represented as two dimensional array.
 * --> Demolition robot must start from the top left corner of the lot, which is always flat, and can move one block up, down, left or  right at a time.
 * --> The demolition robot cannot enter trenches and cannot leave the lot.
 * --> The flat areas are represented as 1, are with trenches represented by 0 and the obstacle is represented by 9.
 * <p>
 * Input:
 * The input to the function/method consists of three arguments.
 * 1. numRows, an integer representing the number of rows.
 * 2. numColumns, an integer representing the number of columns.
 * 3. lot, representing the two dimensional grid of integers.
 * <p>
 * Output:
 * Return an integer representing the minimum distance traversed to remove the obstacle else return -1.
 * <p>
 * Constraints:
 * 1<= numRows, numColumns <= 1000
 * <p>
 * Example:
 * numRows = 3
 * numColumns = 3
 * lot: [1, 0, 0]
 * [1, 0, 0]
 * [1, 9, 1]
 * <p>
 * Output: 3
 * <p>
 * Explanation:
 * Starting from the top left corner demolition robot traversed the cells (0, 0) --> (1, 0) --> (2, 0) --> (2, 1). The robot traveresed total distance 3 to remove the obstacle.
 * So, the output is 3.
 *
 * We have used here a Binary Tree Traversal methodology and solved this probem.
 *
 * @author Selva
 * @since 05/22/2019
 */
public class Amazon2 {

    public static void main(String[] ar) {
        Amazon2 robotMovement = new Amazon2();
        robotMovement.removeObstacle(
                6,
                6,
                Arrays.asList(
                        Arrays.asList(1, 1, 1, 1, 1, 0),
                        Arrays.asList(1, 1, 0, 0, 1, 0),
                        Arrays.asList(1, 0, 9, 0, 1, 0),
                        Arrays.asList(1, 0, 1, 1, 1, 0),
                        Arrays.asList(1, 1, 0, 0, 0, 0),
                        Arrays.asList(1, 1, 1, 0, 0, 0)));
    }

    private static final int FLAT = 1;
    private static final int TRENCH = 0;
    private static final int OBSTACLE = 9;

    // METHOD SIGNATURE BEGINS, THIS METHOD IS REQUIRED
    int removeObstacle(int numRows, int numColumns, List<List<Integer>> lot) {

        // WRITE YOUR CODE HERE

        if (numColumns >= 1 && numColumns <= 1000 && numRows >= 1 && numRows <= 1000) {

            boolean[][] visited = new boolean[numRows][numColumns];
            Node obstacle = null;

            for (int row = 0; row < numRows; row++) {
                List<Integer> columnGridValues = lot.get(row);

                for (int col = 0; col < numColumns; col++) {
                    Integer value = columnGridValues.get(col);

                    if (value == FLAT) {
                        visited[row][col] = false;
                    } else if (value == TRENCH) {
                        visited[row][col] = true;
                    } else if (value == OBSTACLE) {
                        visited[row][col] = false;
                        obstacle = new Node(row, col);
                    }
                }
            }

            System.out.println("--> Obstacle --> " + obstacle.getRow() + "," + obstacle.getCol());

            LinkedList<Node> rowQueue = new LinkedList<>();
            int node_left_in_layer = 1;
            int nodes_in_next_layer = 0;
            int stepCount = 0;
            boolean targetReached = false;

            int[] rowDirection = {-1, 1, 0, 0};
            int[] colDirection = {0, 0, 1, -1};

            rowQueue.add(new Node(0, 0));
            visited[0][0] = true;
            while (!rowQueue.isEmpty()) {
                Node currentPosition = rowQueue.poll();
                if (currentPosition.getRow() == obstacle.getRow()
                        && currentPosition.getCol() == obstacle.getCol()) {
                    System.out.println("--> Step count --> " + stepCount);
                    targetReached = true;
                    break;
                }

                for (int i = 0; i < 4; i++) {
                    int nextRowPos = currentPosition.getRow() + rowDirection[i];
                    int nextColPos = currentPosition.getCol() + colDirection[i];

                    if (nextRowPos < 0 || nextColPos < 0) continue;
                    if (nextRowPos >= numRows || nextColPos >= numColumns) continue;

                    // both visited & obstacle
                    if (visited[nextRowPos][nextColPos]) continue;

                    rowQueue.add(new Node(nextRowPos, nextColPos));
                    visited[nextRowPos][nextColPos] = true;
                    nodes_in_next_layer++;
                }

                node_left_in_layer--;

                if (node_left_in_layer == 0) {
                    node_left_in_layer = nodes_in_next_layer;
                    nodes_in_next_layer = 0;
                    stepCount++;
                }
            }

            if (targetReached) {
                return stepCount;
            }
        }

        return -1;
    }

    public class Node {
        private int row;
        private int col;

        public Node(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public int getCol() {
            return col;
        }

        public void setCol(int col) {
            this.col = col;
        }
    }
}