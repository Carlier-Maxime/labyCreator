import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class SizeView extends JDialog {
    private View view;
    private Laby laby;
    private int sizeX = 25;
    private int sizeY = 25;

    public SizeView(View view, Laby laby) {
        super(view, "Choisie la taille");
        setSize(250,100);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);

        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        getContentPane().setBackground(Color.GRAY);
        JTextArea zoneX = new JTextArea();
        zoneX.setBorder(new EmptyBorder(25,25,25,25));
        zoneX.setText(Integer.toString(sizeX));
        getContentPane().add(zoneX,c);
        c.gridx = 1;
        JTextArea zoneY = new JTextArea();
        zoneY.setBorder(new EmptyBorder(25,25,25,25));
        zoneY.setText(Integer.toString(sizeY));
        getContentPane().add(zoneY,c);
        c.gridy = 1;
        JButton Done =  new JButton("Done");
        Done.addActionListener(e -> {
            dispose();
            try {
                sizeX = Integer.parseInt(zoneX.getText());
                sizeY = Integer.parseInt(zoneY.getText());
            } catch (Exception ignored){}
            new Thread(() -> laby.createLaby(sizeX, sizeY)).start();
        });
        getContentPane().add(Done,c);
        setVisible(true);
    }
}
