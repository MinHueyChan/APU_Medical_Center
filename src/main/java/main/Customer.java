package main;
public class Customer extends User{
    public Customer(String userId, String username, String password, String name, String gender, String email, String phone, int age) {
        super(userId, username, password, name, gender, email, phone, age, "Customer");
    }
}