import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.spi.ImageOutputStreamSpi;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.Socket;
import java.util.Base64;
import java.util.Iterator;

public class socketConnection implements Runnable {
    Socket socket;
    DataOutputStream dataOutputStream;
    long fps;

    socketConnection(Socket socket, long fps) throws IOException {
        this.socket = socket;
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.fps = fps;
    }

    @Override
    public void run() {
        while (true) {
            try {
               // Thread.sleep(1000/fps);
                BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "jpg", baos);

                byte[] byteData = baos.toByteArray();
                int size = byteData.length;
                int packges = size / (32768 - 2 * Integer.BYTES);
                System.out.println("Packages: " + packges);
                int lastPackageSize = size % (32768 - 2 * Integer.BYTES);

                if (packges < 1) {
                    dataOutputStream.writeInt(1);
                    dataOutputStream.writeInt(size);
                    dataOutputStream.write(byteData);
                } else if (size % (32768 - 2 * Integer.BYTES) != 0) {
                    int i = 0;
                    if (lastPackageSize < (32768 - 2 * Integer.BYTES / 2)) {
                        packges += 1;
                    }
                    dataOutputStream.writeInt(packges);
                    while (packges > i) {
                        if (i == (packges - 1)) {
                            dataOutputStream.writeInt(size % (32768 - 2 * Integer.BYTES));
                            dataOutputStream.write(byteData, i * (32768 - 2 * Integer.BYTES), size % (32768 - 2 * Integer.BYTES));
                            i++;
                        } else {
                            dataOutputStream.writeInt((32768 - 2 * Integer.BYTES));
                            dataOutputStream.write(byteData, i * (32768 - 2 * Integer.BYTES), (32768 - 2 * Integer.BYTES));
                            i++;
                        }
                    }
                }
            } catch (AWTException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } 

        }

    }
}
