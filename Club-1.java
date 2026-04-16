public class Club {

    String name;
    String branches;
    String manager;
    String location;

    public Club(String name, String branches, String manager, String location) {
        this.name = name;
        this.branches = branches;
        this.manager = manager;
        this.location = location;
    }

    public String toString() {
        return "Club: " + name + " | Branches: " + branches + " | Manager: " + manager + " | Location: " + location;
    }
}
