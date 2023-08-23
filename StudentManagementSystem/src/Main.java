import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    // JDBC URL, username, and password of Oracle database
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "candidate";
    private static final String PASSWORD = "candidate12";

    // JDBC variables
    private static Connection connection;

    public static void main(String[] args) {
        try {
            // Connect to the database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Create a StudentDAO instance
            StudentDAO studentDAO = new StudentDAO(connection);

            // Create a Scanner for user input
            Scanner scanner = new Scanner(System.in);

            // User interaction loop
            boolean exit = false;
            while (!exit) {
                System.out.println("Student Management System Menu:");
                System.out.println("1. Add Student");
                System.out.println("2. View Student by ID");
                System.out.println("3. View All Students");
                System.out.println("4. Update Student");
                System.out.println("5. Delete Student");
                System.out.println("6. Exit");
                System.out.print("Select an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        System.out.print("Enter Student ID: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter Student Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Student Age: ");
                        int age = scanner.nextInt();
                        scanner.nextLine();
                        Student student = new Student(id, name, age);
                        studentDAO.addStudent(student);
                        break;
                    case 2:
                        System.out.print("Enter Student ID: ");
                        int searchId = scanner.nextInt();
                        Student retrievedStudent = studentDAO.getStudentById(searchId);
                        if (retrievedStudent != null) {
                            System.out.println("Retrieved Student: " + retrievedStudent);
                        } else {
                            System.out.println("Student not found.");
                        }
                        break;
                    case 3:
                        List<Student> allStudents = studentDAO.getAllStudents();
                        if (!allStudents.isEmpty()) {
                            System.out.println("All Students:");
                            for (Student s : allStudents) {
                                System.out.println(s);
                            }
                        } else {
                            System.out.println("No students found.");
                        }
                        break;
                    case 4:
                        System.out.print("Enter Student ID to update: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter new Student Name: ");
                        String updatedName = scanner.nextLine();
                        System.out.print("Enter new Student Age: ");
                        int updatedAge = scanner.nextInt();
                        scanner.nextLine();
                        Student updatedStudent = new Student(updateId, updatedName, updatedAge);
                        studentDAO.updateStudent(updatedStudent);
                        break;
                    case 5:
                        System.out.print("Enter Student ID to delete: ");
                        int deleteId = scanner.nextInt();
                        studentDAO.deleteStudent(deleteId);
                        break;
                    case 6:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please select a valid option.");
                }
            }

            // Close the Scanner
            scanner.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

class Student {
    private int id;
    private String name;
    private int age;

    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

class StudentDAO {
    private Connection connection;

    public StudentDAO(Connection connection) {
        this.connection = connection;
    }

    public void addStudent(Student student) {
        String insertQuery = "INSERT INTO students (id, name, age) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setInt(1, student.getId());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setInt(3, student.getAge());
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Student added successfully.");
            } else {
                System.out.println("Failed to add student.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Student getStudentById(int id) {
        String selectQuery = "SELECT * FROM students WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Student(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("age")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Student> getAllStudents() {
        String selectAllQuery = "SELECT * FROM students";
        List<Student> students = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectAllQuery)) {
            while (resultSet.next()) {
                students.add(new Student(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("age")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public void updateStudent(Student student) {
        String updateQuery = "UPDATE students SET name = ?, age = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, student.getName());
            preparedStatement.setInt(2, student.getAge());
            preparedStatement.setInt(3, student.getId());
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Student updated successfully.");
            } else {
                System.out.println("Failed to update student.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStudent(int id) {
        String deleteQuery = "DELETE FROM students WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Student deleted successfully.");
            } else {
                System.out.println("Failed to delete student.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
