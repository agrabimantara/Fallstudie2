package test;

import java.util.Random;

public class Test {


    private  static void  randomCluster(){
        Random rand = new Random();

        //create the grid
        final int rowWidth = 100;
        final int colHeight = 2;
        int low = -100;
        int high = 100;

        int [][] board = new int [rowWidth][colHeight];

        //fill the grid
        for (int row = 0; row < board.length; row++) {

            for (int col = 0; col < board[row].length; col++) {

                board[row][col] = rand.nextInt(high-low) + low;
            }
        }
        //display output
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {

                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }

    }

    public static void main(String[] args) {
        randomCluster();
    }
}
