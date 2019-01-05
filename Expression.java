import java.util.*;
public class Expression{
  public static boolean isToken(String input) { //checks if given input is an operator/function
    for(Token reference : Token.tokens){
      if(reference.equals(input)){
        return true;
      }
    }
    return false;
  }
  public static boolean isToken(char input) { //char version
    return isToken(input + "");
  }
  public static boolean isNumchar(char input) {
    return Character.isDigit(input) || input == '.' || input == 'E' || input == 'B';
  }
  // Shunting-yard Algorithm :D
  // It uses an output queue and an operator stack in order to determine an order of operations
  public static ArrayList<String> shunt(String input){
    ArrayList<String> queue = new ArrayList<String>(); //queue is the output
    ArrayList<Token> stack = new ArrayList<Token>(); //stack is the stack of operators/functions
    int i = 0;
    while (i < input.length()) {
      String holder = ""; //this holds the current number/token in the case it is more than one character long
      if (isNumchar(input.charAt(i))){ //if current char is a number...
        int periodCount = 0; //counter for decimals, only one decimal point per number
        while(i < input.length() && isNumchar(input.charAt(i))) { //...and subsequent charcters create a valid number...
          if (input.charAt(i) == '.') { //counts periods
            periodCount++;
          }
          if(periodCount>1){ //detects invalid numbers with more than one decimal point
            throw new IllegalArgumentException("A number has one or more extra decimal points.");
          }
          holder += input.charAt(i); //records subsequent digits
          i++;
        }
        queue.add(holder); //...add the number to the queue
      }
      else if (isToken(input.charAt(i))) { //if the current char is a Token
        Token temp = new Token(input.charAt(i)+""); //create a Token
        while (stack.size() > 0 && temp.isSlower(stack.get(0))) { //if this token has lower pcredence...
          queue.add("" + stack.remove(0)); //...add tokens of higher precedence to the output
        }
        stack.add(0,temp); //add the token to the stack
        i++;
      }
      else if (input.charAt(i) == ' ') { //skips spaces
        i++;
      }
      else if(!isNumchar(input.charAt(i)) && !isToken(input.charAt(i))){ //throw an exception for unsupported symbols
        throw new IllegalArgumentException("Unrecognized symbol");
      }
    }
    stack.forEach((x) -> queue.add(x.toString())); //drops stack on queue
    return queue;
  }
  public static double simplify(String operation, double a, double b) { //applies the operations/functions
    if (operation.equals("+")) {
      return a + b;
    }
    if (operation.equals("-")) {
      return a - b;
    }
    if (operation.equals("*")) {
      return a * b;
    }
    if (operation.equals("/")) {
      return a / b;
    }
    return 0;
  }
  public static double evaluate(String expression) {
    ArrayList<String> sorted = shunt(expression);
    int i = 2;
    while (sorted.size() > 1) {
      if (isToken(sorted.get(i))) {
        i = i - 2;
        double a = Double.parseDouble(sorted.remove(i));
        double b = Double.parseDouble(sorted.remove(i));
        String oper = sorted.remove(i);
        sorted.add(i,"" + simplify(oper,a,b));
      }
      i++;
    }
    return Double.parseDouble(sorted.remove(0));
  }
}
