import java.util.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.text.*;
public class Graph {
  private static ArrayList<String> varList = new ArrayList<String>() {{ //list of accepted variables when graphing
    add("x");
    add("y");
  }};
  public static double solve(ArrayList<String> input, double[] substitutes) throws FileNotFoundException { //substitutes values into variables and solves
    for(int i = 0; i < input.size(); i++) { //iterates through input
      if (input.get(i).equals("x")) { //if there is an x, substitute the first value in the list
        input.add(i,substitutes[0] + "");
        input.remove(i + 1);
      }
      if (input.get(i).equals("y")) { //if there is a y, substitute the second value in the list
        input.add(i,substitutes[1] + "");
        input.remove(i + 1);
      }
    }
    return Expression.evaluate(input); //return the evaluated value after substituting
  }
  public static double solve(String input, double[] substitutes) throws FileNotFoundException{ //string version
    ArrayList<String> sorted = Expression.shunt(input,varList); //shunt the string
    return solve(sorted,substitutes);
  }
  public static int colorwheel(int x) { //return a color in order of the rainbow, used to make graphing multiple functions more exciting/easier to differentiate
    if (x % 6 ==0) { //red
      return  (10<<24) | (255<<16) | (0<<8) | 0;
    }
    if (x % 6 ==1) { //orange
      return  (10<<24) | (255<<16) | (99<<8) | 0;
    }
    if (x % 6 ==2) { //yellow
      return  (18<<24) | (255<<16) | (215<<8) | 20;
    }
    if (x % 6 ==3) { //green
      return  (10<<24) | (0<<16) | (255<<8) | 0;
    }
    if (x % 6 ==4) { //blue
      return  (10<<24) | (0<<16) | (0<<8) | 255;
    }
    return  (10<<24) | (153<<16) | (0<<8) | 153; //purple
  }
  public static void graph(ArrayList<String> equations) throws FileNotFoundException { //the meat of the graphing
    double x = 0; // x pixel value
    double y = 0; // y pixel value
    int width = 2000; // width of image in pixels
    int height = 2000; // height of image in pixels, the graph goes from -10 to 10 in both the x an y direction numerically
    int color =  (120<<24) | (255<<16) | (255<<8) | 255; //color of what is being drawn, currently white to set the background
    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); // image of the graph
    File f; // file that the graph is being written to
    for(x = 0;x < height; x++) { // sets background to white, starts black
      for (y = 0; y < width; y ++){ //loops through each pixel
        img.setRGB((int)x, (int)y, color);
      }
    }
    color =  (1<<24) | (0<<16) | (0<<8) | 0; //set color to black for the axes
    y = height /2 ; //x-axis is in the middle of the image
    for (x = 0; x < height; x++) {
      img.setRGB((int)x, (int)y, color); // for all x values draw a point at the middle of the image, creating the x-axis
      img.setRGB((int)y, (int)x, color); // also draw the axis rotated 90 degrees clockwise, creating the y-axis
    }
    int equationCount = 0; //counts number of equations for color purposes
    for(String expression : equations){ //loop through list of equations
      color = colorwheel(equationCount); //change color accordingly
      ArrayList<String> variables = new ArrayList<String>(); //used to store variables used in case they're not x and y
      int equals = 0; //count number of equality signs
      int i = 0;
      while(i<expression.length()){ //loop through current equation
        if(expression.charAt(i)=='=' && equals++>0){ //if there are more than one equality signs, throw an error
          throw new IllegalArgumentException("There are multiple equality signs.");
        }
        if(expression.charAt(i)>='a' && expression.charAt(i)<='z' && !variables.contains(expression.charAt(i)+"")){ //if a lowercase letter is found...
          String holder = ""; //create a holder to store current variable/function
          while(i<expression.length() && expression.charAt(i)>='a' && expression.charAt(i)<='z'){ //...add subsequent lowercase letters
            holder+=expression.charAt(i);
            i++;
          }
          if(variables.size()>=2 && holder.length()==1){ //if more than two variables are detected, throw an error
            throw new IllegalArgumentException("There are more than two variables.");
          }
          else{ //otherwise, add the variable to the list
            if(variables.size()==1 && holder.length()==1 && variables.get(0).compareTo(holder+"")>0){ //add the variable that is alphabetically first to the first index
              variables.add(0,holder+"");
            }
            else if(holder.length()==1){ //add the other variable
              variables.add(holder+"");
            }
          }
        }
        else{ //if the holder contains a function and not a variable, continue
          i++;
        }
      }
      if(variables.size()==2 && equals==0){ //if there are two variables and no equality sign, then it's not an equation and can't be graphed
        throw new IllegalArgumentException("This is not an equation.");
      }
      String converted = ""; //converts expresssion to be in terms of x and y in case other variables are used
      for(i = 0; i<expression.length(); i++){ //loop through expression
        if(expression.charAt(i)>='a' && expression.charAt(i)<='z'){ //if a letter is found, add subsequent letters as usual
          String holder = "";
          while(i<expression.length() && expression.charAt(i)>='a' && expression.charAt(i)<='z'){
            holder+=expression.charAt(i);
            i++;
          }
          if(holder.equals("y") && variables.size()==1){ //if y is the only variables, add y instead of converting it to x
            converted+='y';
          }
          else if(holder.length()==1 && holder.charAt(0)==variables.get(0).charAt(0)){ //otherwise, if the variable is alphabetically first, convert it to x
            converted+='x';
          }
          else if(variables.size()>1 && holder.length()==1 && holder.charAt(0)==variables.get(1).charAt(0)){ //otherwise, if the variable matches the second, convert it to y
            converted+='y';
          }
          else{ //otherwise, add holder (which is a function) to the string
            converted+=holder;
          }
          i--; //subtract one from i as i moves past end of function/variable
        }
        else{
          converted+=expression.charAt(i); //if it's not a letter, just tack it on
        }
      }
      converted = converted.trim().replaceAll("\\s",""); //removes whitespace
      ArrayList<String> split = new ArrayList<String>(); //stores each half of the equation
      for(String half : converted.split("=")){
        split.add(half);
      }
      double tempx = 0; // temporary x for calculations
      double tempy = 0; // temporary y for calculations
      ArrayList<String> copy; // copy of the expression as solve() alters it
      int oldValue = 0; //previous y value, used to make grpahs look more connected and smoother
      int dif = 0; //difference between oldValue and current calculated value
      if(variables.size()==1 && !expression.contains("=") || split.contains("y") || split.contains("x")){ //if the expression is a function
        boolean asymptote = false; //detects if there's some kind of discontinuity, used to make graphs with discontinuities more accurate
        for(x = 0; x < width;x += 1) { //loop through every x-value
          dif = 0; //reset difference
          if(split.contains("y")){ //if this function is of the form y=f(x), remove the y and equal sign and shunt it
            copy = Expression.shunt(converted.replaceAll("y=","").replaceAll("=y",""),varList);
          }
          else{ //if this function is of the form x=f(y), remove the x and equal sign and shunt it
            copy = Expression.shunt(converted.replaceAll("x=","").replaceAll("=x","").replaceAll("y","x"),varList);
          }
          tempx = x/100-10; // convert pixel x to numerical value
          DecimalFormat rounder = new DecimalFormat("#.##"); //used to round to two decimal places, java floating point arithmetic is not entirely accurate
          tempx = Double.parseDouble(rounder.format(tempx)); //round
          tempy = solve(copy,new double[]{tempx}); // calculate the numerical y value
          if(Double.isNaN(tempy)){ // if the calculated value was not a number, the pixel value also becomes not a number
            y = Double.NaN;
          } else {
            y = Math.round(100 * (10 - (tempy))); //convert numerical value to pixel value
          }
          if(y>2000 && oldValue<2000 && oldValue>0 || y<0 && oldValue>0 && oldValue<2000){ //if the calculated value goes beyond the border, set asymptote to true
            asymptote = true;
          }
          if(oldValue>2000 || oldValue<0){ //if the previous value was also beyond the border, reset asymptote
            asymptote = false;
          }
          if (asymptote || y < 2000 && y > 0) { // if the y is within the range of the graph or it's the first y value before the graph goes beyond the border
            if(y < 2000 && y > 0){ //draw the point if y is inside borders
              if(split.contains("x")){ //if the expression is a function of y, draw the point with x and y coordinates switched
                img.setRGB((int)y, (int)x, color);
              }
              else{ //otherwise, use normal coordinates
                img.setRGB((int)x, (int)y, color);
              }
            }
            if(!expression.contains("floor") && !expression.contains("ceil")){ //if the expression isn't one that contains jump discontinuities without indeterminate forms
              y = (double)(int)y; //round y to an integer
              while(x > 0 && oldValue + dif != y) { //while oldValue has not caught up with current value
                if (y > oldValue) {dif += 1;} //add one to difference if current is above old, subtract one if below
                else {dif -= 1;}
                if (y - dif < 2000 && y - dif > 0) { //if this new point is in the borders
                  if(split.contains("x")){ //if function of y, switch x and y and draw point
                    img.setRGB((int)y - dif, (int)x, color);
                  }
                  else{ //otherwise, draw point normally
                    img.setRGB((int)x, (int)y - dif, color); // sets the value to color red
                  } //this connects the points of the graph in the case where the derivative is too high/low, making the graph look dotted/dashed instead of continuous
                }
              }
            }
          }
          if(Double.isNaN(y)){ //if there's a discontinuity
            copy = Expression.shunt(converted.replaceAll("y=","").replaceAll("=y",""),varList); //reset copy
            oldValue = (int)(100 * (10 - (solve(copy,new double[]{(x+1) / 100 - 10})))); //set oldValue to the value calculated from the next x value
          } else{ //otherwise, set oldValue to current y value
            oldValue = (int)y;
          } //doing this makes graphs with discontinuities not connect where they shouldn't
        }
      } else { //if the expression is a relation
        int[][] points = new int[2000][2000]; //array to store whenever there is a point, used to make graphs look more connected
        ArrayList<ArrayList<Integer>> xgaps = new ArrayList<ArrayList<Integer>>(); //list of gaps horizontally
        ArrayList<ArrayList<Integer>> ygaps = new ArrayList<ArrayList<Integer>>(); //list of gaps vertically
        ArrayList<Integer> start = new ArrayList<Integer>(); //endpoints of initial gap (everything)
        start.add(0);
        start.add(2000);
        xgaps.add(start); //add initial gap to both gap lists
        ygaps.add(start);
        boolean fillX = true; //used to toggle between fillling horizontal gaps and vertical gaps
        double cutoff = 0.01; //cutoff for point status, necessary because floating point arithmetic is not exact
        ArrayList<String> sortedLeft = Expression.shunt(split.get(0),varList); //shunted left side of the equation
        ArrayList<String> sortedRight = Expression.shunt(split.get(1),varList); //shunted right side of the equation
        while(cutoff<0.1){ //while cutoff is not too high
          for(int index = 0; index < (fillX? xgaps.size():ygaps.size()); index++){ //for each gap
            for(int xcor = xgaps.get(fillX? index:0).get(0); xcor < xgaps.get(fillX? index:0).get(1); xcor+=1) { //loop through the points in the gap
              for(int ycor = ygaps.get(fillX? 0:index).get(0); ycor < ygaps.get(fillX? 0:index).get(1); ycor+=1){
                tempx = xcor / 100.0 - 10; //numerical value
                tempy = 10 - ycor / 100.0;
                ArrayList<String> copyLeft = new ArrayList<String>(sortedLeft); //create copies as it gets altered
                ArrayList<String> copyRight = new ArrayList<String>(sortedRight);
                double[] xy = new double[2]; //list for substituing into solve
                xy[0]=tempx;
                xy[1]=tempy;
                double difference = solve(copyLeft,xy)-solve(copyRight,xy); //difference between evaluated values of left and right
                if(Double.isNaN(difference)){ //if there's a discontinuity, set that point to -1, effectively ignoring it
                  points[xcor][ycor]=-1;
                }
                else if(Math.abs(difference)<=cutoff){ //if the difference is within the cutoff, the point is drawn
                  img.setRGB(xcor,ycor,color); //draw the point
                  points[xcor][ycor]=1; //set that point to 1
                }
              }
            }
          }
          if(fillX){ //if horizontal gaps are being filled, reset list of gaps (horizontal gaps are filled first)
            xgaps = new ArrayList<ArrayList<Integer>>();
          } else { //otherwise, reset vertical gaps
            ygaps = new ArrayList<ArrayList<Integer>>();
          }
          boolean inGraph = false; //determines if we are inside the graph
          ArrayList<Integer> gap = new ArrayList<Integer>(); //stores the endpoints of the current gap
          ArrayList<Integer> gapStarts = new ArrayList<Integer>(); //list of starting points of the gaps, attempt to make program faster
          if(fillX){
            for(int a = 0; a<2000; a++){
              for(int b = 0; b<2000; b++){ //loop through the points
                if(points[a][b]==1 && !inGraph){ //if a point is found and we're not in the graph
                  inGraph = true; //we are now in the graph
                  if(!gapStarts.contains(a+1)){ //if the gap start point has not been accounted for
                    gapStarts.add(a+1); //add it to the list
                    gap.add(new Integer(a+1)); //add the start point to gap
                  }
                  break; //move on to next x-value
                }
                else if(points[a][b]==1 && inGraph){ //if a point is found and we're in the graph
                  inGraph = false; //we are now out of the graph
                  if(gap.size()>0 && gap.get(0)!=a-1){ //if the two points are not next to each other
                    gap.add(new Integer(a-1)); //add the endpoint to gap
                    if(!xgaps.contains(gap)){ //if this is a new gap
                      xgaps.add(gap); //add it to the list of gaps
                    }
                    gap = new ArrayList<Integer>(); //reset gap
                  }
                }
              }
            }
          } else { //otherwise, do the same thing but vertically
            for(int a = 0; a<2000; a++){
              for(int b = 0; b<2000; b++){
                if(points[b][a]==1 && !inGraph){
                  inGraph = true;
                  if(!gapStarts.contains(a+1)){
                    gapStarts.add(a+1);
                    gap.add(new Integer(a+1));
                  }
                  //break;
                }
                else if(points[b][a]==1 && inGraph){
                  inGraph = false;
                  if(gap.size()>0 && gap.get(0)!=a-1){
                    gap.add(new Integer(a-1));
                    if(!ygaps.contains(gap)){
                      ygaps.add(gap);
                    }
                    gap = new ArrayList<Integer>();
                  }
                }
              }
            }
          }
          cutoff+=0.01; //increment cutoff to allow points in the gaps
          if((cutoff>0.1 || xgaps.size()==0) && fillX){ //if cutoff went over max value and horizontal gaps are being filled, switch to vertical gaps
            xgaps = new ArrayList<ArrayList<Integer>>();
            xgaps.add(start); //reset list of horizontal gaps
            fillX = false;
            cutoff = 0.02; //reset cutoff
          }
          if(ygaps.size()==0){ //if there are no more gaps, stop
            break;
          }
        }
      }
      equationCount++; //increment equation in the case of multiple equations
    }
    try{ //create image file with graph
      f = new File("Graph.png"); // names file Graph.png
      ImageIO.write(img, "png", f);
    }catch(IOException e){
      System.out.println("Error: " + e);
    }
  }
}
