import java.util.*;
@SuppressWarnings("overrides")
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
    add(new Token("!",5,0,1));
    add(new Token("root",5,0,2));
    add(new Token("abs",5,0,1));
    add(new Token("floor",5,0,1));
    add(new Token("ceil",5,0,1));
    add(new Token("gcf",5,0,2));
    add(new Token("lcm",5,0,2));
    add(new Token("ln",5,0,1));
    add(new Token("log",5,0,2));
    add(new Token("choose",5,0,2));
    add(new Token("permute",5,0,2));
    add(new Token("sin",5,0,1));
    add(new Token("cos",5,0,1));
    add(new Token("tan",5,0,1));
    add(new Token("sec",5,0,1));
    add(new Token("csc",5,0,1));
    add(new Token("cot",5,0,1));
    add(new Token("asin",5,0,1));
    add(new Token("acos",5,0,1));
    add(new Token("atan",5,0,1));
    add(new Token("sinh",5,0,1));
    add(new Token("cosh",5,0,1));
    add(new Token("tanh",5,0,1));
  }};
  public Token(String name, int precedence, int associativity, int arguments){ //initial constructor with defined parameters
    value = name;
    priority = precedence;
    direction = associativity;
    parameters = arguments;
  }
  public Token(String name){ //constructor that finds predefined token, used for convenience
    value = name;
    for(Token reference:tokens){
      if(reference.value.equals(name)){
        priority = reference.priority;
        direction = reference.direction;
        parameters = reference.parameters;
      }
    }
  }
  public int getArgs(){ //returns number of arguments
    return parameters;
  }
  public int getDirection(){ //returns direction of token
    return direction;
  }
  public boolean equals(Object input){ //checks for equality
    if(input==this){ //a token is equal to itself
      return true;
    }
    if(input instanceof Token){ //token equality is determined by name
      return value.equals(((Token)input).value);
    }
    if(input instanceof String){ //token equality but given name
      return (value.equals(input));
    }
    else{ //otherwise, they're not equal
      return false;
    }
  }
  public boolean equals(char input){ //equality check but for characters
    return (value.equals(input + ""));
  }
  public boolean isSlower(Token input) { //checks if this token is slower or of the same precedence and left associaitve
    if (this.priority < input.priority) { //if this token has lower precedence, it's considered slower
      return true;
    }
    if (this.priority == input.priority && input.direction == -1) { //if the two tokens have the same precedence, this token is considered slower if it's left associative
      return true;
    }
    return false; //otherwise, it's faster
  }
  public String toString() { //returns the name of the token
    return value;
  }
  public static boolean isOperator(Token check){ //checks if the token is an operator as opposed to a function
    return check.value.length()==1;
  }
  public static double gcf(double a, double b) {// finds gcd of 2 numbers using a simple recusive loop
    if(a % 1 != 0.0 || b % 1 != 0) { //checks if they are integers
      throw new IllegalArgumentException("Please use integers for gcf");
    }
    a=Math.abs(a); //take the absolute value in case they're negative
    b=Math.abs(b);
    if (a % b == 0) {
      return b;
    }
    return gcf(b % a, a);
  }
  public static double factorial(double a){ //calculates factorials recursively
    if(a % 1 != 0.0 || a<0) {
      throw new IllegalArgumentException("Please use positive integers for factorial");
    }
    if(a==0.0){
      return 1.0;
    }
    return a*factorial(a-1);
  }
  public static double choose(double a, double b){ //combinations
    if(a % 1 != 0.0 || b % 1 != 0 || a<0 || b<0) { //throw error if positive integers aren't used
      throw new IllegalArgumentException("Please use positive integers for choose");
    }
    if(b>a){ //throw error if second number is greater than the first
      throw new IllegalArgumentException("Second parameter for choose has to be less than or equal to the first");
    }
    return factorial(a)/(factorial(b)*(factorial(a-b))); //calculator combination
  }
  public static double permute(double a, double b){ //permutations
    if(a % 1 != 0.0 || b % 1 != 0 || a<0 || b<0) { //same errors as combinations
      throw new IllegalArgumentException("Please use positive integers for permute");
    }
    if(b>a){
      throw new IllegalArgumentException("Second parameter for permute has to be less than or equal to the first");
    }
    return factorial(a)/factorial(a-b); //calculate permutation
  }
}
