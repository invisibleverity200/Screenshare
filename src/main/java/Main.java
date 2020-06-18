import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        GUI gui = new GUI();
        try {
            new Thread(new ProxySocket(6,1)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
