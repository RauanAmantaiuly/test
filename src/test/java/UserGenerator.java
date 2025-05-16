public class UserGenerator {

    public static UserRequest generateUser(String name, String email, String gender, String status) {
        return UserRequest.builder()
                .name(name)
                .email(email)
                .gender(gender)
                .status(status)
                .build();
    }
}
