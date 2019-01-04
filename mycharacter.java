import java.util.*;
public class mycharacter{
  private String value;
  private int speed;
  private int direction;
  public mycharacter(String a, int b,int c) {
    a = value;
    b = speed;
    c = direction;
  }
  public boolean compareTo(String input) {
    return (value == input);
  }
  public boolean compareTo(char input) {
    return (value == input + "");
  }
  public boolean isSlower(mycharacter input) {
    if (this.speed < input.speed) {
      return true;
    }
    if (this.speed == input.speed || input.direction == -1) {
      return true;
    }
    return false;
  }
  public static  ArrayList<mycharacter> createData() {
    ArrayList<mycharacter> output = new ArrayList<mycharacter>();
    mycharacter addition = new mycharacter("+",2,-1);
    mycharacter subtract = new mycharacter("-",2,-1);
    mycharacter multiply = new mycharacter("*",2,-1);
    mycharacter divide = new mycharacter("/",2,-1);
    output.add(addition);
    output.add(subtract);
    output.add(multiply);
    output.add(divide);
    return output;
  }
}
