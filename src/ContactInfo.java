import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class ContactInfo {
    protected String ID = "";
    protected String address = "";
    protected String email = "";
    protected String phone = "";

    private static final String fileName = "src/Files/contactInfos.txt";

    public ContactInfo() {
    }

    public ContactInfo(String ID, String address, String email, String phone) {
        this.ID = ID;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    public ContactInfo(String address, String email, String phone) {
        this.address = address;
        this.email = email;
        this.phone = phone;
    }


    public String getID() {
        return ID;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }


    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean validate() {
        return email.endsWith("@gmail.com") && phone.matches("\\d{8}") && !address.contains(",") && address.length() > 10;
    }

    private boolean generateID() {
        if (!validate())
            return false;
        Random rand = new Random();
        this.ID = this.phone.substring(0, 4) + "_" + email.substring(0, 4) + "_" + (rand.nextInt(9000) + 1000);
        return true;
    }


    public boolean addContactInfo() {

        if (this.generateID())
            return this.appendToFile();
        return false;
    }

    public boolean updateContactInfo() {
        ArrayList<ContactInfo> contactInfos = loadContactsInfoFromFile();
        for (int i = 0; i < contactInfos.size(); i++) {
            if (contactInfos.get(i).equals(this)) {
                contactInfos.set(i, this);
                return writeToFile(contactInfos);
            }
        }
        return false;
    }

    public boolean deleteContactInfo() {
        ArrayList<ContactInfo> contactInfos = loadContactsInfoFromFile();
        ArrayList<ContactInfo> newContactInfos = new ArrayList<>();
        for(ContactInfo contactInfo : contactInfos) {
            if (!contactInfo.equals(this)) {
                newContactInfos.add(contactInfo);
            }
        }

         return writeToFile(newContactInfos);
    }

    public static ArrayList<ContactInfo> loadContactsInfoFromFile() {
        ArrayList<ContactInfo> contactInfos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    contactInfos.add(new ContactInfo(parts[0], parts[1], parts[2], parts[3]));
                }
            }
        } catch (IOException e) {
            return new ArrayList<ContactInfo>();
        }
        return contactInfos;
    }

    private boolean writeToFile(ArrayList<ContactInfo> contactInfos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (ContactInfo c : contactInfos) {
                bw.write(c.toCSVString());
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

    public static ContactInfo findByID(String ID) {

        return loadContactsInfoFromFile().stream().filter(contactInfo -> contactInfo.getID().equals(ID)).findFirst().orElse(new ContactInfo());
    }

    @Override
    public String toString() {
        String s="";
        s += " Address : " + this.address + "\n";
        s += " Email   : " + this.email + "\n";
        s += " Phone   : " + this.phone + "\n";
        return s;

    }

    public String toCSVString() {
        return this.ID + "," + this.getAddress() + "," + this.getEmail() + "," + this.getPhone();
    }


    public boolean equals(ContactInfo contactInfo) {
        return this.ID.equals(contactInfo.getID());
    }
}
