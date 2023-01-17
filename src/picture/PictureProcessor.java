package picture;

import java.util.ArrayList;
import java.util.List;

public class PictureProcessor {
    public static void main(String[] args) {
        if (args[0].compareTo("invert") == 0) {
            Picture input = new Picture(args[1]);
            input.invert();
            input.saveAs(args[2]);
        }
        if (args[0].compareTo("grayscale") == 0) {
            Picture input = new Picture(args[1]);
            input.grayscale();
            input.saveAs(args[2]);
        }
        if (args[0].compareTo("rotate") == 0) {
            Picture input = new Picture(args[2]);
            if (args[1].compareTo("90") == 0) {
                Picture out = input.rotate90();
                out.saveAs(args[3]);
            } else if (args[1].compareTo("180") == 0) {
                Picture out = input.rotate180();
                out.saveAs(args[3]);
            } else if (args[1].compareTo("270") == 0) {
                Picture out = input.rotate270();
                out.saveAs(args[3]);
            }
        }
        if (args[0].compareTo("flip") == 0) {
            Picture input = new Picture(args[2]);
            if (args[1].compareTo("H") == 0) {
                Picture out = input.flipHorizontal();
                out.saveAs(args[3]);
            } else if (args[1].compareTo("V") == 0) {
                Picture out = input.flipVertical();
                out.saveAs(args[3]);
            }
        }
        if (args[0].compareTo("blend") == 0) {
            List<Picture> inputs = new ArrayList<>();
            for (int i = 1; i < args.length - 1; i++) {
                inputs.add(new Picture(args[i]));
            }
            Picture out = Picture.blend(inputs);
            out.saveAs(args[args.length - 1]);
        }
        if (args[0].compareTo("blur") == 0) {
            Picture input = new Picture(args[1]);
            Picture out = input.blur();
            out.saveAs(args[2]);
        }
    }
}
