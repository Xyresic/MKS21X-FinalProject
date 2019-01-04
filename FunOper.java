import java.util.*;
public class FunOper{
  private String value;
  private int speed;
  private int direction;
  public FunOper(String a, int b,int c) {
    value = a;
    speed = b;
    direction = c;
  }

  public boolean equals(String input) {
    return (value.equals(input));
  }
  public boolean equals(char input) {
    return (value.equals(input + ""));
  }
  public boolean isSlower(FunOper input) {
    if (this.speed < input.speed) {
      return true;
    }
    if (this.speed == input.speed && input.direction == -1) {
      return true;
    }
    return false;
  }
  public String toString() {
    return value;
  }
  public String valueOf() {
    return value;
  }
  public static ArrayList<FunOper> createData() {
    ArrayList<FunOper> output = new ArrayList<FunOper>();
    FunOper addition = new FunOper("+",2,-1);
    FunOper subtract = new FunOper("-",2,-1);
    FunOper multiply = new FunOper("*",3,-1);
    FunOper divide = new FunOper("/",3,-1);
    output.add(addition);
    output.add(subtract);
    output.add(multiply);
    output.add(divide);
    return output;
  }
}
