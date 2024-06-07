import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartFrame extends JPanel implements ActionListener {
    JFrame frame = new JFrame();
    JLabel label = new JLabel("Bigtooth內卷被他的室友發現 請幫助Bigtooth逃離他室友的追殺");
    JButton button = new JButton("Start Game");
    Image image = new ImageIcon("img/background.jpg").getImage();

    StartFrame() {
        // 設置JPanel的大小和佈局
        this.setLayout(null);
        this.setSize(800, 800);
        // 設置標籤和按鈕
        label.setBounds(0, 280, 800, 100);
        label.setFont(new Font("Comic Sans", Font.BOLD, 25));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setForeground(Color.white);

        button.setBounds(230, 400, 300, 40);
        button.setFocusable(false);
        button.addActionListener(this);
        button.setBackground(Color.cyan);
        button.setBorder(BorderFactory.createEtchedBorder());

        // 添加組件到JPanel
        this.add(button);
        this.add(label);

        // 設置JFrame的屬性
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLayout(null);
        frame.add(this);
        frame.setVisible(true);
        frame.setTitle("再繼續內卷阿");
        frame.setResizable(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            frame.dispose();
            new GameFrame();
        }
    }
}