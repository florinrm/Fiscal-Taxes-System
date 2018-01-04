package tax_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class UploadFiles extends JFrame {

    public String username;
    public String password;

    public boolean checkFiles () {
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

    public UploadFiles (String username, String password) {
        super ("Încărcare de fișiere");
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setForeground(Color.orange);
        this.setMinimumSize(new Dimension(400, 500));
        this.setLayout(new FlowLayout());
        this.setIconImage(new ImageIcon("icons\\file_upload_icon.png").getImage());
        this.username = username;
        this.password = password;
        this.getContentPane().setBackground(Color.PINK);
        JPanel panel2 = new JPanel ();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        //panel2.setLayout(new GridLayout());
        panel2.setMinimumSize(new Dimension(400, 400));
        JButton produs = new JButton("Încarcă produse.txt");
        JButton taxe = new JButton("Încarcă taxe.txt");
        JButton facturi = new JButton("Încarcă facturi.txt");
        JButton start = new JButton ("Start aplicație!");
        JButton delete_produs = new JButton("Șterge produse.txt");
        JButton delete_taxe = new JButton("Șterge taxe.txt");
        JButton delete_facturi = new JButton("Șterge facturi.txt");
        produs.setFont(new Font("Calibri Light", Font.PLAIN, 20));
        taxe.setFont(new Font("Calibri Light", Font.PLAIN, 20));
        facturi.setFont(new Font("Calibri Light", Font.PLAIN, 20));
        start.setFont(new Font("Calibri Light", Font.PLAIN, 20));
        delete_facturi.setFont(new Font("Calibri Light", Font.PLAIN, 20));
        delete_produs.setFont(new Font("Calibri Light", Font.PLAIN, 20));
        delete_taxe.setFont(new Font("Calibri Light", Font.PLAIN, 20));
        JLabel info = new JLabel("                           ");
        info.setFont(new Font("Segoe UI", Font.PLAIN, 20));

        delete_produs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                if (button.getText().equals(delete_produs.getText())) {
                    File file = new File ("produse.txt");
                    if (file.exists()) {
                        file.delete();
                        info.setForeground(new Color(22, 122, 72));
                        info.setText("produse.txt șters");
                    }
                }
            }
        });
        delete_taxe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                if (button.getText().equals(delete_taxe.getText())) {
                    File file = new File ("taxe.txt");
                    if (file.exists()) {
                        file.delete();
                        info.setForeground(new Color(22, 122, 72));
                        info.setText("taxe.txt șters");
                    }
                }
            }
        });
        delete_facturi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                if (button.getText().equals(delete_facturi.getText())) {
                    File file = new File ("facturi.txt");
                    if (file.exists()) {
                        file.delete();
                        info.setForeground(new Color(22, 122, 72));
                        info.setText("facturi.txt șters");
                    }
                }
            }
        });

        JFileChooser produs_file = new JFileChooser();
        JFileChooser taxe_file = new JFileChooser();
        JFileChooser facturi_file = new JFileChooser();
        produs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                if (button.getText().equals(produs.getText()))
                {
                    if (!new File ("produse.txt").exists()) {
                        produs_file.setDialogTitle("Alege produsele");
                        produs_file.setCurrentDirectory(new File("C:\\"));
                        produs_file.setFileSelectionMode(JFileChooser.FILES_ONLY);
                        produs_file.showOpenDialog(null);
                        File file = produs_file.getSelectedFile();
                        while (file == null) {
                            info.setText("Choose a file!");
                            taxe_file.showOpenDialog(null);
                            file = taxe_file.getSelectedFile();
                        }
                        try {
                            Files.copy(file.toPath(), new File ("produse.txt").toPath());
                            info.setForeground(new Color(22, 122, 72));
                            info.setText("Fișierul produse.txt adăugat!");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        info.setForeground(new Color(186, 26, 63));
                        info.setText("Fișierul produse.txt deja există!");
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
                    if (!new File ("taxe.txt").exists()) {
                        taxe_file.setDialogTitle("Alege taxele");
                        taxe_file.setCurrentDirectory(new File("C:\\"));
                        taxe_file.setFileSelectionMode(JFileChooser.FILES_ONLY);
                        taxe_file.showOpenDialog(null);
                        File file = taxe_file.getSelectedFile();
                        while (file == null) {
                            info.setText("Choose a file!");
                            taxe_file.showOpenDialog(null);
                            file = taxe_file.getSelectedFile();
                        }
                        try {
                            Files.copy(file.toPath(), new File ("taxe.txt").toPath());
                            info.setForeground(new Color(22, 122, 72));
                            info.setText("Fișierul taxe.txt adăugat!");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                    } else {
                        info.setForeground(new Color(186, 26, 63));
                        info.setText("Fișierul taxe.txt deja există!");
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
                    if (!new File("facturi.txt").exists()) {
                        facturi_file.setDialogTitle("Alege facturile");
                        facturi_file.setCurrentDirectory(new File("C:\\"));
                        facturi_file.setFileSelectionMode(JFileChooser.FILES_ONLY);
                        facturi_file.showOpenDialog(null);
                        File file = facturi_file.getSelectedFile();
                        while (file == null) {
                            info.setText("Choose a file!");
                            taxe_file.showOpenDialog(null);
                            file = taxe_file.getSelectedFile();
                        }
                        try {
                            Files.copy(file.toPath(), new File ("facturi.txt").toPath());
                            info.setForeground(new Color(22, 122, 72));
                            info.setText("Fișierul facturi.txt adăugat!");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        info.setForeground(new Color(186, 26, 63));
                        info.setText("Fișierul facturi.txt deja există!");
                    }
                }
            }
        });

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                if (button.getText().equals(start.getText())) {
                    if (checkFiles()) {
                        new WelcomePage(username, password);
                        dispose();
                        setVisible(false);
                    }
                    else
                        info.setForeground(new Color(186, 26, 63));
                        info.setText("Încarcă toate fișierele!");
                }
            }
        });

        panel2.setBackground(Color.pink);
        panel2.add(Box.createRigidArea(new Dimension(5,10)));
        panel2.add(produs);
        panel2.add(Box.createRigidArea(new Dimension(5,10)));
        panel2.add(delete_produs);
        panel2.add(Box.createRigidArea(new Dimension(5,10)));
        panel2.add(taxe);
        panel2.add(Box.createRigidArea(new Dimension(5,10)));
        panel2.add(delete_taxe);
        panel2.add(Box.createRigidArea(new Dimension(5,10)));
        panel2.add(facturi);
        panel2.add(Box.createRigidArea(new Dimension(5,10)));
        panel2.add(delete_facturi);
        panel2.add(Box.createRigidArea(new Dimension(5,10)));
        panel2.add(start);
        panel2.add(Box.createRigidArea(new Dimension(5,10)));
        panel2.add(info);
        this.add(panel2);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }
}
