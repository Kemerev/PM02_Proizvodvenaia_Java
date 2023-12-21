import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Timer;


public class Main extends JFrame {

    private static Main Main_Main;
    private static long last_frame_time;
    private static Image logo;
    private static Image elka;
    private static Image elka2; // Добавлено для elka2.png
    private static float drop_left = 200;
    private static float drop_top = -100;
    private static float drop_v = 200;
    private static int score = 0;
    private static boolean isElka1 = true; // Переменная для отслеживания текущего изображения

    public static void main(String[] args) throws IOException {
        logo = ImageIO.read(Main.class.getResourceAsStream("logo.png"));
        logo = resizeImage(logo, logo.getWidth(null) / 2, logo.getHeight(null) / 2);

        elka = ImageIO.read(Main.class.getResourceAsStream("elka.png"));
        elka2 = ImageIO.read(Main.class.getResourceAsStream("elka2.png"));

        Main_Main = new Main();
        Main_Main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Main_Main.setResizable(false);
        Main_Main.setSize(elka.getWidth(null), elka.getHeight(null));

        last_frame_time = System.nanoTime();
        GameField game_field = new GameField();
        game_field.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));

        JLabel carSelectLabel = new JLabel("Выберите тип автомобиля:");
        JComboBox<String> carSelect = new JComboBox<>(new String[]{
                "Выберите тип автомобиля",
                "VW Golf VII",
                "Audi A1 S-LINE",
                "Toyota Camry",
                "BMW 320 ModernLine",
                "Mercedes-Benz GLK",
                "VW Passat CC"
        });

        JLabel pickUpLocationLabel = new JLabel("Место получения автомобиля:");
        JTextField pickUpLocation = new JTextField();

        JLabel pickUpDateTimeLabel = new JLabel("Дата и время получения:");
        JTextField pickUpDateTime = new JTextField();

        JLabel dropOffDateTimeLabel = new JLabel("Дата и время сдачи:");
        JTextField dropOffDateTime = new JTextField();

        JButton submitButton = new JButton("Продолжить бронирование автомобиля");

        panel.add(carSelectLabel);
        panel.add(carSelect);
        panel.add(pickUpLocationLabel);
        panel.add(pickUpLocation);
        panel.add(pickUpDateTimeLabel);
        panel.add(pickUpDateTime);
        panel.add(dropOffDateTimeLabel);
        panel.add(dropOffDateTime);
        panel.add(submitButton);

        game_field.add(panel);

        Timer timer = new Timer(15000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isElka1 = !isElka1; // Переключаем между elka1 и elka2 каждые 15 секунд
                game_field.repaint(); // Перерисовываем поле для отображения нового изображения
            }
        });
        timer.start(); // Запускаем таймер

        // Позиционируем форму по центру экрана
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - Main_Main.getWidth()) / 2;
        int y = (screenSize.height - Main_Main.getHeight()) / 2;
        Main_Main.setLocation(x, y);

        Main_Main.add(game_field);
        Main_Main.setVisible(true);
    }

    private static void onRepaint(Graphics g){
        
    }

    private static class GameField extends JPanel{
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(logo, 0, 0, null);
            if (isElka1) {
                g.drawImage(elka, 0, 0, null); // Отображаем elka.png
            } else {
                g.drawImage(elka2, 0, 0, null); // Отображаем elka2.png
            }

            onRepaint(g);
            repaint();
        }

        @Override
        public Dimension getPreferredSize() {
            if (elka != null) {
                return new Dimension(elka.getWidth(null), elka.getHeight(null));
            } else {
                return super.getPreferredSize();
            }
        }
    }

    private static Image resizeImage(Image originalImage, int newWidth, int newHeight) {
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();
        return resizedImage;
    }
}
