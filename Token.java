import java.util.*;
public class Token{
  // value is the actual string version of the operator. This only exist to compare and keep track
  private String value;
  // different Functions and Operators have different priorities
  // add and subtract is 2 multiply and divide is 3 power is 4 functions are 5
  private int priority;
  // -1 is left 1 is right quick and easy to read
  private int direction;

  // creates a Token
  public Token(String a, int b,int c) {
    value = a;
    priority = b;
    direction = c;
  }


// a compareTO method for strings
  public boolean equals(String input) {
    return (value.equals(input));
  }

// a char version so its easy
  public boolean equals(char input) {
    return (value.equals(input + ""));
  }

// returns if its slower or same priority but left direction
  public boolean isSlower(Token input) {
    if (this.priority < input.priority) {
      return true;
    }
    if (this.priority == input.priority && input.direction == -1) {
      return true;
    }
    return false;
  }



  // a toString cause why not
  public String toString() {
    return value;
  }

  // valueOf just in case I need it for debugging and other purposes
  public String valueOf() {
    return value;
  }
  // the great current list of Token that can easily migrate
  public static ArrayList<Token> createData() {
    ArrayList<Token> output = new ArrayList<Token>();
    Token addition = new Token("+",2,-1);
    Token subtract = new Token("-",2,-1);
    Token multiply = new Token("*",3,-1);
    Token divide = new Token("/",3,-1);
    output.add(addition);
    output.add(subtract);
    output.add(multiply);
    output.add(divide);
    return output;
  }
}
