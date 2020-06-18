import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) {
        try {
            new Thread(new ProxySocket(6,1)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
