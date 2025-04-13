package service;

import dao.EmployeeDao;
import entity.Employee;
import entity.enums.EmployeeStatus;
import util.InputUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeService {
    private final EmployeeDao employeeDAO = new EmployeeDao();

    public List<Employee> getAll() {
        return employeeDAO.findAll();
    }

    public void displayEmployeesWithPaging() {
        List<Employee> employees = employeeDAO.findAll();
        if (employees.isEmpty()) {
            System.out.println("Không có nhân viên nào để hiển thị.");
            return;
        }

        final int pageSize = 5;
        int total = employees.size();
        int totalPages = (int) Math.ceil((double) total / pageSize);
        int currentPage = 1;

        while (true) {
            System.out.println("\nDanh sách nhân viên - Trang " + currentPage + "/" + totalPages);
            int start = (currentPage - 1) * pageSize;
            int end = Math.min(start + pageSize, total);

            for (int i = start; i < end; i++) {
                Employee e = employees.get(i);
                System.out.printf("ID: %-5d | Tên: %-20s | Lương: %.2f | Trạng thái: %-10s\n",
                        e.getId(), e.getName(), e.getSalary(), e.getStatus().getLabel());
            }

            System.out.println("\n[n] Trang tiếp | [p] Trang trước | [q] Thoát");
            String cmd = InputUtil.inputString("Chọn: ").toLowerCase();
            switch (cmd) {
                case "n" -> {
                    if (currentPage < totalPages) currentPage++;
                    else System.out.println("Bạn đang ở trang cuối.");
                }
                case "p" -> {
                    if (currentPage > 1) currentPage--;
                    else System.out.println("Bạn đang ở trang đầu.");
                }
                case "q" -> {
                    System.out.println("Thoát danh sách nhân viên.");
                    return;
                }
                default -> System.out.println("Lệnh không hợp lệ.");
            }
        }
    }

    public void addEmployee() {
        String name = InputUtil.inputString("Nhập tên nhân viên: ");
        double salary = InputUtil.inputDouble("Nhập lương: ");
        int departmentId = InputUtil.inputInt("Nhập ID phòng ban: ");
        System.out.println("Chọn trạng thái: 1. Đang làm | 2. Nghỉ phép | 3. Nghỉ việc");
        int status = InputUtil.inputInt("Chọn: ");
        Employee employee = new Employee();
        employee.setName(name);
        employee.setSalary(salary);
        employee.setDepartmentId(departmentId);
        employee.setStatus(EmployeeStatus.fromInt(status));
        employeeDAO.save(employee);
        System.out.println("Thêm nhân viên thành công.");
    }

    public void updateEmployee() {
        String id = InputUtil.inputString("Nhập ID nhân viên cần sửa: ");
        Employee employee = employeeDAO.findById(id);
        if (employee == null) {
            System.out.println("Không tìm thấy nhân viên.");
            return;
        }

        String name = InputUtil.inputString("Nhập tên mới: ");
        double salary = InputUtil.inputDouble("Nhập lương mới: ");
        int departmentId = InputUtil.inputInt("Nhập ID phòng ban mới: ");
        System.out.println("Chọn trạng thái: 1. Đang làm | 2. Nghỉ phép | 3. Nghỉ việc");
        int status = InputUtil.inputInt("Chọn: ");

        employee.setName(name);
        employee.setSalary(salary);
        employee.setDepartmentId(departmentId);
        employee.setStatus(EmployeeStatus.fromInt(status));

        employeeDAO.update(employee);
        System.out.println("Cập nhật nhân viên thành công.");
    }

    public void deleteEmployee() {
        int id = InputUtil.inputInt("Nhập ID nhân viên cần xóa: ");
        employeeDAO.delete(id);
        System.out.println("Đã xóa nhân viên.");
    }

    public void sortByName() {
        List<Employee> list = employeeDAO.findAll();
        list.sort(Comparator.comparing(Employee::getName));
        list.forEach(e -> System.out.printf("ID: %-5d | Tên: %-20s | Lương: %.2f\n", e.getId(), e.getName(), e.getSalary()));
    }

    public void sortBySalaryDesc() {
        List<Employee> list = employeeDAO.findAll();
        list.sort(Comparator.comparing(Employee::getSalary).reversed());
        list.forEach(e -> System.out.printf("ID: %-5d | Tên: %-20s | Lương: %.2f\n", e.getId(), e.getName(), e.getSalary()));
    }

    public void sortByNameAsc() {
        List<Employee> list = employeeDAO.findAll();
        if (list.isEmpty()) {
            System.out.println("Không có nhân viên để sắp xếp.");
            return;
        }

        list.sort(Comparator.comparing(Employee::getName));
        System.out.println("Danh sách nhân viên (sắp xếp theo tên tăng dần):");
        for (Employee e : list) {
            System.out.printf("ID: %-5d | Tên: %-20s | Lương: %.2f | Trạng thái: %-10s\n",
                    e.getId(), e.getName(), e.getSalary(), e.getStatus().getLabel());
        }
    }


    public void searchByName() {
        String keyword = InputUtil.inputString("Nhập tên cần tìm: ");
        List<Employee> list = employeeDAO.findAll()
                .stream()
                .filter(e -> e.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());

        if (list.isEmpty()) {
            System.out.println("Không tìm thấy nhân viên.");
        } else {
            list.forEach(e -> System.out.printf("ID: %-5d | Tên: %-20s | Lương: %.2f\n", e.getId(), e.getName(), e.getSalary()));
        }
    }

    public long countByDepartment(int departmentId) {
        return employeeDAO.findAll().stream()
                .filter(e -> e.getDepartmentId() == departmentId)
                .count();
    }

    public double totalSalaryByDepartment(int departmentId) {
        return employeeDAO.findAll().stream()
                .filter(e -> e.getDepartmentId() == departmentId)
                .mapToDouble(Employee::getSalary)
                .sum();
    }
}
