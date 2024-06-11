import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

interface ButtonPressedListener {
    void onButtonPressed();
}

public class GameFrame extends JFrame implements ButtonPressedListener {
    Timer gametimer;
    ImageIcon icon;
    die die=new die(this);
    Level1 level1 = new Level1(this);
    AwardPanel awardFrame = new AwardPanel(level1, this);

    EndPanel endFrame = new EndPanel(this);

    Level2 level2 = new Level2(this);
    AwardPanel2 awardFrame2 = new AwardPanel2(level2, this);

    WriteItByYourSelf WriteItByYourSelf = new WriteItByYourSelf(this);
    CardLayout cardLayout = new CardLayout();
    JPanel mainPanel = new JPanel(cardLayout);
    int Level = 1;

    @Override
    public void onButtonPressed() {
        if(Level == 1){
            cardLayout.show(mainPanel, "level1");
            gametimer.start();
            level1.StartTimers();
        }else{
            cardLayout.show(mainPanel, "level2");
            gametimer.start();
            level2.StartTimers();
        }
    }

    public void ShowEndFrame() {
        cardLayout.show(mainPanel, "endFrame");
    }
    public void ShowDie(){
        cardLayout.show(mainPanel, "die");
    }

    public void ShowLevel2() {
        level2.timer.start();
        level2.movesliper.start();
        level2.IFGameEnd.start();
        level2.ddcTimer.start();
        level2.movetooth.start();
        gametimer.start();
        cardLayout.show(mainPanel, "level2");

    }

    public void ShowWriteItByYourSelf() {
        cantclose();
        cardLayout.show(mainPanel, "WriteItByYourSelf");
    }

    public void stopgametimer() {
        gametimer.stop();
    }

    public void closewindow() {
        this.dispose();
    }

    public void newgame() {
        new GameFrame();
    }

    public void cantclose() {
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
    public void canclose() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void startgametimer() {
        gametimer.start();
    }

    GameFrame() {
        this.setTitle("憨豆");
        this.setResizable(false);
        // gametimer
        gametimer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Level == 1){
                    // System.out.println("Level 1");
                    level1.StopTimers();
                    cardLayout.show(mainPanel, "awardFrame");
                }else{
                    // System.out.println("Level 2");
                    level2.StopTimers();
                    cardLayout.show(mainPanel, "awardFrame2");
                }
                gametimer.stop();
            }
        });
        gametimer.start();
        mainPanel.add(level1, "level1");
        mainPanel.add(awardFrame, "awardFrame");
        mainPanel.add(awardFrame2, "awardFrame2");
        mainPanel.add(endFrame, "endFrame");
        mainPanel.add(level2, "level2");
        mainPanel.add(WriteItByYourSelf, "WriteItByYourSelf");
        mainPanel.add(die, "die");
        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width, screenSize.height);
        this.setVisible(true);
        canclose();
    }
}