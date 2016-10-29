package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;


public class Downloader {

    public Downloader() {}

    public void savePhotoFromUrl(String photoUrl, String photoFilePath, String ext) {
        BufferedImage photo;
        try {

            URL url = new URL(photoUrl);
            photo = ImageIO.read(url);



            ImageIO.write(photo, ext, new File(photoFilePath));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
