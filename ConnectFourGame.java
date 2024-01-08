package com.example.connectfour;

import java.util.Random;

public class ConnectFourGame {
    public static final int ROW = 7;
    public static final int COL = 6;
    public static final int EMPTY = 0;
    public static final int BLUE = 1;
    public static final int RED = 2;
    public static final int DISCS = 42;  // 7x6 grid with 42 possible positions
    private int[][] boardGrid;
    private int player;  // Added member variable for player

    public ConnectFourGame() {
        boardGrid = new int[ROW][COL];
        newGame();  // Initialize the game when a new instance is created
    }

    public void newGame() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                boardGrid[row][col] = EMPTY;
            }
        }
        player = BLUE;  // Set the player to the first player (BLUE)
    }

    public int getDisc(int row, int col) {
        return boardGrid[row][col];
    }

    public boolean isGameOver() {
        return isBoardFull() || isWin();
    }

    private boolean isBoardFull() {
        // Check if all discs are placed on the board
        int count = 0;
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                if (boardGrid[row][col] != EMPTY) {
                    count++;
                }
            }
        }
        return count == DISCS;
    }

    private boolean isWin() {
        return checkHorizontal() || checkVertical() || checkDiagonal();
    }

    private boolean checkHorizontal() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col <= COL - 4; col++) {
                int disc = boardGrid[row][col];
                if (disc != EMPTY &&
                        disc == boardGrid[row][col + 1] &&
                        disc == boardGrid[row][col + 2] &&
                        disc == boardGrid[row][col + 3]) {
                    return true; // Four consecutive discs in a row
                }
            }
        }
        return false;
    }

    private boolean checkVertical() {
        for (int row = 0; row <= ROW - 4; row++) {
            for (int col = 0; col < COL; col++) {
                int disc = boardGrid[row][col];
                if (disc != EMPTY &&
                        disc == boardGrid[row + 1][col] &&
                        disc == boardGrid[row + 2][col] &&
                        disc == boardGrid[row + 3][col]) {
                    return true; // Four consecutive discs in a column
                }
            }
        }
        return false;
    }

    private boolean checkDiagonal() {
        // Check diagonal from top-left to bottom-right
        for (int row = 0; row <= ROW - 4; row++) {
            for (int col = 0; col <= COL - 4; col++) {
                int disc = boardGrid[row][col];
                if (disc != EMPTY &&
                        disc == boardGrid[row + 1][col + 1] &&
                        disc == boardGrid[row + 2][col + 2] &&
                        disc == boardGrid[row + 3][col + 3]) {
                    return true; // Four consecutive discs in a diagonal
                }
            }
        }

        // Check diagonal from top-right to bottom-left
        for (int row = 0; row <= ROW - 4; row++) {
            for (int col = 3; col < COL; col++) {
                int disc = boardGrid[row][col];
                if (disc != EMPTY &&
                        disc == boardGrid[row + 1][col - 1] &&
                        disc == boardGrid[row + 2][col - 2] &&
                        disc == boardGrid[row + 3][col - 3]) {
                    return true; // Four consecutive discs in a diagonal
                }
            }
        }

        return false;
    }

    public void setState(String gameState) {

    }

    public void selectDisc(int row, int col) {
        // Implementation for selecting a disc at a specific row and column
        for (int r = ROW - 1; r >= 0; r--) {
            if (boardGrid[r][col] == EMPTY) {
                boardGrid[r][col] = player;
                switchPlayer();
                break;
            }
        }
    }


    public String getState() {
        // Implementation for getting the game state
        // Convert array boardGrid to a string and return it
        StringBuilder gameState = new StringBuilder();
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                gameState.append(boardGrid[row][col]);
            }
        }
        return gameState.toString();
    }

    private void switchPlayer() {
        player = (player == BLUE) ? RED : BLUE;  // Switch players
    }
}

