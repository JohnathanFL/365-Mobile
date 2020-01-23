// Overengineering: The Movie
import java.util.*;

public class Assignment2 {
  ArrayList<String> emps = new ArrayList();
  Scanner input = new Scanner(System.in);

  public static String createMenu(ArrayList<String> list) {
    String result = "";
    int i = 0;
    for (String s : list) {
      result = result + "\t" + i + ". " + s + "\n";
    }
    return result;
  }

  public void addEmp() {
    while(true) {
      System.out.print("\tEnter the new employee's name: ");
      // Making sure we don't have something like ' John' instead of 'John'
      String name = this.input.nextLine().trim();
      int i = 0;
      // Going for a rustc style error message:
      // Input: J0hn
      // Output:
      // ERROR: May only have letters and spaces in employee names.
      // Error occured here:
      // J0hn
      //  ^
      for (char c : choice) {
        // Thus we allow only letters and spaces
        if (!Character.isLetter(c) && !Character.isSpace(c)) {
          System.out.println("ERROR: May only have letters and spaces in employee names.");
          System.out.printf("\t%s\n\t");
          for (int j = 0; j < i; j++) System.out.print(" ");
          System.out.println("^\n<Press Enter to go back to main menu>");
          Scanner.nextLine();
          continue;
        }
      }
      for (String emp : this.emps) {
        if (name.equals(emp)) {
          System.out.printf("\tERROR: Employee '%s' already in database.\n", name);
          continue;
        }
      }
      this.emps.add(name);
      System.out.printf("\tSUCCESS: Employee '%s' added to database.\n", name);
      break;
    }
  }
  public void delEmp() {
    // while(true): noun
    // 1. The poor man's goto
    // Ex: "You fool, why did you use a while(true)?"
    while(true) {
      // Could also do nextInt, but then we'd have to deal with a weird state where it tried to parse but failed
      String choice = this.input.nextLine();
      if (!choice.matches("^\\d+$")) {
        System.out.println("\tERROR: Enter a valid number.");
        continue;
      }
      int i = Integer.parseInt(choice);
      
      if (i >= this.emps.size()) {
        System.out.printf("\tERROR: Index out of range. Enter a number in range %d-%d\n", 0, this.emps.size() - 1);
        continue;
      }

      this.emps.remove(i);
      break;
    }
  }
  public void printEmp() {
    // Yay reusability
    createMenu(this.emps);
  }
  public void mainLoop() {
    boolean shouldQuit = false,
            // This is so we only repaint the menu when we get out of a submenu.
            dirty = true;
    do {
      if (dirty) {
        System.out.println(createMenu( new ArrayList(Arrays.asList(
          "Add New Employee",
          "Delete Employee",
          "Change Employee Name",
          "Print Employee Roster",
          "Quit"
        ))));
        dirty = false;
      }
      String choice = this.input.nextLine();

      if (!choice.matches("^\\d+$")) {
        System.out.println("ERROR: Enter a valid choice (number)");
        continue;
      }
      int i = Integer.parseInt(choice);
      if (i > 5 || i <= 0) {
        System.out.println("ERROR: Choice must be in range 1..=5");
      }
      // If we got this far, we need to repaint because there'll be a submenu painted soon
      dirty = true;
      switch (i) {
        case 1: addEmp(); break;
        case 2: delEmp(); break;
        case 3: chgEmp(); break;
        case 4: printEmp(); break;
        case 5: shouldQuit = true;
      }
    } while (!shouldQuit);
  }
  public static void main(String[] args) {
    (new Assignment2()).mainLoop();
  }

}
