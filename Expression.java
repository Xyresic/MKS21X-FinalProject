import java.util.*;
import java.io.*;
public class Expression{
  public static boolean isToken(String input) { //checks if given input is an operator/function
    if(Token.tokens.contains(new Token(input))){
      return true;
    }
    return false;
  }
  public static boolean isToken(char input) { //char version
    return isToken(input + "");
  }
  public static boolean isNumchar(char input) { //checks if a character is part of a number
    return Character.isDigit(input) || input == '.';
  }
  // Shunting-yard Algorithm :D
  // It uses an output queue and an operator stack in order to determine an order of operations
  public static ArrayList<String> shunt(String input) throws FileNotFoundException{
    ArrayList<String> queue = new ArrayList<String>(); //queue is the output
    ArrayList<Token> stack = new ArrayList<Token>(); //stack is the stack of operators/functions
    int i = 0;
    Token curFunction = new Token(""); //stores the current function
    int commaCount = 0; //stores number of commas
    while (i < input.length()) {
      String holder = ""; //this holds the current number in the case it is more than one character long
      if (isNumchar(input.charAt(i))){ //if current char is a number...
        int periodCount = 0; //counter for decimals, only one decimal point per number
        while(i < input.length() && isNumchar(input.charAt(i))) { //...and subsequent charcters create a valid number...
          if (input.charAt(i) == '.') { //counts periods
            periodCount++;
          }
          if(periodCount>1){ //detects invalid numbers with more than one decimal point
            throw new IllegalArgumentException("A number has one or more extra decimal points");
          }
          holder += input.charAt(i); //records subsequent digits
          i++;
        }
        queue.add(holder); //...add the number to the queue
      }
      else if (isToken(input.charAt(i))) { //if the current char is a Token
        Token temp = new Token(input.charAt(i)+"");
        while (stack.size() > 0 && temp.isSlower(stack.get(0)) && !stack.get(0).equals(new Token("("))) { //if this token has lower pcredence...
          queue.add("" + stack.remove(0)); //...add tokens of higher precedence to the queue
        }
        stack.add(0,temp); //add the token to the stack
        i++;
      }
      else if (Character.isLetter(input.charAt(i))) { //if the current char is a letter...
        while(i<input.length() && Character.isLetter(input.charAt(i))){ //...add subsequent letters to a temporary holder
          holder+=input.charAt(i);
          i++;
        }
        if(holder.equals("PREV")){ //previous answer
          Scanner scanner = new Scanner(new File("data.txt"));
          queue.add(scanner.next());
        }
        else if(holder.equals("pi")){ //detecting constants
          queue.add(Math.PI+"");
        }
        else if(holder.equals("e")){
          queue.add(Math.E+"");
        }
        else if(holder.equals("phi")){
          queue.add(Calculator.phi+"");
        }
        else if(holder.length()==1 && (int)holder.charAt(0)>=65 && (int)holder.charAt(0)<=90){ //stored variables
          Scanner scanner = new Scanner(new File("data.txt"));
          for(int counter=0;counter<(int)holder.charAt(0)-63;counter++){ //loop through file with stored variables
            scanner.next();
          }
          queue.add(scanner.next());
        }
        else if(!isToken(holder)){ //if the string is not a valid function, throw an error
          throw new IllegalArgumentException("Unrecognized token: "+holder);
        }
        else{ //otherwise, treat the string as a valid function
          Token temp = new Token(holder);
          while (stack.size() > 0 && temp.isSlower(stack.get(0)) && !stack.get(0).equals(new Token("("))) { //if this token has lower pcredence...
            queue.add("" + stack.remove(0)); //...add tokens of higher precedence to the queue
          }
          curFunction=temp;
          stack.add(0,temp); //add the token to the stack
        }
      }
      else if (input.charAt(i) == ',') { //commas function as right parenthesis in functions
        if(curFunction.toString().length()==0){ //if there is a comma outaside of a function, throw an error
          throw new IllegalArgumentException("There are one or more commas outside of a function");
        }
        commaCount++;
        while(stack.size()>0 && !stack.get(0).equals(new Token("("))){ //pop operators until left parenthesis
          queue.add("" + stack.remove(0));
        }
        i++;
      }
      else if(input.charAt(i)=='('){ //if open parenthesis, add it to stack
        stack.add(0,new Token("("));
        i++;
      }
      else if(input.charAt(i)==')'){ //if close parenthesis, pop tokens until open parenthesis is found
        if(curFunction.toString().length()>0 && commaCount!=curFunction.getArgs()-1){ //if there is an incorrect number of commas, throw an error
          throw new IllegalArgumentException("Missing or extraneous commas");
        }
        curFunction = new Token(""); //reset the current function and comma count
        commaCount = 0;
        if(!stack.contains(new Token("("))){ //if there is no open parenthesis, throw an error
          throw new IllegalArgumentException("There are one or more unmatched parentheses");
        }
        if(stack.size()>1 && stack.get(0).equals(new Token("-")) && stack.get(1).equals(new Token("("))){ //if there is a negative number, add it to the queue
          queue.set(queue.size()-1,"-"+queue.get(queue.size()-1));
          stack.remove(0); //remove negative sign
        }
        while(stack.size()>0 && !stack.get(0).equals(new Token("("))){
          queue.add("" + stack.remove(0));
        }
        stack.remove(0); //remove unnecessary open parenthesis
        i++;
      }
      else if (input.charAt(i) == ' ') { //skips spaces
        i++;
      }
      else { //if nothing supported is found, throw an error
        throw new IllegalArgumentException("Unrecognized symbol: "+input.charAt(i));
      }
    }
    while(stack.size()>0){ //drops stack on queue
      if(stack.contains(new Token("("))){
        throw new IllegalArgumentException("There are one or more unmatched parentheses");
      }
      queue.add("" + stack.remove(0));
    }
    //System.out.println(queue);
    return queue;
  }
  public static double simplify(String operation, double...inputs) { //applies the operations/functions
    if (operation.equals("+")) {
      return inputs[0]+inputs[1];
    }
    if (operation.equals("-")) {
      return inputs[0]-inputs[1];
    }
    if (operation.equals("*")) {
      return inputs[0]*inputs[1];
    }
    if (operation.equals("/")) {
      return inputs[0]/inputs[1];
    }
    if (operation.equals("^")) {
      return Math.pow(inputs[0],inputs[1]);
    }
    if (operation.equals("%")) {
      return Math.floorMod((long)inputs[0],(long)inputs[1]);
    }
    if (operation.equals("root")) {
      if(inputs[0]==2){
        return Math.sqrt(inputs[1]);
      }
      if(inputs[0]==3){
        return Math.cbrt(inputs[1]);
      }
      return Math.pow(inputs[1],1/inputs[0]);
    }
    if (operation.equals("abs")) {
      return Math.abs(inputs[0]);
    }
    if (operation.equals("floor")) {
      return Math.floor(inputs[0]);
    }
    if (operation.equals("ceil")) {
      return Math.ceil(inputs[0]);
    }
    if (operation.equals("gcf")) {
      return Algorithms.gcd(inputs[0],inputs[1]);
    }
    if (operation.equals("lcm")) {
      return inputs[0]*inputs[1]/Algorithms.gcd(inputs[0],inputs[1]);
    }
    if (operation.equals("ln")) {
      return Math.log(inputs[0]);
    }
    if (operation.equals("log")) {
      return Math.log(inputs[1])/Math.log(inputs[0]);
    }
    return 0;
  }
  public static double evaluate(String expression) throws FileNotFoundException { //calculates value of given expression
    ArrayList<String> sorted = shunt(expression); //parses string using shunting-yard
    int i = 0;
    while (sorted.size() > 1) { //while there are tokens...
      if (isToken(sorted.get(i))) { //...if the token is a function or operation...
        Token temp = new Token(sorted.get(i));
        i = i - temp.getArgs(); //...move index pointer back spaces equal to number of parameters the token takes
        double a = Double.parseDouble(sorted.remove(i)); //store the first parameter...
        if(temp.getArgs()==2){ //if the token takes two parameters...
          double b = Double.parseDouble(sorted.remove(i)); //...store the second parameter...
          String oper = sorted.remove(i); //...store the token...
          sorted.add(i,"" + simplify(oper,a,b)); //...calculate value
        }
        else{ //if the token takes one parameter...
          String oper = sorted.remove(i); //...store the token...
          sorted.add(i,"" + simplify(oper,a)); //...calculate value
        }
      }
      i++;
    }
    return Double.parseDouble(sorted.remove(0));
  }
}
