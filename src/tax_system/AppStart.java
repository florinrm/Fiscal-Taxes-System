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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    private JButton change_password;
    private JPanel buttons_panel;

    // criptez parolele in login.txt
    private String encypherPassword (String password) {
        String cypher = "";
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256"); // criptare cu SHA-256
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            cypher = hexString.toString().toUpperCase();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return cypher;
    }

    // crearea unui cont nou
    private void insert_data (String username, String password) {
        File data_file = new File("login.txt");
        HashMap <String, String> map = new HashMap<>();
        if (!data_file.exists())
            try {
                data_file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        Scanner scan = null;
        if (username.length() == 0 || password.length() == 0) {
            succeded.setForeground(new Color(186, 26, 63));
            succeded.setText("Completați toate câmpurile!");
            return;
        }
        password = encypherPassword(password);
        try {
            scan = new Scanner(data_file);
            String line;
            while (scan.hasNextLine()) {
                line = scan.nextLine();
                String[] data = line.split (" ");
                if (username.equals(data[0]))
                {
                    succeded.setForeground(new Color(186, 26, 63));
                    succeded.setText("Contul deja există!");
                    return;
                }
                map.put(data[0], data[1]); // adaugam in dictionarul pe care il scriem in login.txt
            }
            scan.close();
            map.put(username, password); // adaugam informatiile noi
            System.setOut(new PrintStream(data_file));
            for (Map.Entry<String, String> entry: map.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            } // scriem in fisier datele despre useri
            succeded.setForeground(new Color(22, 122, 72));
            succeded.setText("Cont creat!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // logarea in cont
    private void login_account (String username, String password) {
        File data_file = new File ("login.txt");
        if (!data_file.exists())
        {
            succeded.setForeground(new Color(186, 26, 63));
            succeded.setText("Contul nu există!");
            return;
        }
        if (username.length() == 0 || password.length() == 0) {
            succeded.setForeground(new Color(186, 26, 63));
            succeded.setText("Completați toate câmpurile!");
            return;
        }
        String original = password;
        password = encypherPassword(password); // criptare
        Scanner scan = null;
        try {
            scan = new Scanner (data_file);
            String line;
            while (scan.hasNextLine()) {
                line = scan.nextLine();
                String[] data = line.split(" ");
                if (username.equals(data[0])) {
                    if (password.equals(data[1])) { // daca contul si username-ul sunt corecte => start aplicatie
                        succeded.setForeground(new Color(22, 122, 72));
                        succeded.setText("Logare efectuată cu succes!");
                        if (checkFiles())
                            new WelcomePage(data[0], original); // aplicatie
                        else
                            new UploadFiles(data[0], data[1]); // daca nu-s toate fisierele, le incarcam
                        setVisible(false); //you can't see me!
                        dispose();
                        return;
                    } else {
                        succeded.setForeground(new Color(186, 26, 63));
                        succeded.setText("Parola greșită!");
                        return;
                    }
                }
            }
            scan.close();
            succeded.setForeground(new Color(186, 26, 63));
            succeded.setText("Contul nu există!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // vedem daca sunt toate cele 3 fisiere in radacina proiectului
    private boolean checkFiles () {
        File folder = new File (".");
        File[] files = folder.listFiles();
        int count = 0;
        for (File fisier: files) {
            if (fisier.getAbsolutePath().contains("facturi.txt"))
                count++;
            if (fisier.getAbsolutePath().contains("taxe.txt"))
                count++;
            if (fisier.getAbsolutePath().contains("produse.txt"))
                count++;
        }
        return (count == 3);
    }


    public AppStart (String username, String password) {
        super ("Logare cont");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(600, 600));
        this.setLayout(new FlowLayout());
        this.getContentPane().setBackground(new Color(66, 167, 244));

        this.user = new JTextField(25);
        this.user.setText(username);
        this.panel = new JPanel();
        this.panel.setBackground(new Color(66, 167, 244));
        this.pass = new JPasswordField(25);
        this.pass.setText(password);
        this.username = new JLabel("Utilizator", JLabel.CENTER);
        this.password = new JLabel("Parolă", JLabel.CENTER);
        this.username.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        this.password.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        this.username.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        this.password.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        this.pass.setFont(new Font("Calibri Light", Font.PLAIN, 20));
        this.user.setFont(new Font("Calibri Light", Font.PLAIN, 20));
        this.username.setForeground(Color.white);
        this.password.setForeground(Color.white);
        this.login_button = new JButton ("Logare");
        this.create_account = new JButton("Creează cont nou");
        this.change_password = new JButton("Schimbați parola");
        this.login_button.setFont(new Font("Calibri Light", Font.PLAIN, 20));
        this.create_account.setFont(new Font("Calibri Light", Font.PLAIN, 20));
        this.change_password.setFont(new Font("Calibri Light", Font.PLAIN, 20));
        this.change_password.setAlignmentX(JButton.CENTER_ALIGNMENT);
        this.succeded = new JLabel("              ");
        this.succeded.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        ImageIcon icon = new ImageIcon("icons\\Apps-preferences-desktop-user-password-icon.png");
        this.setIconImage(icon.getImage());
        this.succeded.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.Y_AXIS));
        this.login_button.setMnemonic(KeyEvent.VK_ENTER);
        // apasarea pe butonul de creare de cont nou
        this.create_account.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                if (button.getText().equals(create_account.getText())) {
                    insert_data(user.getText(), pass.getText());
                }
            }
        });
        // apasarea pe butonul de logare
        this.login_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                if (button.getText().equals(login_button.getText())) {
                    login_account(user.getText(), pass.getText());
                }
            }
        });

        // daca apas pe butonul de schimbarea parolei va aparea fereastra corespunzatoare
        this.change_password.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                if (button.getText().equals(change_password.getText())) {
                    new ChangePassword();
                }
            }
        });

        this.buttons_panel = new JPanel();
        this.buttons_panel.setBackground(new Color(66, 167, 244));
        this.buttons_panel.setLayout(new FlowLayout());
        this.panel.setMinimumSize(new Dimension (300, 400));
        this.buttons_panel.add(this.login_button);
        this.buttons_panel.add(this.create_account);
        this.panel.add(Box.createRigidArea(new Dimension(5,5)));
        this.add(new JLabel(new ImageIcon("icons\\bill-taxes.png")));
        this.panel.add(Box.createRigidArea(new Dimension(5,5)));
        this.panel.add(this.username);
        this.panel.add(Box.createRigidArea(new Dimension(5,5))); // vrem sa aerisim putin
        this.panel.add(this.user);
        this.panel.add(Box.createRigidArea(new Dimension(5,5)));
        this.panel.add(this.password);
        this.panel.add(Box.createRigidArea(new Dimension(5,5)));
        this.panel.add(this.pass);
        this.panel.add(Box.createRigidArea(new Dimension(5,15)));
        this.panel.add(this.buttons_panel);
        this.panel.add(Box.createRigidArea(new Dimension(5,5)));
        this.panel.add(this.change_password);
        this.panel.add(Box.createRigidArea(new Dimension(5,5)));
        this.panel.add(this.succeded);
        this.add(panel);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }

    public static void main (String[] args) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()); // un Look and Feel placut
        }
        catch (UnsupportedLookAndFeelException | ClassNotFoundException
                | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        new AppStart("", "");
    }
}

