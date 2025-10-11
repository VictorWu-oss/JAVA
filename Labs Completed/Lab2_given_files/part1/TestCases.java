package part1;

import java.util.*;

import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestCases
{
   private final static double DELTA = 0.0001;

   ////////////////////////////////////////////////////////////
   //                      SimpleIf Tests                    //
   ////////////////////////////////////////////////////////////

   @Test
   public void testAnalyzeApplicant()
   {
      assertTrue(SimpleIf.analyzeApplicant(89, 85));
   }

   @Test
   public void testAnalyzeApplicant2()
   {
      assertFalse(SimpleIf.analyzeApplicant(15, 90));
   }

   @Test
   public void testAnalyzeApplicant3() {assertFalse(SimpleIf.analyzeApplicant(92, 99));}

   /* TO DO: Write one more valid test case. */
   @Test
   public void testAnalyzeApplicant4() {assertTrue(SimpleIf.analyzeApplicant(99, 90));}

   @Test
   public void testMaxAverage() {
      assertEquals(SimpleIf.maxAverage(89.5, 91.2), 91.2, DELTA);
   }

   @Test
   public void testMaxAverage2() {
      assertEquals(SimpleIf.maxAverage(60, 89), 89, DELTA);
   }

   @Test
   public void testMaxAverage3() {assertEquals(SimpleIf.maxAverage(89.9, 90), 90, DELTA);}

   /* TO DO: Write one more valid test case. */
   @Test
   public void testMaxAverage4() {assertEquals(SimpleIf.maxAverage(99.9, 99.1), 99.9, DELTA);}


   ////////////////////////////////////////////////////////////
   //                    SimpleLoop Tests                    //
   ////////////////////////////////////////////////////////////

   @Test
   public void testSimpleLoop1()
   {
      assertEquals(7, SimpleLoop.sum(3, 4));
   }

   @Test
   public void testSimpleLoop2()
   {
      assertEquals(7, SimpleLoop.sum(-2, 4));
   }

   @Test
   public void testSimpleLoop3() {assertEquals(10, SimpleLoop.sum(1, 4));}

   /* TO DO: Write one more valid test case to make sure that this function is not just returning 7. */
   @Test
   public void testSimpleLoop4() {assertEquals(49, SimpleLoop.sum(4,10));}

   /* TO DO: Write one more valid test case to make sure that this function is not just returning 7. */
   @Test
   public void testSimpleLoop5() {assertEquals(33, SimpleLoop.sum(10,12));}

   ////////////////////////////////////////////////////////////
   //                    SimpleArray Tests                   //
   ////////////////////////////////////////////////////////////

   @Test
   public void testSimpleArray1()
   {
      /* What is that parameter?  They are newly allocated arrays with initial values. */
      assertArrayEquals(
         new boolean[] {false, false, true, true, false, false},
         SimpleArray.applicantAcceptable(new int[] {80, 85, 89, 92, 76, 81}, 85)
      );
   }

   @Test
   public void testSimpleArray2()
   {
      assertArrayEquals(
         new boolean[] {false, false},
         SimpleArray.applicantAcceptable(new int[] {80, 83}, 92));
   }

   @Test
   public void testSimpleArray3()
   {
      /* TO DO: Add a new test case. */
      assertArrayEquals(
           new boolean[] {true, false, false, false, true},
              SimpleArray.applicantAcceptable(new int[] {100, 81, 32,52,101}, 90)
      );

      /* TO DO: Add a new test case. */
      assertArrayEquals(
              new boolean[] {true, true, true, false, true},
              SimpleArray.applicantAcceptable(new int[] {100, 100, 100,52,101}, 99)
      );

      assertArrayEquals(
           new boolean[] {false, false, false, true, true, false},
              SimpleArray.applicantAcceptable(new int[] {82, 83, 84, 85, 90, 75}, 84)
      );
   }


   ////////////////////////////////////////////////////////////
   //                    SimpleList Tests                    //
   ////////////////////////////////////////////////////////////

   @Test
   public void testSimpleList1()
   {
      List<Integer> input =
         new LinkedList<>(Arrays.asList(80, 85, 89, 92, 76, 81));
      List<Boolean> expected =
         new ArrayList<>(Arrays.asList(false, false, true, true, false, false));

      assertEquals(expected, SimpleList.applicantAcceptable(input, 85));
   }

   @Test
   public void testSimpleList2()
   {
      List<Boolean> expected = Arrays.asList(false, false, true, true, false, false);
      List<Integer> input = Arrays.asList(80, 85, 89, 92, 76, 81);
      assertEquals(expected, SimpleList.applicantAcceptable(input, 88));
   }

   /* TO DO: Add a new test case. */
   @Test
   public void testSimpleList3(){
      List<Boolean> expected = Arrays.asList(true, true, true, true, true, true, true);
      List<Integer> input = Arrays.asList(90,91,92,93,94,95,96);
      assertEquals(expected, SimpleList.applicantAcceptable(input, 89));
   }

   /* TO DO: Add a new test case. */
   @Test
   public void testSimpleList4(){
      List<Boolean> expected = Arrays.asList(false, true, false, true, false, true, false);
      List<Integer> input = Arrays.asList(46,91,32,93,55,95,61);
      assertEquals(expected, SimpleList.applicantAcceptable(input, 75));
   }

   ////////////////////////////////////////////////////////////
   //                    BetterLoop Tests                    //
   ////////////////////////////////////////////////////////////

   @Test
   public void testFourOver85()
   {
      assertFalse(BetterLoop.atLeastFourOver85(new int[] {89, 93, 100, 39, 84, 63}));
   }

   @Test
   public void testFourOver85_2()
   {
      assertTrue(BetterLoop.atLeastFourOver85(new int[] {89, 86, 90, 92, 84, 88}));
   }

   @Test
   public void testFourOver85_3()
   {
      assertFalse(BetterLoop.atLeastFourOver85(new int[] {65, 30, 45, 17, 90, 80}));

      assertFalse(BetterLoop.atLeastFourOver85(new int[] {85, 80, 85, 75, 80, 92}));
   }

   /* TO DO: Write a valid test case where the expected result is false. */
   @Test
   public void testFourOver85_4(){
      assertFalse(BetterLoop.atLeastFourOver85(new int[] {65,12,45,23,35,23,56,84,43}));
   }

   @Test
   public void testAverage1() {assertEquals(81.0, BetterLoop.average(new int[] {85, 80, 85, 75, 80, 92}), DELTA);}

   /* TO DO: Write one more valid test case */
   @Test
   public void testAverage2() {assertEquals(47.0, BetterLoop.average(new int[] {75, 21, 74, 22, 47, 85}), DELTA);}


   ////////////////////////////////////////////////////////////
   //                    ExampleMap Tests                    //
   ////////////////////////////////////////////////////////////

   @Test
   public void testExampleMap1()
   {
      Map<String, List<CourseGrade>> courseListsByStudent = new HashMap<>();
      courseListsByStudent.put("Julie",
         Arrays.asList(
            new CourseGrade("CPE 123", 89),
            new CourseGrade("CPE 101", 90),
            new CourseGrade("CPE 202", 99),
            new CourseGrade("CPE 203", 100),
            new CourseGrade("CPE 225", 89)));
      courseListsByStudent.put("Paul",
         Arrays.asList(
            new CourseGrade("CPE 101", 86),
            new CourseGrade("CPE 202", 80),
            new CourseGrade("CPE 203", 76),
            new CourseGrade("CPE 225", 80)));
      courseListsByStudent.put("Zoe",
         Arrays.asList(
            new CourseGrade("CPE 123", 99),
            new CourseGrade("CPE 203", 91),
            new CourseGrade("CPE 471", 86),
            new CourseGrade("CPE 473", 90),
            new CourseGrade("CPE 476", 99),
            new CourseGrade("CPE 572", 100)));

      List<String> expected = Arrays.asList("Julie", "Zoe");

      /*
       * Why compare HashSets here?  Just so that the order of the elements in the list is not important for this test.*/
      assertEquals(new HashSet<>(expected),
         new HashSet<>(ExampleMap.highScoringStudents(
            courseListsByStudent, 85)));
   }

   @Test
   public void testExampleMap2()
   {
      Map<String, List<CourseGrade>> courseListsByStudent = new HashMap<>();
      courseListsByStudent.put("Julie",
              Arrays.asList(
                      new CourseGrade("CPE 123", 90),
                      new CourseGrade("CPE 101", 91),
                      new CourseGrade("CPE 202", 92),
                      new CourseGrade("CPE 203", 100),
                      new CourseGrade("CPE 225", 89)));
      courseListsByStudent.put("Paul",
              Arrays.asList(
                      new CourseGrade("CPE 101", 87),
                      new CourseGrade("CPE 202", 90),
                      new CourseGrade("CPE 203", 91),
                      new CourseGrade("CPE 225", 100)));
      courseListsByStudent.put("Zoe",
              Arrays.asList(
                      new CourseGrade("CPE 123", 100),
                      new CourseGrade("CPE 203", 92),
                      new CourseGrade("CPE 471", 85),
                      new CourseGrade("CPE 473", 100),
                      new CourseGrade("CPE 476", 90),
                      new CourseGrade("CPE 572", 91)));

      List<String> expected = Arrays.asList("Julie", "Paul");


      /* Why compare HashSets here?  Just so that the order of the elements in the list is not important for this test.*/
      assertEquals(new HashSet<>(expected),
              new HashSet<>(ExampleMap.highScoringStudents(
                      courseListsByStudent, 86)));
   }

   /* TO DO: Write another valid test case. */
   @Test
   public void testExample4()
   {
      Map<String, List<CourseGrade>> courseListsByStudent = new HashMap<>();
      courseListsByStudent.put("Victor",
              Arrays.asList(
                      new CourseGrade("CPE 123", 100),
                      new CourseGrade("CPE 101", 100),
                      new CourseGrade("CPE 202", 100),
                      new CourseGrade("CPE 203", 100),
                      new CourseGrade("CPE 225", 100)));
      courseListsByStudent.put("Sarah",
              Arrays.asList(
                      new CourseGrade("CPE 101", 99),
                      new CourseGrade("CPE 202", 100),
                      new CourseGrade("CPE 203", 100),
                      new CourseGrade("CPE 225", 100)));
      courseListsByStudent.put("Benny",
              Arrays.asList(
                      new CourseGrade("CPE 123", 100),
                      new CourseGrade("CPE 203", 100),
                      new CourseGrade("CPE 471", 100),
                      new CourseGrade("CPE 473", 100),
                      new CourseGrade("CPE 476", 100),
                      new CourseGrade("CPE 572", 99)));

      List<String> expected = Arrays.asList("Victor");

      /* Why compare HashSets here?  Just so that the order of the elements in the list is not important for this test.*/
      assertEquals(new HashSet<>(expected),
              new HashSet<>(ExampleMap.highScoringStudents(
                      courseListsByStudent, 99)));
   }

   /* TO DO: Write another valid test case. */
   @Test
   public void testExample5()
   {
      Map<String, List<CourseGrade>> courseListsByStudent = new HashMap<>();
      courseListsByStudent.put("Victor",
              Arrays.asList(
                      new CourseGrade("CPE 123", 100),
                      new CourseGrade("CPE 101", 100),
                      new CourseGrade("CPE 202", 100),
                      new CourseGrade("CPE 203", 100),
                      new CourseGrade("CPE 225", 100)));
      courseListsByStudent.put("Sarah",
              Arrays.asList(
                      new CourseGrade("CPE 101", 99),
                      new CourseGrade("CPE 202", 100),
                      new CourseGrade("CPE 203", 100),
                      new CourseGrade("CPE 225", 100)));
      courseListsByStudent.put("Benny",
              Arrays.asList(
                      new CourseGrade("CPE 123", 100),
                      new CourseGrade("CPE 203", 100),
                      new CourseGrade("CPE 471", 100),
                      new CourseGrade("CPE 473", 100),
                      new CourseGrade("CPE 476", 100),
                      new CourseGrade("CPE 572", 99)));

      List<String> expected = Arrays.asList("Victor");

      /* Why compare HashSets here?  Just so that the order of the elements in the list is not important for this test.*/
      assertEquals(new HashSet<>(expected),
              new HashSet<>(ExampleMap.highScoringStudents(
                      courseListsByStudent, 99)));
   }

}
