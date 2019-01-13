import java.util.*;
public class Token{
  private String value; //String version of operator/function
  private int priority; //priority of token when calculating
  //addition and subtraction have priority 2
  //multiplication and division have priority 3
  //exponentiation has priority 4
  //parentheses and functions have priority 5
  private int direction; //direction of precedence, -1 is left, 1 is right, 0 is function
  private int parameters; //number of arguments the token takes
  public static ArrayList<Token> tokens = new ArrayList<Token>() {{ //list of implemented operations/tokens
    add(new Token("+",2,-1,2));
    add(new Token("-",2,-1,2));
    add(new Token("*",3,-1,2));
    add(new Token("/",3,-1,2));
    add(new Token("^",4,1,2));
    add(new Token("%",3,-1,2));
    add(new Token("(",5,1,0));
    add(new Token("root",5,0,2));
    add(new Token("abs",5,0,1));
    add(new Token("floor",5,0,1));
    add(new Token("ceil",5,0,1));
    add(new Token("gcf",5,0,2));
    add(new Token("lcm",5,0,2));
    add(new Token("ln",5,0,1));
    add(new Token("log",5,0,2));
  }};
  public Token(String name, int precedence, int associativity, int arguments){
    value = name;
    priority = precedence;
    direction = associativity;
    parameters = arguments;
  }
  public Token(String name){
    value = name;
    for(Token reference:tokens){
      if(reference.value.equals(name)){
        priority = reference.priority;
        direction = reference.direction;
        parameters = reference.parameters;
      }
    }
  }
  public int getArgs(){
    return parameters;
  }
  public int getDirection(){
    return direction;
  }
  public boolean equals(Object input){ //checks for equality
    if(input==this){
      return true;
    }
    if(input instanceof Token){
      return value.equals(((Token)input).value);
    }
    if(input instanceof String){
      return (value.equals(input));
    }
    else{
      return false;
    }
  }
  public boolean equals(char input){
    return (value.equals(input + ""));
  }
  public boolean isSlower(Token input) { //checks if this token is slower or of the same precedence and left associaitve
    if (this.priority < input.priority) {
      return true;
    }
    if (this.priority == input.priority && input.direction == -1) {
      return true;
    }
    return false;
  }
  public String toString() {
    return value;
  }
  public static boolean isOperator(Token check){
    return check.value.length()==1;
  }
  public static double gcf(double a, double b) {
    if(a % 1 != 0.0 || b % 1 != 0) {
      throw new IllegalArgumentException("Please use integers");
    }
    if (a % b == 0) {
      return b;
    }
    return gcf(b % a, a);
  }
}
