package Lab3;

import javax.swing.*;
import java.awt.*;

//У вікні застосунку розмістити три фігури: круг червоного кольору,
//        квадрат синього кольору, прямокутну трапецію зеленого кольору.
//        Створити напис біля кожної фігури, який містить інформацію про її
//        назву та колір. Напис повинен мати наступні характеристики: розмір
//        16пт, колір темно-сірий, напівжирний. В правому нижньому кутку
//        додати інформацію про розробника програми

public class Task1 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Lab3 Block1");
        //frame.setSize(400, 400);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        frame.setBounds(dimension.width / 2 - 200, dimension.height / 2 - 200, 400, 400);
        frame.add(new MyComponent());

    }

    static class MyComponent extends JComponent {
        @Override
        protected void paintComponent(Graphics g) {
            final int startPosition =20;
            final int margin =10;
            final Color fontColor = Color.green;
            Font font = new Font("Calibri ", Font.BOLD, 16);
            Graphics2D g2 = (Graphics2D) g;
            g2.setFont(font);
            g2.setPaint(fontColor);
            g2.drawString("Harlamov Igor", 215, 350);
            g2.drawString("Triangle", startPosition, startPosition);
            g2.drawString("Square", 100+startPosition+margin, startPosition);
            g2.drawString("Circle", startPosition, 100+startPosition+2*margin);


            int x[]={startPosition/2+margin+50,startPosition/2+margin,startPosition/2+margin+100};
            int y[]={startPosition+margin,startPosition+100,startPosition+100};
            g2.fillPolygon(x,y,3);

            Color color1 = Color.blue;
            Color color2 = Color.yellow;
            GradientPaint gp = new GradientPaint(startPosition+2*margin+100, startPosition+margin, color1, 100, 100, color2);
            g2.setPaint(gp);
            g2.fillRect(startPosition+2*margin+100, startPosition+margin, 100, 100);

            g2.setPaint(Color.black);
            g2.fillOval(startPosition, 100+startPosition+3*margin, 100, 100);

        }
    }
}