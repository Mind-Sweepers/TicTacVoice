package com.mindsweepers.tictacvoice;

/**
 * Created by viviandiec on 2018-02-18.
 */

import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class TicTacToe {
    TextToSpeech tts;
    String board[][];
    private static TicTacToe ticTacToe;

    public TicTacToe(){
        board = new String[3][3];
    }

    public static TicTacToe getInstance() {
        if (ticTacToe==null) {
            ticTacToe = new TicTacToe();
        }

        return ticTacToe;
    }

    public String[][] getBoard(){
        return ticTacToe.board;
    }

    public void setPiece(String x) {
        board[0][0]=x;

    }

//    public void init(){
//        userInput();
//    }
    public int userInput(String s, int tries) {

//            String input;
//            System.out.println("Enter a coordinate: ");
//            Scanner scanner = new Scanner(System.in);
//            input = scanner.nextLine(); //potentially fix

            String temp = Character.toString(s.charAt(0));

            Log.e("temp", temp);
            int result = 0;

            int row = -1;
            boolean rowValid = true;

            if (temp.equals("A")) {
                row = 0;
            }
            else if (temp.equals("B")) {
                row = 1;
            }
            else{
                row = 2;
            }

            if (row == -1) {
                rowValid = false;
            }

            if (rowValid){
                String temp1 = Character.toString(s.charAt(1));

                Log.e("temp1", temp1);

                int column = Integer.parseInt(temp1);
                column = column - 1;

                if(empty(row, column)) {
                    if (tries % 2 == 0) {
                        board[row][column]="X";
                    }
                    else {
                        board[row][column]="O";
                    }
                }

                if (check(tries)) {
                    if (tries % 2 == 0) {
                        Log.e("winner", "Player O won");
                        result = 1;
                    }
                    else {
                        Log.e("winner", "Player X won");
                        result = 2;
                    }
                }
            }
        tries ++;
        printBoard();
        return result;
    }

    //check if user won
    public boolean check(int tries) {

        if(tries<4) {
            return false;
        }

        else if((board[0][0]==board[0][1]&&board[0][1]==board[0][2] && (board[0][0] == "X" || board[0][0] == "O")) ||
                (board[1][0]==board[1][1]&&board[1][1]==board[1][2] && (board[1][0] == "X" || board[1][0] == "O")) ||
                (board[2][0]==board[2][1]&&board[2][1]==board[2][2] && (board[2][0] == "X" || board[2][0] == "O")) ||
                (board[0][0]==board[1][1]&&board[1][1]==board[2][2] && (board[0][0] == "X" || board[0][0] == "O")) ||
                (board[0][2]==board[1][1]&&board[1][1]==board[2][0] && (board[0][2] == "X" || board[0][2] == "O")) ||
                (board[0][0]==board[1][0]&&board[1][0]==board[2][0] && (board[0][0] == "X" || board[0][0] == "O")) ||
                (board[0][1]==board[1][1]&&board[1][1]==board[2][1] && (board[0][1] == "X" || board[0][1] == "O")) ||
                (board[0][2]==board[1][2]&&board[1][2]==board[2][2] && (board[0][2] == "X" || board[0][2] == "O"))) {
            return true;
        }
        return false;

    }

    public boolean empty(int row, int column) {
        if (board[row][column]==null) {
            return true;
        }
        else {
            return false;
        }
    }

    public static void main(String[] args) {
        new TicTacToe();
    }

    public void resetBoard(){
        board = null;
    }

    public void printBoard(){
        for (int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                System.out.print(board[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

}

