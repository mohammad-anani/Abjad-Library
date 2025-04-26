import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Library implements ILibrary {
    private String ID;
    private String name;
    private String directorEmployeeID;
    private String type;
    private int membershipDurationInMonths;
    private int borrowingDays;
    private int[] lateFees = new int[4];
    private ArrayList<String> branchesID = new ArrayList<>();
    private ArrayList<String> membersID = new ArrayList<>();

    private static final String fileName = "src/Files/libraries.txt";

    public Library() {
    }

    public Library(String ID, String name, String directorEmployeeID, String type,
                   int membershipDurationInMonths, int borrowingDays, int[] lateFees,
                   ArrayList<String> branchesID, ArrayList<String> membersID) {
        this.ID = ID;
        this.name = name;
        this.directorEmployeeID = directorEmployeeID;
        this.type = type;
        this.membershipDurationInMonths = membershipDurationInMonths;
        this.borrowingDays = borrowingDays;
        this.lateFees = lateFees;
        this.branchesID = branchesID;
        this.membersID = membersID;
    }

    public Library(String name, String type, int membershipDurationInMonths, int borrowingDays, int[] lateFees) {
        this.name = name;
        this.type = type;
        this.membershipDurationInMonths = membershipDurationInMonths;
        this.borrowingDays = borrowingDays;
        this.lateFees = lateFees;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public Employee getDirector() {
        return Employee.findByID(directorEmployeeID);
    }

    public String getType() {
        return type;
    }

    public int getMembershipDurationInMonths() {
        return membershipDurationInMonths;
    }

    public void setMembershipDurationInMonths(int duration) {
        this.membershipDurationInMonths = duration;
    }

    public int getBorrowingDays() {
        return borrowingDays;
    }

    public void setBorrowingDays(int borrowingDays) {
        this.borrowingDays = borrowingDays;
    }

    public int[] getLateFees() {
        return lateFees;
    }


    public void setLateFees(int[] l) {
        lateFees = l;
    }

    public void setDirectorEmployeeID(String directorEmployeeID) {
        this.directorEmployeeID = directorEmployeeID;
    }

    public boolean validate() {
        String types = "public,private,college,school";
        return !name.isEmpty() && types.contains(type) && borrowingDays > 10 && membershipDurationInMonths >= 1;
    }

    public boolean generateID() {
        if (!validate()) return false;
        this.ID = name.substring(0, 5) + "_" + getDirector().fullName.substring(0, 2) + "_" + type.substring(0, 2);
        return true;
    }

    public boolean addLibrary() {
        if (generateID())
            return this.appendToFile();
        return false;
    }

    public boolean updateLibrary() {
        ArrayList<Library> libraries = loadFromFile();
        for (int i = 0; i < libraries.size(); i++) {
            if (libraries.get(i).getID().equals(this.ID)) {
                libraries.set(i, this);
                return writeToFile(libraries);
            }
        }
        return false;
    }

    public boolean addBranch(String branchID) {
        if (!branchesID.contains(branchID)) {
            branchesID.add(branchID);
            return true;
        }
        return false;
    }

    public boolean removeBranch(String branchID) {
        return branchesID.remove(branchID);
    }

    public boolean addMember(String memberID) {
        if (!membersID.contains(memberID)) {
            membersID.add(memberID);
            return true;
        }
        return false;
    }

    public boolean removeMember(String memberID) {
        return membersID.remove(memberID);
    }

    public ArrayList<Branch> getBranches() {
        return Branch.findByID(branchesID);
    }

    public ArrayList<String> getBranchesID() {
        return branchesID;
    }

    public ArrayList<Member> getMembers() {
        return Member.findByID(membersID);
    }

    public ArrayList<String> getMembersID() {
        return membersID;
    }

    public static ArrayList<Library> loadFromFile() {
        ArrayList<Library> libraries = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 9) {
                    String ID = parts[0];
                    String name = parts[1];
                    String directorID = parts[2];
                    String type = parts[3];
                    int membershipDuration = Integer.parseInt(parts[4]);
                    int borrowingDays = Integer.parseInt(parts[5]);

                    String[] sLateFees = parts[6].split("/");
                    int[] lateFees = new int[4];
                    for (int i = 0; i < 4; i++) {
                        lateFees[i] = Integer.parseInt(sLateFees[i]);
                    }

                    ArrayList<String> membersID = !parts[7].equals("none") ? new ArrayList<>(Arrays.asList(parts[7].split("/"))) : new ArrayList<>();
                    ArrayList<String> branchesID = !parts[8].equals("none") ? new ArrayList<>(Arrays.asList(parts[8].split("/"))) : new ArrayList<>();

                    libraries.add(new Library(ID, name, directorID, type, membershipDuration, borrowingDays, lateFees, branchesID, membersID));
                }
            }
        } catch (IOException e) {
            return new ArrayList<>();
        }
        return libraries;
    }

    private boolean writeToFile(ArrayList<Library> libraries) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Library l : libraries) {
                bw.write(l.toCSVString());
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private boolean appendToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            bw.write(this.toCSVString());
            bw.newLine();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static Library findByID(String ID) {
        return loadFromFile().stream()
                .filter(l -> l.getID().equals(ID))
                .findFirst()
                .orElse(null);
    }

    public boolean equals(Library l) {
        return this.ID.equals(l.getID());
    }


    public String toCSVString() {
        String[] strFees = new String[lateFees.length];
        for (int i = 0; i < lateFees.length; i++) {
            strFees[i] = String.valueOf(lateFees[i]);
        }

        return ID + "," +
                name + "," +
                directorEmployeeID + "," +
                type + "," +
                membershipDurationInMonths + "," +
                borrowingDays + "," +
                String.join("/", strFees) + "," +
                (!membersID.isEmpty() ? String.join("/", membersID) : "none") + "," +
                (!branchesID.isEmpty() ? String.join("/", branchesID) : "none");
    }

    @Override
    public String toString() {

        NumberFormat fmt= NumberFormat.getCurrencyInstance();


        String s = "------------------------------\n" +
                "           Library            \n" +
                "------------------------------\n" +
                " ID              : " + ID + "\n" +
                " Name            : " + name + "\n";
        s +=    " Type            : " + type + "\n" +
                " MbShip Duration : " + membershipDurationInMonths + " months\n" +
                " Borrow Days     : " + borrowingDays + " days\n" +
                " LateFees        : 1-2 days: " + fmt.format(lateFees[0]) +
                "\n                   3-4 days: " + fmt.format(lateFees[1]) +
                "\n                   5-6 days: " + fmt.format(lateFees[2]) +
                "\n                   7-8 days: " + fmt.format(lateFees[3]) + "\n";
        s += getDirector().toString();
        s += "------------------------------";
        return s;
    }
}
