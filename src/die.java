import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

public class die extends JPanel implements ActionListener {
    Image DieBigtooth = new ImageIcon("img/DieBigTooth.jpg").getImage();
    JButton button = new JButton("離開");
    GameFrame gameFrame;
    JLabel label = new JLabel("卒");
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    die(GameFrame gameFrame) {
        this.setLayout(null);
        this.gameFrame = gameFrame;
        gameFrame.canclose();
        button.setBounds(screenSize.width / 2 - 150, screenSize.height * 5 / 6, 300, 35);
        button.setFocusable(false);
        button.addActionListener(this);
        button.setBackground(Color.cyan);
        button.setBorder(BorderFactory.createEtchedBorder());
        button.setFont(new Font("Comic Sans", Font.BOLD, 20));
        this.add(button);

        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBounds(screenSize.width * 8 / 10, screenSize.height * 1 / 10, 200, 200);
        label.setFont(new Font("Comic Sans", Font.BOLD, 200));
        label.setOpaque(false);
        label.setForeground(Color.white);
        label.setVerticalAlignment(JLabel.CENTER);
        this.add(label);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(DieBigtooth, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            gameFrame.closewindow();
            System.exit(0);
        }
    }

}