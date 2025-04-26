import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Branch {
    private String ID;
    private String contactInfoID;
    private String managerEmployeeID;
    private String openingHours;
    private ArrayList<String> employeesID = new ArrayList<>();
    private ArrayList<String> booksID = new ArrayList<>();
    private ArrayList<String> borrowingsID = new ArrayList<>();
    private static final String fileName = "src/Files/branches.txt";

    public Branch() {
    }

    public Branch(String ID, String contactInfoID, String managerEmployeeID, String openingHours,
                  ArrayList<String> employeesID, ArrayList<String> booksID, ArrayList<String> borrowingsID) {
        this.ID = ID;
        this.contactInfoID = contactInfoID;
        this.managerEmployeeID = managerEmployeeID;
        this.openingHours = openingHours;
        this.employeesID = employeesID;
        this.booksID = booksID;
        this.borrowingsID = borrowingsID;
    }

    public Branch(String contactInfoID, String managerEmployeeID, String openingHours) {
        this.contactInfoID = contactInfoID;
        this.managerEmployeeID = managerEmployeeID;
        this.openingHours = openingHours;
    }

    public Branch(String openingHours) {
        this.openingHours = openingHours;
    }

    public String getID() {
        return ID;
    }

    public ContactInfo getContactInfo() {
        return ContactInfo.findByID(contactInfoID);
    }

    public Employee getManager() {
        return Employee.findByID(managerEmployeeID);
    }

    public ArrayList<Employee> getEmployees() {
        return Employee.findByID(employeesID);
    }

    public ArrayList<String> getEmployeesID() {
        return employeesID;
    }

    public ArrayList<Book> getBooks() {
        return Book.findByID(booksID);
    }

    public ArrayList<String> getBooksID() {
        return booksID;
    }

    public ArrayList<String> getBorrowingsID() {
        return borrowingsID;
    }

    public ArrayList<Borrowing> getBorrowings() {
        return Borrowing.findByID(borrowingsID);
    }

    public void setContactInfoID(String contactInfoID) {
        this.contactInfoID = contactInfoID;
    }

    public void setManagerEmployeeID(String managerEmployeeID) {
        this.managerEmployeeID = managerEmployeeID;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public boolean addEmployee(String employeeID) {
        if (!employeesID.contains(employeeID)) {
            employeesID.add(employeeID);
            return true;
        }
        return false;
    }

    public boolean removeEmployee(String employeeID) {
        return employeesID.remove(employeeID);
    }

    public boolean addBook(String bookID) {
        if (!booksID.contains(bookID)) {
            booksID.add(bookID);
            return true;
        }
        return false;
    }

    public boolean removeBook(String bookID) {
        return booksID.remove(bookID);
    }

    public boolean addBorrowing(String borrowingID) {
        if (!borrowingsID.contains(borrowingID)) {
            borrowingsID.add(borrowingID);
            return true;
        }
        return false;
    }

    public boolean validate() {
        return !openingHours.isEmpty();
    }

    public Library getLibrary() {
        return Library.loadFromFile().stream().filter(l -> l.getBranchesID().contains(ID)).findFirst().orElse(null);
    }

    private boolean generateID() {
        if (!validate()) return false;
        Random rand = new Random();
        ID = "BRANCH_" + (rand.nextInt(9000) + 1000);
        return true;
    }

    public boolean addBranch() {
        if (generateID())
            return appendFile();
        return false;
    }

    public boolean updateBranch() {
        ArrayList<Branch> branches = loadFromFile();
        for (int i = 0; i < branches.size(); i++) {
            if (branches.get(i).getID().equals(this.ID)) {
                branches.set(i, this);
                return writeToFile(branches);
            }
        }
        return false;
    }

    public boolean deleteBranch() {
        if(!this.getContactInfo().deleteContactInfo()) return false;


        ArrayList<Branch> branches = loadFromFile();
        ArrayList<Branch> newBranches = new ArrayList<>();

        for(Branch b : branches) {
            if(!b.equals(this)) {
                newBranches.add(b);
            }
        }
                return writeToFile(newBranches);

    }


    public static ArrayList<Branch> loadFromFile() {
        ArrayList<Branch> branches = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    ArrayList<String> employees = !parts[4].equals("none") ? new ArrayList<>(Arrays.asList(parts[4].split("/"))) : new ArrayList<>();
                    ArrayList<String> books = !parts[5].equals("none") ? new ArrayList<>(Arrays.asList(parts[5].split("/"))) : new ArrayList<>();
                    ArrayList<String> borrowings = !parts[6].equals("none") ? new ArrayList<>(Arrays.asList(parts[6].split("/"))) : new ArrayList<>();
                    branches.add(new Branch(parts[0], parts[1], parts[2], parts[3], employees, books, borrowings));
                }
            }
        } catch (IOException e) {
            return new ArrayList<>();
        }
        return branches;
    }

    private boolean writeToFile(ArrayList<Branch> branches) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Branch b : branches) {
                bw.write(b.toCSVString());
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private boolean appendFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            bw.write(this.toCSVString());
            bw.newLine();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static Branch findByID(String ID) {
        return loadFromFile().stream()
                .filter(b -> b.getID().equals(ID))
                .findFirst()
                .orElse(null);
    }

    public static ArrayList<Branch> findByID(ArrayList<String> IDs) {
        ArrayList<Branch> all = loadFromFile();
        ArrayList<Branch> result = new ArrayList<>();
        for (Branch b : all) {
            if (IDs.contains(b.getID())) {
                result.add(b);
            }
        }
        return result;
    }

    public boolean equals(Branch b) {
        return this.ID.equals(b.getID());
    }

    public String toCSVString() {
        return ID + "," + contactInfoID + "," + managerEmployeeID + "," + openingHours + "," +
                (!employeesID.isEmpty() ? String.join("/", employeesID) : "none") + "," +
                (!booksID.isEmpty() ? String.join("/", booksID) : "none") + "," +
                (!borrowingsID.isEmpty() ? String.join("/", borrowingsID) : "none");
    }


    @Override
    public String toString() {
        return "------------------------------\n" +
                "           Branch             \n" +
                "------------------------------\n" +
                " ID              : " + ID + "\n" +
                " ManagerID       : " + managerEmployeeID + "\n" +
                " Hours           : " + openingHours + "\n" +
                " Nb of Employees : " + employeesID.size() + "\n" +
                " Nb of Books     : " + booksID.size() + "\n" +
                " Nb of Borrowings: " + borrowingsID.size() + "\n" +
                getContactInfo().toString() +
                "------------------------------";
    }
}
