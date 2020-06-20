import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class MyCanvas extends Canvas {
    private JFrame frame;

    BufferStrategy bufferStrategy;
    Graphics graphics;

    MyCanvas(JFrame frame) {
        super();

        this.frame = frame;

        this.setSize(1920, 1080);
        this.setBackground(Color.BLACK);
        this.setVisible(true);
        this.setFocusable(false);
        
        frame.getContentPane().add(this);

        this.createBufferStrategy(3);

    }

    public void paintImage(BufferedImage image) {
        bufferStrategy = this.getBufferStrategy();
        graphics = bufferStrategy.getDrawGraphics();
        graphics.clearRect(0, 0, 1920, 1080);

        graphics.drawImage(image, 0, 0, null);

        bufferStrategy.show();
        graphics.dispose();
    }
}
