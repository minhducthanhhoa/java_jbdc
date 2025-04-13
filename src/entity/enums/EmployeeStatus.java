package entity.enums;

public enum EmployeeStatus {
    WORKING(1, "Đang làm"),
    VACATION(2, "Nghỉ phép"),
    RESIGNED(3, "Nghỉ việc");

    private final int value;
    private final String label;

    EmployeeStatus(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public static EmployeeStatus fromInt(int value) {
        for (EmployeeStatus status : EmployeeStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Giá trị trạng thái không hợp lệ: " + value);
    }
}
