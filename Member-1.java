public class Member {

    int id;
    String name;
    String phone;
    int numberOfChildren;

    public Member(int id, String name, String phone, int numberOfChildren) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.numberOfChildren = numberOfChildren;
    }

    public String toString() {
        return "Member ID: " + id + " | Name: " + name + " | Phone: " + phone + " | Children: " + numberOfChildren;
    }
}
