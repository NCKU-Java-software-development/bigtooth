import javax.swing.*;
import java.awt.*;

public class AwardPanel extends JPanel {
    JLabel title = new JLabel("你存活了10秒請選擇一個效果來幫助Bigtooth這個憨包");
    ImageIcon healicon = new ImageIcon("img/heal.png");
    ImageIcon attackicon = new ImageIcon("img/attack.png");
    ImageIcon speedicon = new ImageIcon("img/speed.png");
    Level1 gameFrame;
    ButtonPressedListener listener;

    public void healbigtooth(Level1 panel) {
        panel.heal();
    }

    public void speed(Level1 panel) {
        panel.IncreaseSpeed();
    }

    public void attack(Level1 panel) {
        panel.IncreaseAttack();
    }

    Image upgradebg = new ImageIcon("img/upgradebg.jpg").getImage();
    JButton healthbutton = new JButton("回復bigtooth的生命值");
    JButton attackbutton = new JButton("增加bigtooth牙齒的殺傷力");
    JButton speedbutton = new JButton("增加bigtooth牙齒的速度");

    AwardPanel(Level1 gameFrame, ButtonPressedListener listener) {
        this.gameFrame = gameFrame;
        this.listener = listener;
        this.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 200, 250));

        title.setHorizontalAlignment(JLabel.CENTER);
        title.setPreferredSize(new Dimension(1000, 250));
        title.setFont(new Font("Arial", Font.BOLD, 60));
        title.setOpaque(false);
        title.setForeground(Color.white);
        title.setVerticalAlignment(JLabel.BOTTOM);

        attackbutton.setFocusable(false);
        healthbutton.setFocusable(false);
        speedbutton.setFocusable(false);

        attackbutton.setPreferredSize(new Dimension(200, 400));
        healthbutton.setPreferredSize(new Dimension(200, 400));
        speedbutton.setPreferredSize(new Dimension(200, 400));

        attackbutton.setIcon(attackicon);
        speedbutton.setIcon(speedicon);
        healthbutton.setIcon(healicon);

        attackbutton.setHorizontalTextPosition(JButton.CENTER);
        attackbutton.setVerticalTextPosition(JButton.TOP);
        healthbutton.setHorizontalTextPosition(JButton.CENTER);
        healthbutton.setVerticalTextPosition(JButton.TOP);
        speedbutton.setHorizontalTextPosition(JButton.CENTER);
        speedbutton.setVerticalTextPosition(JButton.TOP);

        attackbutton.setFont(new Font("Comic Sans", Font.BOLD, 15));
        healthbutton.setFont(new Font("Comic Sans", Font.BOLD, 15));
        speedbutton.setFont(new Font("Comic Sans", Font.BOLD, 15));

        attackbutton.setBackground(Color.ORANGE);
        speedbutton.setBackground(Color.ORANGE);
        healthbutton.setBackground(Color.ORANGE);

        attackbutton.setBorder(BorderFactory.createEtchedBorder());
        speedbutton.setBorder(BorderFactory.createEtchedBorder());
        healthbutton.setBorder(BorderFactory.createEtchedBorder());

        attackbutton.addActionListener(e -> {
            this.attack(gameFrame);
            listener.onButtonPressed();
        });

        healthbutton.addActionListener(e -> {
            this.healbigtooth(gameFrame);
            listener.onButtonPressed();
        });

        speedbutton.addActionListener(e -> {
            this.speed(gameFrame);
            listener.onButtonPressed();
        });

        this.add(title, BorderLayout.NORTH);
        buttonPanel.add(healthbutton);
        buttonPanel.add(attackbutton);
        buttonPanel.add(speedbutton);
        buttonPanel.setOpaque(false);
        this.add(buttonPanel, BorderLayout.CENTER);

        this.setSize(600, 600);
        // this.setBackground(Color.white);
        this.setOpaque(true);
        this.setVisible(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(upgradebg, 0, 0, getWidth(), getHeight(), this);
    }
}
