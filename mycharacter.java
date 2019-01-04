import java.util.*;
public class mycharacter{
  private String value;
  private int speed;
  private int direction;
  public mycharacter(String a, int b,int c) {
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
  public boolean isSlower(mycharacter input) {
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
  public static ArrayList<mycharacter> createData() {
    ArrayList<mycharacter> output = new ArrayList<mycharacter>();
    mycharacter addition = new mycharacter("+",2,-1);
    mycharacter subtract = new mycharacter("-",2,-1);
    mycharacter multiply = new mycharacter("*",3,-1);
    mycharacter divide = new mycharacter("/",3,-1);
    output.add(addition);
    output.add(subtract);
    output.add(multiply);
    output.add(divide);
    return output;
  }
}
