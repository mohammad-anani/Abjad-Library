import java.util.Scanner;

public class Finder {

    public static Scanner sc = new Scanner(System.in);

    public static Branch findBranch() {

        System.out.println("------------------------------");
        System.out.println("      Find Branch Screen");
        System.out.println("------------------------------");
        String branchID;
        System.out.print("Enter branch ID: ");
        branchID = sc.nextLine();
        Branch branch = Branch.findByID(branchID);
        if (Main.currentLibrary.getBranchesID().contains(branchID)) {
            System.out.println("Branch successfully found.");
            System.out.println(branch);
        } else {
            System.out.println("Branch could not be found.");
            return null;
        }

        return branch;
    }

    public static Employee findEmployee() {
        System.out.println("------------------------------");
        System.out.println("      Find Employee Screen");
        System.out.println("------------------------------");
        String employeeID;
        System.out.print("Enter employee ID: ");
        employeeID = sc.nextLine();
        if (Main.currentBranch.getEmployeesID().contains(employeeID)) {
            Employee employee = Employee.findByID(employeeID);
            System.out.println("Employee successfully found.");
            System.out.println(employee);
            return employee;
        } else {
            System.out.println("Employee could not be found.");
            return null;
        }
    }

    public static Member findMember() {
        System.out.println("------------------------------");
        System.out.println("      Find Member Screen");
        System.out.println("------------------------------");
        String memberID;
        System.out.print("Enter member ID: ");
        memberID = sc.nextLine();
        if (Main.currentLibrary.getMembersID().contains(memberID)) {
            Member member = Member.findByID(memberID);
            System.out.println("Member successfully found.");
            System.out.println(member);
            return member;
        } else {
            System.out.println("Member could not be found.");
            return null;
        }
    }

    public static Borrowing findBorrowing() {
        System.out.println("------------------------------");
        System.out.println("      Find Borrowing Screen");
        System.out.println("------------------------------");
        String borrowingID;
        System.out.print("Enter borrowing ID: ");
        borrowingID = sc.nextLine();
        if (Main.currentBranch.getBorrowingsID().contains(borrowingID)) {
            Borrowing borrowing = Borrowing.findByID(borrowingID);
            System.out.println("Borrowing successfully found.");
            System.out.println(borrowing);
            return borrowing;
        } else {
            System.out.println("Member could not be found.");
            return null;
        }
    }

    public static Book findBookInBranch() {
        System.out.println("------------------------------");
        System.out.println("  Find Book in Branch Screen");
        System.out.println("------------------------------");
        String bookID;
        System.out.print("Enter book ID: ");
        bookID = sc.nextLine();
        if (Main.currentBranch.getBooksID().contains(bookID)) {
            Book book = Book.findByID(bookID);
            System.out.println("Book successfully found.");
            System.out.println(book);
            return book;
        } else {
            System.out.println("Book could not be found.");
            return null;
        }
    }

    public static Book findBookInSystem() {
        System.out.println("------------------------------");
        System.out.println("  Find Book in Sytem Screen");
        System.out.println("------------------------------");
        String bookID;
        System.out.print("Enter book ID: ");
        bookID = sc.nextLine();
        Book book = Book.loadBookFromFile().stream().filter(b -> b.getID().equals(bookID)).findFirst().orElse(null);
        if (book != null) {
            System.out.println("Book successfully found.");
            System.out.println(book);
            return book;
        } else {
            System.out.println("Book could not be found.");
            return null;
        }
    }

}
