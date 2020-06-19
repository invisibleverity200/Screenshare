import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

public class SocketConnection implements Runnable {
    Socket socket;
    DataOutputStream dataOutputStream;
    long fps;
    Protocol protocol;

    SocketConnection(Socket socket, long fps) throws IOException {
        this.socket = socket;
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.fps = fps;
        protocol = new MyProtocol();
    }

    @Override
    public void run() {
        while (true) {
            try {
                BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                byte[][] data = protocol.encode(image);
                dataOutputStream.writeInt(data.length);
                for (int x = 0; x < data.length; x++) {
                    dataOutputStream.writeInt(data[x].length);
                    dataOutputStream.write(data[x]);
                }

            } catch (AWTException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

