import java.util.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
public class Algebra {
  public static double solve(ArrayList<String> input, double substitute) throws FileNotFoundException { //solves for y for given x-value
    for(int i = 0; i < input.size(); i++) {
      if (input.get(i).equals("x")) {
        input.add(i,substitute + "");
        input.remove(i + 1);
      }
    }
    return Expression.evaluate(input);
  }
  public static double solve(String input, double x) throws FileNotFoundException{ //string version
    ArrayList<String> sorted = Expression.shunt(input);
    return solve(input,x);
  }
  public static void graph(ArrayList<String> sorted) throws FileNotFoundException { //creates an image file of the given equation
    int x = 0;
    int y = 0;
    int width = 2000;
    int height = 2000;
    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    File f;
    int color =  (120<<24) | (255<<16) | (255<<8) | 255; //sets image background to white
    for(x;x < height; x++) {
      for (y; y < width; y++)
      img.setRGB(x, y, color);
    }
    color =  (1<<24) | (0<<16) | (0<<8) | 0; //creates the axes
    y = height /2 ;
    for (x = 0; x < height; x++) {
      img.setRGB(x, y, color);
      img.setRGB(y, x, color);
    }
    color = (10<<24) | (255<<16) | (0<<8) | 0; //draws the function by evaluating for values of x 
    double tempx = 0;
    double tempy = 0;
    ArrayList<String> copy;
    for(x = 0; x < width; x++) {
      copy = new ArrayList<String>(sorted);
      tempx = ((double)x) / 100 - 10;
      tempy = solve(copy,tempx);
      y = (int)(100 * (10 - (tempy)));
      if (y < 2000 && y > 0) {
        img.setRGB(x, y, color);
      }
    }
    try{ //create image file with graph
      f = new File("Output.png");
      ImageIO.write(img, "png", f);
    }catch(IOException e){
      System.out.println("Error: " + e);
    }
  }
}
