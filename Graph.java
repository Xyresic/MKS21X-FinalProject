import java.util.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
public class Graph {
  private static ArrayList<String> varList = new ArrayList<String>() {{
    add("x");
    add("y");
  }};
  public static double solve(ArrayList<String> input, double[] substitutes) throws FileNotFoundException { //solves for y for given x-value
    for(int i = 0; i < input.size(); i++) {
      if (input.get(i).equals("x")) {
        input.add(i,substitutes[0] + "");
        input.remove(i + 1);
      }
      if (input.get(i).equals("y")) {
        input.add(i,substitutes[1] + "");
        input.remove(i + 1);
      }
    }
    return Expression.evaluate(input);
  }
  public static double solve(String input, double[] substitutes) throws FileNotFoundException{ //string version
    ArrayList<String> sorted = Expression.shunt(input,varList);
    return solve(sorted,substitutes);
  }
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
  public static void graph(ArrayList<String> equations) throws FileNotFoundException {
    double x = 0; // x pixel value
    double y = 0; // y pixel value
    int width = 2000; // 20.00 width value
    int height = 2000; // 20.00 height value Overall it is a 10 by 10 graph
    int color =  (120<<24) | (255<<16) | (255<<8) | 255; //sets image background to white, (255, 255, 255) is white in RGB
    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); // Image type that we are going to use for Graph purposes. Not sure if there is a better version but this is the first one i found
    File f; // file that the graph is being written to
    for(x = 0;x < height; x++) { // the entire image starts black. Therefore, code is need to convert the entire screen to white for standard purposes
      for (y = 0; y < width; y ++){ // Standard Loop to get though all the values
        img.setRGB((int)x, (int)y, color);
      }
    }
    color =  (1<<24) | (0<<16) | (0<<8) | 0; //creates the axes
    y = height /2 ; // half way in the chart
    for (x = 0; x < height; x++) {
      img.setRGB((int)x, (int)y, color); // for all x values draw a line at the middle of the image
      img.setRGB((int)y, (int)x, color); // Rotated it 90 degrees. This is just a more convinet way to draw it
    }
    int equationCount = 0;
    for(String expression : equations){
      color = colorwheel(equationCount);
      ArrayList<String> variables = new ArrayList<String>();
      int equals = 0;
      int i = 0;
      while(i<expression.length()){
        if(expression.charAt(i)=='=' && equals++>0){
          throw new IllegalArgumentException("There are multiple equality signs.");
        }
        if(expression.charAt(i)>='a' && expression.charAt(i)<='z' && !variables.contains(expression.charAt(i)+"")){
          String holder = "";
          while(i<expression.length() && expression.charAt(i)>='a' && expression.charAt(i)<='z'){
            holder+=expression.charAt(i);
            i++;
          }
          if(variables.size()>=2 && holder.length()==1){
            throw new IllegalArgumentException("There are more than two variables.");
          }
          else{
            if(variables.size()==1 && holder.length()==1 && variables.get(0).compareTo(holder+"")>0){
              variables.add(0,holder+"");
            }
            else if(holder.length()==1){
              variables.add(holder+"");
            }
          }
        }
        else{
          i++;
        }
      }
      if(variables.size()==2 && equals==0){
        throw new IllegalArgumentException("This is not an equation.");
      }
      String converted = "";
      for(i = 0; i<expression.length(); i++){
        if(expression.charAt(i)==variables.get(0).charAt(0)){
          converted+='x';
        }
        else if(variables.size()>1 && expression.charAt(i)==variables.get(1).charAt(0)){
          converted+='y';
        }
        else{
          converted+=expression.charAt(i);
        }
      }
      converted = converted.trim().replaceAll("\\s","");
      ArrayList<String> split = new ArrayList<String>();
      for(String half : converted.split("=")){
        split.add(half);
      }
      double only = 0;
      double tempx = 0; // Saves a temporary x value to be used for math purposes
      double tempy = 0; // Saves a temporary x value to be used for math purposes
      ArrayList<String> copy; // makes a copy because our current code deletes the values in the ArrayList. This is because the x value keeps on getting replaced
      int oldvalue = 0;
      int dif = 0;
      if(variables.size()==1 || split.contains("y")){
        boolean asymptote = false;
        for(x = 0; x < width;x += 1) {
          dif = 0;
          copy = Expression.shunt(converted.replaceAll("y=","").replaceAll("=y",""),varList); // every time it needs to reset the value
          tempx = (x) / 100 - 10; // convert standard double x to pixel value
          tempy = solve(copy,new double[]{tempx}); // returns standard double y by using x value and substituting it into the function
          if(Double.isNaN(tempy)){ // converts standard double y to pixel value
            y = Double.NaN;
          } else {
            y = 100 * (10 - (tempy));
          }
          if(y>2000 && oldvalue<2000 && oldvalue>0 || y<0 && oldvalue>0 && oldvalue<2000){
            asymptote = true;
          }
          if(oldvalue>2000 || oldvalue<0){
            asymptote = false;
          }
          if (asymptote || y < 2000 && y > 0) { // if the y is within the range of the graph
            if(y < 2000 && y > 0){
              img.setRGB((int)x, (int)y, color);
            }
            y=(double)(int)y;
            while(x > 0 && oldvalue + dif != y) {
              if (y > oldvalue) {dif += 1;}
              else {dif -= 1;}
              //System.out.println(tempx+","+tempy+","+y+","+oldvalue+","+dif);
              if (y - dif < 2000 && y - dif > 0) {
                //System.out.println(y+","+oldvalue+","+dif);
                img.setRGB((int)x, (int)y - dif, color); // sets the value to color red
              }
            }
            if (y == 1000 && only <= 0) {
              System.out.println("The root is " + tempx);
              only = 1;
            }
            only -= .01;
          }
          if(Double.isNaN(y)){
            copy = Expression.shunt(converted.replaceAll("y=","").replaceAll("=y",""),varList);
            oldvalue = (int)(100 * (10 - (solve(copy,new double[]{(x+1) / 100 - 10}))));
          } else{
            oldvalue = (int)y;
          }
        }
      }
      else{
        ArrayList<String> sortedLeft = Expression.shunt(split.get(0),varList);
        ArrayList<String> sortedRight = Expression.shunt(split.get(1),varList);
        for(int xcor = 0; xcor < width; xcor+=1) {
          for(int ycor = 0; ycor < height; ycor+=1){
            tempx = xcor / 100.0 - 10;
            tempy = 10 - ycor / 100.0;
            ArrayList<String> copyLeft = new ArrayList<String>(sortedLeft);
            ArrayList<String> copyRight = new ArrayList<String>(sortedRight);
            double[] xy = new double[2];
            xy[0]=tempx;
            xy[1]=tempy;
            double difference = solve(copyLeft,xy)-solve(copyRight,xy);
            if(difference>=-0.01 && difference<=0.01){
              img.setRGB(xcor,ycor,color);
            }
          }
        }
      }
      equationCount++;
    }
    try{ //create image file with graph
      f = new File("Graph.png"); // names file Graph.png
      ImageIO.write(img, "png", f);
    }catch(IOException e){
      System.out.println("Error: " + e);
    }
  }
}
