//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WorldTests {
    public WorldTests() {
    }

    public static String makeSave(int rows, int cols, String... entities) {
        StringBuilder sb = new StringBuilder(String.format("Rows:\n%d\nCols:\n%d\nEntities:\n", rows, cols));

        for(String entity : entities) {
            sb.append(entity).append('\n');
        }

        return sb.toString();
    }

    @Test
    public void testTreeAnimation() {
        String sav = makeSave(1, 1, "tree mytree 0 0 0.250 100.0 1");
        List<String> entities = VirtualWorld.headlessMain(new String[]{sav}, 5.0F);
        Assertions.assertEquals(1, entities.size());
        Assertions.assertEquals("mytree 0 0 20", entities.get(0));
    }

    @Test
    public void testFairyAnimation() {
        String sav = makeSave(1, 1, "fairy myfairy 0 0 0.100 100.0");
        List<String> entities = VirtualWorld.headlessMain(new String[]{sav}, 1.0F);
        Assertions.assertEquals(1, entities.size());
        Assertions.assertEquals("myfairy 0 0 10", entities.get(0));
    }

    @Test
    public void testObstacleAnimation() {
        String sav = makeSave(1, 1, "obstacle myobstacle 0 0 0.500");
        List<String> entities = VirtualWorld.headlessMain(new String[]{sav}, 8.0F);
        Assertions.assertEquals(1, entities.size());
        Assertions.assertEquals("myobstacle 0 0 16", entities.get(0));
    }

    @Test
    public void testParsing() {
        String sav = "Rows:\n3\nCols:\n5\nEntities:\nBackgrounds:\ngrass grass grass grass grass grass\ngrass grass grass grass grass\ngrass grass grass grass grass\ngrass\nBackgrounds:\n\nEntities:";
        List<String> entities = VirtualWorld.headlessMain(new String[]{sav}, 1.0F);
        Assertions.assertEquals(0, entities.size());
        Assertions.assertThrows(IllegalArgumentException.class, () -> VirtualWorld.headlessMain(new String[]{"Entities:\noops"}, 1.0F));
        Assertions.assertThrows(IllegalArgumentException.class, () -> VirtualWorld.headlessMain(new String[]{"Entities:\noops _ 0 0"}, 1.0F));
        Assertions.assertDoesNotThrow(() -> VirtualWorld.headlessMain(new String[]{"Rows:\n1"}, 1.0F));
    }

    @Test
    public void testWithinBounds() {
        String sav = makeSave(9, 4, "house houseiry 4 9");
        List<String> entities = VirtualWorld.headlessMain(new String[]{sav}, 1.0F);
        Assertions.assertEquals(0, entities.size());
    }

    @Test
    public void testTryAddEntity() {
        String sav = makeSave(1, 1, "stump stumpo 0 0", "stump stumpy 0 0");
        Assertions.assertThrows(IllegalArgumentException.class, () -> VirtualWorld.headlessMain(new String[]{sav}, 1.0F));
    }

    @Test
    public void testTrampleNotFull() {
        String sav = makeSave(1, 5, "dude  0 0 0.300 100.0 1", "stump mystump 2 0", "tree  4 0 100.0 100.0 1");
        List<String> entities = VirtualWorld.headlessMain(new String[]{sav}, 10.0F);
        Assertions.assertEquals(0, entities.size());
    }

    @Test
    public void testTrampleFull() {
        String sav = makeSave(1, 6, "tree  0 0 100.0 100.0 1", "dude  1 0 0.300 100.0 1", "stump mystump 3 0", "house  5 0");
        List<String> entities = VirtualWorld.headlessMain(new String[]{sav}, 10.0F);
        Assertions.assertEquals(0, entities.size());
    }

    @Test
    public void testFairyPathing() {
        String sav = makeSave(15, 20, "fairy myfairy 10 9 100.0 0.300", "obstacle  9 11 1.126", "obstacle  10 12 1.126", "obstacle  11 11 1.126", "stump  10 14", "stump  0 0", "house  10 8");
        List<String> entities = VirtualWorld.headlessMain(new String[]{sav}, 5.0F);
        Assertions.assertEquals(1, entities.size());
        Assertions.assertEquals("myfairy 10 11 0", entities.get(0));
    }

    @Test
    public void testSaplingIntoStump() {
        String sav = makeSave(2, 2, "dude  1 0 0.010 100.0 1", "sapling mysapling 0 0 0");
        List<String> entities = VirtualWorld.headlessMain(new String[]{sav}, 6.0F);
        Assertions.assertEquals(1, entities.size());
        Assertions.assertEquals("stump_mysapling 0 0 0", entities.get(0));
    }

    @Test
    public void testStumpIntoSapling() {
        String sav = makeSave(2, 2, "fairy  1 0 100.0 0.100", "stump mystump 0 0");
        List<String> entities = VirtualWorld.headlessMain(new String[]{sav}, 2.0F);
        Assertions.assertEquals(1, entities.size());
        Assertions.assertEquals("sapling_mystump", ((String)entities.get(0)).split(" ", 2)[0]);
    }

    @Test
    public void testStumpIntoSaplingIntoTree() {
        String sav = makeSave(2, 2, "fairy  1 0 100.0 0.100", "stump mystump 0 0");
        List<String> entities = VirtualWorld.headlessMain(new String[]{sav}, 8.0F);
        Assertions.assertEquals("tree_sapling_mystump", ((String)entities.get(0)).split(" ", 2)[0]);
        Assertions.assertEquals(1, entities.size());

    }

    @Test
    public void testSaplingIntoTree() {
        String sav = makeSave(1, 1, "sapling mysapling 0 0 0");
        List<String> entities = VirtualWorld.headlessMain(new String[]{sav}, 5.0F);
        Assertions.assertEquals(1, entities.size());
        Assertions.assertEquals("tree_mysapling 0 0 0", entities.get(0));
    }

    @Test
    public void testTreeIntoStump() {
        String sav = makeSave(2, 1, "tree mytree 0 0 100.0 0.100 1", "dude  0 1 1.000 0.100 10");
        List<String> entities = VirtualWorld.headlessMain(new String[]{sav}, 5.0F);
        Assertions.assertEquals(1, entities.size());
        Assertions.assertEquals("stump_mytree 0 0 0", entities.get(0));
    }

    @Test
    public void testDudeAnimation() {
        String sav = makeSave(1, 1, "dude mydude 0 0 1.000 0.100 1");
        List<String> entities = VirtualWorld.headlessMain(new String[]{sav}, 10.0F);
        Assertions.assertEquals(1, entities.size());
        Assertions.assertEquals("mydude 0 0 100", entities.get(0));
    }

    @Test
    public void testDudeLimit() {
        String sav = makeSave(5, 20, "dude mydude 1 1 0.300 100.0 4", "tree  1 2 100.0 0.020 1", "tree  2 2 100.0 0.020 1", "tree  3 2 100.0 0.020 1", "tree  4 2 100.0 0.020 1", "tree  5 2 100.0 0.020 1", "tree  6 2 100.0 0.020 1");
        List<String> entities = VirtualWorld.headlessMain(new String[]{sav}, 8.0F);
        Assertions.assertTrue(entities.stream().anyMatch("mydude 4 1 0"::equals));
    }

    @Test
    public void testDudePathing() {
        String sav = makeSave(15, 20, "dude mydude 10 9 1.000 100.0 1", "obstacle  11 11 1.126", "obstacle  10 12 1.126", "obstacle  9 11 1.126", "tree  10 14 0.250 1.150 2", "tree  0 0 0.250 1.150 2", "house  10 8");
        List<String> entities = VirtualWorld.headlessMain(new String[]{sav}, 8.0F);
        Assertions.assertEquals(1, entities.size());
        Assertions.assertEquals("mydude 10 11 0", entities.get(0));
    }

    @Test
    public void testDudeTransformNotFull() {
        String sav = makeSave(3, 5, "dude mydude 0 1 0.500 100.0 1", "tree  2 1 0.250 0.001 1", "house  4 1");
        List<String> entities = VirtualWorld.headlessMain(new String[]{sav}, 5.0F);
        Assertions.assertEquals(1, entities.size());
        Assertions.assertEquals("mydude 3 1 0", entities.get(0));
    }

    @Test
    public void testDudeTransformFull() {
        String sav = makeSave(3, 5, "dude mydude 1 1 0.500 100.0 1", "tree  0 1 0.250 0.001 1", "house  4 1", "tree  4 2 0.250 0.001 1");
        List<String> entities = VirtualWorld.headlessMain(new String[]{sav}, 5.0F);
        Assertions.assertTrue(entities.stream().anyMatch("mydude 4 2 0"::equals));
    }

    /// Self testing for A Star pathing algorithm
    /// 10 x 10 world where dude is at 2,2 and must find a path to the sapling @ 2,6. It should end up right above the tree
    ///           | -----------> |
    ///           |  OBSTACLE  (Dude)
    ///     DUDE  |  OBSTACLE  TREE
    ///             OBSTACLE
    ///  OBSTACLE OBSTACLE
    ///
    ///
    @Test
    public void testAStarPathingDude(){
        String sav = makeSave(
                7, 7, "dude mydude 2 2 0.500 0.500 0",
                "obstacle  1 4 1.126",
                "obstacle  2 4 1.126",
                "obstacle  3 4 1.126",
                "obstacle  4 3 1.126",
                "obstacle  4 2 1.126",
                "tree  2 6 0.250 0.001 1");
        List<String> entities = VirtualWorld.headlessMain(new String[]{sav}, 10.0F);
        ///  Within lifetime dude will collect some resources, and the stump will
        Assertions.assertEquals(2, entities.size());
        Assertions.assertEquals("mydude 1 6 12", entities.get(0));
    }

    ///  No path exists so the Dude stays in the same tile
    @Test
    public void testAStarPathingDudeNoPath(){
        String sav = makeSave(
                7, 7, "dude mydude 2 2 0.500 0.500 0",
                "obstacle  0 4 1.126",
                "obstacle  1 4 1.126",
                "obstacle  2 4 1.126",
                "obstacle  3 4 1.126",
                "obstacle  4 4 1.126",
                "obstacle  5 4 1.126",
                "obstacle  6 4 1.126",
                "obstacle  7 4 1.126",
                "tree  2 6 0.250 0.001 1");
        List<String> entities = VirtualWorld.headlessMain(new String[]{sav}, 10.0F);
        Assertions.assertEquals("mydude 2 2 20", entities.get(0));
    }

    ///  Fairy's route to a stump
    @Test
    public void testAStarPathingFairy(){
        String sav = makeSave(
                15, 20, "fairy myfairy 10 9 100.0 0.300",
                "obstacle  9 11 1.126",
                "obstacle  10 12 1.126",
                "obstacle  11 11 1.126",
                "stump  10 14",
                "stump  0 0",
                "stump  0 14",
                "house  10 8");
        List<String> entities = VirtualWorld.headlessMain(new String[]{sav}, 9.0F);
        ///  Within lifetime fairy only has enough time to transform stump at 10 14 and 0 14 to a sapling before it can reach stump at 0 0
        Assertions.assertEquals(3, entities.size());
        /// Sapling HP varies with run time
        Assertions.assertTrue(entities.get(0).startsWith("tree_sapling_ 10 14"));
        Assertions.assertEquals("sapling_ 0 14 3", entities.get(1));
    }

    ///  No path exists so Fairy stands still
    @Test
    public void testAStarPathingFairyNoPath(){
        String sav = makeSave(
                7, 7, "fairy myfairy 2 2 0.500 0.500",
                "obstacle  0 4 1.126",
                "obstacle  1 4 1.126",
                "obstacle  2 4 1.126",
                "obstacle  3 4 1.126",
                "obstacle  4 4 1.126",
                "obstacle  5 4 1.126",
                "obstacle  6 4 1.126",
                "obstacle  7 4 1.126",
                "stump  2 6");
        List<String> entities = VirtualWorld.headlessMain(new String[]{sav}, 5.0F);
        Assertions.assertEquals(1, entities.size());
        Assertions.assertEquals("myfairy 2 2 10", entities.get(0));
    }
}
