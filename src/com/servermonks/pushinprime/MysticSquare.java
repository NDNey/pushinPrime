package com.servermonks.pushinprime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.servermonks.pushinprime.Colors.CYAN;
import static com.servermonks.pushinprime.Colors.RESET;

public class MysticSquare {

    private int rowColumnLength = 3;
    private int squareCount = 9;
    private List<Integer> board;
    private List<Integer> validMoves = new ArrayList<>();
    private boolean isOver;

    public MysticSquare() {
        rowColumnLength = 3;
        squareCount = rowColumnLength*rowColumnLength;
    }

    public MysticSquare(int rowColumnLength) {
        this.rowColumnLength = rowColumnLength;
        squareCount = rowColumnLength^2;
    }

    public void createBoard() {
        board = new ArrayList<>(squareCount);
        for(int i = 0; i< squareCount; i++) {
            board.add(i);
        }
        Collections.shuffle(board, new Random(System.currentTimeMillis()));
        setValidMoves(board.indexOf(0)+1);
    }

    public String showBoard() {
        String lines = "";
        for(int i=0;i<squareCount;i=i+3) {
            String line = "";
            for (int ii=0;ii<rowColumnLength;ii++) {
                int index = ii + i;
                String num = String.valueOf(board.get(index));
                if(num.equals("0")) {
                    num = "&nbsp;&nbsp;";
                }
                if(validMoves.contains(board.get(index))) {
                    num = CYAN + num + RESET;
                }
                line = line + "&nbsp;&nbsp;&nbsp;" + num;
            }
            lines = lines + line + "<br>";
        }
        System.out.println(lines);
        return lines;
    }

    public String showValidMoves() {
        String validMovesString= "";
        for(int i=0;i<validMoves.size();i++) {
            String delimiter = ", ";
            if(i == validMoves.size() - 2) {
                delimiter = " or ";
            }
            validMovesString = validMovesString + validMoves.get(i) + delimiter;
        }
        validMovesString = validMovesString.substring(0,validMovesString.length()-2);
        return validMovesString;
    }

    public boolean isValidMove(int square) {
        return true;
    }

    public void setValidMoves(int square) {
        validMoves = new ArrayList<>();
        if(square - 1 < 0 || square - rowColumnLength < 0 ||
                square + rowColumnLength > squareCount ||
                square + 1 > squareCount) {
        }  if(square - rowColumnLength > 0) {
            validMoves.add(square - rowColumnLength);
        }  if(square + rowColumnLength < squareCount) {
            validMoves.add(square + rowColumnLength);
        }
                if ((square + 1) % rowColumnLength != 0) {
                    validMoves.add(square + 1);
                }
                if ((square - 1) % rowColumnLength != 0) {
                    validMoves.add(square - 1);
                }
        System.out.println(square + ": " + validMoves.toString());
    }

    public boolean isOver() {
        return isOver;
    }

    public boolean move() {
        return true;
    }

    public boolean checkForWin() {
        return true;
    }
}