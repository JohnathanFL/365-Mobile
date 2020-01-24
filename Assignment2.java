// Overengineering: The Movie
import java.util.*;

public class Assignment2 {
  final static ArrayList<String> menu = new ArrayList(Arrays.asList(
          "Add New Employee",
          "Delete Employee",
          "Change Employee Name",
          "Print Employee Roster",
          "Quit"
        ));
  static Scanner input = new Scanner(System.in);
  ArrayList<String> emps = new ArrayList();

  public static String createMenu(ArrayList<String> list) {
    String result = "";
    int i = 0;
    for (String s : list) {
      // ++i so we get 1-indexing
      result = result + "\t" + ++i + ". " + s + "\n";
    }
    return result;
  }


  private String getNewName(boolean mustUnique) {
    // Labeled since we have nested to break from
    mainLoop: while(true) {
      System.out.print("Enter the new employee's name (or empty for cancel): ");
      // Making sure we don't have something like ' John' instead of 'John'
      String name = this.input.nextLine().trim();
      // Going for a rustc style error message:
      // Input: J0hn
      // Output:
      // ERROR: May only have letters and spaces in employee names.
      // Error occured here:
      // J0hn
      //  ^

      int i = 0;
      // Because java doesn't allow foreach on a string... for reasons...
      for (char c : name.toCharArray()) {
        // Thus we allow only letters and spaces
        if (!Character.isLetter(c) && !Character.isSpace(c)) {
          System.out.println("ERROR: May only have letters and spaces in employee names.");
          System.out.printf("\t%s\n\t", name);
          for (int j = 0; j < i; j++) System.out.print(" ");
          System.out.println("^");
          continue mainLoop;
        }
        i++;
      }
      if (mustUnique) {
        for (String emp : this.emps) {
          if (name.equals(emp)) {
            System.out.printf("\tERROR: Employee '%s' already in database.\n", name);
            continue mainLoop;
          }
        }
      }
      return name;
    }
    }

  private static int chooseFrom(ArrayList<String> list, String prompt) {
    System.out.print(createMenu(list));
    // while(true): noun
    // 1. The poor man's goto
    while (true) {
      System.out.print(prompt);
      String choice = input.nextLine();
      if (!choice.matches("^\\d+$")) {
        System.out.println("ERROR: You must enter a NUMBER. (Digits only)");
        continue;
      }
      int index = Integer.parseInt(choice);
      // -1 because we'll let the user do 1-indexing instead of 0
      if (index - 1 >= list.size()) {
        System.out.printf("ERROR: Your choice must be in the range 1-%d\n", list.size());
        continue;
      }

      return index - 1;
    }
  }
  private static void waitForEnter() {
    System.out.println("<Any key to continue...>");
    input.nextLine();
    // The poor man's system("clear")
    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
  }
  
  public void addEmp() {
    String name = getNewName(true);
    if (name.isEmpty()) return;
    this.emps.add(name);
    System.out.printf("\tSUCCESS: Employee '%s' added to database.\n", name);
    waitForEnter();
  }
  
  public void delEmp() {
    if(this.emps.size() == 0) {
      System.out.println("ERROR: Ain't no employees in the database to delete!");
      waitForEnter();
      return;
    }
    int index = chooseFrom(this.emps, "Pick an employee from the list above to delete: ");
    String oldName = this.emps.get(index);
    this.emps.remove(index);
    System.out.printf("\tSUCCESS: Employee '%s' removed from the database.\n", oldName);
    waitForEnter();
  }

  public void printEmp() {
    System.out.println("All employees:");
    // Yay for reusability!
    System.out.print(createMenu(this.emps));
    waitForEnter();
  }
  public void chgEmp() {
    if(this.emps.size() == 0) {
      System.out.println("ERROR: Ain't no employees in the database to change!");
      System.out.println("<Press enter to continue>");
      input.nextLine();
      return;
    }
    // ... and another three cheers for reusability!
    int index = chooseFrom(this.emps, "Pick an employee from the list above to change: ");
    String newName = getNewName(true);
    if (newName.isEmpty()) return;
    String oldName = this.emps.get(index);
    this.emps.set(index, newName); 
    System.out.printf("\tSUCCESS: Employee '%s' is now known as '%s'.\n", oldName, newName);
    waitForEnter();
  }
  public void mainLoop() {
    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    System.out.println("Welcome to the Employanator 9000");
    boolean shouldQuit = false;
    do {
      int choice = chooseFrom(menu, ">>> ");
      switch (choice) {
        // Since I built chooseFrom to assume input 1-indexing -> output 0-indexing,
        // we use 0-indexing here
        case 0: addEmp(); break;
        case 1: delEmp(); break;
        case 2: chgEmp(); break;
        case 3: printEmp(); break;
        case 4:
          System.out.println("Adiaŭ!");
          shouldQuit = true;
          break;
        default: // unreachable, as chooseFrom already checked bounds
          break;
      }
    } while (!shouldQuit);
  }
  public static void main(String[] args) {
    (new Assignment2()).mainLoop();
  }

}
