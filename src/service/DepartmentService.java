package service;

import dao.DepartmentDao;
import entity.Department;
import service.EmployeeService;
import util.InputUtil;

import java.util.List;

public class DepartmentService {
    private final DepartmentDao departmentDAO = new DepartmentDao();

    public List<Department> getAll() {
        return departmentDAO.findAll();
    }

    public void displayDepartmentsWithPaging() {
        List<Department> departments = departmentDAO.findAll();
        if (departments.isEmpty()) {
            System.out.println("Không có phòng ban nào để hiển thị.");
            return;
        }

        final int pageSize = 5;
        int total = departments.size();
        int totalPages = (int) Math.ceil((double) total / pageSize);
        int currentPage = 1;

        while (true) {
            System.out.println("\nDanh sách phòng ban - Trang " + currentPage + "/" + totalPages);
            int start = (currentPage - 1) * pageSize;
            int end = Math.min(start + pageSize, total);

            for (int i = start; i < end; i++) {
                Department d = departments.get(i);
                System.out.printf("ID: %-5d | Tên: %-20s | Trạng thái: %-10s\n",
                        d.getId(), d.getName(), d.isStatus() ? "Hoạt động" : "Không hoạt động");
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
                    System.out.println("Thoát danh sách phòng ban.");
                    return;
                }
                default -> System.out.println("Lệnh không hợp lệ.");
            }
        }
    }

    public void addDepartment() {
        String name = InputUtil.inputString("Nhập tên phòng ban: ");
        String description = InputUtil.inputString("Nhập mô tả: ");
        Department department = new Department();
        department.setName(name);
        department.setDescription(description);
        department.setStatus(true);
        departmentDAO.save(department);
        System.out.println("Thêm phòng ban thành công.");
    }

    public void updateDepartment() {
        int id = InputUtil.inputInt("Nhập ID phòng ban cần sửa: ");
        Department department = departmentDAO.findById(id);
        if (department == null) {
            System.out.println("Không tìm thấy phòng ban.");
            return;
        }
        String name = InputUtil.inputString("Nhập tên mới: ");
        String description = InputUtil.inputString("Nhập mô tả mới: ");
        department.setName(name);
        department.setDescription(description);
        departmentDAO.update(department);
        System.out.println("Cập nhật phòng ban thành công.");
    }

    public void deleteDepartment(EmployeeService employeeService) {
        int id = InputUtil.inputInt("Nhập ID phòng ban cần xóa: ");
        long count = employeeService.countByDepartment(id);
        if (count > 0) {
            System.out.println("Không thể xóa. Phòng ban còn nhân viên.");
            return;
        }
        departmentDAO.delete(id);
        System.out.println("Xóa phòng ban thành công.");
    }

    public void departmentWithMostEmployees(EmployeeService employeeService) {
        List<Department> departments = departmentDAO.findAll();
        Department maxDept = null;
        long maxCount = -1;
        for (Department d : departments) {
            long count = employeeService.countByDepartment(d.getId());
            if (count > maxCount) {
                maxCount = count;
                maxDept = d;
            }
        }
        if (maxDept != null) {
            System.out.printf("Phòng ban nhiều nhân viên nhất: %s (%d nhân viên)\n", maxDept.getName(), maxCount);
        } else {
            System.out.println("Không có dữ liệu.");
        }
    }

    public void departmentWithHighestSalary(EmployeeService employeeService) {
        List<Department> departments = departmentDAO.findAll();
        Department maxDept = null;
        double maxSalary = -1;
        for (Department d : departments) {
            double totalSalary = employeeService.totalSalaryByDepartment(d.getId());
            if (totalSalary > maxSalary) {
                maxSalary = totalSalary;
                maxDept = d;
            }
        }
        if (maxDept != null) {
            System.out.printf("Phòng ban có tổng lương cao nhất: %s (%.2f)\n", maxDept.getName(), maxSalary);
        } else {
            System.out.println("Không có dữ liệu.");
        }
    }
}
