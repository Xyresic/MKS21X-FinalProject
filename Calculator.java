import java.util.*;
public class Calculator{
  public static double pi = 3.141592653589793;
  public static double e = 2.718281828459045;
  public static double phi = 1.618033988749895;
  private static double prevAns;
  private static boolean rads = true;
  private double[] storedVal = new double[26];
  public void store(char name, double value){
    if((int)name<65 || (int)name>90){
      throw new IllegalArgumentException("Please use a capital letter.");
    }
    storedVal[name-65]=value;
  }
  public static void help(){
  }
  public static void main(String[] args){
    if(args.length == 1 && !args[0].contains("=")){
      Expression temp = new Expression(args[0]);
      prevAns=temp.evaluate();
      System.out.println(prevAns);
    }
  }
}
