public class Student {
    private int id;
    private String name;
    private String course;
    private double marks;

    public Student(int id, String name, String course, double marks) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.marks = marks;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public double getMarks() {
        return marks;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    // Convert student object to file format
    public String toFileString() {
        return id + "|" + name + "|" + course + "|" + marks;
    }

    // Create Student object from file data
    public static Student fromFileString(String line) {
        String[] data = line.split("\\|");

        if (data.length != 4) {
            throw new IllegalArgumentException("Invalid student data.");
        }

        int id = Integer.parseInt(data[0]);
        String name = data[1];
        String course = data[2];
        double marks = Double.parseDouble(data[3]);

        return new Student(id, name, course, marks);
    }

    @Override
    public String toString() {
        return "ID: " + id +
                ", Name: " + name +
                ", Course: " + course +
                ", Marks: " + marks;
    }
}

