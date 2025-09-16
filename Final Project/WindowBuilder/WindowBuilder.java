import javax.swing.*;
import java.awt.Color;
import helper_classes.*;

public class WindowBuilder {
  public static void main(String[] args) {

     JFrame frame = new JFrame("My Awesome Window");
     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     frame.setSize(615, 333);
     JPanel panel = new JPanel();
     panel.setLayout(null);
     panel.setBackground(Color.decode("#1e1e1e"));

     JLabel element1 = new JLabel("Welcome");
     element1.setBounds(205, 95, 212, 66);
     element1.setFont(CustomFontLoader.loadFont("./resources/fonts/Lato.ttf", 50));
     element1.setForeground(Color.decode("#D9D9D9"));
     panel.add(element1);

     JButton element2 = new JButton("Enter");
     element2.setBounds(254, 201, 106, 29);
     element2.setBackground(Color.decode("#2e2e2e"));
     element2.setForeground(Color.decode("#D9D9D9"));
     element2.setFont(CustomFontLoader.loadFont("./resources/fonts/Lato.ttf", 14));
     element2.setBorder(new RoundedBorder(4, Color.decode("#979797"), 1));
     element2.setFocusPainted(false);
     OnClickEventHelper.setOnClickColor(element2, Color.decode("#232323"), Color.decode("#2e2e2e"));
     panel.add(element2);

     frame.add(panel);
     frame.setVisible(true);

  }
}