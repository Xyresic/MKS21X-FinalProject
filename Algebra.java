import java.util.*;
import java.io.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
public class Algebra {
  public static double solve(ArrayList<String> input, double x) throws FileNotFoundException {
    for(int i = 0; i < input.size(); i++) {
      if (input.get(i) == "x") {
        input.add(i,x + "");
        input.remove(i + 1);

      }
    }
    return Expression.evaluate(input);
  }
  public static double solve(String input, double x) throws FileNotFoundException{
    ArrayList<String> sorted = Expression.shunt(input);
    return solve(input,x);
  }
  public static void graph(String expression) throws FileNotFoundException {
  ArrayList<String> sorted = Expression.shunt(expression);
  System.out.println(sorted);
  int x = 0;
  int y = 0;
  int width = 2000;
  int height = 2000;
  BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
  File f = null;
  // sets screen white
  int color =  (120<<24) | (255<<16) | (255<<8) | 255;
  for(x = 0;x < height; x++) {
    for (y = 0; y < width; y ++)
    img.setRGB(x, y, color);
  }
  // creates a cross
  color =  (1<<24) | (0<<16) | (0<<8) | 0;
  y = height /2 ;
  for (x = 0; x < height; x ++) {
    img.setRGB(x, y, color);
    img.setRGB(y, x, color);
  }
  // draws the function
  color = (10<<24) | (255<<16) | (0<<8) | 0;
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
  try{
    f = new File("Output.png");
    ImageIO.write(img, "png", f);
  }catch(IOException e){
    System.out.println("Error: " + e);
  }



  }
}
