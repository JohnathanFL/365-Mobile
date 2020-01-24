/*
Simple demonstration of scanners and various ways of iterating.
Johnathan Lee
Due 02/24/20
*/
import java.util.*;

public class Lab2 {
  public static ArrayList<Integer> readScores() {
    Scanner input = new Scanner(System.in);
    ArrayList<Integer> tmpList = new ArrayList<Integer>();
    int num;
    System.out.println("Enter a score, -999 to quit: ");
    num = input.nextInt();
    while (num != -999) {
      tmpList.add(num);
      System.out.println("Enter a score -999 to quit: ");
      num = input.nextInt();
    }

    return tmpList;
  }
  public static void displayList(ArrayList<Integer> tmpList) {
    for (int ct = 0; ct < tmpList.size(); ct++)
      System.out.println(tmpList.get(ct));
  }

  public static void main(String[] args) {
    ArrayList<Integer> numList = new ArrayList();
    numList = readScores();
    displayList(numList);
    displayListV2(numList);
    displayListV3(numList);

    System.out.println("\nAfter deleting 10: ");

    numList = delScore(numList, 10);
    displayList(numList);
  }

  public static void displayListV2(ArrayList<Integer> tmpList) {
    for (int n : tmpList) System.out.printf("%d %s", n, " ");
    System.out.println();
  }

  public static void displayListV3(ArrayList<Integer> tmpList) {
    Iterator<Integer> i = tmpList.iterator();
    while (i.hasNext()) {
      Object obj = i.next();
      System.out.println(obj);
    }
  }

  public static ArrayList<Integer> delScore(ArrayList<Integer> tmpList, int dSc) {
    ArrayList<Integer> myList = new ArrayList();
    for (int pos = 0; pos < tmpList.size(); pos++)
      if (tmpList.get(pos) != dSc)
        myList.add(tmpList.get(pos));

    return myList;
  }
}
