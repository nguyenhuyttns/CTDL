package training.exam.entity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLDatabaseConnection {
    private static final String JDBC_URL = "jdbc:mysql://localhost:1306/FinanceTracker?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "huy";
    private static final String PASSWORD = "123456";

    private Connection connection;
    // Kết nối cơ sở dữ liệu
    public void Connect() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
                System.out.println("Kết nối cơ sở dữ liệu MySQL thành công.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi kết nối cơ sở dữ liệu MySQL.");
        }
    }

    public void addUser(UserDAO user) {
        String sql = "INSERT INTO Users (Username, Password) VALUES (?, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void addTransaction(TransactionDAO tran) {
        String sql = "INSERT INTO Transactions (User_id, Amount, Category, Type, Date, Description) VALUES (?, ?, ?, ?, ?, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, tran.getUserId());
            stmt.setDouble(2, tran.getAmount());
            stmt.setString(3, tran.getCategory());
            stmt.setString(4, tran.getType());
            stmt.setDate(5, new java.sql.Date(tran.getDate().getTime()));
            stmt.setString(6, tran.getDescription());
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void RemoveTransaction(int tranId) {
        String sql = "DELETE FROM Transactions WHERE Transaction_id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,tranId);
            stmt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<TransactionDAO> GetUserTransactions(int userId)
    {
        List<TransactionDAO> transactions = new ArrayList<>();
        String sql = "SELECT * FROM Transactions WHERE User_id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,userId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                TransactionDAO transaction = new TransactionDAO();
                transaction.setTransactionId(rs.getInt("Transaction_id"));
                transaction.setUserId(rs.getInt("User_id"));
                transaction.setAmount(rs.getDouble("Amount"));
                transaction.setCategory(rs.getString("Category"));
                transaction.setType(rs.getString("Type"));
                transaction.setDate(rs.getDate("Date"));
                transaction.setDescription(rs.getString("Description"));
                transactions.add(transaction);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }

    // Lấy báo cáo giao dịch tháng
    public List<TransactionDAO> GetMonthlyReport(int userId, int month, int year) {
        List<TransactionDAO> transactions = new ArrayList<>();
        String sql = "SELECT * FROM Transactions WHERE User_id = ? AND MONTH(Date) = ? AND YEAR(Date) = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, month);
            stmt.setInt(3, year);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TransactionDAO transaction = new TransactionDAO();
                transaction.setTransactionId(rs.getInt("Transaction_id"));
                transaction.setUserId(rs.getInt("User_id"));
                transaction.setAmount(rs.getDouble("Amount"));
                transaction.setCategory(rs.getString("Category"));
                transaction.setType(rs.getString("Type"));
                transaction.setDate(rs.getDate("Date"));
                transaction.setDescription(rs.getString("Description"));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public void updateUserPassword(int userId, String newPassword) {
        String sql = "UPDATE Users SET Password = ? WHERE User_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newPassword);
            stmt.setInt(2, userId);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Mật khẩu của người dùng có ID " + userId + " đã được cập nhật.");
            } else {
                System.out.println("Không tìm thấy người dùng với ID: " + userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi khi cập nhật mật khẩu.");
        }
    }


    public void SelectDataUser() {
        try {
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM Users";
            ResultSet rs = stmt.executeQuery(sql);

            // Hiển thị kết quả
            while (rs.next()) {
                int userId = rs.getInt("User_id");
                String username = rs.getString("Username");
                String password = rs.getString("Password");

                System.out.println("User ID: " + userId + ", Username: " + username + ", Password: " + password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi khi truy vấn cơ sở dữ liệu.");
        }
    }

    public void SelectDataTransaction(){
        try{
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM Transactions";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                int tranId = rs.getInt("Transaction_id");
                int userId = rs.getInt("User_id");
                double amount = rs.getDouble("Amount");
                String category = rs.getString("Category");
                String type = rs.getString("Type");
                String date = rs.getString("Date");
                String description = rs.getString("Description");
                System.out.println("Transaction ID: " + tranId + ", User ID: " + userId + ", Amount: " + amount + ", Category: " + category + ", Type: " + type + ", Date: " + date + ", Description: " + description);
             }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Loi truy van csdl");
        }
    }


    // Hàm main để kiểm tra kết nối
    public static void main(String[] args) {
        MySQLDatabaseConnection dbConnection = new MySQLDatabaseConnection();
        dbConnection.Connect();
        dbConnection.SelectDataUser();
        dbConnection.SelectDataTransaction();

    }
}
