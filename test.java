
import java.util.*;

public class test{
  char[] operations = {'+','-','*','/'};
  public boolean isOperator(char input) {
    for (char c : operations) {
      if (c == input) {
        return true;
      }
    }
    return false;
  }
  double result = 9.12;
  public void Splitter(String input){
      ArrayList<String> output;
      ArrayList<String> stacks;
      int i = 0;
      while (i < input.length()) {
        // case senario for no spaces
        String holder = "";
        int peroidcount = 0;
        if (Character.isDigit(input.charAt(i)) || input.charAt(i) == '.') {
        while((Character.isDigit(input.charAt(i)) || input.charAt(i) == '.')  && peroidcount < 2 && i < input.length()) {
          if (input.charAt(i) == '.') {
            peroidcount += 1;
          }
          holder += input.charAt(i);
          i += 1;
        }
        output.add(holder);
      }

     if (isOperator(input.charAt(i))) {

        }
      }



        output.add(holder);
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
