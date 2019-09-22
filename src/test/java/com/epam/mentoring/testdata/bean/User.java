package com.epam.mentoring.testdata.bean;

public class User {

    private final String login_name;
    private final String password;
    private final String email;
    private final String receiver_email;

    private User(UserBuilder builder) {
        this.login_name = builder.login_name;
        this.password = builder.password;
        this.email = builder.email;
        this.receiver_email = builder.receiver_email;
    }

    public String getLoginName() {
        return login_name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getReceiver_email() {
        return receiver_email;
    }

    public static class UserBuilder {
        private final String login_name;
        private final String password;
        private final String email;
        private final String receiver_email;

        public UserBuilder(String login_name, String password, String email, String receiver_email) {
            this.login_name = login_name;
            this.password = password;
            this.email = email;
            this.receiver_email = receiver_email;
        }

        public User build() {
            User user = new User(this);
            if (login_name.isEmpty() || password.isEmpty() || email.isEmpty()) {
                throw new IllegalStateException("Empty login_name, password, email or reciever_email is empty");
            }
            return user;
        }
    }
}
