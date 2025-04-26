import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

public class Creater {

    public static Scanner sc = new Scanner(System.in);

    public static Library createLibrary() {
        Library library = new Library();
        String name, type;
        int membershipDurationInMonths, borrowingDays;
        int[] lateFees = new int[4];
        boolean invalid = false;
        do {
            System.out.println("------------------------------");
            System.out.println("         Library Adding       ");
            System.out.println("------------------------------");
            if (invalid) {
                System.out.println("You entered invalid data.Try again.");
            }
            System.out.print("Enter Library Name: ");
            name = sc.nextLine();

            System.out.print("Enter Library Type: ");
            type = sc.nextLine();
            System.out.print("Enter Library Membership Duration in Months: ");
            try {
                membershipDurationInMonths = sc.nextInt();
            } catch (Exception e) {
                membershipDurationInMonths = 6;
                System.out.println("Integer not entered,replaced with a default value of 6.");
                sc.nextLine();
            }

            System.out.print("Enter Library Borrowing Days: ");
            try {
                borrowingDays = sc.nextInt();
            } catch (Exception e) {
                borrowingDays = 30;
                System.out.println("Integer not entered,replaced with a default value of 30.");
                sc.nextLine();
            }

            System.out.println("Enter Library Late Fees: ");
            for (int i = 0; i < lateFees.length; i++) {
                System.out.print("Enter fees for " + ((i * 2) + 1) + "-" + ((i * 2) + 2) + " days:");
                try {
                    lateFees[i] = sc.nextInt();
                } catch (Exception e) {
                    lateFees[i] = 5;
                    System.out.println("Integer not entered,replaced with a default value of 5$.");
                    sc.nextLine();
                }

            }
            sc.nextLine();
            library = new Library(name, type, membershipDurationInMonths, borrowingDays, lateFees);
            invalid = !library.validate();
        } while (invalid);
        Employee director = createEmployee("director");
        if (director == null) {

            System.out.println("Library adding failed");
            System.out.println("------------------------------");
            return null;
        }
        library.setDirectorEmployeeID(director.getID());
        if (library.addLibrary()) {
            System.out.println("Library added successfully with ID: " + library.getID() + ".");
            System.out.println("------------------------------");
            return library;
        } else {
            System.out.println("Library adding failed");
            System.out.println("------------------------------");
            return null;
        }
    }

    public static ContactInfo createContactInfo() {
        ContactInfo contactInfo = new ContactInfo();
        String phone, email, address;
        boolean invalid = false;
        do {
            System.out.println("------------------------------");
            System.out.println("     Contact Info Adding      ");
            System.out.println("------------------------------");
            if (invalid) {
                System.out.println("You entered invalid data.Try again.");
            }
            System.out.print("Enter Phone Number:");
            phone = sc.next();
            System.out.print("Enter Email:");
            email = sc.next();
            sc.nextLine();
            System.out.print("Enter Address:");
            address = sc.nextLine();
            contactInfo = new ContactInfo(address, email, phone);
            invalid = !contactInfo.validate();
        } while (invalid);
        if (contactInfo.addContactInfo()) {
            System.out.println("Contact Info added successfully.");
            System.out.println("------------------------------");
            return contactInfo;
        } else {
            System.out.println("Contact Info adding failed");
            System.out.println("------------------------------");
            return null;
        }

    }

    public static Member createMember() {
        Member member = new Member();
        String name, gender;
        int age;
        boolean invalid = false;
        do {
            System.out.println("------------------------------");
            System.out.println("        Member Adding       ");
            System.out.println("------------------------------");
            if (invalid) {
                System.out.println("You entered invalid data.Try again.");
            }
            System.out.print("Enter Full Name:");
            name = sc.nextLine();
            System.out.print("Enter age:");
            try {
                age = sc.nextInt();
            } catch (Exception e) {
                age = 20;
                System.out.println("Integer not entered,replaced with a default value of 20.");
                sc.nextLine();
            }
            sc.nextLine();
            System.out.print("Enter gender:");
            gender = sc.next();
            sc.nextLine();
            member = new Member(name, age, gender);
            invalid = !member.validate();
        } while (invalid);
        ContactInfo contactInfo = createContactInfo();
        if (contactInfo == null) {

            System.out.println("Member adding failed");
            System.out.println("------------------------------");
            return null;
        }
        member = new Member(name, age, gender, contactInfo.getID());
        if (member.addMember()) {
            System.out.println("Member added successfully with ID: " + member.getID() + " until " + member.getMembershipEnd() + ".");
            System.out.println("------------------------------");
            return member;
        } else {
            System.out.println("Member adding failed");
            System.out.println("------------------------------");
            return null;
        }

    }

