import java.awt.image.BufferedImage;
import java.util.ArrayList;

public interface Protocol {
    byte[][] encode(BufferedImage image);

    BufferedImage decode(ArrayList<Byte> data);
}
