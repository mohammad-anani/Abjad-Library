import java.util.Scanner;

public class Deleter {

    public static Scanner sc = new Scanner(System.in);

    public static void removeBranch(Branch branch) {

        if(branch==null)
        {
            return;
        }

        if (branch.equals(Main.currentBranch)) {
            System.out.println("You cannot remove the branch you logged in with.");
            return;
        }
        System.out.println("Are you sure you want to delete the branch? (yes/no):");
        if (sc.nextLine().equalsIgnoreCase("yes")) {
            Main.currentLibrary.removeBranch(branch.getID());
            if (Main.currentLibrary.updateLibrary() && branch.deleteBranch()) {
                System.out.println("Branch successfully removed.");
            } else
                System.out.println("Branch could not be deleted.");
        } else
            System.out.println("Branch could not be deleted.");
    }


    public static void deleteBookFromSystem(Book book) {

        if(book==null)
        {
            return;
        }

        if (Main.currentLibrary.getBranches().stream().anyMatch(b -> b.getBorrowings().stream().anyMatch(bo ->  bo.getBook().getID().equals(book.getID()) && bo.stillBorrowed() ))) {
            System.out.println("Book Still borrowed.");
            return;
        }


        System.out.println("Are you sure you want to delete this book from the system?");
        if (sc.nextLine().equalsIgnoreCase("yes")) {
            book.deleteBook();

            System.out.println("Book successfully deleted.");
        } else {
            System.out.println("Book could not be deleted.");
        }
    }


    public static void removeBookFromBranch(Book book) {

        if(book==null)
        {
            return;
        }

        if (Main.currentBranch.getBorrowings().stream().anyMatch(b -> b.getBook().getID().equals(book.getID()))) {
            System.out.println("Book Still borrowed.");
            return;
        }

        System.out.println("Are you sure you want to remove this book from the branch?");
        if (sc.nextLine().equalsIgnoreCase("yes")) {
            Main.currentBranch.removeBook(book.getID());
            if (Main.currentBranch.updateBranch())
                System.out.println("Book successfully removed");
            else
                System.out.println("Book could not be removed.");
        } else
            System.out.println("Book could not be removed.");
    }


    public static void deleteMember(Member member) {

        if(member==null)
        {
            return;
        }

        if (Main.currentLibrary.getBranches().stream().anyMatch(b -> b.getBorrowings().stream().anyMatch(bo -> bo.getMember().getID().equals(member.getID())))) {
            System.out.println("Member still borrowing a book.");
            return;
        }

        System.out.println("Are you sure you want to delete this member?");
        if (sc.nextLine().equalsIgnoreCase("yes")) {
            Main.currentLibrary.removeMember(member.getID());
            if (Main.currentLibrary.updateLibrary()) {
                System.out.println("Member successfully deleted.");
            } else
                System.out.println("Member not removed");
        } else
            System.out.println("Member could not be deleted.");
    }


    public static void deleteEmployee(Employee employee) {

        if(employee==null)
        {
            return;
        }

        if (employee.equals(Main.currentLibrary.getDirector()) || employee.equals(Main.currentBranch.getManager()) || employee.equals(Main.currentEmployee)) {
            System.out.println("You cannot delete current employee or current Branch manager or current Library director");
            System.out.println("You cannot delete current employee or current Branch manager or current Library director");
            return;
        }

        System.out.println("Are you sure you want to delete this employee?");
        if (sc.nextLine().equalsIgnoreCase("yes")) {
            Main.currentBranch.removeEmployee(employee.getID());
            if (Main.currentBranch.updateBranch())
                System.out.println("Employee successfully deleted.");
            else
                System.out.println("Employee could not be deleted.");
        } else
            System.out.println("Employee could not be deleted.");
    }


}
