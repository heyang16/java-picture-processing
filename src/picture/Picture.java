package picture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * A class that encapsulates and provides a simplified interface for
 * manipulating an image. The internal representation of the image is based on
 * the RGB direct colour model.
 */
public class Picture {

    /**
     * The internal image representation of this picture.
     */
    private final BufferedImage image;

    /**
     * Construct a new (blank) Picture object with the specified width and
     * height.
     */
    public Picture(int width, int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    /**
     * Construct a new Picture from the image data in the specified file.
     */
    public Picture(String filepath) {
        try {
            image = ImageIO.read(new File(filepath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Test if the specified point lies within the boundaries of this picture.
     *
     * @param x the x co-ordinate of the point
     * @param y the y co-ordinate of the point
     * @return <tt>true</tt> if the point lies within the boundaries of the
     * picture, <tt>false</tt> otherwise.
     */
    public boolean contains(int x, int y) {
        return x >= 0 && y >= 0 && x < getWidth() && y < getHeight();
    }

    /**
     * Returns true if this Picture is graphically identical to the other one.
     *
     * @param other The other picture to compare to.
     * @return true iff this Picture is graphically identical to other.
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (!(other instanceof Picture)) {
            return false;
        }

        Picture otherPic = (Picture) other;

        if (image == null || otherPic.image == null) {
            return image == otherPic.image;
        }
        if (image.getWidth() != otherPic.image.getWidth()
                || image.getHeight() != otherPic.image.getHeight()) {
            return false;
        }

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                if (image.getRGB(i, j) != otherPic.image.getRGB(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Return the height of the <tt>Picture</tt>.
     *
     * @return the height of this <tt>Picture</tt>.
     */
    public int getHeight() {
        return image.getHeight();
    }

    /**
     * Return the colour components (red, green, then blue) of the pixel-value located at (x,y).
     *
     * @param x x-coordinate of the pixel value to return
     * @param y y-coordinate of the pixel value to return
     * @return the RGB components of the pixel-value located at (x,y).
     * @throws ArrayIndexOutOfBoundsException if the specified pixel-location is not contained within
     *                                        the boundaries of this picture.
     */
    public Color getPixel(int x, int y) {
        int rgb = image.getRGB(x, y);
        return new Color((rgb >> 16) & 0xff, (rgb >> 8) & 0xff, rgb & 0xff);
    }

    /**
     * Return the width of the <tt>Picture</tt>.
     *
     * @return the width of this <tt>Picture</tt>.
     */
    public int getWidth() {
        return image.getWidth();
    }

    @Override
    public int hashCode() {
        if (image == null) {
            return -1;
        }
        int hashCode = 0;
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                hashCode = 31 * hashCode + image.getRGB(i, j);
            }
        }
        return hashCode;
    }

    public void saveAs(String filepath) {
        try {
            ImageIO.write(image, "png", new File(filepath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Update the pixel-value at the specified location.
     *
     * @param x   the x-coordinate of the pixel to be updated
     * @param y   the y-coordinate of the pixel to be updated
     * @param rgb the RGB components of the updated pixel-value
     * @throws ArrayIndexOutOfBoundsException if the specified pixel-location is not contained within
     *                                        the boundaries of this picture.
     */
    public void setPixel(int x, int y, Color rgb) {

        image.setRGB(
                x,
                y,
                0xff000000
                        | (((0xff & rgb.getRed()) << 16)
                        | ((0xff & rgb.getGreen()) << 8)
                        | (0xff & rgb.getBlue())));
    }

    /**
     * Returns a String representation of the RGB components of the picture.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                Color rgb = getPixel(x, y);
                sb.append("(");
                sb.append(rgb.getRed());
                sb.append(",");
                sb.append(rgb.getGreen());
                sb.append(",");
                sb.append(rgb.getBlue());
                sb.append(")");
            }
            sb.append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }

    private static List<Integer> getMinimumDimensions(List<Picture> pictures) {
        // Returns the smallest minimum width and height of a list of pictures
        // in the form [minWidth, minHeight].
        int minWidth = pictures.get(0).getWidth();
        int minHeight = pictures.get(0).getHeight();
        for (Picture picture : pictures) {
            int width = picture.getWidth();
            if (width < minWidth) {
                minWidth = width;
            }
            int height = picture.getHeight();
            if (height < minHeight) {
                minHeight = height;
            }
        }
        return List.of(minWidth, minHeight);
    }

    public void invert() {
        // Inverts the color of the picture
        int newRed, newGreen, newBlue;
        Color originalPixel;
        for (int x = 0; x < this.getWidth(); x++) {
            for (int y = 0; y < this.getHeight(); y++) {
                originalPixel = this.getPixel(x, y);
                // Inverts the red, blue and green components
                newRed = 255 - originalPixel.getRed();
                newGreen = 255 - originalPixel.getGreen();
                newBlue = 255 - originalPixel.getBlue();
                // Modifies the color values of the current pixel
                this.setPixel(x, y, new Color(newRed, newGreen, newBlue));
            }
        }
    }

    public void grayscale() {
        // Converts the picture to grayscale by averaging color values
        for (int x = 0; x < this.getWidth(); x++) {
            for (int y = 0; y < this.getHeight(); y++) {
                Color originalPixel = this.getPixel(x, y);
                int avg =
                        (originalPixel.getBlue() + originalPixel.getGreen() +
                                originalPixel.getRed()) / 3;
                this.setPixel(x, y, new Color(avg, avg, avg));
            }
        }
    }

    public Picture rotate90() {
        // Rotates the picture 90 degrees clockwise
        // Returns a picture after rotation
        int width = this.getWidth();
        int height = this.getHeight();
        Picture out = new Picture(height, width);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                out.setPixel(height - y - 1, x, this.getPixel(x, y));
            }
        }
        return out;
    }

    public Picture rotate180() {
        // Rotates the picture 180 degrees clockwise
        // Returns a picture after rotation
        int width = this.getWidth();
        int height = this.getHeight();
        Picture out = new Picture(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                out.setPixel(x, y, this.getPixel(width - x - 1,
                        height - y - 1));
            }
        }
        return out;
    }

    public Picture rotate270() {
        // Rotates the picture 270 degrees clockwise
        // Returns a picture after rotation
        int width = this.getWidth();
        int height = this.getHeight();
        Picture out = new Picture(height, width);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                out.setPixel(y, width - x - 1, this.getPixel(x, y));
            }
        }
        return out;
    }

    public Picture flipHorizontal() {
        // Flips the picture horizontally
        int width = this.getWidth();
        int height = this.getHeight();
        Picture out = new Picture(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                out.setPixel(x, y, this.getPixel(width - x - 1, y));
            }
        }
        return out;
    }

    public Picture flipVertical() {
        // Flips the picture vertically
        int width = this.getWidth();
        int height = this.getHeight();
        Picture out = new Picture(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                out.setPixel(x, y, this.getPixel(x, height - y - 1));
            }
        }
        return out;
    }

    public static Picture blend(List<Picture> inputs) {
        // Blends all the pictures in a list of pictures by getting the average
        // pixel values.

        // Gets the dimensions of the final picture by finding the minimum
        // height and width.
        List<Integer> minDims = getMinimumDimensions(inputs);
        int minWidth = minDims.get(0);
        int minHeight = minDims.get(1);
        int size = inputs.size();
        // Creates output picture
        Picture out = new Picture(minWidth, minHeight);
        int totalRed, totalGreen, totalBlue;
        Color pixel;
        for (int x = 0; x < minWidth; x++) {
            for (int y = 0; y < minHeight; y++) {
                totalRed = 0;
                totalGreen = 0;
                totalBlue = 0;
                for (Picture picture : inputs) {
                    pixel = picture.getPixel(x, y);
                    totalRed += pixel.getRed();
                    totalGreen += pixel.getGreen();
                    totalBlue += pixel.getBlue();
                }
                Color avg = new Color(totalRed / size,
                        totalGreen / size, totalBlue / size);
                out.setPixel(x, y, avg);
            }
        }
        return out;
    }

    public Picture blur() {
        // Blurs the picture
        int width = this.getWidth();
        int height = this.getHeight();
        Picture out = new Picture(width, height);
        int totalRed, totalGreen, totalBlue;
        Color pixel;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // Checks if pixel is at the border
                if (x > 0 && x < width - 1 && y > 0 && y < height - 1) {
                    totalRed = 0;
                    totalGreen = 0;
                    totalBlue = 0;
                    // Computes the average pixel values of neighbouring pixels
                    // including the pixel itself
                    for (int dx = -1; dx <= 1; dx++) {
                        for (int dy = -1; dy <= 1; dy++) {
                            pixel = this.getPixel(x + dx, y + dy);
                            totalRed += pixel.getRed();
                            totalGreen += pixel.getGreen();
                            totalBlue += pixel.getBlue();
                        }
                    }
                    Color avg = new Color(totalRed / 9,
                            totalGreen / 9, totalBlue / 9);
                    // Sets the pixel color to the average
                    out.setPixel(x, y, avg);
                } else { // Pixel at the borders are unchanged
                    out.setPixel(x, y, this.getPixel(x, y));
                }
            }
        }
        return out;
    }

//    public Picture mosaic(List<Picture> inputs, int tileSize) {
//        // Creates a mosaic from a list of pictures
//        // Obtains output dimensions
//        List<Integer> minDims = getMinimumDimensions(inputs);
//        int minWidth = minDims.get(0);
//        int minHeight = minDims.get(1);
//        int size = inputs.size();
//    }
}
