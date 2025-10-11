import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Student {
    private String name;
    private int age;
    private double gpa;
    public Student(String name, int age, double gpa)   {
        this.name = name;
        this.age = age;
        this.gpa = gpa;
    }
    public String getName(){      return name;   }
    public int getAge()    {      return age;   }
    public double getGpa() {      return gpa;   }
}

public class StudentStreamDriver {

    public static List<Student> readInStudents()  {
        List<Student> students = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File("students.txt"));
            while (sc.hasNext()) {
                students.add(new Student(sc.nextLine(),
                        sc.nextInt(),
                        sc.nextDouble()));
                sc.nextLine();
            }
        }
        catch (Exception e) {
            System.out.println("Can't open input file.");
        }
        return students;
    }


    public static void main(String[] args) throws FileNotFoundException {
        List<Student> students = new ArrayList<>();
        /****** Create List of Students from given file *************/
        students = readInStudents();

        /* 1) use stream to filter out students with gpa>=3.20 */
        List<Student> topStudents = students
                .stream()
                .filter(student -> student.getGpa() >= 3.20)
                .collect(Collectors.toList());

        // 2) print topStudents using stream
        for (Student s : topStudents){
            System.out.print(s + ", ");
        }

        // 3a) Using stream, compute average GPA of Students */
        System.out.println("\nAverage Student GPA: " + students
                                .stream()
                                .mapToDouble(student -> student.getGpa())
                                .average()
                                .orElse(0.0));


        // 3b) use reduce for finding average
        double sum = students
                .stream()
                .mapToDouble(Student::getGpa)
                .reduce(0.0, Double::sum);

        double avg = sum / students.size();

        System.out.println("Average GPA: " + avg);


        // 4) Filter students with GPA < 2.77 and raise their GPA by .15 */
        List<Student> raisedStudents = students
                .stream()
                .map(s -> {
                    if(s.getGpa() < 2.77){
                        return new Student(s.getName(), s.getAge(), s.getGpa() + 0.15);
                    }
                    else{
                        return s;
                    }
                })
                .collect(Collectors.toList();

    }
}
