package training.exam.service;

import training.exam.entity.MySQLDatabaseConnection;
import training.exam.entity.TransactionDAO;
import training.exam.entity.UserDAO;

import java.util.List;
import java.util.Scanner;

public class FinanceReport {

    private static MySQLDatabaseConnection dbManager = new MySQLDatabaseConnection();
    private static final Scanner scanner = new Scanner(System.in);

    public FinanceReport(MySQLDatabaseConnection dbManager) {
        this.dbManager = dbManager;
    }

    public void AddUser(){
        System.out.println("Nhập tên người dùng:");
        String username = scanner.nextLine();
        System.out.println("Nhập mật khẩu:");
        String password = scanner.nextLine();

        UserDAO newUser = new UserDAO();
        newUser.setUsername(username);
        newUser.setPassword(password);

        dbManager.addUser(newUser);
        System.out.println("Đăng ký thành công!");
    }

    public void AddTransaction(){
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
        newTransaction.setDate(new java.util.Date());

        dbManager.addTransaction(newTransaction);
        System.out.println("Giao dịch đã được thêm.");
    };

    public void RemoveTransaction(){
        System.out.println("Nhap ID de xoa: ");
        int tranId = scanner.nextInt();
        dbManager.RemoveTransaction(tranId);
        System.out.println("Giao dich da duoc xoa!");
    };

    public void UpdateTransaction(){};
    public List<TransactionDAO> GetTranByUser(int userId){
        List<TransactionDAO> transactions = dbManager.GetUserTransactions(userId);
        if (transactions.isEmpty()) {
            System.out.println("Không tìm thấy giao dịch nào cho người dùng có ID: " + userId);
        } else {
            for (TransactionDAO transaction : transactions) {
                System.out.println(transaction);
            }
        }
        return transactions;
    };

    public MonthlyReport GetMonthlyReport(int userid, int month, int year){
        List<TransactionDAO> transactions = dbManager.GetMonthlyReport(userid, month, year);
        if (transactions.isEmpty()) {
            System.out.println("Khong tim thay!" + userid);
        }else{
            for (TransactionDAO transaction : transactions) {
                System.out.println(transaction);
            }
        }
        return null;
    }

    public void UpdatePassword() {
        System.out.println("Nhap id can sua: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Nhap mk moi: ");
        String password = scanner.nextLine();

        dbManager.updateUserPassword(id, password);
    }

    public void SetBudget(String category, double amount){

    }

}
