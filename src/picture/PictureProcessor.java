package picture;

import java.util.ArrayList;
import java.util.List;

public class PictureProcessor {
  public static void main(String[] args) {
    switch (args[0]) {
      case "invert" -> {
        Picture input = new Picture(args[1]);
        Picture out = input.invert();
        out.saveAs(args[2]);
      }
      case "grayscale" -> {
        Picture input = new Picture(args[1]);
        Picture out = input.grayscale();
        out.saveAs(args[2]);
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
          default -> {
            System.out.println("Rotations can only be a multiple of 90.");
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
          default -> {
            System.out.println("Format: flip [H/V] input output");
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
      case "mosaic" -> {
        int tileSize = Integer.parseInt(args[1]);
        List<Picture> inputs = new ArrayList<>();
        for (int i = 2; i < args.length - 1; i++) {
          inputs.add(new Picture(args[i]));
        }
        Picture out = Picture.mosaic(inputs, tileSize);
        out.saveAs(args[args.length - 1]);
      }
      default -> {
        System.out.println("Incorrect format");
      }
    }
  }
}
