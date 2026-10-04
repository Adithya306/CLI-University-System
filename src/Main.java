import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[]args){

        Scanner scanner = new Scanner(System.in);

//        Student firstStudent = new Student("Garuka", 101);
//        Student secondStudent = new Student("Nuwan", 102);

//      Student [] students = new Student[10];


//        moved this array list to the inmemoryRepository class and call the intialize the inmemory repository in main class
//        List<Student> students = new ArrayList<>();

        StudentRepository repository = new InMemoryRepository();

//        students.add(firstStudent);
//        students.add(secondStudent);

//        for (Student student : students){
//            System.out.println(student);
//        }
//
//        System.out.println(firstStudent.getStudentID());
        while(true) {

            System.out.println("---Kuppi CLI Menu---");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Exit");
            System.out.println("4. Add Tutor");
            int choice = scanner.nextInt();
            scanner.nextLine();

//            if(choice == 3){
//                break;
//            }

            switch(choice){

//                case 1:{
//                    System.out.println("Add Student");
//                }
//                case 1 -> System.out.println("Add Student");
//                case 2 -> System.out.println("View All Students");
//                case 3 -> {
//                    System.out.println("Exit");
//                    return;
//                }
//                default -> System.out.println("Invalid choice");

                case 1 -> {

                    try {
                        System.out.println("Enter Student Name");
                        String name = scanner.nextLine();
                        System.out.println("Enter Student ID");
                        int id = scanner.nextInt();

//                        students.add(new Student(name, id));

                        repository.save(new Student(name,id));

                        System.out.println("Student Added successfully");
                    } catch (Exception e){
                        System.out.println("Error: Invalid input. Student ID must be a number.");
                        scanner.nextLine();
                    }

                }

                case 2 -> {

//                    commented below lines and directly added the new repository object and call displayAll method

//                    System.out.println("\n Registered students");

//                    for(Student student : students){
//                        System.out.println(student);
//                    }

                    repository.displayAll();
                }
                case 3 -> {
                    System.out.println("Exiting the program");
                    return;
                }

                case 4 ->{
                    try {
                        System.out.println("Enter Tutor name");
                        String name = scanner.nextLine();

                        System.out.println("Enter Tutor ID");
                        int id = scanner.nextInt();

                        scanner.nextLine();

                        System.out.println("Enter Tutor Specialization");
                        String specialization = scanner.nextLine();

                        repository.save(new Tutor(name, id, specialization));

                    } catch (Exception e){
                        System.out.println(e);
                        scanner.nextLine();
                    }

                }
                default -> {
                    System.out.println("Invalid choice");
                }

            }
        }

    }
}
