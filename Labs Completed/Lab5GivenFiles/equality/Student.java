import java.util.List;
import java.util.Objects;

class Student
{
   private final String surname;
   private final String givenName;
   private final int age;
   private final List<CourseSection> currentCourses;

   public Student(final String surname, final String givenName, final int age,
      final List<CourseSection> currentCourses)
   {
      this.surname = surname;
      this.givenName = givenName;
      this.age = age;
      this.currentCourses = currentCourses;
   }

   // implement equals
   @Override
   public boolean equals(Object o){
      if (o == null) return false;
      if (this == o) return true;
      if (!(o instanceof Student)) return false;
      Student student = (Student) o;
      return this.surname.equals(student.surname) &&
              this.givenName.equals(student.givenName) &&
              this.age == student.age &&
              this.currentCourses.equals(student.currentCourses);
   }

   //hashCode using appropriate methods of java.util.Objects
   @Override
   public int hashCode(){
      return Objects.hash(surname, givenName, age, currentCourses);
   }

}
