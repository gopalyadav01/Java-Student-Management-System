import java.util.ArrayList;
import java.util.Scanner;

public class StudentManagementSystem {

    private static final ArrayList<Student> students = FileHandler.loadStudents();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("      STUDENT MANAGEMENT SYSTEM");
        System.out.println("======================================");

        while (true) {

            displayMenu();

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    addStudent();
                    break;

                case 2:
                    viewStudents();
                    break;

                case 3:
                    searchStudent();
                    break;

                case 4:
                    filterStudents();
                    break;

                case 5:
                    updateStudent();
                    break;

                case 6:
                    deleteStudent();
                    break;

                case 7:
                    FileHandler.saveStudents(students);
                    System.out.println("Thank you for using the system.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please select 1-7.");
            }
        }
    }

    // Display main menu
    private static void displayMenu() {

        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student");
        System.out.println("4. Filter Students");
        System.out.println("5. Update Student");
        System.out.println("6. Delete Student");
        System.out.println("7. Exit");
        System.out.println("===============================");
    }

    // Add student
    private static void addStudent() {

        System.out.println("\n----- Add Student -----");

        int id = readPositiveInt("Enter student ID: ");

        if (findStudentById(id) != null) {
            System.out.println("Student ID already exists.");
            return;
        }

        String name = readNonEmptyString("Enter student name: ");
        String course = readNonEmptyString("Enter course: ");

        double marks = readMarks();

        Student student = new Student(id, name, course, marks);

        students.add(student);

        FileHandler.saveStudents(students);

        System.out.println("Student added successfully.");
    }

    // View all students
    private static void viewStudents() {

        System.out.println("\n----- Student Records -----");

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        displayStudentList(students);
    }

    // Search student
    private static void searchStudent() {

        System.out.println("\n----- Search Student -----");

        System.out.println("1. Search by ID");
        System.out.println("2. Search by Name");

        int choice = readInt("Enter search option: ");

        if (choice == 1) {

            int id = readPositiveInt("Enter student ID: ");

            Student student = findStudentById(id);

            if (student != null) {
                System.out.println("\nStudent found:");
                System.out.println(student);
            } else {
                System.out.println("Student not found.");
            }

        } else if (choice == 2) {

            String name = readNonEmptyString("Enter student name: ");

            boolean found = false;

            for (Student student : students) {

                if (student.getName().toLowerCase()
                        .contains(name.toLowerCase())) {

                    System.out.println(student);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No student found with that name.");
            }

        } else {
            System.out.println("Invalid search option.");
        }
    }

    // Filter students
    private static void filterStudents() {

        System.out.println("\n----- Filter Students -----");

        System.out.println("1. Filter by Course");
        System.out.println("2. Filter by Minimum Marks");

        int choice = readInt("Enter filter option: ");

        ArrayList<Student> filteredStudents = new ArrayList<>();

        if (choice == 1) {

            String course = readNonEmptyString("Enter course: ");

            for (Student student : students) {

                if (student.getCourse().equalsIgnoreCase(course)) {
                    filteredStudents.add(student);
                }
            }

        } else if (choice == 2) {

            double minimumMarks = readMarks();

            for (Student student : students) {

                if (student.getMarks() >= minimumMarks) {
                    filteredStudents.add(student);
                }
            }

        } else {

            System.out.println("Invalid filter option.");
            return;
        }

        if (filteredStudents.isEmpty()) {
            System.out.println("No students matched your filter.");
        } else {
            System.out.println("\nFiltered Student Records:");
            displayStudentList(filteredStudents);
        }
    }

    // Update student
    private static void updateStudent() {

        System.out.println("\n----- Update Student -----");

        int id = readPositiveInt("Enter student ID to update: ");

        Student student = findStudentById(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Current student details:");
        System.out.println(student);

        String name = readNonEmptyString("Enter new name: ");
        String course = readNonEmptyString("Enter new course: ");
        double marks = readMarks();

        student.setName(name);
        student.setCourse(course);
        student.setMarks(marks);

        FileHandler.saveStudents(students);

        System.out.println("Student updated successfully.");
    }

    // Delete student
    private static void deleteStudent() {

        System.out.println("\n----- Delete Student -----");

        int id = readPositiveInt("Enter student ID to delete: ");

        Student student = findStudentById(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Student to be deleted:");
        System.out.println(student);

        System.out.print("Are you sure you want to delete this student? (yes/no): ");

        String confirmation = scanner.nextLine().trim();

        if (confirmation.equalsIgnoreCase("yes")) {

            students.remove(student);

            FileHandler.saveStudents(students);

            System.out.println("Student deleted successfully.");

        } else {
            System.out.println("Delete operation cancelled.");
        }
    }

    // Find student by ID
    private static Student findStudentById(int id) {

        for (Student student : students) {

            if (student.getId() == id) {
                return student;
            }
        }

        return null;
    }

    // Display a list of students
    private static void displayStudentList(ArrayList<Student> studentList) {

        for (Student student : studentList) {
            System.out.println(student);
        }
    }

    // Read integer safely
    private static int readInt(String message) {

        while (true) {

            System.out.print(message);

            try {

                return Integer.parseInt(scanner.nextLine().trim());

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid integer.");
            }
        }
    }

    // Read positive integer
    private static int readPositiveInt(String message) {

        while (true) {

            int value = readInt(message);

            if (value > 0) {
                return value;
            }

            System.out.println("ID must be greater than 0.");
        }
    }

    // Read marks between 0 and 100
    private static double readMarks() {

        while (true) {

            System.out.print("Enter marks (0-100): ");

            try {

                double marks = Double.parseDouble(
                        scanner.nextLine().trim()
                );

                if (marks >= 0 && marks <= 100) {
                    return marks;
                }

                System.out.println("Marks must be between 0 and 100.");

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid number.");
            }
        }
    }

    // Read non-empty text
    private static String readNonEmptyString(String message) {

        while (true) {

            System.out.print(message);

            String value = scanner.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("This field cannot be empty.");
        }
    }
}
