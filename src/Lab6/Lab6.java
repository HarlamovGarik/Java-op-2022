package Lab6;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Lab6 {
    static boolean draw = false;
    static boolean delete = false;


    static JFrame frame;
    static DrawFigure drawFigure = new DrawFigure();
    static int[] sides = new int[3];
    static final JLabel[] labels = new JLabel[3];
    static final JTextField[] inputs = new JTextField[3];
    static final JButton btn = new JButton("Calculation");
    static final JMenuItem check = new JMenuItem("перевірка");
    static final JMenuItem calculate = new JMenuItem("обчислити");

    static JMenu change_color = new JMenu("Змінити колір на:");
    static final JMenuItem delete_figure = new JMenuItem("Видалити");
    static final JMenuItem circle_figure = new JMenuItem("Надпис");
    static boolean checkInputs = true;
    static Color select_color = new Color(77, 77, 77);
    static Color MYBLUE = new Color(46, 126, 255);
    static Color agree = new Color(43, 145, 51);
    static Color desagree = new Color(161, 34, 27);
    public static void main(String[] args) {
        Font labelFont = new Font("Corbel", Font.BOLD, 16);
        Font fieldFont = new Font("Calibri ", Font.PLAIN, 16);
        Font buttonFont = new Font("Anton", Font.PLAIN, 20);

        Border blueBorder = BorderFactory.createLineBorder(MYBLUE);

        frame = new JFrame("Lab6");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        frame.setBounds(dimension.width / 2 - 200, dimension.height / 2 - 200, 700, 400);

        frame.setLayout(new GridLayout(0, 2));
        frame.add(drawFigure);
        JPanel panel = new JPanel(new GridLayout(4, 2, 25, 10));
        for (int i = 0; i < 3; i++) {
            JLabel label = new JLabel("Введіть - " + i + " сторону:");
            label.setFont(labelFont);
            panel.add(label);
            JTextField textField = new JTextField();
            textField.setFont(fieldFont);
            textField.setBorder(blueBorder);
            textField.getDocument().addDocumentListener(new DocumentListener() {

                @Override
                public void insertUpdate(DocumentEvent e) {
                    checkInputs = true;
                    for (int i = 0; i < inputs.length; i++) {
                        String text = inputs[i].getText();
                        sides[i] = formatString(text);
                        if (text.trim().isEmpty()) {
                            checkInputs = false;
                            break;
                        }
                    }
                    btn.setEnabled(checkInputs);
                    if(checkInputs) btn.setBackground(agree); else btn.setBackground(desagree);
                    check.setEnabled(checkInputs);
                    calculate.setEnabled(checkInputs);
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    checkInputs = true;
                    for (int i = 0; i < inputs.length; i++) {
                        String text = inputs[i].getText();
                        sides[i] = formatString(text);
                        if (text.trim().isEmpty()) {
                            checkInputs = false;
                            break;
                        }
                    }
                    btn.setEnabled(checkInputs);
                    if(checkInputs) btn.setBackground(agree); else btn.setBackground(desagree);
                    check.setEnabled(checkInputs);
                    calculate.setEnabled(checkInputs);
                }

                @Override
                public void changedUpdate(DocumentEvent e) {

                }
            });
            inputs[i] = textField;
            panel.add(textField);
        }
        btn.setFont(buttonFont);
        btn.addActionListener(Lab6::calculate);
        btn.setBackground(desagree);
        btn.setEnabled(false);
        panel.add(btn);
        frame.add(panel);
        createBarMenu();

        frame.setVisible(true);

    }

    private static void createBarMenu() {
        JMenuBar jMenuBar = new JMenuBar();
        JMenu file_menu = new JMenu("Програма");
        JMenu edit_menu = new JMenu("Правка");
        JMenu triangle_menu = new JMenu("Трикутник");

        file_menu.add(check);
        file_menu.add(calculate);

        file_menu.addSeparator();

        JMenuItem exit = file_menu.add(new JMenuItem("Вихід"));

        JMenuItem copy = edit_menu.add(new JMenuItem("Копіювати"));
        JMenuItem paste = edit_menu.add(new JMenuItem("Вставити"));

        triangle_menu.add(change_color);
        triangle_menu.add(circle_figure);
        triangle_menu.addSeparator();
        triangle_menu.add(delete_figure);


        JMenuItem red = change_color.add(new JMenuItem("Червоний"));
        JMenuItem blue = change_color.add(new JMenuItem("Синій"));
        JMenuItem green = change_color.add(new JMenuItem("Зелений"));
        JMenuItem yellow = change_color.add(new JMenuItem("Жовтий"));

        red.addActionListener(e -> color(Color.red));
        blue.addActionListener(e -> color(Color.blue));
        green.addActionListener(e -> color(Color.green));
        yellow.addActionListener(e -> color(Color.yellow));

        delete_figure.setBackground(desagree);
        delete_figure.setEnabled(false);
        circle_figure.setEnabled(false);

        jMenuBar.add(file_menu);
        jMenuBar.add(edit_menu);
        jMenuBar.add(triangle_menu);

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        check.addActionListener(Lab6::check);
        calculate.addActionListener(Lab6::calculate);
        check.setAccelerator(KeyStroke.getKeyStroke("ctrl F"));
        calculate.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
        check.setEnabled(false);
        calculate.setEnabled(false);
        delete_figure.addActionListener(Lab6::delete);
        frame.setJMenuBar(jMenuBar);
    }

    private static void color(Color color) {
        select_color=color;
        frame.revalidate();

    }

    static void delete(ActionEvent e) {
        drawFigure.delete_figure();
        frame.revalidate();
        delete_figure.setEnabled(false);
        circle_figure.setEnabled(false);
    }

    static void check(ActionEvent e) {
        if (isExistTriangle(sides[0], sides[1], sides[2])) {
            printMessage("Трикутник існує", "Перевірка існування трикутника");
        } else {
            printMessage("Трикутник не існує", "Перевірка існування трикутника");
        }
    }


    static void calculate(ActionEvent e) {
        if (isExistTriangle(sides[0], sides[1], sides[2]))
            areaOfTriangle(sides[0], sides[1], sides[2]);
        else
            printMessage("Трикутник не існує, введіть інші дані", "Перевірка існування трикутника");
        drawFigure.new_figure(sides);
        delete_figure.setEnabled(true);
        circle_figure.setEnabled(true);
        frame.revalidate();
    }


    static int formatString(String number) {
        number = number.trim();
        return Integer.parseInt(number);
    }

    static public void areaOfTriangle(int a, int b, int c) {
        float p = (a + b + c);
        float halfP = p / 2;
        float P = a+b+c;
        float S = (float) Math.sqrt(halfP * (halfP - a) * (halfP - b) * (halfP - c));

        float radiusInCircle = S / halfP ;

        double radiusCircusCircle = 3.14 * Math.pow(((a * b * c) / (4 * S)), 2);

        String message = "";
        message += "Периметр трикутника -> " + P;
        message += "\nПлощина трикутника -> " + S;
        message += "\nрадіус вписаного кола -> " + radiusInCircle;
        message += "\nрадіус описаного кола -> " + radiusCircusCircle;
        printMessage(message, "Розрахунки");
    }


    private static void printMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
    }

    private static boolean isExistTriangle(int a, int b, int c) {
        return (a + b > c && a + c > b && b + c > a);
    }

    static class DrawFigure extends JComponent {
        private int[] sides;
        private Graphics2D g2;

        @Override
        protected void paintComponent(Graphics g) {
            g2 = (Graphics2D) g;
            if (draw) {
                draw(select_color);
                frame.repaint();

            }
            if (delete) {
                draw(new Color(238, 238, 238));
                frame.repaint();
            }

        }

        public void change_color() {
            draw = true;
            delete = false;
        }

        public void delete_figure() {
            draw = false;
            delete = true;
        }

        public void new_figure(int[] sides) {
            this.sides = formattedSides(sides);
            draw = true;
            delete = false;
        }

        private int[] formattedSides(int[] sides) {
            int max_side = sides[0];
            int[] formatted_sides = new int[3];
            for (int side : sides) {
                if (side > max_side) max_side = side;
            }
            for (int i = 0; i < formatted_sides.length; i++) {
                formatted_sides[i] = sides[i] * 300 / max_side;
            }
            return formatted_sides;
        }

        private void draw(Color color) {
            sides = formattedSides(sides);
            int starPositionY = (300 - sides[1]) / 2;
            int starPositionX = (300 - sides[0]) / 2;
            int[] x = {starPositionX + sides[0], starPositionX, starPositionX + sides[0]};
            int[] y = {starPositionY, starPositionY, starPositionY + sides[1]};
            g2.setPaint(color);
            g2.fillPolygon(x, y, 3);
            frame.repaint();
        }

        public int[] getSides() {
            return sides;
        }
    }
}