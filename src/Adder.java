import java.util.Scanner;

public class Adder {

    public static Scanner sc = new Scanner(System.in);


    public static void addBranch() {
        Main.currentLibrary.addBranch(Creater.createBranch().getID());
        Main.currentLibrary.updateLibrary();
    }

    public static void addBook() {
        Book book = Creater.createBook();

    }

    public static void addBookToBranch() {


        System.out.println("Do you want to add a book(yes),or add an existing Book(no)?");
        if (sc.next().equalsIgnoreCase("yes")) {

            Main.currentBranch.addBook(Creater.createBook().getID());

        } else {
            boolean exist = false;
            String bookID;
            do {
                System.out.print("Enter Book ID: ");
                bookID = sc.next();
                exist = Book.findByID(bookID) != null;

                if (Main.currentBranch.getBooksID().contains(bookID)) {
                    System.out.println("Book already exists in branch.");
                    exist = false;
                }
            } while (!exist);
            Main.currentBranch.addBook(bookID);
        }
        sc.nextLine();

        if (Main.currentBranch.updateBranch())
            System.out.println("Book successfully added.");
        else
            System.out.println("Book adding failed");

    }

    public static void addBorrowing() {
        Main.currentBranch.addBorrowing(Creater.createBorrowing().getID());
        Main.currentBranch.updateBranch();
    }


    public static void addMember() {
        Main.currentLibrary.addMember(Creater.createMember().getID());
        Main.currentLibrary.updateLibrary();
    }

    public static void addEmployee() {
        Main.currentBranch.addEmployee(Creater.createEmployee("normal").getID());
        Main.currentBranch.updateBranch();
    }
}
