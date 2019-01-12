import java.util.*;
import java.io.*;
public class Calculator{
  public static double phi = 1.618033988749895;
  private static Scanner scanner = new Scanner(new File("data.txt")); //scanner for data file containing previous answer and stored variables
  private static String[] data = new String[28]; //array to store values in data.txt
  public static void store(char name, double value) throws FileNotFoundException { //stores a value attatched to a capital letter
    if((int)name<65 || (int)name>90){ //if name is not a capital letter, throw an error
      throw new IllegalArgumentException("Please use a capital letter.");
    }
    data[(int)name-63] = value+"\n"; //change value attatched to said letter
    rewrite();
  }
  private static void rewrite(){ //writes to data.txt to match changes made
    PrintWriter writer = new PrintWriter(new File("data.txt"));
    for(String line : data){
      writer.print(line);
    }
    writer.close();
  }
  public static void help(){ //prints directions
    //To be implemented
  }
  public static void main(String[] args) throws FileNotFoundException {
    for(int i  = 0; i<28; i++){ //intializes data
      data[i]=scanner.next()+"\n";
    }
    if(args.length>0 && (args[0].equals("radians") || args[0].equals("degrees"))){ //checks for radians/degrees and switches accordingly
      data[1]=args[0];
      rewrite();
    }
    if(args.length == 1 && !args[0].contains("=")){ //checks if user inputted an expression
      data[0]=Expression.evaluate(args[0]);
      rewrite();
      System.out.println(data[0]);
    }
  }
}
