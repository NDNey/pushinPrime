package com.servermonks.pushinprime;

import com.servermonks.pushinprime.app.Player;
import com.servermonks.pushinprime.app.PushinPrimeApp;
import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.PrintStream;

public class Board {

    private PrintStream printStream;
    private int boardWidth = 800;
    private int boardHeight = 800;
    private JFrame frame;
    private JTextPane textPane;
    private JTextField commandInput;
    private JTextArea commandInfo;
    private JTextField clockText;
    private String playerName = "you";
    private long time = 0;
    private Clock clock;
    private Font sysOutTextFont = new Font("SansSerif", Font.BOLD, 12);
    private Font inputTextFont = new Font("SansSerif", Font.BOLD, 35);
    private Color sysOutColorBG = Color.white;
    private Color commandInputColorBG = Color.lightGray;
    private Color clockColorBG = Color.darkGray;
    private Player player;
    private ByteArrayInputStream inputStream = new ByteArrayInputStream("".getBytes());


    private static Board instance;

    public Board() {
        createBoard();
        clear();
    }

    public static Board getInstance() {
        if(instance == null) {
            instance = new Board();
        }
        return instance;
    }

    public Board(Player player) {
        this.player = player;
    }

    public void clear() {
        textPane.setText("<html><head><style>body{width:100%;text-align:left;}</style></head><body><div id=\"content\"></div></body></html>");
    }

    public void createBoard() {

        frame = new JFrame("pushinPRIME :)");
        frame.setSize(new Dimension(boardWidth, boardHeight));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container pane = frame.getContentPane();
        pane.setLayout(new GridBagLayout());
        pane.setSize(boardWidth, boardHeight);

        textPane = new JTextPane();
        textPane.setContentType("text/html;charset=UTF-16");
        textPane.setEditable(false);
        textPane.setBackground(new java.awt.Color(sysOutColorBG.getRGB()));
        textPane.setFont(sysOutTextFont);
        textPane.setText("<html><head><style>body{width:100%;text-align:left;}</style></head><body><div id=\"content\"></div></body></html>");

        GridBagConstraints sysOutScrollPaneConstraints = new GridBagConstraints();
        sysOutScrollPaneConstraints.fill = GridBagConstraints.BOTH;
        sysOutScrollPaneConstraints.gridx = 0;
        sysOutScrollPaneConstraints.gridy = 0;
        sysOutScrollPaneConstraints.weighty = 1.00;
        sysOutScrollPaneConstraints.gridwidth = 2;

        JScrollPane sysOutScrollPane = new JScrollPane(textPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sysOutScrollPane.setMinimumSize(new Dimension(800, 600));
        sysOutScrollPane.setMaximumSize(new Dimension(800, 600));
        sysOutScrollPane.setSize(800, 600);
        sysOutScrollPane.setBackground(new java.awt.Color(Color.darkGray.getRGB()));
        pane.add(sysOutScrollPane, sysOutScrollPaneConstraints);

        commandInput = new JTextField(50);
        commandInput.setMinimumSize(new Dimension(400, 45));
        commandInput.setMaximumSize(new Dimension(400, 45));
        commandInput.setBackground(new java.awt.Color(commandInputColorBG.getRGB()));
        commandInput.setFont(inputTextFont);
        GridBagConstraints commandInputConstraints = new GridBagConstraints();
        commandInputConstraints.fill = GridBagConstraints.BOTH;
        commandInputConstraints.gridx = 0;
        commandInputConstraints.gridy = 1;
        commandInputConstraints.weightx = 1;

        commandInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if(evt.getKeyCode() == 10) {
                    String input = commandInput.getText();
                    PushinPrimeApp.setInputStream(new ByteArrayInputStream(input.getBytes()));
                    commandInput.setText("");
                }
            }
        });

        pane.add(commandInput, commandInputConstraints);

        clockText = new JTextField("00:00");
        clockText.setMinimumSize(new Dimension(100, 45));
        clockText.setMaximumSize(new Dimension(100, 45));
        clockText.setEditable(false);
        clockText.setFont(inputTextFont);
        GridBagConstraints clockTextConstraints = new GridBagConstraints();
        clockTextConstraints.fill = GridBagConstraints.BOTH;
        clockTextConstraints.gridx = 1;
        clockTextConstraints.gridy = 1;
        pane.add(clockText, clockTextConstraints);

        frame.setVisible(true);

        commandInput.requestFocus();

        startClock();
    }

    public void startClock() {
        if(clock == null)
            clock = new Clock();
        clock.start();
    }

    public void stopClock() {
        clock.clockRunning = false;
        clock = null;
    }

    public void resetClock() {
        stopClock();
        time = 0;
    }

    public JTextPane getTextPane() {
        return textPane;
    }

    public void setTextPane(JTextPane textPane) {
        this.textPane = textPane;
    }

    class Clock extends Thread {

        public boolean clockRunning = true;

        @Override
        public void run() {
            while (clockRunning) {
                try {
                    Thread.sleep(1000);
                    time++;
                    if(time > 3600) {
                        time = 0;
                    }
                    String timeString = String.format("%02d:%02d" , time / 60, time%60);
                    clockText.setText(timeString);
                } catch (Exception e) { e.printStackTrace(); }
            }
        }
    }

    public JTextField getCommandInput() {
        return commandInput;
    }

    public void setCommandInput(JTextField commandInput) {
        this.commandInput = commandInput;
    }
}