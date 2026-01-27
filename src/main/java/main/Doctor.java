package main;
public class Doctor extends User{
    public Doctor(String userId, String username, String password, String name,
            String gender, String email, String phone, int age) {
        super(userId, username, password, name, 
                gender, email, phone, age, "Doctor");
    }
}