package tax_system;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
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

    // daca exista produsul (un produs cu pret 0 la o tara = produs inexistent)
    public boolean checkProdus (Vector<Produs> list, String denumire, String categorie, String tara) {
        for (int i = 0; i < list.size(); ++i) {
            if (denumire.equals(list.get(i).getDenumire())
                    && categorie.equals(list.get(i).getCategorie())
                    && tara.equals(list.get(i).getTaraOrigine())) {
                if (new Double (list.get(i).getPret()).equals(new Double(0)))
                    return false;
                else
                    return true;
            }
        }
        return false;
    }

    public double findPret (Vector<Produs> list, String nume, String taraOrigine, String categorie) {
        for (int i = 0; i < list.size(); ++i) {
            if (nume.equals(list.get(i).getDenumire())
                    && categorie.equals(list.get(i).getCategorie())
                    && taraOrigine.equals(list.get(i).getTaraOrigine()))
                return list.get(i).getPret();
        }
        return 0;
    }

    public void updateProduseFile (Vector<Produs> list, TreeSet<String> lista_tari) {
        ArrayList<String> countries = new ArrayList<>(lista_tari);
        try {
            System.setOut(new PrintStream(new File("produse.txt")));
            System.out.print("Produs Categorie ");
            int no_countries = countries.size();
            for (int i = 0; i < no_countries; ++i) {
                if (i == no_countries - 1)
                    System.out.print(countries.get(i) + "\n");
                else
                    System.out.print(countries.get(i) + " ");
            }
            Collections.sort(list, new Comparator<Produs>() {
                @Override
                public int compare(Produs o1, Produs o2) {
                    if (o1.getCategorie().equals(o2.getCategorie()))
                        return (-1) * o1.getDenumire().compareTo(o2.getDenumire());
                    else
                        return (-1) * o1.getCategorie().compareTo(o2.getCategorie());
                }
            });
            for (int i = 0; i < list.size(); i = i + no_countries) {
                System.out.print(list.get(i).getDenumire() + " " + list.get(i).getCategorie() + " ");
                for (int j = 0; j < no_countries; ++j) {
                    if (j == no_countries - 1)
                    {
                        if (i == list.size() - 1)
                            System.out.print (findPret(list, list.get(i).getDenumire(), countries.get(j), list.get(i).getCategorie()));
                        else
                            System.out.print (findPret(list, list.get(i).getDenumire(), countries.get(j), list.get(i).getCategorie()) + "\n");
                    }
                    else
                        System.out.print (findPret(list, list.get(i).getDenumire(), countries.get(j), list.get(i).getCategorie()) + " ");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.setOut(System.out);
    }

    public WelcomePage (String username) {
        super ("Sistem de facturi fiscale");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.BLUE);
        this.setMinimumSize(new Dimension(1100, 740));
        this.getContentPane().setLayout(new BorderLayout(10, 10));
        ImageIcon icon = new ImageIcon("icons\\dollar_icon.jpg");
        this.setIconImage(icon.getImage());
        this.user_info = new JLabel("Sunteți logat ca: " + username);
        this.user_info.setFont(new Font("Cambria Math", Font.PLAIN, 30));
        this.user_info.setForeground(Color.white);
        this.info1 = new JPanel();
        this.info1.setBackground(new Color(104, 11, 31));
        this.tabs = new JTabbedPane();
        this.logout = new JButton("Delogare");
        JButton close = new JButton("Închide");
        this.logout.setFont(new Font("Calibri Light", Font.PLAIN, 20));
        close.setFont(new Font("Calibri Light", Font.PLAIN, 20));
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                if (button.getText().equals(close.getText())) {
                    dispose();
                    setVisible(false);
                    System.exit(69); // wink wink
                }
            }
        });
        this.user_info.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        logout.setAlignmentX(JButton.CENTER_ALIGNMENT);
        JPanel login_box = new JPanel();
        login_box.setLayout(new BoxLayout(login_box, BoxLayout.PAGE_AXIS));
        login_box.setBackground(new Color(104, 11, 31));
        this.info1.setLayout(new FlowLayout());
        Border paddingBorder = BorderFactory.createEmptyBorder(10,0,0,0);
        JLabel profile_icon = new JLabel(new ImageIcon("icons\\login_icon_2.png"));
        profile_icon.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        login_box.add(profile_icon);
        login_box.add(user_info);
        JPanel button_panel = new JPanel();
        button_panel.setBackground(new Color(104, 11, 31));
        button_panel.setLayout(new BoxLayout(button_panel, BoxLayout.PAGE_AXIS));
        button_panel.add(Box.createRigidArea(new Dimension(20,0)));
        button_panel.add(logout);
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                if (button.getText().equals(logout.getText())) {
                    new AppStart();
                    setVisible(false);
                    dispose();
                }
            }
        });
        close.setAlignmentX(JButton.CENTER_ALIGNMENT);
        login_box.add(Box.createRigidArea(new Dimension(10,10)));
        login_box.add(button_panel);
        login_box.add(Box.createRigidArea(new Dimension(10,10)));
        login_box.add(close);
        this.info1.add(Box.createRigidArea(new Dimension(10,10)));
        this.info1.add(login_box);

        FileParsing parsing = new FileParsing();
        final Vector<Produs> list_produse = parsing.getListProdus("produse.txt");
        HashMap <String, HashMap<String, Double>> map = parsing.getTaxe1("taxe.txt");
        ArrayList <Magazin> list_magazine = parsing.getMagazine("facturi.txt", list_produse, map);
        DecimalFormat df = new DecimalFormat("#.####");

        JPanel panel2 = new JPanel ();
        JPanel button_box = new JPanel();
        button_box.setLayout(new BoxLayout(button_box, BoxLayout.PAGE_AXIS));
        button_box.setMinimumSize(new Dimension(400, 100));
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.LINE_AXIS));
        // panel2.setMaximumSize(new Dimension(50, 60));
        panel2.setBackground(Color.PINK);
        JButton produs = new JButton("Încarcă produse.txt");
        JButton taxe = new JButton("Încarcă taxe.txt");
        JButton facturi = new JButton("Încarcă facturi.txt");
        JButton gestiune = new JButton ("Gestiune");
        JButton delete_produs = new JButton("Șterge produse.txt");
        JButton delete_taxe = new JButton("Șterge taxe.txt");
        JButton delete_facturi = new JButton("Șterge facturi.txt");
        produs.setFont(new Font("Calibri Light", Font.PLAIN, 20));
        taxe.setFont(new Font("Calibri Light", Font.PLAIN, 20));
        facturi.setFont(new Font("Calibri Light", Font.PLAIN, 20));
        gestiune.setFont(new Font("Calibri Light", Font.PLAIN, 20));
        delete_facturi.setFont(new Font("Calibri Light", Font.PLAIN, 20));
        delete_produs.setFont(new Font("Calibri Light", Font.PLAIN, 20));
        delete_taxe.setFont(new Font("Calibri Light", Font.PLAIN, 20));
        JLabel info = new JLabel("                                 ");
        info.setFont(new Font("Cambria Math", Font.PLAIN, 20));
        JLabel imgLabel = new JLabel(new ImageIcon("icons\\database-image.png"));

        JLabel some_space = new JLabel("Încărcați sau ștergeți fișierele de procesat");
        some_space.setFont(new Font("Cambria Math", Font.PLAIN, 20));

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
                        tabs.setEnabledAt(3, false);
                        tabs.setEnabledAt(2, false);
                    } else {
                        info.setForeground(new Color(186, 26, 63));
                        info.setText("Fișierul nu există!");
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
                        tabs.setEnabledAt(3, false);
                    } else {
                        info.setForeground(new Color(186, 26, 63));
                        info.setText("Fișierul nu există!");
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
                        tabs.setEnabledAt(3, false);
                    } else {
                        info.setForeground(new Color(186, 26, 63));
                        info.setText("Fișierul nu există!");
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
                            info.setForeground(new Color(186, 26, 63));
                            info.setText("Alege fișierul!");
                            taxe_file.showOpenDialog(null);
                            file = taxe_file.getSelectedFile();
                        }
                        try {
                            Files.copy(file.toPath(), new File ("produse.txt").toPath());
                            Vector<Produs> copy_list = parsing.getListProdus("produse.txt");
                            list_produse.removeAll(list_produse);
                            list_produse.addAll(copy_list);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        if (!tabs.isEnabledAt(3) && checkFiles())
                            tabs.setEnabledAt(3, true);
                        File produs_file = new File ("produse.txt");
                        if (produs_file.exists() && !tabs.isEnabledAt(2))
                            tabs.setEnabledAt(2, true);
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
                            info.setForeground(new Color(186, 26, 63));
                            info.setText("Alege fișierul!");
                            taxe_file.showOpenDialog(null);
                            file = taxe_file.getSelectedFile();
                        }
                        try {
                            Files.copy(file.toPath(), new File ("taxe.txt").toPath());
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        if (!tabs.isEnabledAt(3) && checkFiles()) {
                            tabs.setEnabledAt(3, true);
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
                            info.setForeground(new Color(186, 26, 63));
                            info.setText("Alege fișierul!");
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
                    } else {
                        info.setForeground(new Color(186, 26, 63));
                        info.setText("Fișierul facturi.txt deja există!");
                    }
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
                        info.setForeground(new Color(22, 122, 72));
                        info.setText("Gestiunea s-a făcut!");
                    }
                    else {
                        info.setForeground(new Color(186, 26, 63));
                        info.setText("Încarcă toate fișierele!");
                    }
                }
            }
        });

        button_box.setBackground(Color.pink);
        // button_box.add(Box.createRigidArea(new Dimension(5,10)));
        button_box.add(some_space);
        button_box.add(Box.createRigidArea(new Dimension(5,10)));
        button_box.add(produs);
        button_box.add(Box.createRigidArea(new Dimension(5,10)));
        button_box.add(delete_produs);
        button_box.add(Box.createRigidArea(new Dimension(5,10)));
        button_box.add(taxe);
        button_box.add(Box.createRigidArea(new Dimension(5,10)));
        button_box.add(delete_taxe);
        button_box.add(Box.createRigidArea(new Dimension(5,10)));
        button_box.add(facturi);
        button_box.add(Box.createRigidArea(new Dimension(5,10)));
        button_box.add(delete_facturi);
        button_box.add(Box.createRigidArea(new Dimension(5,10)));
        button_box.add(gestiune);
        button_box.add(Box.createRigidArea(new Dimension(5,10)));
        button_box.add(info);
        button_box.add(Box.createRigidArea(new Dimension(5,60)));
        panel2.add(Box.createRigidArea(new Dimension(50,10)));
        panel2.add(button_box);
        panel2.add(Box.createRigidArea(new Dimension(120,10)));
        panel2.add(imgLabel);

        JPanel panel4 = new JPanel();
        panel4.setBackground(new Color(66, 143, 244));
        panel4.setLayout(new BoxLayout(panel4, BoxLayout.X_AXIS));
        JPanel box_panel1 = new JPanel();
        box_panel1.setBackground(Color.WHITE);
        box_panel1.setLayout(new BoxLayout(box_panel1, BoxLayout.Y_AXIS));
        box_panel1.setMaximumSize(new Dimension(340, 400));
        JPanel box_panel2 = new JPanel();
        box_panel2.setLayout(new BoxLayout(box_panel1, BoxLayout.Y_AXIS));

        Magazin maximum_maga = getMagazinMaximum(list_magazine);
        JLabel header1 = new JLabel("Magazinul cu cele mai mari vânzări");
        header1.setFont(new Font("Cambria Math", Font.PLAIN, 20));
        JLabel nume_magazin1 = new JLabel("Nume: " + maximum_maga.nume);
        nume_magazin1.setFont(new Font("Cambria Math", Font.PLAIN, 20));
        JLabel total_fara_taxe1 = new JLabel("Totalul fără taxe: "
                + df.format(maximum_maga.getTotalFaraTaxe()).replaceAll(",", "."));
        total_fara_taxe1.setFont(new Font("Cambria Math", Font.PLAIN, 20));
        JLabel total_cu_taxe1 = new JLabel("Totalul cu taxe: "
                + df.format(maximum_maga.getTotalCuTaxe()).replaceAll(",", "."));
        total_cu_taxe1.setFont(new Font("Cambria Math", Font.PLAIN, 20));
        JLabel total_cu_taxe_scutite1 = new JLabel("Totalul cu taxe scutite: "
                + df.format(maximum_maga.getTotalCuTaxeScutite()).replaceAll(",", "."));
        total_cu_taxe_scutite1.setFont(new Font("Cambria Math", Font.PLAIN, 20));

        Factura max_factura = getFacturaMaxim(list_magazine);
        JLabel factura_header = new JLabel ("Factura cu suma totală cea mai mare");
        factura_header.setFont(new Font("Cambria Math", Font.PLAIN, 20));
        JLabel nume_factura = new JLabel ("Nume factură: " + max_factura.denumire);
        nume_factura.setFont(new Font("Cambria Math", Font.PLAIN, 20));
        JLabel prov_factura = new JLabel ("Magazin de proveniență: "
                + searchFactura(list_magazine, max_factura).nume);
        prov_factura.setFont(new Font("Cambria Math", Font.PLAIN, 20));
        JLabel total_fara_taxe_factura = new JLabel("Totalul fără taxe: "
                + df.format(max_factura.getTotalFaraTaxe()).replaceAll(",", "."));
        total_fara_taxe_factura.setFont(new Font("Cambria Math", Font.PLAIN, 20));
        JLabel total_cu_taxe_factura = new JLabel("Totalul cu taxe: "
                + df.format(max_factura.getTotalCuTaxe()).replaceAll(",", "."));
        total_cu_taxe_factura.setFont(new Font("Cambria Math", Font.PLAIN, 20));

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
        list_countries.setFont(new Font("Cambria Math", Font.PLAIN, 20));
        list_countries.setVisibleRowCount(15);
        list_countries.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrolling_countries = new JScrollPane(list_countries);
        scrolling_countries.setMaximumSize(new Dimension(400, 400));
        scrolling_countries.setVerticalScrollBar(new JScrollBar(JScrollBar.VERTICAL));
        Iterator iterator1 = countries.iterator();
        while (iterator1.hasNext()) {
            String country = iterator1.next().toString();
            Magazin magazin_country = getMagazinMaxTara(list_magazine, country);
            model1.addElement("Magazinul cu cele mai mari vânzari în " + country);
            model1.addElement("Nume: " + maximum_maga.nume);
            model1.addElement("Totalul fără taxe: "
                    + df.format(magazin_country.getTotalFaraTaxe()).replaceAll(",", "."));
            model1.addElement("Totalul cu taxe: "
                    + df.format(magazin_country.getTotalCuTaxe()).replaceAll(",", "."));
            model1.addElement("Totalul cu taxe scutite: "
                    + df.format(magazin_country.getTotalCuTaxeScutite()).replaceAll(",", "."));
            model1.addElement("\n\n");
        }

        DefaultListModel<String> model2 = new DefaultListModel<>();
        JList<String> list_categorii = new JList<>(model2);
        list_categorii.setFont(new Font("Cambria Math", Font.PLAIN, 20));
        list_categorii.setVisibleRowCount(15);
        list_categorii.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrolling_categorii = new JScrollPane(list_categorii);
        scrolling_categorii.setMaximumSize(new Dimension(380, 400));
        scrolling_categorii.setVerticalScrollBar(new JScrollBar(JScrollBar.VERTICAL));
        Iterator iterator2 = categorii_produse.iterator();
        while (iterator2.hasNext()) {
            String categorie = iterator2.next().toString();
            Magazin magazin_categorie = getMagazinMaxCategorie(list_magazine, categorie);
            model2.addElement("Magazinul cu cele mai mari vânzari în " + categorie);
            model2.addElement("Nume: " + magazin_categorie.nume);
            model2.addElement("Totalul fără taxe: "
                    + df.format(magazin_categorie.getTotalFaraTaxe()).replaceAll(",", "."));
            model2.addElement("Totalul cu taxe: "
                    + df.format(magazin_categorie.getTotalCuTaxe()).replaceAll(",", "."));
            model2.addElement("Totalul cu taxe scutite: "
                    + df.format(magazin_categorie.getTotalCuTaxeScutite()).replaceAll(",", "."));
            model2.addElement("\n\n");
        }

        panel4.add(Box.createRigidArea(new Dimension(30,10)));
        panel4.add(box_panel1);
        panel4.add(Box.createRigidArea(new Dimension(30,10)));
        panel4.add(scrolling_countries);
        panel4.add(Box.createRigidArea(new Dimension(30,10)));
        panel4.add(scrolling_categorii);
        panel4.add(Box.createRigidArea(new Dimension(30,10)));

        JPanel panel3 = new JPanel();
        DefaultListModel<String> model_produse = new DefaultListModel<>();
        JList<String> list1 = new JList<>(model_produse);
        list1.setVisibleRowCount(5);
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));
        panel3.setBackground(new Color(22, 122, 72));
        JScrollPane scrolling = new JScrollPane(list1);
        list1.setFont(new Font("Cambria Math", Font.PLAIN, 20));
        scrolling.setMaximumSize(new Dimension(300, 500));
        scrolling.setVerticalScrollBar(new JScrollBar(JScrollBar.VERTICAL));
        Collections.sort(list_produse, new Comparator<Produs>() {
            @Override
            public int compare(Produs o1, Produs o2) {
                if (o1.getCategorie().equals(o2.getCategorie()))
                    return (-1) * o1.getDenumire().compareTo(o2.getDenumire());
                else
                    return (-1) * o1.getCategorie().compareTo(o2.getCategorie());
            }
        });
        for (int i = 0; i < list_produse.size(); ++i) {
            if (! new Double (list_produse.get(i).getPret()).equals(new Double(0))) {
                model_produse.addElement("Nume: " + list_produse.get(i).getDenumire());
                model_produse.addElement("Categorie: " + list_produse.get(i).getCategorie());
                model_produse.addElement("Țară origine: " + list_produse.get(i).getTaraOrigine());
                model_produse.addElement("Preț: " + list_produse.get(i).getPret());
                model_produse.addElement("\n");
            }
        }
        JPanel minipanel1 = new JPanel();
        minipanel1.setBackground(new Color(22, 122, 72));
        minipanel1.setLayout(new BoxLayout(minipanel1, BoxLayout.Y_AXIS));
        minipanel1.setMinimumSize(new Dimension(300, 630));
        String[] options = {"După denumire", "După țară"};
        JLabel criteriu = new JLabel("Alege criteriul de sortare");
        criteriu.setFont(new Font("Cambria Math", Font.PLAIN, 15));
        criteriu.setForeground(Color.WHITE);
        criteriu.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        JComboBox<String> list_options = new JComboBox<>(options);
        list_options.setFont(new Font("Calibri Light", Font.PLAIN, 15));
        list_options.setMaximumSize(new Dimension(120, 20));
        list_options.setAlignmentX(JComboBox.LEFT_ALIGNMENT);
        JButton sort = new JButton("Sortează");
        sort.setFont(new Font("Calibri Light", Font.PLAIN, 15));
        sort.setAlignmentX(JButton.LEFT_ALIGNMENT);
        sort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                if (button.getText().equals(sort.getText())) {
                    if (list_options.getSelectedItem().equals(list_options.getItemAt(0))) {
                        Collections.sort(list_produse, new Comparator<Produs>() {
                            @Override
                            public int compare(Produs o1, Produs o2) {
                                return o1.getDenumire().compareTo(o2.getDenumire());
                            }
                        });
                        model_produse.removeAllElements();
                        for (int i = 0; i < list_produse.size(); ++i) {
                            if (! new Double (list_produse.get(i).getPret()).equals(new Double(0))) {
                                model_produse.addElement("Nume: " + list_produse.get(i).getDenumire());
                                model_produse.addElement("Categorie: " + list_produse.get(i).getCategorie());
                                model_produse.addElement("Țară origine: " + list_produse.get(i).getTaraOrigine());
                                model_produse.addElement("Preț: " + list_produse.get(i).getPret());
                                model_produse.addElement("\n");
                            }
                        }
                    } else {
                        Collections.sort(list_produse, new Comparator<Produs>() {
                            @Override
                            public int compare(Produs o1, Produs o2) {
                                return o1.getTaraOrigine().compareTo(o2.getTaraOrigine());
                            }
                        });
                        model_produse.removeAllElements();
                        for (int i = 0; i < list_produse.size(); ++i) {
                            if (! new Double (list_produse.get(i).getPret()).equals(new Double(0))) {
                                model_produse.addElement("Nume: " + list_produse.get(i).getDenumire());
                                model_produse.addElement("Categorie: " + list_produse.get(i).getCategorie());
                                model_produse.addElement("Țară origine: " + list_produse.get(i).getTaraOrigine());
                                model_produse.addElement("Preț: " + list_produse.get(i).getPret());
                                model_produse.addElement("\n");
                            }
                        }
                    }
                }
            }
        });
        JLabel check_if_prod_adaugat = new JLabel("");
        check_if_prod_adaugat.setFont(new Font("Cambria Math", Font.PLAIN, 15));
        JLabel check_if_prod_sters = new JLabel("");
        check_if_prod_sters.setFont(new Font("Cambria Math", Font.PLAIN, 15));
        JLabel text1 = new JLabel ("Adaugă numele produsului:");
        text1.setForeground(Color.WHITE);
        text1.setFont(new Font("Cambria Math", Font.PLAIN, 15));
        JLabel text2 = new JLabel ("Adaugă prețul produsului:");
        text2.setFont(new Font("Cambria Math", Font.PLAIN, 15));
        text2.setForeground(Color.WHITE);
        JComboBox<String> alege_categoria = new JComboBox<>(new Vector<>(parsing.categoriiProduse));
        alege_categoria.setFont(new Font("Calibri Light", Font.PLAIN, 15));
        alege_categoria.setMaximumSize(new Dimension(120, 20));
        JComboBox<String> alege_tara = new JComboBox<>(new Vector<>(parsing.tariOrigine));
        alege_tara.setFont(new Font("Calibri Light", Font.PLAIN, 15));
        alege_tara.setMaximumSize(new Dimension(120, 20));
        JTextField alege_produs = new JTextField(20);
        alege_produs.setFont(new Font("Cambria Math", Font.PLAIN, 15));
        JTextField alege_pret = new JTextField(15);
        alege_pret.setFont(new Font("Cambria Math", Font.PLAIN, 15));
        JButton adauga = new JButton("Adaugă produs");
        adauga.setFont(new Font("Calibri Light", Font.PLAIN, 15));
        JButton sterge = new JButton("Șterge produs");
        sterge.setFont(new Font("Calibri Light", Font.PLAIN, 15));
        JButton editeaza = new JButton("Editează produs");
        editeaza.setFont(new Font("Calibri Light", Font.PLAIN, 15));
        JButton cauta = new JButton("Caută produs");
        cauta.setFont(new Font("Calibri Light", Font.PLAIN, 15));
        JPanel add_produs = new JPanel(); // panelul pentru adaugarea de produs
        add_produs.setLayout(new BoxLayout(add_produs, BoxLayout.PAGE_AXIS));
        add_produs.setBackground(new Color(22, 122, 72));
        JPanel first_line_add = new JPanel();
        JPanel second_line_add = new JPanel();
        first_line_add.setLayout(new BoxLayout(first_line_add, BoxLayout.PAGE_AXIS));
        first_line_add.setBackground(new Color(22, 122, 72));
        second_line_add.setLayout(new BoxLayout(second_line_add, BoxLayout.PAGE_AXIS));
        second_line_add.setBackground(new Color(22, 122, 72));
        first_line_add.add(text1);
        first_line_add.add(Box.createRigidArea(new Dimension(5,10)));
        first_line_add.add(alege_produs);
        second_line_add.add(text2);
        second_line_add.add(Box.createRigidArea(new Dimension(5,10)));
        second_line_add.add(alege_pret);
        add_produs.setMaximumSize(new Dimension(200, 180));
        add_produs.add(first_line_add);
        add_produs.add(Box.createRigidArea(new Dimension(2,5)));
        add_produs.add(alege_categoria);
        add_produs.add(Box.createRigidArea(new Dimension(2,5)));
        add_produs.add(second_line_add);
        add_produs.add(Box.createRigidArea(new Dimension(2,5)));
        add_produs.add(alege_tara);
        add_produs.add(Box.createRigidArea(new Dimension(2,5)));
        add_produs.setAlignmentX(JPanel.LEFT_ALIGNMENT);

        adauga.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                if (button.getText().equals(adauga.getText())) {
                    String denumire_produs = alege_produs.getText();
                    String categorie_produs = (String) alege_categoria.getSelectedItem();
                    String tara_produs = (String) alege_tara.getSelectedItem();
                    String pret_produs_text = alege_pret.getText();
                    if (denumire_produs.length() == 0 || pret_produs_text.length() == 0) {
                        check_if_prod_adaugat.setText("Completați toate câmpurile!");
                        check_if_prod_adaugat.setForeground(new Color(186, 26, 63));
                    } else {
                        double pret_produs = Double.parseDouble(pret_produs_text);
                        if (! checkProdus(list_produse, denumire_produs, categorie_produs, tara_produs)) {
                            boolean ok = false;
                            for (int i = 0; i < list_produse.size(); ++i) {
                                if (denumire_produs.equals(list_produse.get(i).getDenumire())
                                        && categorie_produs.equals(list_produse.get(i).getCategorie())
                                        && tara_produs.equals(list_produse.get(i).getTaraOrigine())) {
                                    ok = true;
                                    list_produse.get(i).setPret(pret_produs);
                                    check_if_prod_adaugat.setText("Produs adăugat!");
                                    check_if_prod_adaugat.setForeground(Color.BLACK);
                                }
                            }
                            // e buna!
                            if (!ok) {
                                list_produse.add(new Produs(denumire_produs, categorie_produs, tara_produs, pret_produs));
                                check_if_prod_adaugat.setText("Produs adăugat!");
                                check_if_prod_adaugat.setForeground(Color.BLACK);
                                Iterator iterator_tari = parsing.tariOrigine.iterator();
                                while (iterator_tari.hasNext()) {
                                    String add_country = iterator_tari.next().toString();
                                    if (!add_country.equals(tara_produs))
                                        list_produse.add(new Produs(denumire_produs, categorie_produs, add_country, 0));
                                }
                            }
                            model_produse.removeAllElements();
                            for (int i = 0; i < list_produse.size(); ++i) {
                                if (! new Double (list_produse.get(i).getPret()).equals(new Double(0))) {
                                    model_produse.addElement("Nume: " + list_produse.get(i).getDenumire());
                                    model_produse.addElement("Categorie: " + list_produse.get(i).getCategorie());
                                    model_produse.addElement("Țară origine: " + list_produse.get(i).getTaraOrigine());
                                    model_produse.addElement("Preț: " + list_produse.get(i).getPret());
                                    model_produse.addElement("\n");
                                }
                            }
                            ArrayList <Magazin> list_magazine = parsing.getMagazine("facturi.txt", list_produse, map);

                            Magazin maximum_maga = getMagazinMaximum(list_magazine);
                            nume_magazin1.setText("Nume: " + maximum_maga.nume);
                            total_fara_taxe1.setText("Totalul fără taxe: "
                                    + df.format(maximum_maga.getTotalFaraTaxe()).replaceAll(",", "."));
                            total_cu_taxe1.setText("Totalul cu taxe: "
                                    + df.format(maximum_maga.getTotalCuTaxe()).replaceAll(",", "."));
                            total_cu_taxe_scutite1.setText("Totalul cu taxe scutite: "
                                    + df.format(maximum_maga.getTotalCuTaxeScutite()).replaceAll(",", "."));

                            Factura max_factura = getFacturaMaxim(list_magazine);
                            nume_factura.setText("Nume factură: " + max_factura.denumire);
                            prov_factura.setText("Magazin de proveniență: "
                                    + searchFactura(list_magazine, max_factura).nume);
                            total_fara_taxe_factura.setText("Totalul fără taxe: "
                                    + df.format(max_factura.getTotalFaraTaxe()).replaceAll(",", "."));
                            total_cu_taxe_factura.setText("Totalul cu taxe: "
                                    + df.format(max_factura.getTotalCuTaxe()).replaceAll(",", "."));

                            Iterator iterator3 = countries.iterator();
                            model1.removeAllElements();
                            while (iterator3.hasNext()) {
                                String country = iterator3.next().toString();
                                Magazin magazin_country = getMagazinMaxTara(list_magazine, country);
                                model1.addElement("Magazinul cu cele mai mari vânzari în " + country);
                                model1.addElement("Nume: " + maximum_maga.nume);
                                model1.addElement("Totalul fără taxe: "
                                        + df.format(magazin_country.getTotalFaraTaxe()).replaceAll(",", "."));
                                model1.addElement("Totalul cu taxe: "
                                        + df.format(magazin_country.getTotalCuTaxe()).replaceAll(",", "."));
                                model1.addElement("Totalul cu taxe scutite: "
                                        + df.format(magazin_country.getTotalCuTaxeScutite()).replaceAll(",", "."));
                                model1.addElement("\n\n");
                            }

                            Iterator iterator4 = categorii_produse.iterator();
                            model2.removeAllElements();
                            while (iterator4.hasNext()) {
                                String categorie = iterator4.next().toString();
                                Magazin magazin_categorie = getMagazinMaxCategorie(list_magazine, categorie);
                                model2.addElement("Magazinul cu cele mai mari vânzari în " + categorie);
                                model2.addElement("Nume: " + magazin_categorie.nume);
                                model2.addElement("Totalul fără taxe: "
                                        + df.format(magazin_categorie.getTotalFaraTaxe()).replaceAll(",", "."));
                                model2.addElement("Totalul cu taxe: "
                                        + df.format(magazin_categorie.getTotalCuTaxe()).replaceAll(",", "."));
                                model2.addElement("Totalul cu taxe scutite: "
                                        + df.format(magazin_categorie.getTotalCuTaxeScutite()).replaceAll(",", "."));
                                model2.addElement("\n\n");
                            }
                            updateProduseFile(list_produse, parsing.tariOrigine);
                        } else {
                            check_if_prod_adaugat.setText("Produsul deja există!");
                            check_if_prod_adaugat.setForeground(new Color(186, 26, 63));
                        }
                    }
                }
            }
        });

        sterge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                if (button.getText().equals(sterge.getText())) {
                    String denumire_produs = alege_produs.getText();
                    String categorie_produs = (String) alege_categoria.getSelectedItem();
                    String tara_produs = (String) alege_tara.getSelectedItem();
                    if (denumire_produs.length() == 0 || categorie_produs.length() == 0
                            || tara_produs.length() == 0) {
                        check_if_prod_sters.setText("Completați toate câmpurile!");
                        check_if_prod_sters.setForeground(new Color(186, 26, 63));
                    } else {
                        Vector<Produs> to_be_deleted = new Vector<>();
                        if (checkProdus(list_produse, denumire_produs, categorie_produs, tara_produs)) {
                            for (int i = 0; i < list_produse.size(); ++i) {
                                if (denumire_produs.equals(list_produse.get(i).getDenumire())
                                        && categorie_produs.equals(list_produse.get(i).getCategorie())) {
                                    if (tara_produs.equals(list_produse.get(i).getTaraOrigine())) {
                                        if (new Double(list_produse.get(i).getPret()).equals(new Double(0))) {
                                            check_if_prod_sters.setText("Produsul a fost deja șters!");
                                            check_if_prod_sters.setForeground(new Color(186, 26, 63));
                                        }
                                        else {
                                            list_produse.get(i).setPret(0);
                                            to_be_deleted.add(list_produse.get(i));
                                            check_if_prod_sters.setText("Produsul a fost șters!");
                                            check_if_prod_sters.setForeground(Color.BLACK);
                                        }
                                    } else {
                                        if (new Double (list_produse.get(i).getPret()).equals(new Double(0)))
                                            to_be_deleted.add(list_produse.get(i));
                                    }
                                }
                            }
                            if (to_be_deleted.size() == parsing.tariOrigine.size())
                                list_produse.removeAll(to_be_deleted);
                            updateProduseFile(list_produse, parsing.tariOrigine);
                            model_produse.removeAllElements();
                            for (int i = 0; i < list_produse.size(); ++i) {
                                if (! new Double (list_produse.get(i).getPret()).equals(new Double(0))) {
                                    model_produse.addElement("Nume: " + list_produse.get(i).getDenumire());
                                    model_produse.addElement("Categorie: " + list_produse.get(i).getCategorie());
                                    model_produse.addElement("Țară origine: " + list_produse.get(i).getTaraOrigine());
                                    model_produse.addElement("Preț: " + list_produse.get(i).getPret());
                                    model_produse.addElement("\n");
                                }
                            }
                            ArrayList <Magazin> list_magazine = parsing.getMagazine("facturi.txt", list_produse, map);

                            Magazin maximum_maga = getMagazinMaximum(list_magazine);
                            nume_magazin1.setText("Nume: " + maximum_maga.nume);
                            total_fara_taxe1.setText("Totalul fără taxe: "
                                    + df.format(maximum_maga.getTotalFaraTaxe()).replaceAll(",", "."));
                            total_cu_taxe1.setText("Totalul cu taxe: "
                                    + df.format(maximum_maga.getTotalCuTaxe()).replaceAll(",", "."));
                            total_cu_taxe_scutite1.setText("Totalul cu taxe scutite: "
                                    + df.format(maximum_maga.getTotalCuTaxeScutite()).replaceAll(",", "."));

                            Factura max_factura = getFacturaMaxim(list_magazine);
                            nume_factura.setText("Nume factura: " + max_factura.denumire);
                            prov_factura.setText("Magazin de proveniență: "
                                    + searchFactura(list_magazine, max_factura).nume);
                            total_fara_taxe_factura.setText("Totalul fără taxe: "
                                    + df.format(max_factura.getTotalFaraTaxe()).replaceAll(",", "."));
                            total_cu_taxe_factura.setText("Totalul cu taxe: "
                                    + df.format(max_factura.getTotalCuTaxe()).replaceAll(",", "."));

                            Iterator iterator3 = countries.iterator();
                            model1.removeAllElements();
                            while (iterator3.hasNext()) {
                                String country = iterator3.next().toString();
                                Magazin magazin_country = getMagazinMaxTara(list_magazine, country);
                                model1.addElement("Magazinul cu cele mai mari vânzări în " + country);
                                model1.addElement("Nume: " + maximum_maga.nume);
                                model1.addElement("Totalul fără taxe: "
                                        + df.format(magazin_country.getTotalFaraTaxe()).replaceAll(",", "."));
                                model1.addElement("Totalul cu taxe: "
                                        + df.format(magazin_country.getTotalCuTaxe()).replaceAll(",", "."));
                                model1.addElement("Totalul cu taxe scutite: "
                                        + df.format(magazin_country.getTotalCuTaxeScutite()).replaceAll(",", "."));
                                model1.addElement("\n\n");
                            }

                            Iterator iterator4 = categorii_produse.iterator();
                            model2.removeAllElements();
                            while (iterator4.hasNext()) {
                                String categorie = iterator4.next().toString();
                                Magazin magazin_categorie = getMagazinMaxCategorie(list_magazine, categorie);
                                model2.addElement("Magazinul cu cele mai mari vanzari în " + categorie);
                                model2.addElement("Nume: " + magazin_categorie.nume);
                                model2.addElement("Totalul fără taxe: "
                                        + df.format(magazin_categorie.getTotalFaraTaxe()).replaceAll(",", "."));
                                model2.addElement("Totalul cu taxe: "
                                        + df.format(magazin_categorie.getTotalCuTaxe()).replaceAll(",", "."));
                                model2.addElement("Totalul cu taxe scutite: "
                                        + df.format(magazin_categorie.getTotalCuTaxeScutite()).replaceAll(",", "."));
                                model2.addElement("\n\n");
                            }
                        } else {
                            check_if_prod_sters.setText("Produsul nu există!");
                            check_if_prod_sters.setForeground(new Color(186, 26, 63));
                        }
                    }
                }
            }
        });
        JLabel check_if_produs_exista = new JLabel();
        check_if_produs_exista.setFont(new Font("Cambria Math", Font.PLAIN, 15));
        cauta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                if (button.getText().equals(cauta.getText())) {
                    String denumire_produs = alege_produs.getText();
                    String categorie_produs = (String) alege_categoria.getSelectedItem();
                    String tara_produs = (String) alege_tara.getSelectedItem();
                    if (denumire_produs.length() == 0) {
                        check_if_produs_exista.setText("Completați câmpul de denumire!");
                        check_if_produs_exista.setForeground(new Color(186, 26, 63));
                    } else {
                        if (checkProdus(list_produse, denumire_produs, categorie_produs, tara_produs)) {
                            check_if_produs_exista.setText("Produsul e în baza de date");
                            check_if_produs_exista.setForeground(Color.BLACK);
                            new DateProdus(denumire_produs, categorie_produs, list_magazine);
                        } else {
                            check_if_produs_exista.setText("Produsul nu există!");
                            check_if_produs_exista.setForeground(new Color(186, 26, 63));
                        }
                    }
                }
            }
        });
        JPanel editare_produs = new JPanel();
        editare_produs.setMaximumSize(new Dimension(300, 100));
        editare_produs.setBackground(new Color(22, 122, 72));
        JLabel text3 = new JLabel ("Adaugă numele nou al produsului:");
        text3.setForeground(Color.WHITE);
        text3.setFont(new Font("Cambria Math", Font.PLAIN, 15));
        JLabel text4 = new JLabel ("Adaugă prețul nou al produsului:");
        text4.setForeground(Color.WHITE);
        text4.setFont(new Font("Cambria Math", Font.PLAIN, 15));
        JTextField editare_nume = new JTextField(20);
        editare_nume.setFont(new Font("Cambria Math", Font.PLAIN, 15));
        JTextField editare_pret = new JTextField(15);
        editare_pret.setFont(new Font("Cambria Math", Font.PLAIN, 15));
        editare_produs.setLayout(new BoxLayout(editare_produs, BoxLayout.PAGE_AXIS));
        JPanel first_line_edit = new JPanel();
        JPanel second_line_edit = new JPanel();
        first_line_edit.setLayout(new BoxLayout(first_line_edit, BoxLayout.PAGE_AXIS));
        first_line_edit.setBackground(new Color(22, 122, 72));
        second_line_edit.setLayout(new BoxLayout(second_line_edit, BoxLayout.PAGE_AXIS));
        second_line_edit.setBackground(new Color(22, 122, 72));
        first_line_edit.add(text3);
        first_line_edit.add(Box.createRigidArea(new Dimension(5,2)));
        first_line_edit.add(editare_nume);
        second_line_edit.add(text4);
        second_line_edit.add(Box.createRigidArea(new Dimension(5,2)));
        second_line_edit.add(editare_pret);
        editare_produs.add(first_line_edit);
        editare_produs.add(second_line_edit);

        JLabel edit_result = new JLabel();
        edit_result.setFont(new Font("Cambria Math", Font.PLAIN, 15));
        editeaza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                if (button.getText().equals(editeaza.getText())) {
                    String original_denumire = alege_produs.getText();
                    String final_denumire = editare_nume.getText();
                    String final_pret = editare_pret.getText();
                    String tara = (String) alege_tara.getSelectedItem();
                    String categorie = (String) alege_categoria.getSelectedItem();
                    double first_price = findPret(list_produse, original_denumire, tara, categorie);
                    if (original_denumire.length() == 0 || final_denumire.length() == 0 || final_pret.length() == 0) {
                        edit_result.setText("Completați toate câmpurile");
                        edit_result.setForeground(new Color(186, 26, 63));
                    } else {
                        if (checkProdus(list_produse, original_denumire, categorie, tara)) {
                            double final_price = Double.parseDouble(final_pret);
                            boolean yes = false;
                            for (int i = 0; i < list_produse.size(); ++i) {
                                if (original_denumire.equals(list_produse.get(i).getDenumire())
                                        && categorie.equals(list_produse.get(i).getCategorie())) {
                                    list_produse.get(i).setDenumire(final_denumire);
                                    if (tara.equals(list_produse.get(i).getTaraOrigine()))
                                        list_produse.get(i).setPret(final_price);
                                    edit_result.setText("Produsul a fost editat!");
                                    edit_result.setForeground(Color.BLACK);
                                    updateProduseFile(list_produse, parsing.tariOrigine);
                                }
                            }
                            model_produse.removeAllElements();
                            for (int i = 0; i < list_produse.size(); ++i) {
                                if (! new Double (list_produse.get(i).getPret()).equals(new Double(0))) {
                                    model_produse.addElement("Nume: " + list_produse.get(i).getDenumire());
                                    model_produse.addElement("Categorie: " + list_produse.get(i).getCategorie());
                                    model_produse.addElement("Țară origine: " + list_produse.get(i).getTaraOrigine());
                                    model_produse.addElement("Preț: " + list_produse.get(i).getPret());
                                    model_produse.addElement("\n");
                                }
                            }
                            ArrayList <Magazin> list_magazine = parsing.getMagazine("facturi.txt", list_produse, map);

                            Magazin maximum_maga = getMagazinMaximum(list_magazine);
                            nume_magazin1.setText("Nume: " + maximum_maga.nume);
                            total_fara_taxe1.setText("Totalul fără taxe: "
                                    + df.format(maximum_maga.getTotalFaraTaxe()).replaceAll(",", "."));
                            total_cu_taxe1.setText("Totalul cu taxe: "
                                    + df.format(maximum_maga.getTotalCuTaxe()).replaceAll(",", "."));
                            total_cu_taxe_scutite1.setText("Totalul cu taxe scutite: "
                                    + df.format(maximum_maga.getTotalCuTaxeScutite()).replaceAll(",", "."));

                            Factura max_factura = getFacturaMaxim(list_magazine);
                            nume_factura.setText("Nume factură: " + max_factura.denumire);
                            prov_factura.setText("Magazin de proveniență: "
                                    + searchFactura(list_magazine, max_factura).nume);
                            total_fara_taxe_factura.setText("Totalul fără taxe: "
                                    + df.format(max_factura.getTotalFaraTaxe()).replaceAll(",", "."));
                            total_cu_taxe_factura.setText("Totalul cu taxe: "
                                    + df.format(max_factura.getTotalCuTaxe()).replaceAll(",", "."));

                            Iterator iterator3 = countries.iterator();
                            model1.removeAllElements();
                            while (iterator3.hasNext()) {
                                String country = iterator3.next().toString();
                                Magazin magazin_country = getMagazinMaxTara(list_magazine, country);
                                model1.addElement("Magazinul cu cele mai mari vânzari în " + country);
                                model1.addElement("Nume: " + maximum_maga.nume);
                                model1.addElement("Totalul fără taxe: "
                                        + df.format(magazin_country.getTotalFaraTaxe()).replaceAll(",", "."));
                                model1.addElement("Totalul cu taxe: "
                                        + df.format(magazin_country.getTotalCuTaxe()).replaceAll(",", "."));
                                model1.addElement("Totalul cu taxe scutite: "
                                        + df.format(magazin_country.getTotalCuTaxeScutite()).replaceAll(",", "."));
                                model1.addElement("\n\n");
                            }

                            Iterator iterator4 = categorii_produse.iterator();
                            model2.removeAllElements();
                            while (iterator4.hasNext()) {
                                String category = iterator4.next().toString();
                                Magazin magazin_categorie = getMagazinMaxCategorie(list_magazine, category);
                                model2.addElement("Magazinul cu cele mai mari vânzări în " + category);
                                model2.addElement("Nume: " + magazin_categorie.nume);
                                model2.addElement("Totalul fără taxe: "
                                        + df.format(magazin_categorie.getTotalFaraTaxe()).replaceAll(",", "."));
                                model2.addElement("Totalul cu taxe: "
                                        + df.format(magazin_categorie.getTotalCuTaxe()).replaceAll(",", "."));
                                model2.addElement("Totalul cu taxe scutite: "
                                        + df.format(magazin_categorie.getTotalCuTaxeScutite()).replaceAll(",", "."));
                                model2.addElement("\n\n");
                            }
                        } else {
                            edit_result.setText("Produsul nu există!");
                            edit_result.setForeground(new Color(186, 26, 63));
                        }
                    }
                }
            }
        });

        minipanel1.add(criteriu);
        minipanel1.add(Box.createRigidArea(new Dimension(5,10)));
        minipanel1.add(list_options);
        minipanel1.add(Box.createRigidArea(new Dimension(5,10)));
        minipanel1.add(sort);
        minipanel1.add(Box.createRigidArea(new Dimension(5,10)));
        minipanel1.add(add_produs);
        minipanel1.add(Box.createRigidArea(new Dimension(5,10)));
        minipanel1.add(adauga);
        minipanel1.add(Box.createRigidArea(new Dimension(5,10)));
        minipanel1.add(check_if_prod_adaugat);
        minipanel1.add(Box.createRigidArea(new Dimension(5,10)));
        minipanel1.add(sterge);
        minipanel1.add(Box.createRigidArea(new Dimension(5,10)));
        minipanel1.add(check_if_prod_sters);
        minipanel1.add(Box.createRigidArea(new Dimension(5,10)));
        minipanel1.add(cauta);
        minipanel1.add(Box.createRigidArea(new Dimension(5,10)));
        minipanel1.add(check_if_produs_exista);
        minipanel1.add(Box.createRigidArea(new Dimension(5,10)));
        minipanel1.add(editare_produs);
        minipanel1.add(Box.createRigidArea(new Dimension(5,10)));
        minipanel1.add(editeaza);
        minipanel1.add(Box.createRigidArea(new Dimension(5,10)));
        minipanel1.add(edit_result);
        panel3.add(Box.createRigidArea(new Dimension(30,10)));
        panel3.add(scrolling);
        panel3.add(Box.createRigidArea(new Dimension(50,10)));
        panel3.add(minipanel1);
        panel3.add(new JLabel(new ImageIcon("icons\\product_icon.png")));


        this.tabs.setBorder(BorderFactory.createCompoundBorder(null, paddingBorder));
        this.tabs.setFont(new Font( "Georgia", Font.PLAIN, 14 ));
        this.tabs.add("  Acasă  ", info1);
        this.tabs.add("  Încărcare fișiere  ", panel2);
        this.tabs.add("  Produse  ", panel3);
        this.tabs.add("  Statistică  ", panel4);

        if (!checkFiles())
            this.tabs.setEnabledAt(3, false);
        File fisier_prod = new File ("produse.txt");
        if (!fisier_prod.exists())
            this.tabs.setEnabledAt(2, false);

        this.info2 = new JPanel();
        info2.setLayout(new BoxLayout(this.info2, BoxLayout.X_AXIS));
        this.info2.add(tabs); // adaugam in info2 JTabbedPane
        this.add(info2);
        this.setResizable(false);
        this.setVisible(true);
        this.pack();
    }
}