public class User {
    public int id;
    public String name;
    public String city;

    // This is what makes System.out.println() work!
    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", city=" + city + "]";
    }
}