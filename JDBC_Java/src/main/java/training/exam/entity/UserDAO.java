package training.exam.entity;

public class UserDAO {
    private int userId;
    private String username;
    private String password;

    // Getter và Setter
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Phương thức validateUser() để kiểm tra người dùng hợp lệ
    public boolean validateUser() {
        return username != null && password != null && !username.isEmpty() && !password.isEmpty();
    }
}
