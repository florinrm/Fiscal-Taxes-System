package tax_system;

import javax.swing.*;
import java.awt.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AppStart extends JFrame {
    private JTextField user;
    private JPanel panel;
    private JPasswordField pass;
    private JButton login_button;
    private JLabel username;
    private JLabel password;
    private JLabel succeded;
    private JButton create_account;
    private JPanel buttons_panel;

    public void insert_data (String username, String password) {
        File data_file = new File("login.txt");
        HashMap <String, String> map = new HashMap<>();
        if (!data_file.exists())
            try {
                data_file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        Scanner scan = null;
        try {
            scan = new Scanner(data_file);
            String line;
            while (scan.hasNextLine()) {
                line = scan.nextLine();
                String[] data = line.split (" ");
                if (username.equals(data[0]))
                {
                    succeded.setText("Account already exists");
                    return;
                }
                map.put(data[0], data[1]);
            }
            scan.close();
            map.put(username, password);
            System.setOut(new PrintStream(data_file));
            for (Map.Entry<String, String> entry: map.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }
            succeded.setText("Account created!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void login_account (String username, String password) {
        File data_file = new File ("login.txt");
        if (!data_file.exists())
        {
            succeded.setText("Account doesn't exist!");
            return;
        }
        Scanner scan = null;
        try {
            scan = new Scanner (data_file);
            String line;
            while (scan.hasNextLine()) {
                line = scan.nextLine();
                String[] data = line.split(" ");
                if (username.equals(data[0])) {
                    if (password.equals(data[1])) {
                        succeded.setText("Logged in succesfully!");
                        new WelcomePage(data[0]);
                        setVisible(false); //you can't see me!
                        dispose();
                        return;
                    } else {
                        succeded.setText("Wrong password!");
                        return;
                    }
                }
            }
            scan.close();
            succeded.setText("Account doesn't exist!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public AppStart () {
        super ("Application Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.BLUE);
        this.setMinimumSize(new Dimension(400, 200));
        this.setLayout(new FlowLayout());

        this.user = new JTextField(25);
        this.panel = new JPanel();
        this.pass = new JPasswordField(25);
        this.username = new JLabel("Username", JLabel.CENTER);
        this.password = new JLabel("Password", JLabel.CENTER);
        this.username.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        this.password.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        this.login_button = new JButton ("Login");
        this.create_account = new JButton("Create new account");
        this.succeded = new JLabel();
        ImageIcon icon = new ImageIcon("Apps-preferences-desktop-user-password-icon.png");
        this.setIconImage(icon.getImage());
        this.succeded.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.Y_AXIS));
        this.create_account.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                if (button.getText().equals("Create new account")) {
                    insert_data(user.getText(), pass.getText());
                }
            }
        });
        this.login_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                if (button.getText().equals("Login")) {
                    login_account(user.getText(), pass.getText());
                }
            }
        });
        this.login_button.setMnemonic(KeyEvent.VK_ENTER);
        this.buttons_panel = new JPanel();
        this.buttons_panel.setLayout(new FlowLayout());
        this.panel.setMinimumSize(new Dimension (300, 400));
        this.buttons_panel.add(this.login_button);
        this.buttons_panel.add(this.create_account);
        this.panel.add(this.username);
        this.panel.add(this.user);
        this.panel.add(this.password);
        this.panel.add(this.pass);
        this.panel.add(this.buttons_panel);
        this.panel.add(this.succeded);
        this.add(panel);
        this.pack();
        this.setResizable(true);
        this.setVisible(true);
    }

    public static void main (String[] args) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        new AppStart();
    }
}

