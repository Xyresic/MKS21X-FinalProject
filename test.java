
import java.util.*;

public class test{
  double result = 9.12;
  public static void Splitter(String input){
      ArrayList<String> output = new ArrayList<String>();
      ArrayList<String> stacks = new ArrayList<String>();
      int i = 0;
      while (i < input.length()) {
        // case senario for no spaces
        String holder = "";
        int peroidcount = 0;
        while((Character.isDigit(input.charAt(i)) || input.charAt(i) == '.')  && peroidcount < 2 && i < input.length()) {
          if (input.charAt(i) == '.') {
            peroidcount += 1;
          }
          holder += input.charAt(i);
          i += 1;

        }
        output.add(holder);
        System.out.println(output);
        i = input.length();
      }



  }


  public static void main(String[] args) {
    String fourfunctiontester = "3.12+6";
    Scanner reader = new Scanner(System.in);
    System.out.println("Enter a Expression:");
    double expression = reader.nextDouble();
    Splitter(fourfunctiontester);

  }
}
