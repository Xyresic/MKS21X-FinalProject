import java.util.*;
public class Token{
  private String value; //String version of operator/function
  private int priority; //priority of token when calculating
  //addition and subtraction have priority 2
  //multiplication and division have priority 3
  //exponentiation has priority 4
  //parentheses and functions have priority 5
  private int direction; //direction of precedence, -1 is left, 1 is right
  public static ArrayList<Token> tokens = new ArrayList<Token>() {{ //list of implemented operations/tokens
    add(new Token("+",2,-1));
    add(new Token("-",2,-1));
    add(new Token("*",3,-1));
    add(new Token("/",3,-1));
  }};
  public Token(String name, int precedence, int associativity){
    value = name;
    priority = precedence;
    direction = associativity;
  }
  public Token(String name){
    value = name;
    for(Token reference:tokens){
      if(reference.value.equals(name)){
        priority = reference.priority;
        direction = reference.direction;
      }
    }
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
}
