import java.util.*;
import java.io.*;
public class Expression{
  public static boolean isToken(String input) { //checks if given input is an operator/function
    if(Token.tokens.contains(new Token(input))){ //if the given input is included in the supported tokens, return true
      return true;
    }
    return false; //otherwise return false
  }
  public static boolean isToken(char input) { //char version
    return isToken(input + "");
  }
  public static boolean isNumchar(char input) { //checks if a character is part of a number
    return Character.isDigit(input) || input == '.';
  }
  // Shunting-yard Algorithm :D
  // It uses an output queue and an operator stack in order to determine an order of operations
  //variables is the list of accepted variables, used for graphing
  public static ArrayList<String> shunt(String input, ArrayList<String> variables) throws FileNotFoundException{
    ArrayList<String> queue = new ArrayList<String>(); //queue is the output
    ArrayList<Token> stack = new ArrayList<Token>(); //stack is the stack of operators/functions
    int i = 0; //index
    Token curFunction = new Token(""); //stores the current function
    int commaCount = 0; //stores number of commas
    while (i < input.length()) { //loops through the input string
      String holder = ""; //this holds the current number/token in the case it is more than one character long
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
      } else if (isToken(input.charAt(i)) && input.charAt(i)!='(') { //if the current char is a Token
        Token temp = new Token(input.charAt(i)+""); //create a temporary token
        while (stack.size() > 0 && temp.isSlower(stack.get(0)) && !stack.get(0).equals(new Token("("))) { //if this token has lower precedence...
          queue.add("" + stack.remove(0)); //...add tokens of higher precedence to the queue
        }
        stack.add(0,temp); //add the token to the stack
        i++;
      } else if (Character.isLetter(input.charAt(i))) { //if the current char is a letter...
        if(i>0 && (Character.isDigit(input.charAt(i-1)) || variables.contains(input.charAt(i-1)+""))){ //checks for coefficients and multiplied variables
          stack.add(0,new Token("*")); //add a multiply token
        }
        do { //...add subsequent letters to a temporary holder
          holder+=input.charAt(i);
          i++;
        } while(i<input.length() && Character.isLetter(input.charAt(i)) && !variables.contains(holder));
        if(holder.equals("PREV")){ //previous answer
          Scanner scanner = new Scanner(new File("data.txt"));
          queue.add(scanner.next());
        } else if(holder.equals("pi")){ //detecting constants
          queue.add(Math.PI+"");
        } else if(holder.equals("e")){
          queue.add(Math.E+"");
        } else if(holder.equals("phi")){
          queue.add(Calculator.phi+"");
        } else if(variables.contains(holder)){ //detecting vairable for graphing
          queue.add(holder);
        } else if(holder.length()==1 && holder.charAt(0)>='A' && holder.charAt(0)<='Z'){ //stored variables
          Scanner scanner = new Scanner(new File("data.txt"));
          for(int counter=0;counter<holder.charAt(0)-63;counter++){ //loop through file with stored variables
            scanner.next();
          }
          queue.add(scanner.next());
        } else if(!isToken(holder)){ //if the string is not a valid function, throw an error
          throw new IllegalArgumentException("Unrecognized token: "+holder);
        } else{ //otherwise, treat the string as a valid function
          Token temp = new Token(holder);
          while (stack.size() > 0 && temp.isSlower(stack.get(0)) && !stack.get(0).equals(new Token("("))) { //if this token has lower pcredence...
            queue.add("" + stack.remove(0)); //...add tokens of higher precedence to the queue
          }
          curFunction=temp; //set current function, used to determine correct comma usage
          stack.add(0,temp); //add the token to the stack
        }
      } else if (input.charAt(i) == ',') { //commas function as right parenthesis in functions
        if(curFunction.toString().length()==0){ //if there is a comma outaside of a function, throw an error
          throw new IllegalArgumentException("There are one or more commas outside of a function");
        }
        commaCount++; //increment count
        while(stack.size()>0 && !stack.get(0).equals(new Token("("))){ //pop operators until left parenthesis
          queue.add("" + stack.remove(0));
        }
        i++;
      } else if(input.charAt(i)=='('){ //if open parenthesis, add it to stack
        if(i>0 && Character.isDigit(input.charAt(i-1))){ //check for coefficients
          stack.add(0,new Token("*"));
        }
        stack.add(0,new Token("("));
        i++;
      } else if(input.charAt(i)==')'){ //if close parenthesis, pop tokens until open parenthesis is found
        if(stack.size()>1 && stack.get(0).equals(new Token("-")) && stack.get(1).equals(new Token("("))){ //if there is a negative number, add it to the queue
          if(i>0 && Character.isLetter(input.charAt(i))){
            queue.add(queue.size()-1,0+"");
          }
          else{
            queue.set(queue.size()-1,"-"+queue.get(queue.size()-1));
            stack.remove(0); //remove negative sign
          }
        }
        else {
          if(curFunction.toString().length()>0 && commaCount!=curFunction.getArgs()-1){ //if there is an incorrect number of commas, throw an error
            throw new IllegalArgumentException("Missing or extraneous commas");
          }
          curFunction = new Token(""); //reset the current function and comma count
          commaCount = 0;
        }
        if(!stack.contains(new Token("("))){ //if there is no open parenthesis, throw an error
          throw new IllegalArgumentException("There are one or more unmatched parentheses");
        }
        while(stack.size()>0 && !stack.get(0).equals(new Token("("))){ //pop operators off the top of the stack
          queue.add("" + stack.remove(0));
        }
        stack.remove(0); //remove unnecessary open parenthesis
        i++;
      } else if (input.charAt(i) == ' ') { //skips spaces
        i++;
      }
       else { //if nothing supported is found, throw an error
        throw new IllegalArgumentException("Unrecognized symbol: "+input.charAt(i));
      }
    }
    while(stack.size()>0){ //drops stack on queue
      if(stack.contains(new Token("("))){ //if there is an extra open parenthesis, throw an error
        throw new IllegalArgumentException("There are one or more unmatched parentheses");
      }
      queue.add("" + stack.remove(0));
    }
    return queue;
  }
  public static double simplify(String operation, double...inputs) { //applies the operations/functions
    if (operation.equals("+")) { // sum
      return inputs[0]+inputs[1];
    }
    if (operation.equals("-")) {// difference
      return inputs[0]-inputs[1];
    }
    if (operation.equals("*")) { // product
      return inputs[0]*inputs[1];
    }
    if (operation.equals("/")) { // quotient
      return inputs[0]/inputs[1];
    }
    if (operation.equals("^")) { // exponentiation
      return Math.pow(inputs[0],inputs[1]);
    }
    if (operation.equals("%")) { // modulo
      return Math.floorMod((long)inputs[0],(long)inputs[1]);
    }
    if (operation.equals("!")) { // factorial
      return Token.factorial(inputs[0]);
    }
    if (operation.equals("root")) { //taking roots
      if(inputs[0]==2){ //specifically using sqrt and cbrt to mitigate rounding errors with division
        return Math.sqrt(inputs[1]);
      }
      if(inputs[0]==3){
        return Math.cbrt(inputs[1]);
      }
      return Math.pow(inputs[1],1/inputs[0]);
    }
    if (operation.equals("abs")) { // absolute value
      return Math.abs(inputs[0]);
    }
    if (operation.equals("floor")) { // floor function
      return Math.floor(inputs[0]);
    }
    if (operation.equals("ceil")) { // ceiling function
      return Math.ceil(inputs[0]);
    }
    if (operation.equals("gcf")) { // greatest common factor
      return Token.gcf(inputs[0],inputs[1]);
    }
    if (operation.equals("lcm")) { // Least common multiple
      return inputs[0]*inputs[1]/Token.gcf(inputs[0],inputs[1]);
    }
    if (operation.equals("ln")) { // natural log
      return Math.log(inputs[0]);
    }
    if (operation.equals("log")) { // logarithms
      return Math.log(inputs[1])/Math.log(inputs[0]);
    }
    if (operation.equals("choose")) { // combinations
      return Token.choose(inputs[0],inputs[1]);
    }
    if (operation.equals("permute")) { // permutations
      return Token.permute(inputs[0],inputs[1]);
    }
    if (operation.equals("sin")) { // sine
      if((inputs[0]/Math.PI)%1==0){ //hardcoded specific values to prevent ronding errors
        return 0.0;
      }
      return Math.sin(Calculator.isRads()? inputs[0]:inputs[0]*Math.PI/180);
    }
    if (operation.equals("cos")) { // cosine
      if((inputs[0]/(Math.PI/2))%2==1 || (inputs[0]/(Math.PI/2))%2==-1){ //hardcoded specific values to prevent ronding errors
        return 0.0;
      }
      return Math.cos(Calculator.isRads()? inputs[0]:inputs[0]*Math.PI/180);
    }
    if (operation.equals("tan")) { // tangent
      if((inputs[0]/(Math.PI/4))%4==1||(inputs[0]/(Math.PI/4))%4==-3){ //hardcoded specific values to prevent ronding errors
        return 1.0;
      }
      if((inputs[0]/(Math.PI/4))%4==-1||(inputs[0]/(Math.PI/4))%4==3){
        return -1.0;
      }
      if((inputs[0]/Math.PI)%1==0){
        return 0.0;
      }
      if((inputs[0]/(Math.PI/2))%2==1 ||(inputs[0]/(Math.PI/2))%2==-1){
        return Double.NaN;
      }
      return Math.tan(Calculator.isRads()? inputs[0]:inputs[0]*Math.PI/180);
    }
    if (operation.equals("sec")) { // secant
      if((inputs[0]/(Math.PI/2))%2==1 || (inputs[0]/(Math.PI/2))%2==-1){ //hardcoded specific values to prevent ronding errors
        return Double.NaN;
      }
      return 1/Math.cos(Calculator.isRads()? inputs[0]:inputs[0]*Math.PI/180);
    }
    if (operation.equals("csc")) { // cosecant
      if((inputs[0]/Math.PI)%1==0){ //hardcoded specific values to prevent ronding errors
        return Double.NaN;
      }
      return 1/Math.sin(Calculator.isRads()? inputs[0]:inputs[0]*Math.PI/180);
    }
    if (operation.equals("cot")) { // cotangent
      if((inputs[0]/(Math.PI/4))%4==1||(inputs[0]/(Math.PI/4))%4==-3){ //hardcoded specific values to prevent ronding errors
        return 1.0;
      }
      if((inputs[0]/(Math.PI/4))%4==-1||(inputs[0]/(Math.PI/4))%4==3){
        return -1.0;
      }
      if((inputs[0]/Math.PI)%1==0){
        return Double.NaN;
      }
      if((inputs[0]/(Math.PI/2))%2==1 ||(inputs[0]/(Math.PI/2))%2==-1){
        return 0.0;
      }
      return 1/Math.tan(Calculator.isRads()? inputs[0]:inputs[0]*Math.PI/180);
    }
    if (operation.equals("asin")) { //arcsine, or sine inverse
      return Calculator.isRads()? Math.asin(inputs[0]):Math.asin(inputs[0])*180/Math.PI;
    }
    if (operation.equals("acos")) { //arcosine, or cosine inverse
      return Calculator.isRads()? Math.acos(inputs[0]):Math.acos(inputs[0])*180/Math.PI;
    }
    if (operation.equals("atan")) { //arctangent, or tangent inverse
      return Calculator.isRads()? Math.atan(inputs[0]):Math.atan(inputs[0])*180/Math.PI;
    }
    if (operation.equals("sinh")) { //hyperbolic sine
      return Math.sinh(inputs[0]);
    }
    if (operation.equals("cosh")) { //hyperbolic cosine
      return Math.cosh(inputs[0]);
    }
    if (operation.equals("tanh")) { //hyperbolic tangent
      return Math.tanh(inputs[0]);
    }
    return 0;
  }
  public static double evaluate(ArrayList<String> sorted) throws FileNotFoundException { //calculates value of given expression
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
        } else{ //if the token takes one parameter...
          String oper = sorted.remove(i); //...store the token...
          sorted.add(i,"" + simplify(oper,a)); //...calculate value
        }
      }
      i++;
    }
    return Double.parseDouble(sorted.remove(0));
  }
  public static double evaluate(String expression) throws FileNotFoundException { //string version
    ArrayList<String> noVars = new ArrayList<String>(); //empty list to prevent use of variables when not graphing
    ArrayList<String> sorted = shunt(expression,noVars); //parses string using shunting-yard
    return evaluate(sorted); //calculates value
  }
}
