import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalTime;
import java.util.List;

import org.testng.annotations.Test;

public class TestCases
{
   @Test
   public void testExercise1()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 40), LocalTime.of(11, 0));
      final CourseSection two = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 40), LocalTime.of(11, 0));

      assertTrue(one.equals(two));
      assertTrue(two.equals(one));
   }

   @Test
   public void testExercise2()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 10), LocalTime.of(10, 0));
      final CourseSection two = new CourseSection("CSC", "203", 35,
         LocalTime.of(1, 10), LocalTime.of(2, 0));

      assertFalse(one.equals(two));
      assertFalse(two.equals(one));
   }

   @Test
   public void testExercise3()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 40), LocalTime.of(11, 0));
      final CourseSection two = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 40), LocalTime.of(11, 0));

      assertEquals(one.hashCode(), two.hashCode());
   }

   @Test
   public void testExercise4()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 10), LocalTime.of(10, 0));
      final CourseSection two = new CourseSection("CSC", "203", 34,
         LocalTime.of(9, 10), LocalTime.of(10, 0));

      assertNotEquals(one.hashCode(), two.hashCode());
      assertFalse(one.equals(two));

   }

   // Own test cases
   // Check if Same data which should be true
   @Test
   public void testExercise5()
   {
      List<CourseSection> list1 = Arrays.asList(
              new CourseSection("CSC", "203", 35, LocalTime.of(9, 10), LocalTime.of(10, 0)),
              new CourseSection("CSC", "203", 35, LocalTime.of(9, 10), LocalTime.of(10, 0)));

      final Student one = new Student("Bob", "Dylan", 35, list1);
      final Student two = new Student("Bob", "Dylan", 35, list1);

      assertEquals(one.hashCode(), two.hashCode());
      assertTrue(one.equals(two));
   }

   // Different surnames
   @Test
   public void testExercise6()
   {
      List<CourseSection> list1 = Arrays.asList(
              new CourseSection("CSC", "203", 35, LocalTime.of(9, 10), LocalTime.of(10, 0)),
              new CourseSection("CSC", "203", 35, LocalTime.of(9, 10), LocalTime.of(10, 0)));

      List<CourseSection> list2 = Arrays.asList(
              new CourseSection("CSC", "203", 35, LocalTime.of(9, 10), LocalTime.of(10, 0)),
              new CourseSection("CSC", "203", 35, LocalTime.of(9, 10), LocalTime.of(10, 0)));

      List<CourseSection> list3 = Arrays.asList(
              new CourseSection("CSC", "203", 35, LocalTime.of(9, 10), LocalTime.of(10, 0)),
              new CourseSection("Math", "143", 39, LocalTime.of(9, 10), LocalTime.of(10, 0)));

      final Student one = new Student("Bob", "Dylan", 35, list1);
      final Student two = new Student("Dylan", "Dylan", 35, list1);

      assertNotEquals(one.hashCode(), two.hashCode());
      assertTrue(list1.equals(list2));
      assertFalse(list1.equals(list3));
   }


   // Null edge cases
   @Test
   public void testExercise7(){
      List<CourseSection> list1 = Arrays.asList(
              new CourseSection("CSC", "203", 35, LocalTime.of(9, 10), LocalTime.of(10, 0)),
              new CourseSection("CSC", "203", 35, LocalTime.of(9, 10), LocalTime.of(10, 0)));

      final Student one = new Student("Bob", "Dylan", 35, list1);

      assertNotEquals(one, null);
   }

   // Check typing
   @Test
   public void testExercise8(){
      List<CourseSection> list1 = Arrays.asList(
              new CourseSection("CSC", "203", 35, LocalTime.of(9, 10), LocalTime.of(10, 0)));

      final Student one = new Student("Bob", "Dylan", 35, list1);

      assertNotEquals(one, "Teacher");
   }



}
