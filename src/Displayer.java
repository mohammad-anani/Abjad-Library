import java.util.ArrayList;
import java.util.Scanner;

public class Displayer {

    public static Scanner sc = new Scanner(System.in);

    public static void displayLibrary() {
        System.out.println(Main.currentLibrary.toString());
    }


    public static void displayBranch() {
        System.out.println(Main.currentBranch.toString());
    }

    public static void displayEmployees() {
        ArrayList<Employee> employees = Main.currentBranch.getEmployees();

        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        for (Employee e : employees) {
            System.out.println(e.toString());
        }
    }

    public static void displayMembers() {
        ArrayList<Member> members = Main.currentLibrary.getMembers();

        if (members.isEmpty()) {
            System.out.println("No members found.");
            return;
        }

        for (Member m : members) {
            System.out.println(m.toString());
        }
    }

    public static void displayAllBooks() {
        ArrayList<Book> books = Book.loadBookFromFile();

        if (books.isEmpty()) {
            System.out.println("No books found.");
            return;
        }

        for (Book b : books) {
            System.out.println(b.toString());
        }
    }

    public static void displayBooks() {
        ArrayList<Book> books = Main.currentBranch.getBooks();

        if (books.isEmpty()) {
            System.out.println("No books found.");
            return;
        }

        for (Book b : books) {
            System.out.println(b.toString());
        }
    }


    public static void displayBranches() {
        ArrayList<Branch> branches = Main.currentLibrary.getBranches();

        if (branches.isEmpty()) {
            System.out.println("No branches found.");
            return;
        }

        for (Branch b : branches) {
            System.out.println(b.toString());
        }

    }

    public static void displayBorrowings() {
        ArrayList<Borrowing> borrowings = Main.currentBranch.getBorrowings();

        if (borrowings.isEmpty()) {
            System.out.println("No borrowings found.");
        }

        for (Borrowing b : borrowings) {
            System.out.println(b.toString());
        }
    }
}
