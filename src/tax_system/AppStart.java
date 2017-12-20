package tax_system;

import javax.swing.*;
import java.awt.*;

public class AppStart extends JFrame {
    public JTextField user;
    public JPanel login;

    public AppStart (String text)
    {
        super(text);
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setMinimumSize (new Dimension(800, 450));
        getContentPane ().setBackground(Color.blue);
        setLayout (new SpringLayout ());
        this.user = new JTextField(15);
        this.login = new JPanel(new SpringLayout());
        this.login.setBounds(5, 5, 290, 290);
        this.login.add(user);
        add(this.login);
        setVisible(true);
        pack();
    }
}
