import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Level1 extends JPanel implements ActionListener {
    Image gamepanelbg = new ImageIcon("img/gamepanelbg.jpg").getImage();
    GameFrame gameFrame;
    int CounterDdcHP = 500;
    int CounterBigtoothHP = 500;
    JProgressBar HP_ddc = new JProgressBar(0, 500);
    JProgressBar HP_Bigtooth = new JProgressBar(0, 500);
    Image[] tooth = new Image[5];
    Image slip;
    Timer timer;
    Timer movetooth;
    Timer ddcTimer;
    Timer movesliper;
    int slipperxvel;
    int slipperyvel;
    int ToothVelocity = 2; // tooth velocity
    ImageIcon bigtooh = new ImageIcon("img/bigtooth.png");
    ImageIcon ddc = new ImageIcon("img/ddc.jpg");
    ImageIcon boom = new ImageIcon("img/boom.png");
    final int BigtoothWidth = bigtooh.getIconWidth();
    final int BigtoothHeight = bigtooh.getIconHeight();
    int toothSpeed = 500;
    int attack = 10;
    Point bigtoohPoint;
    Point prevpt;
    ArrayList<toothgroup> toothGroups = new ArrayList<>();
    ArrayList<Slipper> slippers = new ArrayList<>();
    int ddcX; // ddc image X position
    int ddcY; // ddc image Y position
    final int ddcWidth = ddc.getIconWidth();
    final int ddcHeight = ddc.getIconHeight();

    boolean showCollisionImageDDC = false;
    boolean showCollisionImageBigtooth = false;
    Point collisionPointDDC = new Point(0, 0);
    Point collisionPointBigtooth = new Point(0, 0);
    Timer collisionTimerDDC;
    Timer collisionTimerBigtooth;
    Timer IFGameEnd;
    Random random = new Random();
    boolean isPaused = false;
    int ToothIndex = 0;

    String hitSound = "/hit.wav";
    String backgroundMusic = "/background_music.wav";
    SoundPlayer HitSound = new SoundPlayer(hitSound);
    SoundPlayer BackgroundMusic = new SoundPlayer(backgroundMusic);

    Level1(GameFrame gamepanel) {
        BackgroundMusic.playSound();
        BackgroundMusic.setVolume(-10);

        HitSound.setVolume(6);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLayout(null);
        this.gameFrame = gamepanel;
        HP_ddc.setValue(500);
        HP_ddc.setBounds(0, 0, 400, 20);
        HP_ddc.setStringPainted(true);
        HP_ddc.setFont(new Font("MV Boli", Font.BOLD, 20));
        HP_ddc.setForeground(Color.red);
        HP_ddc.setBackground(Color.BLACK);
        HP_Bigtooth.setValue(500);
        HP_Bigtooth.setBounds(0, screenSize.height - 100, 400, 20);
        HP_Bigtooth.setStringPainted(true);
        HP_Bigtooth.setFont(new Font("MV Boli", Font.BOLD, 20));
        HP_Bigtooth.setForeground(Color.red);
        HP_Bigtooth.setBackground(Color.BLACK);
        this.add(HP_ddc);
        this.add(HP_Bigtooth);

        bigtoohPoint = new Point(screenSize.width / 2 - this.ddcWidth / 2, screenSize.height - this.ddcHeight);
        ddcX = screenSize.width / 2 - this.ddcWidth / 2;
        ddcY = 0;
        ClickListener clickListener = new ClickListener();
        DragListener dragListener = new DragListener();
        this.setBackground(Color.BLACK);
        this.setOpaque(true);
        this.addMouseListener(clickListener);
        this.addMouseMotionListener(dragListener);
        // tooth
        tooth[0] = new ImageIcon("img/tooth1.png").getImage();
        tooth[1] = new ImageIcon("img/tooth2.png").getImage();
        tooth[2] = new ImageIcon("img/tooth3.png").getImage();
        tooth[3] = new ImageIcon("img/tooth4.png").getImage();
        slip = new ImageIcon("img/slipper.png").getImage();
        timer = new Timer(500, this); // timer bigtooth
        timer.start();
        IFGameEnd = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (HP_ddc.getValue() == 0) {
                    // GameEnd
                    gamepanel.ShowEndFrame();
                    StopTimers();
                    gamepanel.stopgametimer();
                    IFGameEnd.stop();
                } else if (HP_Bigtooth.getValue() == 0) {
                    gamepanel.ShowDie();
                    StopTimers();
                    gamepanel.stopgametimer();
                    IFGameEnd.stop();
                }
            }
        });
        IFGameEnd.start();
        collisionTimerDDC = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCollisionImageDDC = false;
                collisionTimerDDC.stop();
                repaint();
            }
        });
        collisionTimerBigtooth = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCollisionImageBigtooth = false;
                collisionTimerBigtooth.stop();
                repaint();
            }
        });
        ddcTimer = new Timer(200, new ActionListener() { // collide on bigtooth ddc attack frequency
            @Override
            public void actionPerformed(ActionEvent e) {
                slipperxvel = random.nextInt(6) - 3;
                slipperyvel = random.nextInt(2, 3);
                Slipper sl = new Slipper((int) (ddcX + 30), (int) (ddcY + 30), slipperxvel, slipperyvel);
                slippers.add(sl);
                repaint();
            }
        });
        ddcTimer.start();
        movetooth = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Iterator<toothgroup> it = toothGroups.iterator();
                while (it.hasNext()) {
                    toothgroup tg = it.next();
                    tg.update();
                    if (CheckCollisionDDC(tg)) {
                        collisionPointDDC.setLocation(tg.x, tg.y);
                        showCollisionImageDDC = true;
                        CounterDdcHP -= (1 + attack);
                        HP_ddc.setValue(CounterDdcHP);
                        collisionTimerDDC.start();
                        it.remove();
                    }
                }
                repaint();
            }
        });
        movetooth.start();
        movesliper = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Iterator<Slipper> iterate = slippers.iterator();
                while (iterate.hasNext()) {
                    Slipper sg = iterate.next();
                    sg.update();
                    if (CheckCollisionBigtooth(sg)) {
                        collisionPointBigtooth.setLocation(sg.x, sg.y);
                        showCollisionImageBigtooth = true;
                        CounterBigtoothHP -= 20;
                        HP_Bigtooth.setValue(CounterBigtoothHP);
                        collisionTimerBigtooth.start();
                        iterate.remove();
                        HitSound.playSound();
                    }
                }
                repaint();
            }
        });
        movesliper.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) { // toothtimer collide on ddc
        toothgroup th = new toothgroup((int) bigtoohPoint.getX() + 30, (int) bigtoohPoint.getY() + 30, ToothVelocity);
        toothGroups.add(th);
        repaint();
    }

    public boolean CheckCollisionDDC(toothgroup tg) {
        Rectangle toothRect = new Rectangle(tg.x, tg.y, tooth[ToothIndex].getWidth(null),
                tooth[ToothIndex].getHeight(null));
        Rectangle ddcRect = new Rectangle(ddcX, ddcY - 50, ddcWidth, ddcHeight);
        return toothRect.intersects(ddcRect);
    }

    public boolean CheckCollisionBigtooth(Slipper sl) {
        Rectangle slipRect = new Rectangle(sl.x, sl.y, slip.getWidth(null), slip.getHeight(null));
        Rectangle BigtoothRect = new Rectangle(bigtoohPoint.x, bigtoohPoint.y - 200, BigtoothWidth, BigtoothHeight);
        return slipRect.intersects(BigtoothRect);
    }

    public class Slipper {
        public int x, xvel;
        public int y, yvel;

        public void update() {
            y += yvel;
            x += xvel;
        }

        Slipper(int x, int y, int xVel, int yVel) {
            this.x = x;
            this.y = y;
            this.yvel = yVel;
            this.xvel = xVel;
        }

        public void paint(Graphics g) {
            Graphics2D g2D = (Graphics2D) g;
            g2D.drawImage(slip, x, y + 200, null);
        }
    }

    public class toothgroup {
        public int x;
        public int y, yvel;

        public void update() {
            y -= yvel;
        }

        toothgroup(int x, int y, int yVel) {
            this.x = x;
            this.y = y;
            this.yvel = yVel;
        }

        public void paint(Graphics g) {
            Graphics2D g2D = (Graphics2D) g;
            g2D.drawImage(tooth[ToothIndex], x, y, null);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(gamepanelbg, 0, 0, getWidth(), getHeight(), this);
        bigtooh.paintIcon(this, g, (int) bigtoohPoint.getX(), (int) bigtoohPoint.getY()); // bigtooth
        ddc.paintIcon(this, g, ddcX, ddcY);
        // 绘制所有 toothgroup 对象
        for (toothgroup tg : toothGroups) {
            tg.paint(g);
        }
        for (Slipper sl : slippers) {
            sl.paint(g);
        }
        if (showCollisionImageBigtooth) {
            boom.paintIcon(this, g, collisionPointBigtooth.x, collisionPointBigtooth.y);
        }
        if (showCollisionImageDDC) {
            boom.paintIcon(this, g, collisionPointDDC.x, collisionPointDDC.y);
        }
    }

    private class ClickListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            prevpt = e.getPoint();
        }
    }

    private class DragListener extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent e) {
            Point currentpt = e.getPoint();
            bigtoohPoint.translate((int) (currentpt.getX() - prevpt.getX()), (int) (currentpt.getY() - prevpt.getY()));
            prevpt = currentpt;
            repaint();
        }
    }

    public void StopTimers() {
        timer.stop();
        ddcTimer.stop();
        isPaused = true;
        movetooth.stop();
        movesliper.stop();
    }

    public void StartTimers() {
        timer.start();
        ddcTimer.start();
        isPaused = false;
        movetooth.start();
        movesliper.start();
    }

    public void heal() {
        CounterBigtoothHP += 90;
        if (CounterBigtoothHP > 500)
            CounterBigtoothHP = 500;
        HP_Bigtooth.setValue(CounterBigtoothHP);
    }

    public void IncreaseSpeed() {
        this.ToothVelocity += 1;
        Iterator<toothgroup> it = toothGroups.iterator();
        while (it.hasNext()) {
            toothgroup tg = it.next();
            tg.yvel += 1;
        }
        toothSpeed -= 100;
        toothSpeed = Math.max(100, toothSpeed);
        timer.setDelay(toothSpeed);
    }

    public void IncreaseAttack() {
        ToothIndex = Math.min(ToothIndex + 1, 3);
        attack += 10;
    }

}
