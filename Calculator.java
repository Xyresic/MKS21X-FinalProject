public class Calculator{
  public static double pi = 3.141592653589793;
  public static double e = 2.718281828459045;
  public static double phi = 1.618033988749895;
  private char[] storedChar = new char[26];
  private double[] storedVal = new double[26];
  for(char c : storedChar){

  }
  public void store(char name, double value){

  }
  public static void help(){
  }
  public static void main(String[] args){
    if(args.length == 1 && !args[0].contains('=')){
      Expression temp = new Expression(args[0]);
      System.out.println(temp.evaluate());
    }
  }
}
