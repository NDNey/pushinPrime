package com.servermonks.pushinprime;

import javax.swing.*;
import java.awt.*;
import java.io.PrintStream;

public class Board {

    JFrame frame = new JFrame();
    JTextArea sysOut = new JTextArea();
    JTextField commandInput = new JTextField();
    JTextArea commandInfo = new JTextArea();

    public void Board() {
        createBoard();
    }

    public void createBoard() {

        frame = new JFrame("pushinPRIME :)");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        sysOut = new JTextArea(50, 10);
        frame.getContentPane().add(sysOut);

        commandInfo = new JTextArea();

        commandInput = new JTextField(50);
        Color c = new Color(0,0,0,100);
        commandInput.setBackground(c);

        frame.pack();
        frame.setVisible(true);

        PrintStream printStream = new PrintStream(new JoutputStream(sysOut));
        System.setOut(printStream);
    }
}
