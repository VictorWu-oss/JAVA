package part2;

import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import part1.Course;
import part1.CourseGrade;
import part1.ExampleMap;
import part1.SimpleIf;
import part2.Applicant;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class TestCases
{
   /*
    * This test is just to get you started.
    */
   @Test
   public void testGetName()
   {
      // This will not compile until you implement the Applicant class
      List<CourseGrade> grades = Arrays.asList(
         new CourseGrade("Intro to CS", 100),
         new CourseGrade("Data Structures", 95),
         new CourseGrade("Algorithms", 91),
         new CourseGrade("Computer Organization", 91),
         new CourseGrade("Operating Systems", 75),
         new CourseGrade("Non-CS", 83)
      );
      Applicant testApplicant = new Applicant("Aakash", grades, "Bachelors", "Indian");
      assertEquals("Aakash", testApplicant.getName());
   }

   /*
    * The tests below here are to verify the basic requirements regarding
    * the "design" of your class.  These are to remain unchanged.
    */
   @Test
   public void testImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getName",
         "getGrades",
         "getGradeFor",
         "getRace",      // Added
         "getEducation" // Added
      );

      final List<Class> expectedMethodReturns = Arrays.asList(
         String.class,
         List.class,
         CourseGrade.class,
         String.class, // Added
         String.class // Added
      );

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0],
         new Class[0],
         new Class[] { String.class },
         new Class[0], // Added
         new Class[0]   // Added
      );

      verifyImplSpecifics(Applicant.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   private static void verifyImplSpecifics(
      final Class<?> clazz,
      final List<String> expectedMethodNames,
      final List<Class> expectedMethodReturns,
      final List<Class[]> expectedMethodParameters)
      throws NoSuchMethodException
   {
      assertEquals(0, Applicant.class.getFields().length,
              "Unexpected number of public fields");

      final List<Method> publicMethods = Arrays.stream(
         clazz.getDeclaredMethods())
            .filter(m -> Modifier.isPublic(m.getModifiers()))
            .collect(Collectors.toList());

      assertEquals(expectedMethodNames.size(), publicMethods.size(),
              "Unexpected number of public methods");

      assertTrue(expectedMethodNames.size() == expectedMethodReturns.size(),
              "Invalid test configuration");
      assertTrue(expectedMethodNames.size() == expectedMethodParameters.size(),
              "Invalid test configuration");

      for (int i = 0; i < expectedMethodNames.size(); i++)
      {
         Method method = clazz.getDeclaredMethod(expectedMethodNames.get(i),
            expectedMethodParameters.get(i));
         assertEquals(expectedMethodReturns.get(i), method.getReturnType());
      }
   }

   // Tests if getGradeFor(String) returns the right score
   @Test
   public void test1(){
      List<CourseGrade> grades = Arrays.asList(
              new CourseGrade("Intro to CS", 100),
              new CourseGrade("Data Structures", 95),
              new CourseGrade("Algorithms", 91),
              new CourseGrade("Computer Organization", 91),
              new CourseGrade("Operating Systems", 75),
              new CourseGrade("Non-CS", 83)
      );
      Applicant testApplicant = new Applicant("Aakash", grades, "Bachelors", "Asian");
      CourseGrade grade = testApplicant.getGradeFor("Intro to CS");
      assertEquals(100, grade.getScore());
   }

   // Tests if getGrades returns the same list of grades for the Applicant
   @Test
   public void test2(){
      List<CourseGrade> grades = Arrays.asList(
              new CourseGrade("Intro to CS", 100),
              new CourseGrade("Data Structures", 95),
              new CourseGrade("Algorithms", 91),
              new CourseGrade("Computer Organization", 91),
              new CourseGrade("Operating Systems", 75),
              new CourseGrade("Non-CS", 83)
      );
      Applicant testApplicant = new Applicant("Aakash", grades, "Bachlors", "Asian");
      assertEquals(grades, testApplicant.getGrades());
   }

   // Tests for a Course that doesn't exist and checks for the score of other courses
   @Test
   public void test3(){
      List<CourseGrade> grades = Arrays.asList(
              new CourseGrade("Intro to CS", 100),
              new CourseGrade("Data Structures", 95),
              new CourseGrade("Algorithms", 91),
              new CourseGrade("Computer Organization", 91),
              new CourseGrade("Operating Systems", 75),
              new CourseGrade("Non-CS", 83)
      );
      Applicant testApplicant = new Applicant("Aakash", grades, "Bachelors", "Asian");
      CourseGrade grade1 = testApplicant.getGradeFor(("Intro To Philosphy"));
      CourseGrade grade2 = testApplicant.getGradeFor(("Data Structures"));
      CourseGrade grade3 = testApplicant.getGradeFor(("Algorithms"));

      assertNull(grade1);
      assertEquals(95, grade2.getScore());
      assertEquals(91, grade3.getScore());
   }

   // Tests for an Applicant that doesn't exist
   @Test
   public void test4(){
      List<CourseGrade> grades = Arrays.asList(
              new CourseGrade("Intro to CS", 100),
              new CourseGrade("Data Structures", 95),
              new CourseGrade("Algorithms", 91),
              new CourseGrade("Computer Organization", 91),
              new CourseGrade("Operating Systems", 75),
              new CourseGrade("Non-CS", 83)
      );
      Applicant testApplicant = new Applicant("Aakash", grades, "Bachelors", "Asian");
      assertNotEquals("Victor", testApplicant.getName());
   }


   // Tests for filter: Grades, Education, and Race
   @Test
   public void test5(){
      List<Applicant> allApplicants = new LinkedList<>();

      List<CourseGrade> grades1 = Arrays.asList(
              new CourseGrade("Intro to CS", 100),
              new CourseGrade("Data Structures", 95),
              new CourseGrade("Algorithms", 91),
              new CourseGrade("Computer Organization", 91),
              new CourseGrade("Operating Systems", 75),
              new CourseGrade("Non-CS", 83)
      );
      Applicant a = new Applicant("Julie", grades1, "Phd", "Asian"); // Fails by grade in OS and Non-CS
      allApplicants.add(a);

      List<CourseGrade> grades2 = Arrays.asList(
              new CourseGrade("Intro to CS", 100),
              new CourseGrade("Data Structures", 100),
              new CourseGrade("Algorithms", 100),
              new CourseGrade("Computer Organization", 100),
              new CourseGrade("Operating Systems", 100),
              new CourseGrade("Non-CS", 100)
      );
      Applicant b = new Applicant("Victor", grades2, "Bachelors", "Asian"); // Passes
      allApplicants.add(b);

      List<String> expected = Arrays.asList("Victor");
      List<String> result = SimpleIf.analyzeApplicant2(allApplicants, 85);
      assertEquals(new java.util.HashSet<>(expected), new java.util.HashSet<>(result));
   }


   // Tests for filter: Grades, Education, and Race
   @Test
   public void test6(){
      List<Applicant> allApplicants = new LinkedList<>();

      List<CourseGrade> grades1 = Arrays.asList(
              new CourseGrade("CPE 123", 90),
              new CourseGrade("CPE 101", 91),
              new CourseGrade("CPE 202", 92),
              new CourseGrade("CPE 203", 100),
              new CourseGrade("CPE 225", 89)
      );
      Applicant a = new Applicant("Julie", grades1, "Phd", "Black"); // Passes
      allApplicants.add(a);

      List<CourseGrade> grades2 = Arrays.asList(
              new CourseGrade("CPE 101", 87),
              new CourseGrade("CPE 202", 90),
              new CourseGrade("CPE 203", 91),
              new CourseGrade("CPE 225", 100)
      );
      Applicant b = new Applicant("Victor", grades2, "High School", "Asian"); // Fails by Education
      allApplicants.add(b);

      List<CourseGrade> grades3 = Arrays.asList(
              new CourseGrade("CPE 123", 100),
              new CourseGrade("CPE 203", 92),
              new CourseGrade("CPE 471", 85),
              new CourseGrade("CPE 473", 100),
              new CourseGrade("CPE 476", 90),
              new CourseGrade("CPE 572", 91)
      );
      Applicant c = new Applicant("Sarah", grades3, "Bachelors", "White"); // Fails by race
      allApplicants.add(c);

      List<String> expected = Arrays.asList("Julie");
      List<String> result = SimpleIf.analyzeApplicant2(allApplicants, 70);
      assertEquals(new java.util.HashSet<>(expected), new java.util.HashSet<>(result));
   }


   // Tests for filter: Grades, Education, and Race
   @Test
   public void test7(){
      List<Applicant> allApplicants = new LinkedList<>();

      List<CourseGrade> grades1 = Arrays.asList(
              new CourseGrade("CPE 123", 90),
              new CourseGrade("CPE 101", 91),
              new CourseGrade("CPE 202", 92),
              new CourseGrade("CPE 203", 100),
              new CourseGrade("CPE 225", 89)
      );
      Applicant a = new Applicant("Julie", grades1, "High School", "American Indian"); // Fails by education
      allApplicants.add(a);

      List<CourseGrade> grades2 = Arrays.asList(
              new CourseGrade("CPE 101", 87),
              new CourseGrade("CPE 202", 90),
              new CourseGrade("CPE 203", 91),
              new CourseGrade("CPE 225", 100)
      );
      Applicant b = new Applicant("Victor", grades2, "High School", "Asian"); // Fails by education and grades
      allApplicants.add(b);

      List<CourseGrade> grades3 = Arrays.asList(
              new CourseGrade("CPE 123", 100),
              new CourseGrade("CPE 203", 92),
              new CourseGrade("CPE 471", 85),
              new CourseGrade("CPE 473", 100),
              new CourseGrade("CPE 476", 90),
              new CourseGrade("CPE 572", 91)
      );
      Applicant c = new Applicant("Sarah", grades3, "Middle School", "White"); // Fails by all 3
      allApplicants.add(c);

      List<String> expected = Arrays.asList();
      List<String> result = SimpleIf.analyzeApplicant2(allApplicants, 85);
      assertEquals(new java.util.HashSet<>(expected), new java.util.HashSet<>(result));
   }
}
