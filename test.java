
import java.util.*;

public class test{
  ArrayList<mycharacter> data = mycharacter.createData();
  public static boolean isOperator(char input) {
  ArrayList<mycharacter> data = mycharacter.createData();
    for (mycharacter c : data) {
      if (c.equals(input)) {
        return true;
      }
    }
    return false;
  }
  public static mycharacter convert(char input) {
    ArrayList<mycharacter> data = mycharacter.createData();
    mycharacter output = null;
      for (mycharacter c : data) {
        if (c.equals(input)) {
          output = c;
            return output;
        }
  }
    return output;
}

  public static ArrayList<String> Splitter(String input){
      ArrayList<String> output = new ArrayList<String>();
      ArrayList<mycharacter> stacks = new ArrayList<mycharacter>();
      int i = 0;
      while (i < input.length()) {
        // case senario for no spaces
        String holder = "";
        int peroidcount = 0;
        if (Character.isDigit(input.charAt(i)) || input.charAt(i) == '.') {
        while(i < input.length() && (Character.isDigit(input.charAt(i)) || input.charAt(i) == '.')  && peroidcount < 2) {
          if (input.charAt(i) == '.') {
            peroidcount += 1;
          }
          holder += input.charAt(i);
          i += 1;
        }
        output.add(holder);
      }
      else if (isOperator(input.charAt(i))) {
        mycharacter temp = convert(input.charAt(i));
        while (stacks.size() > 0 && temp.isSlower(stacks.get(0))) {
          output.add("" + stacks.remove(0));
        }
        stacks.add(0,temp);
        i += 1;
      }
    else if (input.charAt(i) == ' ') {
      i += 1;
    }
    else {
    i += 1;
  }
  }
  while(stacks.size() > 0) {
    output.add("" + stacks.remove(0));
  }
  return output;
}


  public static void main(String[] args) {
    String testing = "1 + 2 / 3 * 4";
    String testing2 = "1 + 2 / 3 - 6 * 4";

    System.out.println(Splitter(testing2));
  }
}
