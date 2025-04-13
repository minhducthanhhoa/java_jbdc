package presentation;

import entity.Account;
import entity.enums.EmployeeStatus;
import service.AccountService;
import service.DepartmentService;
import service.EmployeeService;
import util.InputUtil;

import java.util.List;

public class MainMenu {
    private final AccountService accountService = new AccountService();
    private final DepartmentService departmentService = new DepartmentService();
    private final EmployeeService employeeService = new EmployeeService();

    public void run() {
        while (true) {
            System.out.println("\n======= HỆ THỐNG QUẢN LÝ NHÂN SỰ =======");
            System.out.println("1. Đăng nhập");
            System.out.println("0. Thoát");
            int choice = InputUtil.inputInt("Chọn: ");
            switch (choice) {
                case 1 -> login();
                case 0 -> {
                    System.out.println("Tạm biệt!");
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private void login() {
        String username = InputUtil.inputString("Tên đăng nhập: ");
        String password = InputUtil.inputString("Mật khẩu: ");
        Account account = accountService.login(username, password);
        if (account != null) {
            if (account.getRole().equalsIgnoreCase("ADMIN")) {
                adminMenu();
            } else {
                hrMenu();
            }
        } else {
            System.out.println("Đăng nhập thất bại. Kiểm tra lại thông tin.");
        }
    }

    private void adminMenu() {
        while (true) {
            System.out.println("\n===== MENU ADMIN =====");
            System.out.println("1. Hiển thị danh sách phòng ban");
            System.out.println("2. Thêm mới phòng ban");
            System.out.println("3. Sửa thông tin phòng ban");
            System.out.println("4. Xóa phòng ban");
            System.out.println("5. Quản lý nhân viên");
            System.out.println("6. Thống kê");
            System.out.println("0. Đăng xuất");
            int choice = InputUtil.inputInt("Chọn: ");
            switch (choice) {
                case 1 -> departmentService.displayDepartmentsWithPaging();
                case 2 -> departmentService.addDepartment();
                case 3 -> departmentService.updateDepartment();
                case 4 -> departmentService.deleteDepartment(employeeService);
                case 5 -> hrMenu();
                case 6 -> statisticMenu();
                case 0 -> {
                    System.out.println("Đăng xuất thành công.");
                    return;
                }
                default -> System.out.println("Chọn sai. Vui lòng thử lại.");
            }
        }
    }

    private void hrMenu() {
        while (true) {
            System.out.println("\n===== MENU HR =====");
            System.out.println("1. Hiển thị danh sách nhân viên");
            System.out.println("2. Thêm mới nhân viên");
            System.out.println("3. Cập nhật thông tin nhân viên");
            System.out.println("4. Xóa nhân viên");
            System.out.println("5. Tìm kiếm nhân viên theo tên");
            System.out.println("6. Sắp xếp theo lương giảm dần");
            System.out.println("7. Sắp xếp theo tên tăng dần");
            System.out.println("0. Quay lại");
            int choice = InputUtil.inputInt("Chọn: ");
            switch (choice) {
                case 1 -> employeeService.displayEmployeesWithPaging();
                case 2 -> employeeService.addEmployee();
                case 3 -> employeeService.updateEmployee();
                case 4 -> employeeService.deleteEmployee();
                case 5 -> employeeService.searchByName();
                case 6 -> employeeService.sortBySalaryDesc();
                case 7   -> employeeService.sortByNameAsc();
                case 0 -> {
                    System.out.println("Quay lại MENU chính.");
                    return;
                }
                default -> System.out.println("Chọn sai. Vui lòng thử lại.");
            }
        }
    }

    private void statisticMenu() {
        while (true) {
            System.out.println("\n===== MENU THỐNG KÊ =====");
            System.out.println("1. Thống kê số lượng nhân viên theo phòng ban");
            System.out.println("3. Tổng số nhân viên toàn hệ thống");
            System.out.println("4. Phòng ban có nhiều nhân viên nhất");
            System.out.println("5. Phòng ban có tổng lương cao nhất");
            System.out.println("0. Quay lại");
            int choice = InputUtil.inputInt("Chọn: ");
            switch (choice) {
                case 1 -> statisticByDepartment();
                case 3 -> System.out.println("Tổng số nhân viên: " + employeeService.getAll().size());
                case 4 -> departmentService.departmentWithMostEmployees(employeeService);
                case 5 -> departmentService.departmentWithHighestSalary(employeeService);
                case 0 -> {
                    System.out.println("Quay lại MENU chính.");
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }

    private void statisticByDepartment() {
        List<entity.Department> departments = departmentService.getAll();
        if (departments.isEmpty()) {
            System.out.println("Không có phòng ban nào.");
            return;
        }
        System.out.println("Thống kê số lượng nhân viên theo phòng ban:");
        for (entity.Department d : departments) {
            long count = employeeService.countByDepartment(d.getId());
            System.out.printf("- %-30s | Nhân viên: %d\n", d.getName(), count);
        }
    }

}
