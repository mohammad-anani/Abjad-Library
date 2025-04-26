import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class Member extends Person {
    private String ID = "";
    private String membershipEnd = "";

    private static final String fileName = "src/Files/members.txt";

    public Member() {
    }


    public Member(String ID, String membershipDate, String fullName, int age, String gender, String contactInfoID) {

        super(fullName, age, gender, contactInfoID);
        this.ID = ID;
        this.membershipEnd = membershipDate;

    }

    public Member(String fullName, int age, String gender, String contactInfoID) {

        super(fullName, age, gender, contactInfoID);

    }

    public Member(String fullName, int age, String gender) {

        super(fullName, age, gender);

    }


    public String getID() {
        return ID;
    }

    public String getMembershipEnd() {
        return membershipEnd;
    }

    public Library getLibrary() {
        return Library.loadFromFile().stream().filter(l -> l.getMembers().stream().anyMatch(m -> m.equals(this))).findFirst().orElse(new Library());
    }

    public void renewMembership() {
        this.membershipEnd = LocalDate.parse(this.membershipEnd, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                .plusMonths(getLibrary().getMembershipDurationInMonths())
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public boolean addMember() {
        this.membershipEnd = LocalDate.now().plusMonths(Main.currentLibrary.getMembershipDurationInMonths()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        if (generateID()) {
            return appendToFile();
        }
        return false;
    }

    public boolean updateMember() {
        ArrayList<Member> members = loadMembersFromFile();
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).equals(this)) {
                members.set(i, this);
                return writeToFile(members);
            }
        }
        return false;
    }

    public static ArrayList<Member> loadMembersFromFile() {
        ArrayList<Member> members = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    members.add(new Member(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), parts[4], parts[5]));
                }
            }
        } catch (IOException e) {
            return new ArrayList<>();
        }
        return members;
    }

    private boolean writeToFile(ArrayList<Member> members) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Member m : members) {
                bw.write(m.toCSVString());
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

    public boolean validate() {
        return super.validate();
    }

    private boolean generateID() {
        if (!validate()) return false;
        String[] dates = membershipEnd.split("/");
        Random rand = new Random();
        this.ID = dates[2] + dates[1] + dates[0] + "_" + (rand.nextInt(9000) + 1000);
        return true;
    }

    public static Member findByID(String ID) {
        return loadMembersFromFile().stream().filter(m -> m.getID().equals(ID)).findFirst().orElse(new Member());
    }

    public static ArrayList<Member> findByID(ArrayList<String> IDs) {
        ArrayList<Member> members = new ArrayList<Member>();
        for (Member m : loadMembersFromFile()) {
            if (IDs.contains(m.getID())) members.add(m);
        }
        return members;
    }

    public boolean equals(Member m) {
        return this.ID.equals(m.getID());
    }

    public String toCSVString() {
        return ID + "," + membershipEnd + "," + super.toCSVString();
    }

    @Override
    public String toString() {
        String s = "------------------------------\n";
        s += "           Member             \n";
        s += "------------------------------\n";
        s += " ID             : " + ID + "\n";
        s += " MembershipEnd : " + membershipEnd + "\n";
        s += super.toString();
        s += "------------------------------\n";
        return s;
    }
}