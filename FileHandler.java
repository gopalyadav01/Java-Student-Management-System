import java.io.*;
import java.util.ArrayList;

public class FileHandler {

    private static final String FILE_PATH = "data/students.txt";

    // Save all students to file
    public static void saveStudents(ArrayList<Student> students) {
        File file = new File(FILE_PATH);

        try {
            File parentDirectory = file.getParentFile();

            if (parentDirectory != null && !parentDirectory.exists()) {
                parentDirectory.mkdirs();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

                for (Student student : students) {
                    writer.write(student.toFileString());
                    writer.newLine();
                }

            }

        } catch (IOException e) {
            System.out.println("Error while saving student data: " + e.getMessage());
        }
    }

    // Load students from file
    public static ArrayList<Student> loadStudents() {
        ArrayList<Student> students = new ArrayList<>();

        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return students;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                try {
                    Student student = Student.fromFileString(line);
                    students.add(student);
                } catch (Exception e) {
                    System.out.println(
                            "Warning: Skipping invalid student record."
                    );
                }
            }

        } catch (IOException e) {
            System.out.println("Error while reading student data: " + e.getMessage());
        }

        return students;
    }
}
