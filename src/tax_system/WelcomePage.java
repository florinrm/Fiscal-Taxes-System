package tax_system;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.*;

public class WelcomePage extends JFrame {
    private JLabel user_info;
    private JPanel info1;
    private JPanel info2;
    private JTabbedPane tabs;

    private JButton logout;

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

    public Magazin getMagazinMaximum (ArrayList<Magazin> list) {
        Magazin result = list.get(0);
        for (int i = 0; i < list.size(); ++i) {
            if (new Double(result.getTotalCuTaxe()).compareTo(new Double(list.get(i).getTotalCuTaxe())) <= 0)
                result = list.get(i);
        }
        return result;
    }

    public Factura getFacturaMaxim (ArrayList<Magazin> list) {
        Factura result = list.get(0).facturi.get(0);
        for (int i = 0; i < list.size(); ++i) {
            for (int j = 0; j < list.get(i).facturi.size(); ++j) {
                if (new Double(result.getTotalFaraTaxe()).compareTo(new Double(list.get(i).facturi.get(j).getTotalFaraTaxe())) <= 0)
                    result = list.get(i).facturi.get(j);
            }
        }
        return result;
    }

    public Magazin searchFactura (ArrayList<Magazin> list, Factura factura) {
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i).facturi.contains(factura))
                return list.get(i);
        }
        return null;
    }

    public Magazin getMagazinMaxTara (ArrayList<Magazin> list, String country) {
        Magazin result = list.get(0);
        for (int i = 0; i < list.size(); ++i) {
            if (new Double(list.get(i).getTotalTaraCuTaxe(country)).compareTo(result.getTotalTaraCuTaxe(country)) >= 0)
                result = list.get(i);
        }
        return result;
    }

    public Magazin getMagazinMaxCategorie (ArrayList<Magazin> list, String categorie) {
        Magazin result = list.get(0);
        for (int i = 0; i < list.size(); ++i) {
            if (new Double(list.get(i).getTotalCategorieCuTaxe(categorie)).compareTo(result.getTotalCategorieCuTaxe(categorie)) >= 0)
                result = list.get(i);
        }
        return result;
    }

    public WelcomePage (String username) {
        super ("Application Home");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.BLUE);
        this.setMinimumSize(new Dimension(700, 400));
        this.getContentPane().setLayout(new BorderLayout(10, 10));
        ImageIcon icon = new ImageIcon("Desktop-icon.png");
        this.setIconImage(icon.getImage());
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
                        tabs.setEnabledAt(3, false);
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
                        tabs.setEnabledAt(3, false);
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
                        tabs.setEnabledAt(3, false);
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
                    if (! new File ("produse.txt").exists()) {
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
                        if (!tabs.isEnabledAt(3) && checkFiles())
                            tabs.setEnabledAt(3, true);
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
                    new File ("taxe.txt").delete();
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
                    if (!tabs.isEnabledAt(3) && checkFiles())
                        tabs.setEnabledAt(3, true);
                }
            }
        });
        facturi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                if (button.getText().equals(facturi.getText()))
                {
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
                    if (!tabs.isEnabledAt(3) && checkFiles())
                        tabs.setEnabledAt(3, true);
                }
            }
        });

        gestiune.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                if (button.getText().equals(gestiune.getText())) {
                    if (checkFiles()) {
                        new Main();
                        info.setText("Gestiunea s-a facut!");
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
        panel2.add(gestiune);
        panel2.add(Box.createRigidArea(new Dimension(5,10)));
        panel2.add(info);


        FileParsing parsing = new FileParsing();
        Vector<Produs> list_produse = parsing.getListProdus("produse.txt");
        HashMap <String, HashMap<String, Double>> map = parsing.getTaxe1("taxe.txt");
        ArrayList <Magazin> list_magazine = parsing.getMagazine("facturi.txt", list_produse, map);
        DecimalFormat df = new DecimalFormat("#.####");


        JPanel panel4 = new JPanel();
        panel4.setLayout(new BoxLayout(panel4, BoxLayout.X_AXIS));
        JPanel box_panel1 = new JPanel();
        box_panel1.setLayout(new BoxLayout(box_panel1, BoxLayout.Y_AXIS));
        JPanel box_panel2 = new JPanel();
        box_panel2.setLayout(new BoxLayout(box_panel1, BoxLayout.Y_AXIS));
        Magazin maximum_maga = getMagazinMaximum(list_magazine);

        JLabel header1 = new JLabel("Magazinul cu cele mai mari vanzari");
        JLabel nume_magazin1 = new JLabel("Nume: " + maximum_maga.nume);
        JLabel total_fara_taxe1 = new JLabel("Totalul fara taxe: " + df.format(maximum_maga.getTotalFaraTaxe()).replaceAll(",", "."));
        JLabel total_cu_taxe1 = new JLabel("Totalul cu taxe: " + df.format(maximum_maga.getTotalCuTaxe()).replaceAll(",", "."));
        JLabel total_cu_taxe_scutite1 = new JLabel("Totalul cu taxe scutite: " + df.format(maximum_maga.getTotalCuTaxeScutite()).replaceAll(",", "."));

        Factura max_factura = getFacturaMaxim(list_magazine);
        JLabel factura_header = new JLabel ("Factura cu suma totala cea mai mare");
        JLabel nume_factura = new JLabel ("Nume factura: " + max_factura.denumire);
        JLabel prov_factura = new JLabel ("Magazin de provenienta: " + searchFactura(list_magazine, max_factura).nume);
        JLabel total_fara_taxe_factura = new JLabel("Totalul fara taxe: " + df.format(max_factura.getTotalFaraTaxe()).replaceAll(",", "."));
        JLabel total_cu_taxe_factura = new JLabel("Totalul cu taxe: " + df.format(max_factura.getTotalCuTaxe()).replaceAll(",", "."));

        box_panel1.add(header1);
        box_panel1.add(Box.createRigidArea(new Dimension(10,5)));
        box_panel1.add(nume_magazin1);
        box_panel1.add(Box.createRigidArea(new Dimension(10,5)));
        box_panel1.add(total_fara_taxe1);
        box_panel1.add(Box.createRigidArea(new Dimension(10,5)));
        box_panel1.add(total_cu_taxe1);
        box_panel1.add(Box.createRigidArea(new Dimension(10,5)));
        box_panel1.add(total_cu_taxe_scutite1);
        box_panel1.add(Box.createRigidArea(new Dimension(10,30)));
        box_panel1.add(factura_header);
        box_panel1.add(Box.createRigidArea(new Dimension(10,5)));
        box_panel1.add(nume_factura);
        box_panel1.add(Box.createRigidArea(new Dimension(10,5)));
        box_panel1.add(prov_factura);
        box_panel1.add(Box.createRigidArea(new Dimension(10,5)));
        box_panel1.add(total_fara_taxe_factura);
        box_panel1.add(Box.createRigidArea(new Dimension(10,5)));
        box_panel1.add(total_cu_taxe_factura);
        box_panel1.add(Box.createRigidArea(new Dimension(10,5)));

        TreeSet<String> countries = parsing.tariOrigine;
        TreeSet<String> categorii_produse = parsing.categoriiProduse;

        DefaultListModel<String> model1 = new DefaultListModel<>();
        JList<String> list_countries = new JList<>(model1);
        list_countries.setVisibleRowCount(15);
        list_countries.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrolling_countries = new JScrollPane(list_countries);
        scrolling_countries.setMaximumSize(new Dimension(200, 300));
        scrolling_countries.setVerticalScrollBar(new JScrollBar(JScrollBar.VERTICAL));
        Iterator iterator1 = countries.iterator();
        while (iterator1.hasNext()) {
            String country = iterator1.next().toString();
            Magazin magazin_country = getMagazinMaxTara(list_magazine, country);
            model1.addElement("Magazinul cu cele mai mari vanzari in " + country);
            model1.addElement("Nume: " + maximum_maga.nume);
            model1.addElement("Totalul fara taxe: " + df.format(magazin_country.getTotalFaraTaxe()).replaceAll(",", "."));
            model1.addElement("Totalul cu taxe: " + df.format(magazin_country.getTotalCuTaxe()).replaceAll(",", "."));
            model1.addElement("Totalul cu taxe scutite: " + df.format(magazin_country.getTotalCuTaxeScutite()).replaceAll(",", "."));
            model1.addElement("\n\n");
        }
        DefaultListModel<String> model2 = new DefaultListModel<>();
        JList<String> list_categorii = new JList<>(model2);
        list_categorii.setVisibleRowCount(15);
        list_categorii.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrolling_categorii = new JScrollPane(list_categorii);
        scrolling_categorii.setMaximumSize(new Dimension(200, 300));
        scrolling_categorii.setVerticalScrollBar(new JScrollBar(JScrollBar.VERTICAL));
        Iterator iterator2 = categorii_produse.iterator();
        while (iterator2.hasNext()) {
            String categorie = iterator2.next().toString();
            System.out.println(categorie);
            Magazin magazin_categorie = getMagazinMaxCategorie(list_magazine, categorie);
            model2.addElement("Magazinul cu cele mai mari vanzari in " + categorie);
            model2.addElement("Nume: " + magazin_categorie.nume);
            model2.addElement("Totalul fara taxe: " + df.format(magazin_categorie.getTotalFaraTaxe()).replaceAll(",", "."));
            model2.addElement("Totalul cu taxe: " + df.format(magazin_categorie.getTotalCuTaxe()).replaceAll(",", "."));
            model2.addElement("Totalul cu taxe scutite: " + df.format(magazin_categorie.getTotalCuTaxeScutite()).replaceAll(",", "."));
            model2.addElement("\n\n");
        }

        panel4.add(Box.createRigidArea(new Dimension(10,10)));
        panel4.add(box_panel1);
        panel4.add(Box.createRigidArea(new Dimension(10,10)));
        panel4.add(scrolling_countries);
        panel4.add(Box.createRigidArea(new Dimension(10,10)));
        panel4.add(scrolling_categorii);

        this.tabs.setBorder(BorderFactory.createCompoundBorder(null, paddingBorder));
        this.tabs.setFont(new Font( "Georgia", Font.PLAIN, 14 ));
        this.tabs.add("  User Info  ", info1);
        this.tabs.add("  Load Files  ", panel2);
        this.tabs.add("  Products  ", new JPanel());
        this.tabs.add("  Statistics  ", panel4);

        if (!checkFiles())
            this.tabs.setEnabledAt(3, false);

        this.info2 = new JPanel();
        info2.setLayout(new BoxLayout(this.info2, BoxLayout.X_AXIS));
        this.info2.add(tabs); // adaugam in info2 JTabbedPane
        this.add(info2);
        this.setVisible(true);
        this.pack();
    }
}
