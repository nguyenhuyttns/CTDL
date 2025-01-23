package training.exam.main;

import training.exam.entity.UserDAO;
import training.exam.entity.TransactionDAO;
import training.exam.service.DatabaseManager;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final DatabaseManager dbManager = new DatabaseManager();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        dbManager.Connect();

        while (true) {
            System.out.println("Chọn một tùy chọn:");
            System.out.println("1. Đăng ký người dùng mới");
            System.out.println("2. Thêm giao dịch");
            System.out.println("3. Xóa giao dịch");
            System.out.println("4. Xem giao dịch của người dùng");
            System.out.println("5. Xem báo cáo giao dịch theo tháng");
            System.out.println("6. Thoát");

            int option = scanner.nextInt();
            scanner.nextLine(); // Đọc newline sau khi nhập số

            switch (option) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    addTransaction();
                    break;
                case 3:
                    removeTransaction();
                    break;
                case 4:
                    getUserTransactions();
                    break;
                case 5:
                    getMonthlyReport();
                    break;
                case 6:
                    System.out.println("Thoát chương trình.");
                    return; // Thoát khỏi chương trình
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }

    // Đăng ký người dùng mới
    private static void registerUser() {
        System.out.println("Nhập tên người dùng:");
        String username = scanner.nextLine();
        System.out.println("Nhập mật khẩu:");
        String password = scanner.nextLine();

        UserDAO newUser = new UserDAO();
        newUser.setUsername(username);
        newUser.setPassword(password);

        if (newUser.validateUser()) {
            dbManager.AddUser(newUser);
            System.out.println("Đăng ký thành công!");
        } else {
            System.out.println("Thông tin người dùng không hợp lệ.");
        }
    }

    // Thêm giao dịch mới
    private static void addTransaction() {
        System.out.println("Nhập ID người dùng:");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Đọc newline
        System.out.println("Nhập số tiền:");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Đọc newline
        System.out.println("Nhập danh mục:");
        String category = scanner.nextLine();
        System.out.println("Nhập loại giao dịch (income/expense):");
        String type = scanner.nextLine();
        System.out.println("Nhập mô tả giao dịch:");
        String description = scanner.nextLine();

        TransactionDAO newTransaction = new TransactionDAO();
        newTransaction.setUserId(userId);
        newTransaction.setAmount(amount);
        newTransaction.setCategory(category);
        newTransaction.setType(type);
        newTransaction.setDescription(description);
        newTransaction.setDate(new java.util.Date()); // Lấy ngày hiện tại

        dbManager.AddTransaction(newTransaction);
        System.out.println("Giao dịch đã được thêm.");
    }

    // Xóa giao dịch
    private static void removeTransaction() {
        System.out.println("Nhập ID giao dịch để xóa:");
        int transactionId = scanner.nextInt();
        dbManager.RemoveTransaction(transactionId);
        System.out.println("Giao dịch đã được xóa.");
    }

    // Xem giao dịch của người dùng
    private static void getUserTransactions() {
        System.out.println("Nhập ID người dùng:");
        int userId = scanner.nextInt();

        List<TransactionDAO> transactions = dbManager.GetUserTransactions(userId);
        if (transactions.isEmpty()) {
            System.out.println("Không có giao dịch nào.");
        } else {
            for (TransactionDAO transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }

    // Xem báo cáo giao dịch theo tháng
    private static void getMonthlyReport() {
        System.out.println("Nhập ID người dùng:");
        int userId = scanner.nextInt();
        System.out.println("Nhập tháng:");
        int month = scanner.nextInt();
        System.out.println("Nhập năm:");
        int year = scanner.nextInt();

        List<TransactionDAO> report = dbManager.GetMonthlyReport(userId, month, year);
        if (report.isEmpty()) {
            System.out.println("Không có giao dịch trong tháng này.");
        } else {
            for (TransactionDAO transaction : report) {
                System.out.println(transaction);
            }
        }
    }
}
