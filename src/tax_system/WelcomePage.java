package tax_system;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class WelcomePage extends JFrame {
    private JLabel user_info;
    private JPanel info1;
    private JPanel info2;
    private JTabbedPane tabs;

    private JButton logout;

    public WelcomePage (String username) {
        super ("Application Home");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.BLUE);
        this.setMinimumSize(new Dimension(600, 400));
        this.getContentPane().setLayout(new BorderLayout(10, 10));
        this.user_info = new JLabel("You are logged as: " + username);
        this.user_info.setFont(new Font("Georgia", Font.CENTER_BASELINE, 12));
        this.info1 = new JPanel();
        this.tabs = new JTabbedPane();
        this.logout = new JButton("Logout  ");
        logout.setHorizontalTextPosition(AbstractButton.LEADING);
        logout.setVerticalTextPosition(SwingConstants.CENTER);
        logout.setVerticalAlignment(SwingConstants.CENTER);

        //this.info1.setMinimumSize(new Dimension(40, 20));
        this.info1.setLayout(new BoxLayout(this.info1, BoxLayout.Y_AXIS));
        Border paddingBorder = BorderFactory.createEmptyBorder(10,0,0,0);
        Border paddingBorder1 = BorderFactory.createEmptyBorder(10,0,10,0);
        Border paddingBorder2 = BorderFactory.createEmptyBorder(10,20,10,10);
        // Border border = BorderFactory.createLineBorder(Color.BLUE);
        user_info.setBorder(BorderFactory.createCompoundBorder(null, paddingBorder1));
        // user_info.setHorizontalAlignment(JLabel.LEFT);
        logout.setBorder(BorderFactory.createCompoundBorder(null, paddingBorder2));
        // logout.setHorizontalTextPosition(SwingConstants.CENTER);
        this.info1.add(user_info);
        JPanel button_panel = new JPanel();
        button_panel.setLayout(new BoxLayout(button_panel, BoxLayout.Y_AXIS));
        button_panel.add(Box.createRigidArea(new Dimension(20,0)));
        button_panel.add(logout);
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                if (button.getText().equals("Logout  ")) {
                    new AppStart();
                    setVisible(false);
                    dispose();
                }
            }
        });
        this.info1.add(button_panel);

        JPanel panel2 = new JPanel ();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        panel2.setMaximumSize(new Dimension(50, 60));
        JButton produs = new JButton("Load produse");
        JButton taxe = new JButton("Load taxe");
        JButton facturi = new JButton("Load facturi");
        JButton gestiune = new JButton ("Gestiune");

        JFileChooser produs_file = new JFileChooser();
        JFileChooser taxe_file = new JFileChooser();
        JFileChooser facturi_file = new JFileChooser();
        produs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                if (button.getText().equals(produs.getText()))
                {
                    produs_file.setDialogTitle("Choose a file");
                    produs_file.setCurrentDirectory(new File("C:\\"));
                    produs_file.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    produs_file.showOpenDialog(null);
                    File file = produs_file.getSelectedFile();
                    try {
                        Files.copy(file.toPath(), new File ("produs.txt").toPath());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        taxe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                if (button.getText().equals(taxe.getText()))
                {
                    taxe_file.setDialogTitle("Choose a file");
                    taxe_file.setCurrentDirectory(new File("C:\\"));
                    taxe_file.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    taxe_file.showOpenDialog(null);
                    File file = taxe_file.getSelectedFile();
                    try {
                        Files.copy(file.toPath(), new File ("taxe.txt").toPath());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        facturi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                if (button.getText().equals(facturi.getText()))
                {
                    facturi_file.setDialogTitle("Choose a file");
                    facturi_file.setCurrentDirectory(new File("C:\\"));
                    facturi_file.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    facturi_file.showOpenDialog(null);
                    File file = facturi_file.getSelectedFile();
                    try {
                        Files.copy(file.toPath(), new File ("facturi.txt").toPath());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
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

        gestiune.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                if (button.getText().equals(gestiune.getText())) {
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
                    if (count == 3)
                        new Main();
                    else
                        System.out.println("NOOOOPE");
                }
            }
        });

        panel2.setBackground(Color.pink);
        panel2.add(Box.createRigidArea(new Dimension(5,10)));
        panel2.add(produs);
        panel2.add(Box.createRigidArea(new Dimension(5,10)));
        panel2.add(taxe);
        panel2.add(Box.createRigidArea(new Dimension(5,10)));
        panel2.add(facturi);
        panel2.add(Box.createRigidArea(new Dimension(5,10)));
        panel2.add(gestiune);

        this.tabs.setBorder(BorderFactory.createCompoundBorder(null, paddingBorder));
        this.tabs.setFont(new Font( "Georgia", Font.PLAIN, 14 ));
        this.tabs.add("  User Info  ", info1);
        this.tabs.add("  Load Files  ", panel2);
        this.tabs.add("  Products  ", new JPanel());
        this.tabs.add("  Statistics  ", new JPanel());



        this.info2 = new JPanel();
        info2.setLayout(new BoxLayout(this.info2, BoxLayout.X_AXIS));
        this.info2.add(tabs); // adaugam in info2 JTabbedPane
        this.add(info2);
        this.setVisible(true);
        this.pack();
    }
    /*
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
        new WelcomePage();
    } */
}