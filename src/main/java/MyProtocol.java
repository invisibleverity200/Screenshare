import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MyProtocol implements Protocol {
    @Override
    public Package[] encode(BufferedImage image) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[] byteData = baos.toByteArray();
            int size = byteData.length;
            int packges = size / (32768 - 2 * Integer.BYTES);
            int lastPackageSize = size % (32768 - 2 * Integer.BYTES);

            if (lastPackageSize >= size % (32768 - 2 * Integer.BYTES)) packges += 1;

            Package[] packages = new Package[packges];

            for (int x = 0; x < packages.length; x++) {
                if (x == packages.length - 1) {
                    packages[x] = new Package(Arrays.copyOfRange(byteData, x * (32768 - 2 * Integer.BYTES), x * (32768 - 2 * Integer.BYTES) + (size % (32768 - 2 * Integer.BYTES))));
                } else {
                    packages[x] = new Package(Arrays.copyOfRange(byteData, x * (32768 - 2 * Integer.BYTES), x * (32768 - 2 * Integer.BYTES) + (32768 - 2 * Integer.BYTES)));
                }
            }
            return packages;

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
