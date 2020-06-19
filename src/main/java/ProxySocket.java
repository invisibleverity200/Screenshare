import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ProxySocket extends ServerSocket implements Runnable {
    long fps;
    ProxySocket(int port, long fps) throws IOException {
        super(port);
        this.fps = fps;
    }

    public void run() {
        while (true) {
            try {
                Socket connSocket = this.accept();
                new Thread(new SocketConnection(connSocket, fps)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
