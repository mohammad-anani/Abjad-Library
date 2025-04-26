public class PerformMenuOption {

    public static boolean performLibraryMenuChoice(int choice) {
        switch (choice) {
            case 1:
                Displayer.displayLibrary();
                break;
            case 2:
                Menu.branchesMenu();
                break;
            case 3:
                Editer.editLibrary();
                break;
            case 4:
                return true;
        }
        return false;
    }

    public static boolean performBranchMenuChoice(int choice) {
        switch (choice) {
            case 1:
                Displayer.displayBranch();
                break;
            case 2:
                Menu.employeeMenu();
                break;
            case 3:
                Editer.editBranch();
                break;
            case 4:
                return true;
        }
        return false;
    }

    public static boolean performBranchesMenuChoice(int choice) {
        switch (choice) {
            case 1:
                Displayer.displayBranches();
                break;
            case 2:
                Adder.addBranch();
                break;
            case 3:
                Finder.findBranch();
                break;
            case 4:
                Deleter.removeBranch(Finder.findBranch());
                break;
            case 5:
                return true;

        }
        return false;
    }

    public static boolean performEmployeeMenuChoice(int choice) {
        switch (choice) {
            case 1:
                Displayer.displayEmployees();
                break;
            case 2:
                Adder.addEmployee();
                break;
            case 3:
                Editer.editEmployee(Finder.findEmployee());
                break;
            case 4:
                Finder.findEmployee();
                break;
            case 5:
                Deleter.deleteEmployee(Finder.findEmployee());
                break;
            case 6:
                return true;
        }
        return false;
    }

    public static boolean performMemberMenuChoice(int choice) {
        switch (choice) {
            case 1:
                Displayer.displayMembers();
                break;
            case 2:
                Adder.addMember();
                break;
            case 3:
                Editer.renewMembership(Finder.findMember());
                break;
            case 4:
                Editer.editMember(Finder.findMember());
                break;
            case 5:
                Finder.findMember();
                break;
            case 6:
                Deleter.deleteMember(Finder.findMember());
                break;
            case 7:
                return true;
        }
        return false;
    }

    public static boolean performBookMenuChoice(int choice) {
        switch (choice) {
            case 1:
                Displayer.displayAllBooks();
                break;
            case 2:
                Displayer.displayBooks();
                break;
            case 3:
                Adder.addBook();
                break;
            case 4:
                Adder.addBookToBranch();
                break;
            case 5:
                Finder.findBookInSystem();
                break;
            case 6:
                Finder.findBookInBranch();
                break;
            case 7:
                Deleter.deleteBookFromSystem(Finder.findBookInSystem());
                break;
            case 8:
                Deleter.removeBookFromBranch(Finder.findBookInBranch());
                break;
            case 9:
                return true;
        }
        return false;
    }

    public static boolean performBorrowingMenuChoice(int choice) {
        switch (choice) {
            case 1:
                Displayer.displayBorrowings();
                break;
            case 2:
                Adder.addBorrowing();
                break;
            case 3:
                Editer.returnBook(Finder.findBorrowing());
                break;
            case 4:
                Finder.findBorrowing();
                break;
            case 5:
                return true;
        }
        return false;
    }

    public static boolean performMainMenuChoice(int choice) {
        switch (choice) {
            case 1:
                if (!Main.director) {
                    System.out.println("Access denied.");
                    break;
                }
                Menu.libraryMenu();
                break;
            case 2:
                if (!(Main.manager || Main.director)) {
                    System.out.println("Access denied.");
                    break;
                }
                Menu.branchMenu();
                break;
            case 3:
                Menu.memberMenu();
                break;
            case 4:
                Menu.bookMenu();
                break;
            case 5:
                Menu.borrowingMenu();
                break;
            case 6:
                return true;
        }
        return false;
    }

}
