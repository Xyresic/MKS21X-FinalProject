
import java.util.*;

public class test{

// isOperator is a method that obtains a character and checks if it is an operator by looping through a list of known operators
  public static boolean isOperator(String input) {
    ArrayList<FunOper> data = FunOper.createData();
    //data of all the current Operators


    for (FunOper c : data) {
      // looks at all the Operators

      if (c.equals(input)) {
        // if it finds the operator returns true
        return true;
      }
    }
    // if it doesn't find one then return false
    return false;
  }




  // char version so I don't make myself go crazy converting between string and char
  public static boolean isOperator(char input) {
    return isOperator(input + "");
  }



  // converts an extisting operator in string form to one in FunOper form
  public static FunOper convert(String input) {
    ArrayList<FunOper> data = FunOper.createData();
    // looks at the data
    FunOper output = null;
    // this is the output variable
      for (FunOper c : data) {
        if (c.equals(input)) {
          output = c;
            return output;
        }
        // loops through existing operator to find if it exist. It returns it if it exist.
  }
    return output;
    // returns null is it isn't a operator
}


  // char version so I don't make myself go crazy converting between string and char
  public static FunOper convert(char input) {
    return convert(input + "");
}

  public static boolean isNumchar(char input) {
    return Character.isDigit(input) || input == '.';
  }



  // Shunting yard Algorithm :D
  // https://en.wikipedia.org/wiki/Shunting-yard_algorithm I didn't look at any java code.
  // It basically uses an output and a stacks. It holds numbers and end function/Operators in the stacks
  // Then it combines the two
  public static ArrayList<String> Splitter(String input){
    //output is the thing that is gonna get returned
      ArrayList<String> output = new ArrayList<String>();
    // stacks is a stack of operators and functions
      ArrayList<FunOper> stacks = new ArrayList<FunOper>();
      // while loop :)
      int i = 0;
      while (i < input.length()) {
        // case senario for no spaces
        String holder = "";
        // creates the number
        int peroidcount = 0;
        // only 1 peroid count is allowed
        // if the character is a digit/. then it is considered a number;
        if (isNumchar(input.charAt(i))){
        while(i < input.length() && (isNumchar(input.charAt(i)))  && peroidcount < 2) {
          if (input.charAt(i) == '.') {
            peroidcount += 1;
          }
          holder += input.charAt(i);
          i += 1;
        }
        // after there is no more digits to add it drops into the holder
        output.add(holder);
      }
      else if (isOperator(input.charAt(i))) {
        FunOper temp = convert(input.charAt(i));
        while (stacks.size() > 0 && temp.isSlower(stacks.get(0))) {
          output.add("" + stacks.remove(0));
        }
        stacks.add(0,temp);
        i += 1;
      }
      // skips all spaces to make it easier to work with
    else if (input.charAt(i) == ' ') {
      i += 1;
    }
    // saftey, just so no infinite loops occur
    else {
    i += 1;
  }
  }
  // drops stacks into output.
  while(stacks.size() > 0) {
    output.add("" + stacks.remove(0));
  }
  return output;
}

  public static double simplfy(String operation, double a, double b) {
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

    public static double evaulate(ArrayList<String> expression) {
      int i = 2;
      while (expression.size() > 1) {
        if (isOperator(expression.get(i))) {
          i = i - 2;
          double a = Double.parseDouble(expression.remove(i));
          double b = Double.parseDouble(expression.remove(i));
          String oper = expression.remove(i);
          expression.add(i,"" + simplfy(oper,a,b));
        }
        i += 1;
      }
      return Double.parseDouble(expression.remove(0));
    }


    public static double calculate(String input) {
      return (evaulate(Splitter(input)));
    }


  }
