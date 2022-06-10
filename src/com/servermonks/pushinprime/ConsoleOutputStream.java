package com.servermonks.pushinprime;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.io.OutputStream;

public class ConsoleOutputStream extends OutputStream {

    private JTextPane textArea;

    public ConsoleOutputStream(JTextPane textArea) {
        this.textArea = textArea;
    }

    @Override
    public void write(int b) {
        try {
            DefaultCaret caret = (DefaultCaret) textArea.getCaret();
            caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
            textArea.getDocument().insertString(textArea.getDocument().getLength(), String.valueOf((char) b), null);
            textArea.setCaretPosition(textArea.getDocument().getLength());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}