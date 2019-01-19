import java.util.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
public class Graph {
  public static int colorwheel(int x) {
    if (x % 7 ==0) { //red
      return  (10<<24) | (255<<16) | (0<<8) | 0;
    }
    if (x % 7 ==1) { //orange
      return  (10<<24) | (255<<16) | (99<<8) | 0;
    }
    if (x % 7 ==2) { //yellow
      return  (18<<24) | (255<<16) | (215<<8) | 20;
    }
    if (x % 7 ==3) {
      return  (10<<24) | (0<<16) | (255<<8) | 0;
    }
    if (x % 7 ==4) {
      return  (10<<24) | (0<<16) | (0<<8) | 255;
    }
    if (x % 7 ==5) {
      return  (10<<24) | (153<<16) | (153<<8) | 255;
    }
      return  (10<<24) | (153<<16) | (0<<8) | 153;
  }











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
    double tempx = 0; // Saves a temporary x value to be used for math purposes
    double tempy = 0; // Saves a temporary x value to be used for math purposes
    ArrayList<String> copy; // makes a copy because our current code deletes the values in the ArrayList. This is because the x value keeps on getting replaced
    int oldvalue = 0;;
    int dif = 0;
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

    for(x = 0; x < width;x += .01) {
      dif = 0;
      copy = new ArrayList<String>(sorted); // every time it needs to reset the value
      tempx = (x) / 100 - 10; // convert standard double x to pixel value
      tempy = solve(copy,tempx); // returns standard double y by using x value and substituting it into the function
      y = (int)(100 * (10 - (tempy))); // converts standard double y to pixel value
      if (y < 2000 && y > 0) { // if the y is within the range of the graph
        while(x > 0 && oldvalue + dif != y) {
          if (y > oldvalue) {dif += 1;}
          else {dif -= 1;}
        img.setRGB((int)x, y - dif, color); // sets the value to color red
      }
        if (y == 1000 && only <= 0) {
          System.out.println("The root is " + tempx);
          only = 1;
        }
        only -= .01;
      }
      oldvalue = y;
    }
    try{ //create image file with graph
      f = new File("Graph.png"); // names file Graph.png
      ImageIO.write(img, "png", f);
    }catch(IOException e){
      System.out.println("Error: " + e);
    }
  }

  public static void graph(ArrayList<String> expressionList) throws FileNotFoundException {
    // this thing breaks more things than it solves
    char variable = 'x';
    //for(int i = 0; i< expression.length(); i++){
    //  if(Character.isLetter(expression.charAt(i))){
    //    variable = expression.charAt(i);
    //  }
    //}
    ArrayList<String> sorted;
    double x = 0; // x pixel value
    int y = 0; // y pixel value
    int width = 2000; // 20.00 width value
    int height = 2000; // 20.00 height value Overall it is a 10 by 10 graph
    double only = 0;
    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); // Image type that we are going to use for Graph purposes. Not sure if there is a better version but this is the first one i found
    File f; // file that the graph is being written to
    int color =  (120<<24) | (255<<16) | (255<<8) | 255; //sets image background to white, (255, 255, 255) is white in RGB
    double tempx = 0; // Saves a temporary x value to be used for math purposes
    double tempy = 0; // Saves a temporary x value to be used for math purposes
    ArrayList<String> copy; // makes a copy because our current code deletes the values in the ArrayList. This is because the x value keeps on getting replaced
    ArrayList<String> roots = new ArrayList<String>();
    ArrayList<String> troots;
    int oldvalue = 0;
    int dif = 0;
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

    for (int i =0; i < expressionList.size(); i++) {
       color = colorwheel(i);
       oldvalue = 0;
       dif = 0;
       troots = new ArrayList<String>();
       sorted = Expression.shunt(expressionList.get(i),variable);
       for(x = 0; x < width;x += .01) {
         dif = 0;
         copy = new ArrayList<String>(sorted); // every time it needs to reset the value
         tempx = (x) / 100 - 10; // convert standard double x to pixel value
         tempy = solve(copy,tempx); // returns standard double y by using x value and substituting it into the function
         y = (int)(100 * (10 - (tempy))); // converts standard double y to pixel value
         if (y < 2000 && y > 0) { // if the y is within the range of the graph
           while(x > 0 && oldvalue + dif != y) {
             if (y > oldvalue) {dif += 1;}
             else {dif -= 1;}
             if (y - dif < 2000 && y - dif > 0) {
           img.setRGB((int)x, y - dif, color); // sets the value to color red
         }
       }
           if (y == 1000 && only <= 0) {
             troots.add(tempx + "");
             only = 1;
           }
           only -= .01;
         }
         oldvalue = y;
       }
       roots.add("The roots of " + expressionList.get(i) + " are: " + troots);
     }
       try{ //create image file with graph
         f = new File("Graph.png"); // names file Graph.png
         ImageIO.write(img, "png", f);
       }catch(IOException e){
         System.out.println("Error: " + e);
       }
       for (int i =0; i < roots.size(); i++) {
       System.out.println(roots.get(i));
     }

    }





































}
