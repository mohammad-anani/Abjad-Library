public abstract class Person {
    protected String fullName = "";
    protected int age = -1;
    protected String gender = "";
    protected String contactInfoID = "";

    public Person() {
    }

    public Person(String fullName, int age, String gender, String contactInfoID) {
        this.fullName = fullName;
        this.age = age;
        this.gender = gender;
        this.contactInfoID = contactInfoID;
    }

    public Person(String fullName, int age, String gender) {
        this.fullName = fullName;
        this.age = age;
        this.gender = gender;
    }

    public ContactInfo getContactInfo() {
        return ContactInfo.findByID(this.contactInfoID);
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public boolean validate() {
        return fullName.trim().split(" ").length == 2 && fullName.trim().length() > 5 && age > 18 && (gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female"));
    }

    public String toCSVString() {
        return fullName + "," + age + "," + gender + "," + contactInfoID;
    }

    @Override
    public String toString() {
        String s="";
        s += " Full Name     : " + fullName + "\n";
        s += " Age           : " + age + "\n";
        s += " Gender        : " + gender + "\n";
        s += getContactInfo().toString();
        return s;
    }

}


