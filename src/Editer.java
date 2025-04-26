import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Editer {

    public static Scanner sc = new Scanner(System.in);

    public static void editContactInfo(ContactInfo contactInfo) {

        if(contactInfo==null)
            return;

        String phone, email, address;
        boolean invalid = false;
        do {
            System.out.println("------------------------------");
            System.out.println("  Editing Contact Info Screen");
            System.out.println("------------------------------");
            if (invalid) {
                System.out.println("You entered invalid data.Try again.");
            }
            System.out.print("Enter phone:");
            phone = sc.next();
            System.out.print("Enter email:");
            email = sc.next();
            sc.nextLine();
            System.out.print("Enter address:");
            address = sc.nextLine();
            contactInfo.setAddress(address);
            contactInfo.setPhone(phone);
            contactInfo.setEmail(email);
            invalid = !contactInfo.validate();
        } while (invalid);

        if (contactInfo.updateContactInfo()) {
            System.out.println("Contact Info updated successfully.");
        } else {
            System.out.println("Contact Info not updated successfully.");
        }
    }


    public static void editLibrary() {
        int membershipDurationInMonths, borrowingDays;
        int[] lateFees = new int[4];
        boolean invalid = false;
        System.out.println(Main.currentLibrary);
        do {
            System.out.println("------------------------------");
            System.out.println("    Editing Library Screen");
            System.out.println("------------------------------");
            if (invalid) {
                System.out.println("You entered invalid data.Try again.");
            }

            System.out.print("Enter Library Membership Duration in Months: ");
            try {

                membershipDurationInMonths = sc.nextInt();
            } catch (Exception e) {
                membershipDurationInMonths = 12;
                System.out.println("Integer not entered,replaced with a default value of 12.");
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
            Main.currentLibrary.setBorrowingDays(borrowingDays);
            Main.currentLibrary.setMembershipDurationInMonths(membershipDurationInMonths);
            Main.currentLibrary.setLateFees(lateFees);
            invalid = !Main.currentLibrary.validate();
        } while (invalid);

        System.out.print("Do you want to change the director?(yes/no):");
        if (sc.next().equalsIgnoreCase("yes")) {



            System.out.println("Does the director already exists?(yes/no):");
            if (sc.next().equalsIgnoreCase("yes")) {
                boolean exist = false;
                Employee employee = new Employee();
                do {
                    System.out.println("Enter the new director ID: ");
                    String directorID = sc.next();
                    sc.nextLine();
                    employee = Employee.findByID(directorID);
                    exist = employee != null;
                    if (directorID.equals(Main.currentLibrary.getDirector().getID())) {
                        System.out.println("You cannot select the same director");
                        continue;
                    }
                    if (!exist) {
                        System.out.println("Employee not found.");
                    }
                } while (!exist);
                employee.setRole("director");
                employee.updateEmployee();
                Main.currentLibrary.setDirectorEmployeeID(employee.getID());
            } else {
                Main.currentLibrary.setDirectorEmployeeID(Creater.createEmployee("director").getID());
            }
            Main.currentEmployee.setRole("normal");
            Main.currentEmployee.updateEmployee();
        }

        if (Main.currentLibrary.updateLibrary()) {
            System.out.println("Library successfully updated.");
        } else {
            System.out.println("Library could not be updated.");
        }

        System.out.println(Main.currentLibrary);
    }

    public static void editBranch() {
        String openinghours;
        boolean invalid = false;
        System.out.println(Main.currentBranch);
        do {
            System.out.println("------------------------------");
            System.out.println("     Editing Branch Screen");
            System.out.println("------------------------------");
            if (invalid) {
                System.out.println("You entered invalid data.Try again.");
            }

            System.out.println("Enter Opening Hours: ");
            openinghours = sc.nextLine();

            invalid = !Main.currentBranch.validate();
        } while (invalid);
        Main.currentBranch.setOpeningHours(openinghours);

        System.out.print("Do you want to change the manager?(yes/no):");
        if (sc.next().equalsIgnoreCase("yes")) {
            System.out.println("Does the manager already exists?(yes/no):");
            if (sc.next().equalsIgnoreCase("yes")) {
                boolean exist = false;
                Employee employee = new Employee();
                do {
                    System.out.println("Enter the new manager ID: ");
                    String managerID = sc.next();
                    sc.nextLine();
                    employee = Employee.findByID(managerID);
                    if (managerID.equals(Main.currentBranch.getManager().getID())) {
                        System.out.println("You cannot select the same manager");
                        continue;
                    }
                    exist = employee != null;
                    if (!exist) {
                        System.out.println("Employee not found.");
                    }
                } while (!exist);
                employee.setRole("manager");
                employee.updateEmployee();
                Main.currentBranch.setManagerEmployeeID(employee.getID());
            } else {
                Main.currentBranch.setManagerEmployeeID(Creater.createEmployee("manager").getID());
            }

            Main.currentEmployee.setRole("normal");
            Main.currentEmployee.updateEmployee();

            System.out.println(Main.currentBranch);

        }

        System.out.print("Do you want to  change the contact Info?(yes/no):");
        if (sc.next().equalsIgnoreCase("yes")) {
            sc.nextLine();
            editContactInfo(Main.currentBranch.getContactInfo());
        }

        if (Main.currentBranch.updateBranch()) {
            System.out.println("Branch successfully updated.");
        } else {
            System.out.println("Branch could not be updated.");

        }
    }


    public static void renewMembership(Member member) {
        if (LocalDate.parse(member.getMembershipEnd(), DateTimeFormatter.ofPattern("dd/MM/yyyy")).isBefore(LocalDate.now()))
        {
            System.out.println("Membership  not expired yet.Try again after:"+member.getMembershipEnd()+" .");
            return;
        }
            member.renewMembership();
        if (member.updateMember()) {
            System.out.println("Membership renewed successfully until " + member.getMembershipEnd());
        }
    }


    public static void editEmployee(Employee employee) {

        if (employee == null)
            return;

        String name, gender;
        int age, salary;
        boolean invalid = false;



        do {
            System.out.println("------------------------------");
            System.out.println("     Editing "+(employee.getRole().equals("normal")?"Employee":employee.getRole().equals("manager")?"Manager":"Director")+" Screen");
            System.out.println("------------------------------");
            if (invalid) {
                System.out.println("You entered invalid data.Try again.");
            }

            System.out.print("Enter Full name: ");
            name = sc.nextLine();
            System.out.print("Enter age:");
            try {

                age = sc.nextInt();
            } catch (Exception e) {
                age = 20;
                System.out.println("Integer not entered,replaced with a default value of 20.");
                sc.nextLine();
            }
            System.out.print("Enter salary:");
            try {


                salary = sc.nextInt();
            } catch (Exception e) {
                salary = 500;
                System.out.println("Integer not entered,replaced with a default value of 500$.");
                sc.nextLine();
            }
            employee.setAge(age);
            employee.setFullName(name);
            employee.setSalary(salary);
            invalid = !employee.validate();
        } while (invalid);

        System.out.print("Do you want to  change the contact Info?(yes/no):");
        if (sc.next().equalsIgnoreCase("yes"))
            editContactInfo(employee.getContactInfo());

        if (employee.updateEmployee()) {
            System.out.println("Employee successfully updated.");
        } else {
            System.out.println("Employee could not be updated.");
        }
        System.out.println(employee);
        sc.nextLine();
    }

    public static void returnBook(Borrowing borrowing) {

        if(borrowing==null)
            return;

        System.out.println("Do you want to return the book?");
        if (!sc.next().equalsIgnoreCase("yes")) {
            System.out.println("No book returned.");
        }

        int lateDays = borrowing.setReturnDate();

        System.out.println("Late days: " + lateDays);
        System.out.println("Late fees: " + borrowing.getLateFees());
        System.out.println("Paid?(yes/no)");
        boolean paid = false;
        do {
            paid = sc.next().equalsIgnoreCase("yes");
            if (!paid) {
                System.out.println("Please let the member pay");
            }
        } while (!paid);
        System.out.println("How much do you rate the book?");
        int rate;
        try {


            rate = sc.nextInt();
        } catch (Exception e) {
            rate = 3;
            System.out.println("Integer not entered,replaced with a default value of 3.");
            sc.nextLine();
        }
        sc.nextLine();
        Book book = borrowing.getBook();
        book.rateBook(rate);
        if (borrowing.updateBorrowing() && book.updateBook()) {
            System.out.println("Book successfully returned.");
        } else
            System.out.println("Book returning failed");
        sc.nextLine();
    }

    public static void editMember(Member member) {

        if (member == null)
            return;

        String name, gender;
        int age;
        boolean invalid = false;

        do {
            System.out.println("------------------------------");
            System.out.println("     Editing Member Screen");
            System.out.println("------------------------------");
            if (invalid) {
                System.out.println("You entered invalid data.Try again.");
            }

            System.out.print("Enter Full name: ");
            name = sc.nextLine();
            System.out.print("Enter age:");
            try {


                age = sc.nextInt();
            } catch (Exception e) {
                age = 20;
                System.out.println("Integer not entered,replaced with a default value of 20.");
                sc.nextLine();
            }
            member.setAge(age);
            member.setFullName(name);
            invalid = !member.validate();
        } while (invalid);

        System.out.print("Do you want to  change the contact Info?(yes/no):");
        if (sc.next().equalsIgnoreCase("yes"))
            editContactInfo(member.getContactInfo());

        if (member.updateMember()) {
            System.out.println("Member successfully updated.");
        } else {
            System.out.println("Member could not be updated.");
        }
        System.out.println(member);
        sc.nextLine();
    }

}
