package tax_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ChangePassword extends JFrame {

    private JLabel notification = new JLabel();

    public String encypherPassword (String password) {
        String cypher = "";
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            cypher = hexString.toString().toUpperCase();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return cypher;
    }

    public ChangePassword () {
        super ("Change password");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.BLUE);
        this.setMinimumSize(new Dimension(400, 200));
        this.setLayout(new FlowLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        JTextField username = new JTextField(25);
        JPasswordField old_password = new JPasswordField(25);
        JPasswordField new_password = new JPasswordField(25);
        JLabel username_info = new JLabel("Username");
        JLabel old_password_info = new JLabel("Vechea parola");
        JLabel new_password_info = new JLabel("Noua parola");
        JButton change = new JButton("Salveaza");

        change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                if (button.getText().equals(change.getText())) {
                    if (username.getText().length() == 0
                            || old_password.getText().length() == 0
                            || new_password.getText().length() == 0) {
                        notification.setText("Completati toate campurile!");
                    } else {
                        HashMap<String, String> map = new HashMap <>();
                        Scanner scan = null;
                        String user = username.getText();
                        String old_pass = old_password.getText();
                        String new_pass = new_password.getText();
                        old_pass = encypherPassword(old_pass);
                        new_pass = encypherPassword(new_pass);
                        try {
                            scan = new Scanner(new File("login.txt"));
                            boolean found = false;
                            while (scan.hasNextLine()) {
                                String line = scan.nextLine();
                                String[] data = line.split(" ");
                                map.put(data[0], data[1]);
                                if (data[0].equals(user)) {
                                    if (data[1].equals(old_pass)) {
                                        found = true;
                                        map.put(data[0], new_pass);
                                    } else {
                                        notification.setText("Parola veche introdusa gresit!");
                                    }
                                }
                            }
                            if (found) {
                                System.setOut(new PrintStream(new File("login.txt")));
                                notification.setText("Parola schimbata cu succes!");
                                for (Map.Entry<String, String> entry: map.entrySet()) {
                                    System.out.println(entry.getKey() + " " + entry.getValue());
                                }
                                dispose();
                                setVisible(false);
                            } else {
                                notification.setText("Username gresit!");
                            }
                        } catch (FileNotFoundException k) {
                            k.printStackTrace();
                        }
                    }
                }
            }
        });

        panel.add(username_info);
        panel.add(Box.createRigidArea(new Dimension(5,5)));
        panel.add(username);
        panel.add(Box.createRigidArea(new Dimension(5,5)));
        panel.add(old_password_info);
        panel.add(Box.createRigidArea(new Dimension(5,5)));
        panel.add(old_password);
        panel.add(Box.createRigidArea(new Dimension(5,5)));
        panel.add(new_password_info);
        panel.add(Box.createRigidArea(new Dimension(5,5)));
        panel.add(new_password);
        panel.add(Box.createRigidArea(new Dimension(5,5)));
        panel.add(change);
        panel.add(Box.createRigidArea(new Dimension(5,5)));
        panel.add(this.notification);

        this.add(panel);
        this.pack();
        this.setVisible(true);
    }
}
