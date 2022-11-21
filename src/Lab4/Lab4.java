package Lab4;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Objects;
import java.util.regex.Pattern;

public class Lab4 {
    public static void main(String[] args) throws IOException {
        Shell shell = new Shell();
    }

    static public class Shell extends JPanel implements FocusListener {
        static JFrame frame = new JFrame();
        static JPanel panel = new JPanel(new GridLayout());
        static String[] title = new String[]{
                "Інформація про організацію",
                "Назва *",
                "Місто *",
                "Адреса *",
                "Контактна особа",
                "Контактна особа *"
        };
        static final String[] exp_text = new String[]{
                "В тексті наявні спец символи",
                "В тексті наявні цифри",
                "Треба більше текста",
                "Все чудово, ви молодець!",};
        static final JLabel[] labels = new JLabel[title.length];
        static final JLabel[] exp_labels = new JLabel[title.length];
        static final JTextField[] inputs = new JTextField[title.length];
        private Rectangle rect;
        private final boolean checks[] = new boolean[title.length];
        private static JCheckBox checkBox;

        Shell() {
            Font labelFont = new Font("Calibri ", Font.BOLD, 18);
            Font fieldFont = new Font("Calibri ", Font.PLAIN, 16);
            Font buttonFont = new Font("Anton", Font.PLAIN, 20);
            Font errorFont = new Font("Anton", Font.ITALIC, 10);
            Color blue = new Color(46, 126, 255);
            Color agree = new Color(43, 145, 51);
            Color desagree = new Color(161, 34, 27);

            Border blueBorder = BorderFactory.createLineBorder(blue);

            JFrame frame = getFrame();
            frame.add(panel);
            panel.setLayout(null);

            JButton btn_send = new JButton("Send");
            btn_send.setBounds(645, 410, 75, 35);
            btn_send.setForeground(Color.white);
            btn_send.setBackground(agree);
            btn_send.setFont(buttonFont);
            btn_send.setEnabled(false);
            btn_send.addActionListener(this::actionPerformed);
            int x = 20;
            int y = 0;
            for (int i = 0; i < labels.length; i++) {
                if (i == 0 || i == 4) {
                    JLabel label = new JLabel(title[i]);
                    y = y + 10 * i;
                    label.setBounds(x, y, 350, 50);
                    label.setFont(labelFont);
                    label.setForeground(blue);
                    panel.add(label);
                }
                JLabel expLabel = new JLabel();
                JLabel label = new JLabel(title[i]);
                label.setFont(fieldFont);
                y = y + 50;
                label.setBounds(x, y, 250, 50);
                exp_labels[i] = expLabel;
                labels[i] = label;
                panel.add(label);


                JTextField textField = new JTextField(20);
                textField.setBounds(x + 250, y + 15, 250, 25);
                textField.setFont(fieldFont);
                textField.setBorder(blueBorder);
                textField.addFocusListener(this);
                inputs[i] = textField;
                textField.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        for (int i = 0; i < title.length; i++) {
                            String text = inputs[i].getText();
                            if (text.trim().isEmpty()) {
                                panel.remove(exp_labels[i]);
                                panel.revalidate();
                                panel.repaint();
                                btn_send.setEnabled(false);
                            }
                        }
                    }

                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        for (int i = 0; i < title.length; i++) {
                            boolean check = false;
                            String text = inputs[i].getText();
                            if (!text.trim().isEmpty()) {
                                JLabel expLabel = exp_labels[i];
                                System.out.println(text);
                                if (text.matches(".*[^0-9а-яіїa-zA-Z].*")) {
                                    expLabel.setText(exp_text[0]);
                                    expLabel.setForeground(desagree);
                                } else if (text.matches(".*[0-9]+.*")) {
                                    expLabel.setText(exp_text[1]);
                                    expLabel.setForeground(desagree);
                                } else if (text.length() < 10) {
                                    expLabel.setText(exp_text[2]);
                                    expLabel.setForeground(desagree);
                                } else {
                                    expLabel.setText(exp_text[3]);
                                    expLabel.setForeground(agree);
                                    check = true;
                                }

                                expLabel.setBounds(20, labels[i].getY() + 15, 350, 50);

                                exp_labels[i].setFont(fieldFont);
                                panel.add(exp_labels[i]);
                                panel.revalidate();
                                panel.repaint();
                            }
                            checks[i] = check;
                        }
                        btn_send.setEnabled(areAllTrue(checks));
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {

                    }
                });

                panel.add(textField);


            }

            URL imgURL = Shell.class.getResource("java-icon.png");

            ImageIcon icon = new ImageIcon(imgURL);
            System.out.println(icon);
            JLabel testimage = new JLabel();
            testimage.setIcon(icon);
            testimage.setBounds(750 - 283 / 2 - 50, 80, 283, 283);
            panel.add(testimage);

            checkBox = new JCheckBox("Хочу отримувати розсилку ");
            checkBox.setBounds(20, 400, 250, 50);
            checkBox.setFont(fieldFont);
            panel.add(checkBox);

            JButton btnCansel = new JButton("Cancel");
            btnCansel.setBounds(535, 410, 100, 35);
            btnCansel.setBackground(desagree);
            btnCansel.setForeground(Color.white);
            btnCansel.setFont(buttonFont);
            btnCansel.addActionListener(this::actionPerformed_Cansel);
            panel.add(btnCansel);

            panel.add(btn_send);


            frame.setVisible(true);
            panel.revalidate();
        }

        public static boolean areAllTrue(boolean[] array) {
            for (boolean b : array) if (!b) return false;
            return true;
        }

        public void focusGained(FocusEvent e) {
            JTextField textField = (JTextField) e.getComponent();
            textField.setBackground(new Color(211, 218, 238, 255));
            rect = textField.getBounds();
            e.getComponent().setBounds(new Rectangle(textField.getBounds().x,
                    textField.getBounds().y,
                    textField.getWidth() + 100,
                    textField.getHeight()));
        }

        public void focusLost(FocusEvent e) {
            JTextField textField = (JTextField) e.getComponent();
            textField.setBackground(Color.white);
            textField.setBounds(rect);
        }

        public void actionPerformed(ActionEvent e) {
            if (!checkBox.isSelected()) {
                JOptionPane.showMessageDialog(this, "Підпішиться на розсилку", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Дякуємо, ми \n" + "обробляємо вашу інформацію", "Message", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }

        }
        public void actionPerformed_Cansel(ActionEvent e){
            System.exit(0);
        }
        static JFrame getFrame() {
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Organisation form");
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension dimension = toolkit.getScreenSize();
            frame.setBounds(dimension.width / 2 - 350, dimension.height / 2 - 250, 750, 500);
            return frame;
        }
    }
}