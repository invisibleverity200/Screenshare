import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MyProtocol implements Protocol {
    @Override
    public byte[][] encode(BufferedImage image) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[] byteData = baos.toByteArray();
            int size = byteData.length;
            int packges = size / (32768 - 2 * Integer.BYTES);
            int lastPackageSize = size % (32768 - 2 * Integer.BYTES);

            if (lastPackageSize >= size % (32768 - 2 * Integer.BYTES)) packges += 1;

            byte[][] data = new byte[packges][];

            for (int x = 0; x < data.length; x++) {
                if (x == data.length - 1) {
                    data[x] = Arrays.copyOfRange(byteData, x * (32768 - 2 * Integer.BYTES), x * (32768 - 2 * Integer.BYTES) + (size % (32768 - 2 * Integer.BYTES)));
                } else {
                    data[x] = Arrays.copyOfRange(byteData, x * (32768 - 2 * Integer.BYTES), x * (32768 - 2 * Integer.BYTES) + (32768 - 2 * Integer.BYTES));
                }
            }
            return data;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public BufferedImage decode(ArrayList<Byte> data) {
        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(arrayListToByteArray(data)));
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] arrayListToByteArray(ArrayList<Byte> byteArrayList) {
        byte[] array = new byte[byteArrayList.size()];
        for (int x = 0; x < array.length; x++) {
            array[x] = byteArrayList.get(x);
        }
        return array;
    }
}
