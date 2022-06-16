package com.servermonks.pushinprime;

import com.servermonks.pushinprime.app.PushinPrimeApp;

import javax.swing.text.DefaultCaret;
import javax.swing.text.Element;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTMLDocument;

import static com.servermonks.pushinprime.Colors.*;

public class Prompter {

    private Board board;

    public Prompter(Board board) {
        this.board = board;
    }

    public void asciiArt(String var1) {
        info("<pre>" + var1 + "</pre>");
    }

    public void scroll2bottom() {
        DefaultCaret caret = (DefaultCaret) board.getTextPane().getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        board.getTextPane().setCaretPosition(board.getTextPane().getDocument().getLength());
    }

    public void info(String var1) {
        if (var1.contains(YELLOW.toString())) {
            var1 = var1.replace(YELLOW.toString(), "<span style=\"color:yellow;\">");
        }
        if (var1.contains(RED.toString())) {
            var1 = var1.replace(RED.toString(), "<span style=\"color:red;\">");
        }
        if (var1.contains(CYAN.toString())) {
            var1 = var1.replace(CYAN.toString(), "<span style=\"color:#00FFFF;\">");
        }
        if (var1.contains(MAGENTA.toString())) {
            var1 = var1.replace(MAGENTA.toString(), "<span style=\"color:#FF00FF;\">");
        }
        if (var1.contains(GREEN.toString())) {
            var1 = var1.replace(GREEN.toString(), "<span style=\"color:#00FF00;\">");
        }
        if (var1.contains(YELLOW.toString())) {
            var1 = var1.replace(YELLOW.toString(), "<span style=\"color:#FFFF00;\">");
        }
        if (var1.contains(ORANGE.toString())) {
            var1 = var1.replace(ORANGE.toString(), "<span style=\"color:#ffa500;\">");
        }
        if (var1.contains("RESET")) {
            var1 = var1.replace("RESET", "</span>");
        }
        var1 = var1.replace("\n", "<br/>");
        var1 = "<span style=\"\">" + var1 + "</span><br/>";
        StyledDocument styleDoc = board.getTextPane().getStyledDocument();
        HTMLDocument doc = (HTMLDocument) styleDoc;
        Element last = doc.getParagraphElement(doc.getLength());
        try {
            doc.insertBeforeEnd(last, var1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        scroll2bottom();
        try {
            String str = doc.getText(0, doc.getLength() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getInput() {
        while (PushinPrimeApp.inputStream == null || PushinPrimeApp.inputStream.available() == 0) ;
        byte[] byteArray = new byte[PushinPrimeApp.inputStream.available()];
        PushinPrimeApp.inputStream.read(byteArray, 0, PushinPrimeApp.inputStream.available());
        board.getCommandInput().setPlaceholder("");
        return new String(byteArray);
    }

    public String prompt(String message) {
        board.getCommandInput().setText("");
        if (message.substring(message.length() - 2, message.length() - 1).equals(":")) {
            message = message.substring(0, message.length() - 2);
        }
        message = message.toLowerCase();
        board.getCommandInput().setPlaceholder("enter " + message);
        info("<b>" + message + ": </b>");
        return getInput();
    }

    public String prompt(String var1, String var2, String var3) {
        String var4 = null;
        while (true) {
            var4 = prompt(var3);
            if (var4.matches(var2)) {
                break;
            }
        }
        return var4;
    }
}