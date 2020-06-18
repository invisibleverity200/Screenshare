import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ReceiveSocket extends Socket implements Runnable {
    DataInputStream dataInputStream;
    MyCanvas canvas;

    ReceiveSocket(String ip, int port, long fps, MyCanvas canvas) throws IOException {
        super(ip, port);
        dataInputStream = new DataInputStream(this.getInputStream());
        this.canvas = canvas;
    }

    @Override
    public void run() {
        while (true) {
            try {
                while (dataInputStream.available() > 0) {
                    int packages = dataInputStream.readInt();
                    System.out.println("\nReceivePackages: " + packages+"");
                    ArrayList<Byte> imageData = new ArrayList<>();
                    int i = 0;
                    while (packages > i) {
                        while (dataInputStream.available() < Integer.BYTES);
                        int size = dataInputStream.readInt();
                        byte[] packageData = new byte[size];
                        while (dataInputStream.available() < size);

                        dataInputStream.readFully(packageData, 0, size);
                        System.out.println("\nPackage["+i+"]["+size+" Bytes]            [Loaded]");
                        for (int x = 0; x < packageData.length; x++) {
                            imageData.add(packageData[x]);
                        }
                        i++;
                    }
                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(arrayListToByteArray(imageData)));
                    canvas.paintImage(image);
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
