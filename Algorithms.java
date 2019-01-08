public class Algorithms{
  public static double gcd(double a, double b) {
    if(a % 1 != 0.0 || b % 1 != 0) {
      throw new IllegalArgumentException("gcf function did not recieve intergers");
    }
    if (a % b == 0) {
      return b;
    }
    return gcd(b % a, a);
  }
  public static void main(String[] args) {
    System.out.println(gcd(18,29));
  }
}
