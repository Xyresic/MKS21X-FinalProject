import java.util.*;
public class Calculator{
  public static double pi = 3.141592653589793;
  public static double e = 2.718281828459045;
  public static double phi = 1.618033988749895;
  private static double prevAns;
  private static boolean radians = true;
  private double[] storedVal = new double[26];
  public void store(char name, double value){ //stores a value attatched to a capital letter
    if((int)name<65 || (int)name>90){ //if name is not a capital letter, throw an error
      throw new IllegalArgumentException("Please use a capital letter.");
    }
    storedVal[name-65]=value;
  }
  public static void help(){ //prints directions
    //To be implemented
  }
  public static void main(String[] args){
    if(args.length>0 && args[0].equals("radians")){
      radians = true;
    }
    if(args.length>0 && args[0].equals("degrees")){
      radians = false;
    }
    if(args.length == 1 && !args[0].contains("=")){ //checks if user inputted an expression
      prevAns=Expression.evaluate(args[0]);
      System.out.println(prevAns);
    }
  }
}
