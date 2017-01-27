import java.util.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class CoverImage extends JPanel{

    private BufferedImage image;

    public CoverImage() {
       try {                
          image = ImageIO.read(new File("fruitset.jpg"));
       } catch (IOException ex) {
            System.out.println("file does not exist");
       }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters            
    }

}
