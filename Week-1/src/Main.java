import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        // -----------DESERIALIZATION-------------
        // String json = """
        // {
        // "id": 1,
        // "name": "Ankit",
        // "city": "Kolkata"
        // }
        // """;

        // ObjectMapper mapper = new ObjectMapper();

        // User user = mapper.readValue(json, User.class);

        // System.out.println(user.id);
        // System.out.println(user.name);
        // System.out.println(user.city);

        // -------------SERIALIZATION--------------
        // ObjectMapper mapper = new ObjectMapper();

        // User user = new User();
        // user.id = 10;
        // user.name = "Ankit";
        // user.city = "Kolkata";

        // String jsonOutput = mapper.writeValueAsString(user);

        // System.out.println(jsonOutput);

        ObjectMapper mapper = new ObjectMapper();

        String jsonArray = """
                [
                {"id": 1, "name": "Ankit", "city": "Kolkata"}
                {"id": 2, "name": "Shivam", "city": "Kolkata"}
                ]
                """;

        List<User> users = mapper.readValue(jsonArray, new TypeReference<List<User>>() {
        });

        System.out.println(users.get(0));
    }
}