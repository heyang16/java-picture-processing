package picture;

import java.util.ArrayList;
import java.util.List;

public class PictureProcessor {
    public static void main(String[] args) {
        switch (args[0]) {
            case "invert" -> {
                Picture input = new Picture(args[1]);
                input.invert();
                input.saveAs(args[2]);
            }
            case "grayscale" -> {
                Picture input = new Picture(args[1]);
                input.grayscale();
                input.saveAs(args[2]);
            }
            case "rotate" -> {
                Picture input = new Picture(args[2]);
                switch (args[1]) {
                    case "90" -> {
                        Picture out = input.rotate90();
                        out.saveAs(args[3]);
                    }
                    case "180" -> {
                        Picture out = input.rotate180();
                        out.saveAs(args[3]);
                    }
                    case "270" -> {
                        Picture out = input.rotate270();
                        out.saveAs(args[3]);
                    }
                }
            }
            case "flip" -> {
                Picture input = new Picture(args[2]);
                switch (args[1]) {
                    case "H" -> {
                        Picture out = input.flipHorizontal();
                        out.saveAs(args[3]);
                    }
                    case "V" -> {
                        Picture out = input.flipVertical();
                        out.saveAs(args[3]);
                    }
                }
            }
            case "blend" -> {
                List<Picture> inputs = new ArrayList<>();
                for (int i = 1; i < args.length - 1; i++) {
                    inputs.add(new Picture(args[i]));
                }
                Picture out = Picture.blend(inputs);
                out.saveAs(args[args.length - 1]);
            }
            case "blur" -> {
                Picture input = new Picture(args[1]);
                Picture out = input.blur();
                out.saveAs(args[2]);
            }
        }
    }
}
