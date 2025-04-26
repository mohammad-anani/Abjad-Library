public class Menu {


    public static void branchesMenu() {
        boolean exit = false;
        do {
            MenuScreen.branchesMenuScreen();
            exit = PerformMenuOption.performBranchesMenuChoice(Main.readChoice(1, 5));
        } while (!exit);
    }


    public static void bookMenu() {
        boolean exit = false;
        do {
            MenuScreen.booksMenuScreen();
            exit = PerformMenuOption.performBookMenuChoice(Main.readChoice(1, 9));
        } while (!exit);
    }

    public static void borrowingMenu() {
        boolean exit = false;
        do {
            MenuScreen.borrowingMenuScreen();
            exit = PerformMenuOption.performBorrowingMenuChoice(Main.readChoice(1, 5));
        } while (!exit);
    }


    public static void memberMenu() {
        boolean exit = false;
        do {
            MenuScreen.memberMenuScreen();
            exit = PerformMenuOption.performMemberMenuChoice(Main.readChoice(1,7));
        } while (!exit);
    }


    public static void branchMenu() {
        boolean exit = false;
        do {
            MenuScreen.branchMenuScreen();
            exit = PerformMenuOption.performBranchMenuChoice(Main.readChoice(1, 4));
        } while (!exit);
    }


    public static void employeeMenu() {
        boolean exit = false;
        do {
            MenuScreen.employeeMenuScreen();
            exit = PerformMenuOption.performEmployeeMenuChoice(Main.readChoice(1, 6));
        } while (!exit);
    }


    public static void libraryMenu() {
        boolean exit = false;
        do {
            MenuScreen.libraryMenuScreen();
            exit = PerformMenuOption.performLibraryMenuChoice(Main.readChoice(1, 4));
        } while (!exit);
    }
}
