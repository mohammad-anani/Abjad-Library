import java.util.Scanner;

public class Main {

    public static Scanner sc = new Scanner(System.in);

    public static Employee currentEmployee = new Employee();
    public static Branch currentBranch = new Branch();
    public static Library currentLibrary = new Library();
    public static boolean locked = false;
    public static boolean director = false;
    public static boolean manager = false;

    public static boolean startupScreen() {
        boolean start = false;
        System.out.println("------------------------------");
        System.out.println("         Startup Screen       ");
        System.out.println("------------------------------");
        System.out.println("Welcome to the library Management System.So far,there is no libraries in the system.Would you like create a new library(yes/no)?");
        start = sc.next().equalsIgnoreCase("yes");
        if (!start) {
            System.out.println("Thank you for using the library Management System.Program closed.");
            return false;
        }
        sc.nextLine();
        Library library = Creater.createLibrary();
        library.addBranch(Creater.createBranch().getID());
        library.updateLibrary();
        return true;
    }


    public static boolean loginScreen() {
        int attempts = 3;
        boolean successfulLogin = true;
        do {
            String branchID = "", employeeID = "";
            System.out.println("------------------------------");
            System.out.println("          Login Screen        ");
            System.out.println("------------------------------");
            if (!successfulLogin) {
                System.out.println("Invalid Branch and/or Employee ID.Try again.");
                attempts--;
                if (attempts == 0) {
                    return false;
                }
            }
            System.out.print("Enter branch ID: ");
            branchID = sc.next();
            System.out.print("Enter your employee ID: ");
            employeeID = sc.next();
            sc.nextLine();
            currentBranch = Branch.findByID(branchID);
            currentEmployee = Employee.findByID(employeeID);


            successfulLogin =  currentBranch != null && currentEmployee != null && (currentEmployee.existInBranch(currentBranch.getID()) || (director = currentEmployee.getRole().equals("director")) || (manager=currentEmployee.getRole().equals("manager") && currentBranch.getManager().getID().equals(currentEmployee.getID())));

        }
        while (!successfulLogin);
        return true;
    }


    public static int readChoice(int min, int max) {
        if (min >= max) {
            return -1;
        }
        int choice = min - 1;
        while (choice < min || choice > max) {
            System.out.print("Choose an option(" + min + "-" + max + "):");
            try {
                choice = sc.nextInt();

            } catch (Exception e) {
                choice = 1;
            }
        }
        sc.nextLine();
        return choice;
    }


    public static void main(String[] args) {

        boolean start = false;
        if (Library.loadFromFile().isEmpty()) {
            start = startupScreen();
            if (!start) {
                return;
            }
        }

        while (true) {
    currentEmployee=null;
    currentBranch=null;
    currentLibrary=null;
    director=false;
    manager=false;
           locked = !loginScreen();

            if (locked) {
                System.out.println("3 failed attempts.Program locked.");
                return;
            }
            currentLibrary=currentBranch.getLibrary();
            boolean exit = false;
            do {
                MenuScreen.mainMenuScreen();
                exit = PerformMenuOption.performMainMenuChoice(readChoice(1, 6));
            } while (!exit);
        }
    }
}