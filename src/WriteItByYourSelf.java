import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

public class WriteItByYourSelf extends JPanel {
    JLabel label = new JLabel("下一關自己寫啦憨豆");
    JLabel closelabel = new JLabel();
    JLabel closelabel2 = new JLabel();
    Image image = new ImageIcon("img/code.png").getImage();
    GameFrame gamePanel;
    Random random = new Random();
    ImageIcon cross = new ImageIcon("img/cross.png");
    ImageIcon nocross = new ImageIcon("img/nocross.png");
    int x, y;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Boolean CanClose = false;

    WriteItByYourSelf(GameFrame gamePanel) {
        this.gamePanel = gamePanel;
        // 設置JPanel的大小和佈局
        this.setLayout(null);
        this.setSize(screenSize.width, screenSize.height);

        JTextArea terminal = new JTextArea();
        terminal.setText("$ ");
        terminal.setBounds(340, 725, 1050, 150);
        terminal.setEditable(true);
        terminal.setFont(new Font("Monospaced", Font.PLAIN, 14));
        terminal.setForeground(Color.lightGray);
        terminal.setCaretColor(Color.WHITE);
        terminal.setOpaque(false);

        terminal.addKeyListener(new KeyAdapter() {
            private boolean ctrlPressed = false;

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleCommand(terminal);
                    e.consume();
                } else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_C) {
                    terminal.copy();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    ctrlPressed = false;
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
                if (ctrlPressed && e.getKeyChar() == 'v') {
                    terminal.paste();
                } else if (e.getKeyChar() == KeyEvent.VK_CONTROL) {
                    ctrlPressed = true;
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(terminal);
        scrollPane.setBounds(340, 725, 1050, 150); // Adjust the height to allow more lines to be visible
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        this.add(scrollPane);

        JTextArea CodePanel = new JTextArea();
        CodePanel.setBounds(400, 110, 1050, 550);
        CodePanel.setLineWrap(true); // 启用自动换行
        CodePanel.setWrapStyleWord(true); // 换行时保证单词完整
        CodePanel.setOpaque(false);
        CodePanel.setForeground(new Color(170, 170, 170));
        CodePanel.setCaretColor(Color.white);
        CodePanel.requestFocusInWindow();
        CodePanel.setFont(new Font("MV Boli", Font.PLAIN, 18));
        this.add(CodePanel);

        closelabel.setBounds(screenSize.width - 30, 0, 30, 30);

        closelabel.setHorizontalAlignment(JLabel.CENTER);
        closelabel.setVerticalAlignment(JLabel.CENTER);
        closelabel.setIcon(cross);
        closelabel.setOpaque(true);
        closelabel.setVisible(false);
        closelabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                closelabel.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closelabel.setVisible(false);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (CanClose) {
                    gamePanel.closewindow();
                    System.exit(0);
                } else {
                    addlabel();
                }
            }
        });
        this.add(closelabel);
        closelabel2.setBounds(screenSize.width - 30, 0, 30, 30);
        closelabel2.setHorizontalAlignment(JLabel.CENTER);
        closelabel2.setVerticalAlignment(JLabel.CENTER);
        closelabel2.setIcon(nocross);
        closelabel2.setOpaque(true);
        closelabel2.setVisible(true);
        closelabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                closelabel.setVisible(true);
                closelabel2.setVisible(false);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closelabel2.setVisible(true);
            }
        });
        this.add(closelabel2);
        label.setBounds(screenSize.width / 2 - 400, 500, 800, 100);
        label.setFont(new Font("Comic Sans", Font.BOLD, 25));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setForeground(Color.white);

        this.add(label);
        this.setVisible(true);
    }

    public void addlabel() {
        x = random.nextInt(screenSize.width);
        y = random.nextInt(screenSize.height);
        JLabel newLabel = new JLabel("想跑是吧");
        newLabel.setBounds(x, y, 500, 100);
        newLabel.setFont(new Font("Comic Sans", Font.BOLD, 25));
        newLabel.setHorizontalAlignment(JLabel.CENTER);
        newLabel.setForeground(Color.white);
        newLabel.setBackground(Color.red);
        this.add(newLabel);
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, screenSize.width, screenSize.height - 60, this);
    }

    private void handleCommand(JTextArea CodePanel) {
        String command = getLastCommand(CodePanel);
        String output = "";
        
        if (command.equals("JAVA_DEMO_FLAG{我再也不會內卷了}")) {
            output = "被找到flag了不嘻嘻，現在你可以滾了\n";
            CanClose = true;
        } else {
            try {
                @SuppressWarnings("deprecation")
                Process process = Runtime.getRuntime().exec(command);

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    output += line + "\n";
                }
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                while ((line = errorReader.readLine()) != null) {
                    output += line + "\n";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        appendToTextArea("\n" + output + "\n$ ", CodePanel);
    }

    private static String getLastCommand(JTextArea CodePanel) {
        String text = CodePanel.getText();
        String[] lines = text.split("\n");
        if (lines.length == 0) {
            return "";
        } else {
            return lines[lines.length - 1].trim().substring(2);
        }
    }

    private static void appendToTextArea(String text, JTextArea CodePanel) {
        CodePanel.append(text);
        CodePanel.setCaretPosition(CodePanel.getDocument().getLength());
    }
}
