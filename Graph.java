import java.util.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
public class Graph {
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
    char variable = 'x';
    for(int i = 0; i<input.length(); i++){
      if(Character.isLetter(input.charAt(i))){
        variable = input.charAt(i);
      }
    }
    ArrayList<String> sorted = Expression.shunt(input,variable);
    return solve(input,x);
  }
  public static void graph(String expression) throws FileNotFoundException {
    char variable = 'x';
    for(int i = 0; i<expression.length(); i++){
      if(Character.isLetter(expression.charAt(i))){
        variable = expression.charAt(i);
      }
    }
    ArrayList<String> sorted = Expression.shunt(expression,variable);
    double x = 0; // x pixel value
    int y = 0; // y pixel value
    int width = 2000; // 20.00 width value
    int height = 2000; // 20.00 height value Overall it is a 10 by 10 graph
    double only = 0;
    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); // Image type that we are going to use for Graph purposes. Not sure if there is a better version but this is the first one i found
    File f; // file that the graph is being written to
    int color =  (120<<24) | (255<<16) | (255<<8) | 255; //sets image background to white, (255, 255, 255) is white in RGB
    for(x = 0;x < height; x++) { // the entire image starts black. Therefore, code is need to convert the entire screen to white for standard purposes
      for (y = 0; y < width; y ++){ // Standard Loop to get though all the values
        img.setRGB((int)x, y, color);
      }
    }
    color =  (1<<24) | (0<<16) | (0<<8) | 0; //creates the axes
    y = height /2 ; // half way in the chart
    for (x = 0; x < height; x++) {
      img.setRGB((int)x, y, color); // for all x values draw a line at the middle of the image
      img.setRGB(y, (int)x, color); // Rotated it 90 degrees. This is just a more convinet way to draw it
    }
    color = (10<<24) | (255<<16) | (0<<8) | 0; //draws the function by evaluating for values of x. Color is red based on RGB. Might add new colors to the spectrum later
    double tempx = 0; // Saves a temporary x value to be used for math purposes
    double tempy = 0; // Saves a temporary x value to be used for math purposes

    ArrayList<String> copy; // makes a copy because our current code deletes the values in the ArrayList. This is because the x value keeps on getting replaced
    for(x = 0; x < width;x += .01) {
      copy = new ArrayList<String>(sorted); // every time it needs to reset the value
      tempx = (x) / 100 - 10; // convert standard double x to pixel value
      tempy = solve(copy,tempx); // returns standard double y by using x value and substituting it into the function
      y = (int)(100 * (10 - (tempy))); // converts standard double y to pixel value
      if (y < 2000 && y > 0) { // if the y is within the range of the graph
        img.setRGB((int)x, y, color); // sets the value to color red
        if (y == 1000 && only <= 0) {
          System.out.println("The root is " + tempx);
          only = 1;
        }
        only -= .01;
      }
    }
    try{ //create image file with graph
      f = new File("Graph.png"); // names file Graph.png
      ImageIO.write(img, "png", f);
    }catch(IOException e){
      System.out.println("Error: " + e);
    }
  }
}
