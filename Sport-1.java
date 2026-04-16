public class Sport {

    String name;
    int id;
    int numberOfTeams;

    public Sport(String name, int id, int numberOfTeams) {
        this.name = name;
        this.id = id;
        this.numberOfTeams = numberOfTeams;
    }

    public String toString() {
        return "Sport: " + name + " | ID: " + id + " | Teams: " + numberOfTeams;
    }
}
