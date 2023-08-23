
# Student Management System

The Student Management System is a Java-based command-line application that allows users to manage student records in an Oracle database. This project demonstrates the use of Java, JDBC, and SQL for CRUD (Create, Read, Update, Delete) operations on student records.

## Features

- **Add Student**: Add a new student to the database with a unique ID, name, and age.

- **View Student by ID**: Retrieve and display a student's information by their ID.

- **View All Students**: Retrieve and display information for all students in the database.

- **Update Student**: Update a student's name and age by specifying their ID.

- **Delete Student**: Delete a student's record by specifying their ID.

## Prerequisites

- Java Development Kit (JDK) installed on your system.
- Oracle Database installed and accessible.
- JDBC driver for Oracle (typically included in Oracle Database installations).

## Installation

1. Clone this repository to your local machine using `git clone`.
2. Navigate to the project directory:
 `cd student-management-system`

3. Compile the Java source files:
   `javac *.java`


## Usage
1. Make sure your Oracle Database is running and accessible.
2. Update the database connection details in the `StudentManagementSystem.java` file:


Replace `"your_database_url"`, `"your_port"`, `"your_sid"`, `"your_username"`, and `"your_password"` with your actual Oracle Database connection details.

3. Run the program:
java StudentManagementSystem


4. Follow the on-screen menu to interact with the Student Management System.

## Contributing

Contributions to this project are welcome! Feel free to open issues, create pull requests, or suggest improvements.


