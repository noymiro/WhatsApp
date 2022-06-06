import javax.swing.*;
import java.awt.*;


public class MassageWindow extends JPanel {
    private JButton button;


    public MassageWindow(int x, int y, int width, int height) {
        System.out.println("noy");
        this.setBounds(x, y, width, height);
        this.setOpaque(true);
        this.setFocusable(true);
        this.setLayout(null);
        //   this.setDoubleBuffered(true);
        this.setVisible(true);
        this.setSize(600, 400);
        button = new JButton("מספר טלפון אינו תקין");
        button.setBounds(300, 20, 300, 50);

        this.add(button);
        button.setBackground(Color.gray);



    }
}