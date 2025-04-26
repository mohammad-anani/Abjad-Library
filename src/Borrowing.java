import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;

public class Borrowing {
    private String ID = "";
    private String memberID = "";
    private String bookID = "";
    private String borrowDate = "";
    private String dueDate = "";
    private String returnDate = "";
    private int lateFees = 0;
    private static final String fileName = "src/Files/borrowings.txt";

    public Borrowing(String ID, String memberID, String bookID, String borrowDate, String dueDate, String returnDate, int lateFees) {
        this.ID = ID;
        this.memberID = memberID;
        this.bookID = bookID;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.lateFees = lateFees;
    }

    public Borrowing(String memberID, String bookID) {
        this.memberID = memberID;
        this.bookID = bookID;

    }

    public String getID() {
        return ID;
    }

    public Member getMember() {
        return Member.findByID(memberID);
    }

    public Book getBook() {
        return Book.findByID(bookID);
    }

    public int getLateFees() {
        return lateFees;
    }

    public int setReturnDate() {

        LocalDate now = LocalDate.now();
        LocalDate due = LocalDate.parse(dueDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        int lateDays = (int) ChronoUnit.DAYS.between(due, now);

        if (lateDays > 0)
            lateFees = getMember().getLibrary().getLateFees()[(lateDays - 1) / 2];
        else
            lateFees = 0;
        this.returnDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return Math.max(0, lateDays);
    }

    public boolean stillBorrowed()
    {
       return  returnDate.isEmpty();
    }

    public boolean generateID() {

        Random rand = new Random();
        this.ID = this.getBook().getID().substring(0, 3) + "_" + getMember().getID().substring(0, 3) + "_" + (rand.nextInt(9000) + 1000);
        return true;
    }

    public boolean addBorrowing() {
        if (generateID()) {
            this.borrowDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            this.dueDate = LocalDate.now().plusDays(getMember().getLibrary().getBorrowingDays()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            return this.appendToFile();
        }
        return false;
    }

    public boolean updateBorrowing() {
        ArrayList<Borrowing> borrowings = loadFromFile();
        for (int i = 0; i < borrowings.size(); i++) {
            if (borrowings.get(i).equals(this)) {
                borrowings.set(i, this);
                return writeToFile(borrowings);
            }
        }
        return false;
    }

    public static ArrayList<Borrowing> loadFromFile() {
        ArrayList<Borrowing> borrowings = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    borrowings.add(new Borrowing(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5].equals("none")?"":parts[5], Integer.parseInt(parts[6])));
                }
            }
        } catch (IOException e) {
            return new ArrayList<>();
        }
        return borrowings;
    }

    private static boolean writeToFile(ArrayList<Borrowing> borrowings) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Borrowing b : borrowings) {
                bw.write(b.toCSVString());
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean appendToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            bw.write(this.toCSVString());
            bw.newLine();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static Borrowing findByID(String ID) {
        for (Borrowing b : loadFromFile()) {
            if (b.getID().equals(ID)) {
                return b;
            }
        }
        return null;
    }

    public static ArrayList<Borrowing> findByID(ArrayList<String> IDs) {
        ArrayList<Borrowing> found = new ArrayList<>();
        for (String id : IDs) {
            Borrowing b = findByID(id);
            if (b != null) {
                found.add(b);
            }
        }
        return found;
    }

    public boolean equals(Borrowing b) {
        return this.ID.equals(b.getID());
    }

    @Override
    public String toString() {
        return "------------------------------\n" +
                "           Borrowing         \n" +
                "------------------------------\n" +
                " ID         : " + ID + "\n" +
                " BorrowDate : " + borrowDate + "\n" +
                " DueDate    : " + dueDate + "\n" +
                " ReturnDate : " + returnDate + "\n" +
                getMember().toString() +
                getBook().toString() +
                "------------------------------";
    }

    public String toCSVString() {
        return ID + "," + memberID + "," + bookID + "," + borrowDate + "," + dueDate + "," + (returnDate.isEmpty()?"none":returnDate)+ "," + lateFees;
    }
} 