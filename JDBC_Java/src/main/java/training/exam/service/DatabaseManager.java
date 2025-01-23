package training.exam.service;
import training.exam.entity.UserDAO;
import training.exam.entity.TransactionDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String JDBC_URL = "jdbc:sqlserver://localhost:1433;databaseName=FinanceTracker;encrypt=false";
    private static final String USER = "jdbc";
    private static final String PASSWORD = "123";

    private Connection connection;

    // Kết nối cơ sở dữ liệu
    public void Connect() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
                System.out.println("Kết nối cơ sở dữ liệu thành công.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi kết nối cơ sở dữ liệu.");
        }
    }

    // Phương thức thêm người dùng
    public void AddUser(UserDAO user) {
        String sql = "INSERT INTO Users (Username, Password) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Thêm giao dịch
    public void AddTransaction(TransactionDAO transaction) {
        String sql = "INSERT INTO Transactions (User_id, Amount, Category, Type, Date, Description) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, transaction.getUserId());
            stmt.setDouble(2, transaction.getAmount());
            stmt.setString(3, transaction.getCategory());
            stmt.setString(4, transaction.getType());
            stmt.setDate(5, new java.sql.Date(transaction.getDate().getTime()));
            stmt.setString(6, transaction.getDescription());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa giao dịch
    public void RemoveTransaction(int transactionId) {
        String sql = "DELETE FROM Transactions WHERE Transaction_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, transactionId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy giao dịch của người dùng
    public List<TransactionDAO> GetUserTransactions(int userId) {
        List<TransactionDAO> transactions = new ArrayList<>();
        String sql = "SELECT * FROM Transactions WHERE User_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
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
}
