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

    public UploadFiles (String username) {
        super ("Incarca fisierele");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setForeground(Color.orange);
        this.setMinimumSize(new Dimension(400, 400));
        this.setLayout(new FlowLayout());
        this.username = username;
        JPanel panel2 = new JPanel ();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        //panel2.setLayout(new GridLayout());
        panel2.setMinimumSize(new Dimension(400, 400));
        JButton produs = new JButton("Load produse");
        JButton taxe = new JButton("Load taxe");
        JButton facturi = new JButton("Load facturi");
        JButton start = new JButton ("Start aplicatie!");
        JButton delete_produs = new JButton("Delete produse.txt");
        JButton delete_taxe = new JButton("Delete taxe.txt");
        JButton delete_facturi = new JButton("Delete facturi.txt");
        JLabel info = new JLabel();

        delete_produs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                if (button.getText().equals(delete_produs.getText())) {
                    File file = new File ("produse.txt");
                    if (file.exists()) {
                        file.delete();
                        info.setText("produse.txt deleted");
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
                        info.setText("taxe.txt deleted");
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
                        info.setText("facturi.txt deleted");
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
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        info.setText("Fisierul produse.txt deja exista!");
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
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                    }
                } else {
                    info.setText("Fisierul taxe.txt deja exista!");
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
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                } else {
                    info.setText("Fisierul facturi.txt deja exista!");
                }
            }
        });

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                if (button.getText().equals(start.getText())) {
                    if (checkFiles()) {
                        new WelcomePage(username);
                        dispose();
                        setVisible(false);
                    }
                    else
                        info.setText("Pune boss toate fisierele");
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
        this.setContentPane(panel2);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }
}
