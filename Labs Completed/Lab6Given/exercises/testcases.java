import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.function.LongSupplier;
import java.util.function.LongUnaryOperator;
import java.util.function.Predicate;

import org.junit.Test;

public class testcases
{
   /* helper methods used in the tests below */
   // Accepts any list of type T, and a function with T (input value) and T (output value)
   private <T> List<T> mapIt(List<T> list, Function<T,T> func)
   {
      final List<T> results = new LinkedList<>();
      for (final T value : list)
      {
         results.add(func.apply(value));
      }

      return results;
   }

   private <T> List<T> filterIt(List<T> list, Predicate<T> pred)
   {
      final List<T> results = new LinkedList<>();
      for (final T value : list)
      {
         if (pred.test(value))
         {
            results.add(value);
         }
      }
      return results;
   }

   private LongSupplier getNumberGenerator()
   {
      // Returns a new counter starting at 0 each time
      int number[] = {0};
      return () -> number[0]++;
   }

   private LongFunction<LongUnaryOperator> createAdder()
   {
      return x -> y -> x + y;
   }

   /* test cases */
   @Test
   public void testExercise1()
   {
      final LongUnaryOperator func = x -> x + 1;
      assertEquals(8, func.applyAsLong(7));
   }

   @Test
   public void testExercise2()
   {
      final LongSupplier first = getNumberGenerator();
      final LongSupplier second = getNumberGenerator();

      // counter that starts from 0 and the more you call the function it will increase its final count
      assertEquals(0, first.getAsLong());
      assertEquals(1, first.getAsLong());
      assertEquals(0, second.getAsLong());
      assertEquals(2, first.getAsLong());
      assertEquals(1, second.getAsLong());
      assertEquals(3, first.getAsLong());
      assertEquals(2, second.getAsLong());
   }

   @Test
   public void testExercise3()
   {
      final LongFunction<LongUnaryOperator> curried = createAdder();
      final LongUnaryOperator add7 = curried.apply(7);
      final LongUnaryOperator add3 = curried.apply(3);

      assertEquals(9, add7.applyAsLong(2));   // 7 + 2 = 9
      assertEquals(5, add3.applyAsLong(2));   // 3 + 3 = 6
      assertEquals(13, add3.applyAsLong(10)); // 3 + 10 = 13
   }

   @Test
   public void testExercise4()
   {
      final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
      final List<Integer> expected = Arrays.asList(11, 12, 13, 14, 15);
      final int n = 10;

      // mapIt can run given any list and function parameter
      // Make a new list, go through the given list and apply the function to each value and append it to the new list
      // Return new list
      final List<Integer> result = mapIt(numbers, x -> x + n);

      assertEquals(expected, result);
   }

   @Test
   public void testExercise5()
   {
      final List<String> strings = Arrays.asList(
         "hello", // All lowercase
         "Hello", // H is capitalize
         "HeLLo", // H LL is capitalized
         "helLo", // L is capitalized
         "HELLO");// All capitalized
      final List<String> expected = Arrays.asList("hello","hello", "hello", "hello", "hello");

      // uses key extractor format (Class type :: method in the class)
      final List<String> result = mapIt(strings, String::toLowerCase);

      assertEquals(expected, result);
   }

   @Test
   public void testExercise6()
   {
      final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
      final List<Integer> expected = Arrays.asList(2, 4);

      // Filter it accepts any list and a predicate
      // Make a new List and for every value in the given list if they pass the predicate
      // Add to the list and return the new list
      // Logical binary &, results in 1 if both bits are 1 otherwise if any are 0 return 0
      // EX: 3 & 1 is 0011 & 0001 = 0001
      final List<Integer> result = filterIt(numbers, x -> (x & 1) == 0);

      assertEquals(expected, result);
   }

   @Test
   public void testExercise7()
   {
      final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
      final List<Integer> expected = Arrays.asList(4, 16);

      // Thoughts: mapIt only accepts a list and 1 predicate so it will only apply the first pred not the second
      // Incorrect, it will run through the first predicate (check for even numbers) then map over the filtered list and (square them)
      final List<Integer> result = mapIt(
         filterIt(numbers, x -> (x & 1) == 0), x -> x * x);

      assertEquals(expected, result);
   }
}
