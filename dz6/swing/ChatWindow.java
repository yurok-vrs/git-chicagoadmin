package ru.geekbrains.java2.dz.dz6.ЮрийНиконоров.swing;

import ru.geekbrains.java2.dz.dz6.ЮрийНиконоров.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Acer on 19.06.2016.
 */
public class ChatWindow extends JFrame {
    private JTextField textField;
    private JTextArea chatText;
    private Socket socket;

    private PrintWriter out;
    public Scanner in;

    public ChatWindow() {

        super("Чат");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        chatText = new JTextArea();
        JScrollPane chatScroll = new JScrollPane(chatText);
        chatText.setEnabled(false);
        chatText.setAutoscrolls(true);

        JButton button = new JButton("Отправить");

        textField = new JTextField();
        textField.setColumns(40);

        setLayout(new BorderLayout());
        add(chatScroll, BorderLayout.CENTER);
        JPanel panel = new JPanel();
        add(panel, BorderLayout.SOUTH);

        panel.setLayout(new FlowLayout());
        panel.add(textField);
        panel.add(button);

        setBounds(400, 200, 600, 400);
        setResizable(false);
        setVisible(true);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                sendMessage(textField.getText());
                textField.setText("");
                textField.requestFocus();
            }
        });
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == e.VK_ENTER) {
                    sendMessage(textField.getText());
                    textField.setText("");
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                try {
                    out.println(Server.EXIT);
                    out.close();
                    in.close();
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                    System.out.println("error");
                }
            }
        });


        startClient();


    }

    private void startClient() {
        try {
            String HOST = "localhost";
            socket = new Socket(HOST, Server.PORT);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            if (in.hasNextLine()) {
                                String line = in.nextLine();
                                chatText.append(line + '\n');
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("connect error");
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String text) {
        out.println(text);
        out.flush();
        chatText.append("client: "+text + '\n');
    }

}
