package tax_system;

import javax.swing.*;
import java.awt.*;

public class Instructions extends JFrame {
    public Instructions () {
        super ("Instrucțiuni");
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setMinimumSize(new Dimension(1000, 400));
        this.setLayout(new FlowLayout());
        this.setResizable(false);
        this.setIconImage(new ImageIcon("icons\\notepad-edit.png").getImage());
        this.getContentPane().setBackground(new Color(22, 122, 72));
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBackground(new Color(22, 122, 72));
        JLabel instr = new JLabel("                                                       Instrucțiuni");
        instr.setFont(new Font("Calibri Light", Font.PLAIN, 30));
        instr.setForeground(Color.WHITE);

        JLabel adaugare = new JLabel("Adăugare:");
        adaugare.setFont(new Font("Calibri Light", Font.PLAIN, 20));
        adaugare.setForeground(Color.WHITE);
        JLabel adaugare_info = new JLabel("Se completează câmpurile adaugă nume și adaugă preț și se selectează" +
                " țara de origine și categoria produsului");
        adaugare_info.setFont(new Font("Calibri Light", Font.PLAIN, 20));
        adaugare_info.setForeground(Color.WHITE);

        JLabel stergere = new JLabel("Ștergere:");
        stergere.setFont(new Font("Calibri Light", Font.PLAIN, 20));
        stergere.setForeground(Color.WHITE);
        JLabel stergere_info = new JLabel("Se completează câmpul adaugă nume și se selectează" +
                " țara de origine și categoria produsului");
        stergere_info.setFont(new Font("Calibri Light", Font.PLAIN, 20));
        stergere_info.setForeground(Color.WHITE);

        JLabel editare = new JLabel("Editare:");
        editare.setFont(new Font("Calibri Light", Font.PLAIN, 20));
        editare.setForeground(Color.WHITE);
        JLabel editare_info1 = new JLabel("Se completează câmpurile adaugă nume, adaugă preț, editează nume " +
                "și editează preț");
        editare_info1.setFont(new Font("Calibri Light", Font.PLAIN, 20));
        editare_info1.setForeground(Color.WHITE);
        JLabel editare_info2 = new JLabel("(dacă nu se schimbă prețul vechi, băgați-l pe acesta) și se selectează" +
                " țara de origine și categoria produsului");
        editare_info2.setFont(new Font("Calibri Light", Font.PLAIN, 20));
        editare_info2.setForeground(Color.WHITE);

        JLabel cautare = new JLabel("Căutare:");
        cautare.setFont(new Font("Calibri Light", Font.PLAIN, 20));
        cautare.setForeground(Color.WHITE);
        JLabel cautare_info = new JLabel("Se completează câmpurile adaugă nume și se selectează țara și categoria");
        cautare_info.setFont(new Font("Calibri Light", Font.PLAIN, 20));
        cautare_info.setForeground(Color.WHITE);

        panel.add(Box.createRigidArea(new Dimension(0,10)));
        panel.add(instr);
        panel.add(Box.createRigidArea(new Dimension(0,10)));
        panel.add(adaugare);
        panel.add(adaugare_info);
        panel.add(Box.createRigidArea(new Dimension(0,10)));
        panel.add(stergere);
        panel.add(stergere_info);
        panel.add(Box.createRigidArea(new Dimension(0,10)));
        panel.add(editare);
        panel.add(editare_info1);
        panel.add(editare_info2);
        panel.add(Box.createRigidArea(new Dimension(0,10)));
        panel.add(cautare);
        panel.add(cautare_info);
        panel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        this.add(panel);
        this.setVisible(true);
        pack();
    }
}
