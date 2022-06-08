package com.servermonks.pushinprime;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

public class JoutputStream extends OutputStream {

    private JTextArea textArea;

    public JoutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void write(int b) throws IOException {
        textArea.append(String.valueOf((char)b));
        // scrolls the text area to the end of data
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}

//JTextArea textArea = new JTextArea(50, 10);
//PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
//System.setOut(printStream);
//System.setErr(printStream);

//PrintStream standardOut = System.out;
//PrintStream standardErr = System.err;

    /*JFrame frame = new JFrame("pushinPRIME :)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JTextArea textArea = new JTextArea(50,100);
                frame.getContentPane().add(textArea);

                frame.pack();
                frame.setVisible(true);

                JoutputStream jOutputStream = new JoutputStream(textArea);
                PrintStream printStream = new PrintStream(jOutputStream);
                System.setOut(printStream);

                for(int i=0;i<25;i++) {
        System.out.println("pushinPRIME");
        }*/
