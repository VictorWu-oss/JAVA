import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TestCasesComparators
{
   private static final Song[] songs = new Song[] {
         new Song("Decemberists", "The Mariner's Revenge Song", 2005),
         new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
         new Song("Avett Brothers", "Talk on Indolence", 2006),
         new Song("Gerry Rafferty", "Baker Street", 1998),
         new Song("City and Colour", "Sleeping Sickness", 2007),
         new Song("Foo Fighters", "Baker Street", 1997),
         new Song("Queen", "Bohemian Rhapsody", 1975),
         new Song("Gerry Rafferty", "Baker Street", 1978)
      };

   // Define comparator using a class
   // A negative return value indicates that the first parameter object "comes before" the second;
   // A positive return value indicates that the second parameter object "comes before" the first;
   // Zero indicates that the values are equivalent by the ordering
   @Test
   public void testArtistComparatorNegative() {
       // First define object of the class
       ArtistComparator compareArtists = new ArtistComparator();

       // Parameter 1 -> Parameter 2
       int result = compareArtists.compare(songs[0], songs[1]);
       assertTrue(result < 0);

       // Parameter 2 -> Parameter 1
      int result1 = compareArtists.compare(songs[1], songs[0]);
      assertTrue(result1 > 0);

      // Equals
      int result2 = compareArtists.compare(songs[1], songs[1]);
      assertTrue(result2 == 0);
   }

   // Define comparator using Lambda Expression
   @Test
   public void testLambdaTitleComparator()
   {
      Comparator<Song> compareTitle = (song1, song2) -> song1.getTitle().compareTo(song2.getTitle());
      // OR Key Extractor method // Comparator<Song> compareTitle = Comparator.comparing(Song::getTitle);

      int result = compareTitle.compare(songs[0], songs[1]);
      assertTrue(result > 0);

      int result1 = compareTitle.compare(songs[2], songs[1]);
      assertTrue(result1 > 0);

      int result2 = compareTitle.compare(songs[0], songs[0]);
      assertTrue(result2 == 0);
   }

   @Test
   public void testYearExtractorComparator()
   {
      // makes it so it compares in descending order of a list: 2006, 2005, 1998, 1988, 1975, etc
      Comparator<Song> compareYear = Comparator.comparingInt(Song::getYear).reversed();

      int result = compareYear.compare(songs[1], songs[2]);
      assertTrue(result > 0);

      int result1 = compareYear.compare(songs[4], songs[5]);
      assertTrue(result1 < 0);

      int result2 = compareYear.compare(songs[4], songs[4]);
      assertTrue(result2 == 0);
   }

   @Test
   public void testComposedComparator()
   {
      Comparator<Song> compareArtists = Comparator.comparing(Song::getArtist);
      Comparator<Song> compareYears = Comparator.comparing(Song::getYear);

      ComposedComparator composed = new ComposedComparator(compareYears, compareArtists);

      // "Gerry Rafferty", "Baker Street", 1998 , "Gerry Rafferty", "Baker Street", 1978
      // Same artist but when sorted by year in ascending order 2nd parameter should go before the first
      int result = composed.compare(songs[3], songs[7]);
      assertTrue(result > 0);

      // Check but use descending order of years
      // Should return negative value because under the same artist the years given: (1998, 1978) in descending order means the 1st param goes before the 2nd param
      ComposedComparator composedDescendingYears = new ComposedComparator(compareYears.reversed(), compareArtists);
      int result2 = composedDescendingYears.compare(songs[3], songs[7]);
      assertTrue(result2 < 0);
   }

   @Test
   public void testThenComparing()
   {
      Comparator<Song> compareTitle = Comparator.comparing(Song::getTitle);
      Comparator<Song> compareArtist = Comparator.comparing(Song::getArtist);

      Comparator<Song> compareTitleandArtist = compareTitle.thenComparing(compareArtist);

      // Both named Baker Street but under different artists Gerry Rafferty and the Foo Fighters, result > 0, bc F comes before G
      int result = compareTitleandArtist.compare(songs[7], songs[5]);
      assertTrue(result > 0);
   }

   @Test
   public void runSort()
   {
      List<Song> songList = new ArrayList<>(Arrays.asList(songs));
      List<Song> expectedList = Arrays.asList(
         new Song("Avett Brothers", "Talk on Indolence", 2006),
         new Song("City and Colour", "Sleeping Sickness", 2007),
         new Song("Decemberists", "The Mariner's Revenge Song", 2005),
         new Song("Foo Fighters", "Baker Street", 1997),
         new Song("Gerry Rafferty", "Baker Street", 1978),
         new Song("Gerry Rafferty", "Baker Street", 1998),
         new Song("Queen", "Bohemian Rhapsody", 1975),
         new Song("Rogue Wave", "Love's Lost Guarantee", 2005)
         );

      Comparator<Song> compareArtist = Comparator.comparing(Song::getArtist);
      Comparator<Song> compareTitle = Comparator.comparing(Song::getTitle);
      Comparator<Song> compareYear = Comparator.comparing(Song::getYear);

      // Ascending Order comparator that orders by artist, then title, and then year
      Comparator<Song> sortingComparator = compareArtist.thenComparing(compareTitle).thenComparing(compareYear);

      songList.sort(sortingComparator);

      assertEquals(songList, expectedList);
   }
}
