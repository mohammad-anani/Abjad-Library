import java.util.ArrayList;

interface ILibrary {
    String getID();

    String getName();

    Employee getDirector();

    String getType();

    ArrayList<Branch> getBranches();

    ArrayList<Member> getMembers();
}