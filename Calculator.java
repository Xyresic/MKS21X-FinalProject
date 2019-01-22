import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import javax.imageio.ImageIO;
import javax.swing.*;
public class Calculator{
  public static double phi = 1.618033988749895; //the golden ratio
  private static Scanner scanner; //scanner for data file containing previous answer and stored variables
  private static String[] data = new String[28]; //array to store values in data.txt
  public static void store(char name, double value) throws FileNotFoundException { //stores a value attatched to a capital letter
    if(name<'A' || name>'Z'){ //if name is not a capital letter, throw an error
      throw new IllegalArgumentException("Please use a capital letter");
    }
    data[name-63] = value+"\n"; //change value attatched to said letter
    rewrite(); //write changes to file
  }
  public static void store(char name, char variable) throws FileNotFoundException { //stores a value from another variable
    if(name<'A' || name>'Z' || variable<'A' || variable>'Z'){ //if the variables given are not capital letters, throw an error
      throw new IllegalArgumentException("Please use a capital letter");
    }
    data[name-63] = data[variable-63]; //change value stored
    rewrite(); //write changes to variable
  }
  private static void rewrite() throws FileNotFoundException { //writes to data.txt to match changes made
    PrintWriter writer = new PrintWriter(new File("data.txt")); //new printwriter to edit file
    for(String line : data){ //write each line of data to the file
      writer.print(line);
    }
    writer.close(); //close the printwriter
  }
  public static boolean isNumeric(String test){ //checks if given string is a number
    for(int i = 0; i<test.length(); i++){ //checks each character at a time
      if(!Expression.isNumchar(test.charAt(i))){
        return false;
      }
    }
    return true;
  }
  public static boolean isRads(){ //checks if calculator is in radians mode
    return data[1].equals("radians\n");
  }
  public static void main(String[] args) throws FileNotFoundException, IOException{ //where the user interface happens
    System.out.println("Welcome to Terminal Instruments model 1."); //welcome message
    System.out.println("If you need help please type in help"); //help message
    Scanner inputReader = new Scanner(System.in); //scanner to read continuous user input
    String input; //string to store the input
    while(!(input = inputReader.nextLine()).trim().equals("exit")){ //if the user types exit, stop the program
      try{ //try-catch block to catch errors thrown by user inputting wrongly formatted commands
        args = input.trim().split("\\s"); //removes whitespace
        scanner = new Scanner(new File("data.txt")); //intializes scanner
        for(int i  = 0; i<28; i++){ //intializes data
          data[i]=scanner.next()+"\n";
        }
        if(args.length>0 && (args[0].equals("radians") || args[0].equals("degrees"))){ //checks for radians/degrees and switches accordingly
          data[1]=args[0]+"\n"; //changes mode
          rewrite(); //writes changes to file
          String[] temp = new String[args.length-1]; //removes processed command in args
          for(int i = 1; i<args.length; i++){
            temp[i-1] = args[i];
          }
          args=temp;
        }
          if(args.length>0 && (args[0].equals("help"))) {
            String[] temp = new String[args.length-1]; //removes processed command in args
            File help = new File("help.txt");
            Scanner scnr = new Scanner(help);
            int lineNumber = 1;
            while(scnr.hasNextLine()){
           String line = scnr.nextLine();
           System.out.println(line);
           lineNumber++;
           args = temp;
       }
          }
        if(args.length>2 && args[0].equals("store")){ //checks for use of store
          if(args[1].length()>1){ //checks for capital letter for the vairable that is storing
            throw new IllegalArgumentException("Please use a capital letter for the variable");
          } else if(isNumeric(args[2])){ //checks for correct number format
            store(args[1].charAt(0),Double.parseDouble(args[2])); //if check passed, store
          } else if(args[2].equals("PREV")){ //checks for storing previous answer
            store(args[1].charAt(0),Double.parseDouble(data[0]));
          } else if(args[2].equals("pi")){ //checks for storing constants
            store(args[1].charAt(0),Math.PI);
          } else if(args[2].equals("e")){
            store(args[1].charAt(0),Math.E);
          } else if(args[2].equals("phi")){
            store(args[1].charAt(0),phi);
          } else if(args[2].length()>1){ //checks for use of a capital letter for the reference variable
            throw new IllegalArgumentException("Please use a capital letter for the reference variable");
          } else { //if previous check passed, store
            store(args[1].charAt(0),args[2].charAt(0));
          }
          String[] temp = new String[args.length-3]; //removes processed command in args
          for(int i = 3; i<args.length; i++){
            temp[i-3] = args[i];
          }
          args=temp;
        }
        if(args.length == 1 && !args[0].contains("=")){ //checks if user inputted an expression
          data[0]=Expression.evaluate(args[0])+"\n"; //change value of PREV
          rewrite(); //write changed PREV to file
          System.out.println("Ans: "+data[0].substring(0,data[0].length()-1)); //diaplay answer
        }
        if(args.length >= 2 && args[0].equals("graph")){ //checks if user inputted a function
          ArrayList<String> temporaryArray = new ArrayList<String>(); //stores each function if given multiple
          for(int i = 1; i < args.length; i++) { //adds each function to the array
            temporaryArray.add(args[i]);
          }
          Graph.graph(temporaryArray); //graph the functions
          System.out.println("Graph.png updated."); //print success message
          BufferedImage graph = ImageIO.read(new File(".\\Graph.png")); //store the image as a bufferedimage
          JPanel panel = new JPanel() { //create a panel to hold the image
            protected void paintComponent(Graphics g) { //override the method to draw an image
                  super.paintComponent(g);
                  g.drawImage(graph.getScaledInstance(1000, -1, Image.SCALE_SMOOTH), 0, 0, null);
            }
          };
          JFrame gui = new JFrame(); //create a new window
          gui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //set default close operation to close window but not stop program
          gui.setSize(1000,1000); //set size of window
          gui.setVisible(true); //make window visible
          gui.setTitle(temporaryArray.toString().substring(1,temporaryArray.toString().length()-1)); //change title to display graphs
          gui.add(panel); //add panel with image to the window
        }
        System.out.println("--------------------------------------------------------------------"); //print separation line
      } catch(IllegalArgumentException exception){ //catch errors to prevent exiting the program
        System.out.println(exception.getMessage()); //print error message and separation line
        System.out.println("--------------------------------------------------------------------");
      }
    }
  }
}
