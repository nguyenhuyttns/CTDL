package training.exam.main;

import training.exam.entity.MySQLDatabaseConnection;
import training.exam.service.FinanceReport;

import java.util.Scanner;

public class ConsoleMenu {
    private static final MySQLDatabaseConnection dbManager = new MySQLDatabaseConnection();
    private static final Scanner scanner = new Scanner(System.in);
    private static final FinanceReport financeReport = new FinanceReport(dbManager);

    public static void main(String[] args) {
        dbManager.Connect();

        while (true) {
            System.out.println("Chọn một tùy chọn:");
            System.out.println("1. Đăng ký người dùng mới");
            System.out.println("2. Thêm giao dịch");
            System.out.println("3. Xóa giao dịch");
            System.out.println("4. Xem giao dịch của người dùng");
            System.out.println("5. Xem báo cáo giao dịch theo tháng");
            System.out.println("6. Xem danh sach user");
            System.out.println("7. Xem danh sach transaction");
            System.out.println("8. Doi mat khau");
            System.out.println("9. Thoát");

            int option = scanner.nextInt();
            scanner.nextLine(); // Đọc newline sau khi nhập số

            switch (option) {
                case 1:
                    financeReport.AddUser();
                    break;
                case 2:
                    financeReport.AddTransaction();
                    break;
                case 3:
                    financeReport.RemoveTransaction();
                    break;
                case 4:
                    System.out.println("Nhap id nguoi dung: ");
                    int userId = scanner.nextInt();
                    financeReport.GetTranByUser(userId);
                    break;
                case 5:
                    System.out.println("Nhap id nguoi dung: ");
                    int user = scanner.nextInt();
                    System.out.println("Nhap thang : ");
                    int month = scanner.nextInt();
                    System.out.println("Nhap nam: ");
                    int year = scanner.nextInt();
                    financeReport.GetMonthlyReport(user, month, year);
                    break;
                case 6:
                    dbManager.SelectDataUser();
                    break;
                case 7:
                    dbManager.SelectDataTransaction();
                    break;
                case 8:
                    financeReport.UpdatePassword();
                    break;
                case 9:
                    System.out.println("Thoát chương trình.");
                    return; // Thoát khỏi chương trình
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }

}
