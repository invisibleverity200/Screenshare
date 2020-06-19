import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ReceiveSocket extends Socket implements Runnable {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_WHITE = "\u001B[37m";

    DataInputStream dataInputStream;
    MyCanvas canvas;
    Protocol protocol;

    ReceiveSocket(String ip, int port, MyCanvas canvas) throws IOException {
        super(ip, port);
        dataInputStream = new DataInputStream(this.getInputStream());
        this.canvas = canvas;
        protocol = new MyProtocol();
    }

    @Override
    public void run() {
        while (true) {
            try {
                while (dataInputStream.available() > 0) {
                    int packages = dataInputStream.readInt();
                    System.out.println("\n\nHeader: {PackagesAmount: " + packages + "}");
                    ArrayList<Byte> imageData = new ArrayList<>();
                    int i = 0;
                    while (packages > i) {
                        while (dataInputStream.available() < Integer.BYTES) ;
                        int size = dataInputStream.readInt();
                        byte[] packageData = new byte[size];
                        while (dataInputStream.available() < size) ;

                        dataInputStream.readFully(packageData, 0, size);
                        System.out.println("\nPackage[" + i + "][" + size + " Bytes]" + ANSI_GREEN + "      [Loaded]" + ANSI_WHITE);
                        for (int x = 0; x < packageData.length; x++) {
                            imageData.add(packageData[x]);
                        }
                        i++;
                    }
                    canvas.paintImage(protocol.decode(imageData));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private byte[] arrayListToByteArray(ArrayList<Byte> byteArrayList) {
        byte[] array = new byte[byteArrayList.size()];
        for (int x = 0; x < array.length; x++) {
            array[x] = byteArrayList.get(x);
        }
        return array;
    }

}