    public static Employee createEmployee(String role) {
        Employee employee = new Employee();
        String name, gender;
        int salary, age;
        boolean invalid = false;
        do {
            System.out.println("------------------------------");
            System.out.println("        "+(role.equals("normal")?"Employee":role.equals("manager")?"Manager":"Director")+" Adding       ");
            System.out.println("------------------------------");
            if (invalid) {
                System.out.println("You entered invalid data.Try again.");
            }
            System.out.print("Enter Full Name:");
            name = sc.nextLine();
            System.out.print("Enter age:");
            try {
                age = sc.nextInt();
            } catch (Exception e) {
                age = 20;
                System.out.println("Integer not entered,replaced with a default value of 20.");
                sc.nextLine();
            }

            System.out.print("Enter gender:");
            gender = sc.next();
            System.out.print("Enter salary:");
            try {
                salary = sc.nextInt();
            } catch (Exception e) {
                salary = 500;
                System.out.println("Integer not entered,replaced with a default value of 500$.");
                sc.nextLine();
            }
            sc.nextLine();
            employee = new Employee(role, salary, name, age, gender);
            invalid = !employee.validate();
        } while (invalid);
        ContactInfo contactInfo = createContactInfo();
        if (contactInfo == null) {

            System.out.println("Employee adding failed");
            System.out.println("------------------------------");
            return null;
        }
        employee = new Employee(role, salary, name, age, gender, contactInfo.getID());
        if (employee.addEmployee()) {
            System.out.println("Employee added successfully with ID: " + employee.getID() + ".");
            System.out.println("------------------------------");
            return employee;
        } else {
            System.out.println("Employee adding failed");
            System.out.println("------------------------------");
            return null;
        }

    }

    public static Branch createBranch() {
        Branch branch = new Branch();
        String openingHours;
        int salary, age;
        boolean invalid = false;
        do {
            System.out.println("------------------------------");
            System.out.println("        Branch Adding         ");
            System.out.println("------------------------------");
            if (invalid) {
                System.out.println("You entered invalid data.Try again.");
            }
            System.out.print("Enter opening hours:");
            openingHours = sc.nextLine();
            branch = new Branch(openingHours);
            invalid = !branch.validate();
        } while (invalid);
        ContactInfo contactInfo = createContactInfo();
        if (contactInfo == null) {

            System.out.println("Branch adding failed");
            System.out.println("------------------------------");
            return null;
        }
        Employee manager = createEmployee("manager");

        if (manager == null) {

            System.out.println("Branch adding failed");
            System.out.println("------------------------------");
            return null;
        }

        branch = new Branch(contactInfo.getID(), manager.getID(), openingHours);
        if (branch.addBranch()) {
            System.out.println("Branch added successfully with  ID: " + branch.getID() + ".");
            System.out.println("------------------------------");
            return branch;
        } else {
            System.out.println("Branch adding failed");
            System.out.println("------------------------------");
            return null;
        }
    }

    public static Borrowing createBorrowing() {
        String bookID, memberID;
        boolean found = true;
        do {
            System.out.println("------------------------------");
            System.out.println("          Borrow Screen        ");
            System.out.println("------------------------------");
            if (!found) {
                System.out.println("Invalid Book and/or Member ID.Try again.");

            }
            System.out.print("Enter book ID: ");
            bookID = sc.next();
            System.out.print("Enter your member ID: ");
            memberID = sc.next();

            sc.nextLine();

            found = Main.currentBranch.getBooksID().contains(bookID) && Main.currentLibrary.getMembersID().contains(memberID);
            if (LocalDate.parse(Member.findByID(memberID).getMembershipEnd(), DateTimeFormatter.ofPattern("dd/MM/yyyy")).isBefore(LocalDate.now())) {
                found = false;
                System.out.println("The membership of this member has expired.");
            }
        }
        while (!found);
        Borrowing borrowing = new Borrowing(memberID, bookID);

        if (borrowing.addBorrowing()) {
            System.out.println("Book successfully borrowed with  ID: " + borrowing.getID() + ".");
            return borrowing;
        }
        System.out.println("Borrowing failed.");
        return null;
    }

    public static Book createBook() {
        Book book = new Book();
        String title, author, genre;
        int year;
        boolean invalid = false;
        do {
            System.out.println("------------------------------");
            System.out.println("         Book Adding         ");
            System.out.println("------------------------------");
            if (invalid) {
                System.out.println("You entered invalid data.Try again.");
            }
            System.out.print("Enter title:");
            title = sc.nextLine();
            System.out.print("Enter author name:");
            author = sc.nextLine();
            System.out.print("Enter year published:");
            try {
                year = sc.nextInt();
            } catch (Exception e) {
                year = 2000;
                System.out.println("Integer not entered,replaced with a default value of 2000.");
                sc.nextLine();
            }
            sc.nextLine();
            System.out.print("Enter genre:");
            genre = sc.nextLine();
            book=new Book(title,author,year,genre);
            invalid = !book.validate();

        } while (invalid);

        if (book.addBook()) {
            System.out.println("Book added successfully with ID: " + book.getID() + ".");
            System.out.println("Do you want to mock book ratings?(yes/no):");
            if(sc.nextLine().equalsIgnoreCase("yes")) {
                Random rand=new Random();
                for(int i=0;i<50;i++)
                {
                    book.rateBook(rand.nextInt(5)+1);
                }
                if(book.updateBook())
                {
                    System.out.println("Book successfully rated.");
                }
                else
                    System.out.println("Book not rated.");
            }
        } else {
            System.out.println("Book adding failed");
        }
        return book;
    }
}
