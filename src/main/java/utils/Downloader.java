package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;


public class Downloader {

    public Downloader() {}

    public void savePhotoFromUrl(String folderName, String photoUrl) {
        BufferedImage photo;
        try {

            URL url = new URL(photoUrl);
            photo = ImageIO.read(url);

            String [] splitPhotoUrl = photoUrl.split("/");
            // Take the last part of the split
            String photoFileName = splitPhotoUrl[splitPhotoUrl.length-1];
            String ext = photoFileName.substring(photoFileName.length()-3, photoFileName.length());

            ImageIO.write(photo, ext, new File(folderName + "/" + photoFileName));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
