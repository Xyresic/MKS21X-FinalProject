import java.util.*;
public class Driver extends test{
  public static void main(String[] args) {
    String test1 = "1 + 2";
    String test2 = "1 + 2 * 3";
    String test3 = "1 + 2 - 8";
    String test4 = "1 + 2 - 1 - .6";
    String test5 = "1 + 2.33212 / 1 ";
    System.out.println(Splitter(test1));
    System.out.println(Splitter(test2));
    System.out.println(Splitter(test3));
    System.out.println(Splitter(test4));
    System.out.println(Splitter(test5));
  }
}
