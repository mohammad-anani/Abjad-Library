import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;

public class Employee extends Person {
    private String ID = "";
    private String role = "";
    private int salary = 0;
    private static final String fileName = "src/Files/employees.txt";

    public Employee() {
    }

    public Employee(String ID, String role, int salary, String fullName, int age, String gender, String contactInfoID) {
        super(fullName, age, gender, contactInfoID);
        this.ID = ID;
        this.role = role;
        this.salary = salary;
    }

    public Employee(String role, int salary, String fullName, int age, String gender) {
        super(fullName, age, gender);
        this.role = role;
        this.salary = salary;
    }

    public Employee(String role, int salary, String fullName, int age, String gender, String contactInfoID) {
        super(fullName, age, gender);
        this.role = role;
        this.salary = salary;
        this.contactInfoID = contactInfoID;
    }

    public String getID() {
        return ID;
    }

    public String getRole() {
        return role;
    }

    public int getSalary() {
        return salary;
    }


    public void setRole(String role) {
        this.role = role;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }



    public boolean validate() {
        String roles = "director,manager,normal";
        return roles.contains(this.role) && this.salary > 0;
    }


    public boolean generateID() {
        if (!validate()) return false;
        Random rand = new Random();
        this.ID = role.substring(0, 2) + "_" + fullName.substring(0, 3) + "_" + (('a' + rand.nextInt(26)) + ('a' + rand.nextInt(26)) + ('a' + rand.nextInt(26)));
        return true;
    }


    public boolean addEmployee() {
        if (generateID())
            return appendToFile();
        return false;
    }

    public boolean updateEmployee() {
        ArrayList<Employee> employees = loadFromFile();
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).equals(this)) {
                employees.set(i, this);
                return writeToFile(employees);
            }
        }
        return false;
    }


    public static ArrayList<Employee> loadFromFile() {
        ArrayList<Employee> employees = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    employees.add(new Employee(parts[0], parts[1], Integer.parseInt(parts[2]), parts[3], Integer.parseInt(parts[4]), parts[5], parts[6]));
                }
            }
        } catch (IOException e) {
            return new ArrayList<>();
        }
        return employees;
    }

    private boolean writeToFile(ArrayList<Employee> employees) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Employee e : employees) {
                bw.write(e.toCSVString());
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

    public static Employee findByID(String ID) {
        return loadFromFile().stream()
                .filter(e -> e.getID().equalsIgnoreCase(ID))
                .findFirst()
                .orElse(null);
    }

    public boolean existInBranch(String branchID) {
        return Branch.loadFromFile().stream().anyMatch(b -> b.getEmployeesID().contains(this.ID));
    }

    public static ArrayList<Employee> findByID(ArrayList<String> IDs) {
        ArrayList<Employee> found = new ArrayList<>();
        for (Employee e : loadFromFile()) {
            if (IDs.contains(e.getID())) {
                found.add(e);
            }
        }
        return found;
    }


    public boolean equals(Employee e) {
        return this.ID.equals(e.getID());
    }

    public String toCSVString() {
        return ID + "," + role + "," + salary + "," + super.toCSVString();
    }

    @Override
    public String toString() {

        NumberFormat format = NumberFormat.getCurrencyInstance();

        return "------------------------------\n" +
                "           "+
                (this.role.equals("normal")?"Employee":this.role.equals("manager")?"Manager":"Director")
                +"           \n" +
                "------------------------------\n" +
                " ID        : " + ID + "\n" +
                " Role      : " + role + "\n" +
                " Salary    : " + format.format(salary) + "\n" +
                super.toString() +
                "------------------------------\n";
    }
}
