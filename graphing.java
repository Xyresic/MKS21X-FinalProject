import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
public class graphing{


  public static void main(String[] args) {
    int width = 640;
    int height = 640;
    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    File f;
    int x;
    int y;
    int w =  (120<<24) | (255<<16) | (255<<8) | 255;
    for(x = 0;x < height; x++) {
      for (y = 0; y < width; y ++)
      img.setRGB(x, y, w);
    }
    int color =  (1<<24) | (0<<16) | (0<<8) | 0;
    y = height /2 ;
    for (x = 0; x < height; x ++) {
      img.setRGB(x, y, color);
      img.setRGB(y, x, color);
    }

    int p = (10<<24) | (255<<16) | (0<<8) | 0;
    System.out.println(p);
    y = 20;
    for(x = 0;x < height; x++) {
      img.setRGB(x, y, p);
    }
    try{
      f = new File("C:\\Users\\HP PC\\git\\MKS21X-FinalProject\\Output.png");
      ImageIO.write(img, "png", f);
    }catch(IOException e){
      System.out.println("Error: " + e);
    }
  }






}
