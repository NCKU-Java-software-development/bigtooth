import javax.swing.*;
import java.awt.*;

public class EndPanel extends JPanel {
    Image menu=new ImageIcon("img/menu.jpg").getImage();
    JLabel label=new JLabel("你打敗了DDC 你真牛");
    JButton nextbutton = new JButton("下一關");
    JButton exitbutton = new JButton("結束");
    JButton againbutton = new JButton("再來一次");
    GameFrame gameFrame;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    EndPanel(GameFrame gameFrame){
        this.gameFrame=gameFrame;
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3,1,0,50));

        label.setHorizontalAlignment(JLabel.CENTER);
        label.setPreferredSize(new Dimension(screenSize.width, 350));
        label.setFont(new Font("Comic Sans", Font.BOLD, 60));
        label.setOpaque(false);
        label.setForeground(Color.white);
        label.setVerticalAlignment(JLabel.CENTER);

        nextbutton.setFocusable(false);
        exitbutton.setFocusable(false);
        againbutton.setFocusable(false);

        nextbutton.setPreferredSize(new Dimension(300, 100));
        exitbutton.setPreferredSize(new Dimension(300, 100));
        againbutton.setPreferredSize(new Dimension(300, 100));

        nextbutton.setHorizontalTextPosition(JButton.CENTER);
        nextbutton.setVerticalTextPosition(JButton.TOP);
        exitbutton.setHorizontalTextPosition(JButton.CENTER);
        exitbutton.setVerticalTextPosition(JButton.TOP);
        againbutton.setHorizontalTextPosition(JButton.CENTER);
        againbutton.setVerticalTextPosition(JButton.TOP);

        nextbutton.setFont(new Font("Comic Sans", Font.BOLD, 30));
        exitbutton.setFont(new Font("Comic Sans", Font.BOLD, 30));
        againbutton.setFont(new Font("Comic Sans", Font.BOLD, 30));

        nextbutton.setBackground(Color.ORANGE);
        againbutton.setBackground(Color.ORANGE);
        exitbutton.setBackground(Color.ORANGE);

        nextbutton.setBorder(BorderFactory.createEtchedBorder());
        againbutton.setBorder(BorderFactory.createEtchedBorder());
        exitbutton.setBorder(BorderFactory.createEtchedBorder());

        nextbutton.addActionListener(e -> {
            if(gameFrame.Level == 1){
                gameFrame.Level = 2;
                gameFrame.ShowLevel2();
            }else if(gameFrame.Level == 2){
                gameFrame.Level = 3;
                
                gameFrame.ShowWriteItByYourSelf();
            }else{
                gameFrame.ShowEndFrame();
            }
            gameFrame.cantclose();
        });

        exitbutton.addActionListener(e -> {
            gameFrame.closewindow();
            System.exit(0);
        });

        againbutton.addActionListener(e -> {
            gameFrame.closewindow();
            gameFrame.newgame();
        });

        this.add(label, BorderLayout.NORTH);
        buttonPanel.add(exitbutton);
        buttonPanel.add(nextbutton);
        buttonPanel.add(againbutton);
        buttonPanel.setOpaque(false);
        this.add(buttonPanel, BorderLayout.CENTER);

        this.setSize(600, 600);
        this.setOpaque(true);
        this.setVisible(false);
    }
    @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(menu, 0, 0, getWidth(), getHeight(), this);
        }
    
}
