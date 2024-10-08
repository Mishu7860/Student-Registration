import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Course {
    String courseCode;
    String title;
    String description;
    int capacity;
    int registeredStudents;

    Course(String courseCode, String title, String description, int capacity) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.registeredStudents = 0;
    }

    boolean registerStudent() {
        if (registeredStudents < capacity) {
            registeredStudents++;
            return true;
        }
        return false;
    }

    boolean dropStudent() {
        if (registeredStudents > 0) {
            registeredStudents--;
            return true;
        }
        return false;
    }

    boolean isFull() {
        return registeredStudents >= capacity;
    }

    @Override
    public String toString() {
        return "Course Code: " + courseCode + ", Title: " + title + ", Description: " + description +
               ", Capacity: " + capacity + ", Registered Students: " + registeredStudents;
    }
}

class Student {
    String studentID;
    String name;
    List<Course> registeredCourses;

    Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    void registerCourse(Course course) {
        if (course.registerStudent()) {
            registeredCourses.add(course);
            System.out.println("Successfully registered for course: " + course.title);
        } else {
            System.out.println("Course is full: " + course.title);
        }
    }

    void dropCourse(Course course) {
        if (registeredCourses.remove(course)) {
            course.dropStudent();
            System.out.println("Successfully dropped course: " + course.title);
        } else {
            System.out.println("You are not registered for this course: " + course.title);
        }
    }
}

public class CourseRegistrationSystem {
    private static List<Course> courses = new ArrayList<>();
    private static List<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        initializeCourses();
        
        System.out.print("Enter Student ID: ");
        String studentID = scanner.nextLine();
        System.out.print("Enter Student Name: ");
        String studentName = scanner.nextLine();

        Student student = new Student(studentID, studentName);
        students.add(student);

        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. View Courses");
            System.out.println("2. Register for a Course");
            System.out.println("3. Drop a Course");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewCourses();
                    break;
                case 2:
                    registerForCourse(scanner, student);
                    break;
                case 3:
                    dropCourse(scanner, student);
                    break;
                case 4:
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    private static void initializeCourses() {
        courses.add(new Course("CS101", "Introduction to Computer Science", "Basic concepts of computer science.", 30));
        courses.add(new Course("CS102", "Data Structures", "Learn about data structures and algorithms.", 25));
        courses.add(new Course("CS201", "Database Management", "Introduction to database systems.", 20));
        courses.add(new Course("CS202", "Web Development", "Learn how to build web applications.", 35));
    }

    private static void viewCourses() {
        System.out.println("\nAvailable Courses:");
        for (Course course : courses) {
            System.out.println(course);
        }
    }

    private static void registerForCourse(Scanner scanner, Student student) {
        System.out.print("Enter the course code to register: ");
        String courseCode = scanner.next();
        Course course = findCourse(courseCode);

        if (course != null) {
            student.registerCourse(course);
        } else {
            System.out.println("Course not found: " + courseCode);
        }
    }

    private static void dropCourse(Scanner scanner, Student student) {
        System.out.print("Enter the course code to drop: ");
        String courseCode = scanner.next();
        Course course = findCourse(courseCode);

        if (course != null) {
            student.dropCourse(course);
        } else {
            System.out.println("Course not found: " + courseCode);
        }
    }

    private static Course findCourse(String courseCode) {
        for (Course course : courses) {
            if (course.courseCode.equalsIgnoreCase(courseCode)) {
                return course;
            }
        }
        return null; // Course not found
    }
}
