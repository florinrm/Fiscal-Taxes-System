package tax_system;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//clasa de afisare a datelor unui produs dat
public class DateProdus extends JFrame {
    public DateProdus (String denumire, String categorie, ArrayList<Magazin> list_magazine) {
        super ("Date despre produsul " + denumire);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setMinimumSize(new Dimension(600, 400));
        this.setLayout(new FlowLayout());
        this.setIconImage(new ImageIcon("icons\\notepad-edit.png").getImage());

        JPanel panel = new JPanel();
        panel.setBackground(new Color(22, 122, 72));
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        DefaultListModel<String> model1 = new DefaultListModel<>();
        JList<String> date = new JList<>(model1);
        JScrollPane scroll = new JScrollPane(date);
        scroll.setMaximumSize(new Dimension(400, 300));
        scroll.setVerticalScrollBar(new JScrollBar(JScrollBar.VERTICAL));
        date.setFont(new Font("Cambria Math", Font.PLAIN, 20));
        JLabel info = new JLabel("Lista magazinelor unde se găsește produsul");
        info.setFont(new Font("Cambria Math", Font.PLAIN, 20));
        info.setForeground(Color.white);
        info.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        scroll.setAlignmentX(JScrollPane.CENTER_ALIGNMENT);

        // vedem in ce magazine e produsul cautat si le listam cu datele despre produsul cerut
        for (int i = 0; i < list_magazine.size(); ++i) {
            model1.addElement(list_magazine.get(i).nume + ": ");
            boolean found = false;
            for (int j = 0; j < list_magazine.get(i).facturi.size(); ++j) {
                for (int k = 0; k < list_magazine.get(i).facturi.get(j).lista_produse.size(); ++k) {
                    ProdusComandat prod = list_magazine.get(i).facturi.get(j).lista_produse.get(k);
                    if (denumire.equals(prod.getProdus().getDenumire())
                            && categorie.equals(prod.getProdus().getCategorie())) {
                        found = true;
                        model1.addElement("Preț: " + prod.getProdus().getPret());
                        model1.addElement("Țară origine: " + prod.getProdus().getTaraOrigine());
                        model1.addElement("\n\n");
                    }
                }
            }
            if (!found) {
                model1.addElement("-");
                model1.addElement("\n\n");
            }
        }
        panel.add(info);
        panel.add(Box.createRigidArea(new Dimension(10,10)));
        panel.add(scroll);

        this.setContentPane(panel);
        this.setVisible(true);
        this.pack();
    }
}
