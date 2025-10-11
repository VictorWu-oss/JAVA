import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestCases {

    @Test
    public void testLinkedList(){
        LinkedList number = new LinkedList("2354314535");
        assertEquals("2354314535", number.toString());
    }

    @Test
    public void testLinkedList2(){
        LinkedList number = new LinkedList();
        assertEquals("", number.toString());
    }

    @Test
    public void testRemovingLeadingZeros(){
        assertEquals("3543", LinkedList.removeLeadingZeros("000003543"));
    }

    @Test
    public void testRemovingLeadingZeros2(){
        assertEquals("0", LinkedList.removeLeadingZeros("00000"));
    }

    @Test
    public void testAdd(){
        LinkedList num1 = new LinkedList("200");
        LinkedList num2 = new LinkedList("300");
        LinkedList result = LinkedList.add(num1, num2);
        assertEquals("500", result.toString());
    }

    @Test
    public void testAddZero(){
        LinkedList num1 = new LinkedList("0");
        LinkedList num2 = new LinkedList("3");
        LinkedList result = LinkedList.add(num1, num2);
        assertEquals("3", result.toString());
    }

    @Test
    public void testAddMass(){
        LinkedList num1 = new LinkedList("1234567890");
        LinkedList num2 = new LinkedList("987654321");
        LinkedList result = LinkedList.add(num1, num2);
        assertEquals("2222222211", result.toString());
    }

    @Test
    public void testMultiply(){
        LinkedList num1 = new LinkedList("2");
        LinkedList num2 = new LinkedList("3");
        LinkedList result = LinkedList.multiply(num1, num2);
        assertEquals("6", result.toString());
    }

    @Test
    public void testMultiplyZero(){
        LinkedList num1 = new LinkedList("234242");
        LinkedList num2 = new LinkedList("0");
        LinkedList result = LinkedList.multiply(num1, num2);
        assertEquals("0", result.toString());
    }

    @Test
    public void testMassMultiply(){
        LinkedList num1 = new LinkedList("111111111");
        LinkedList num2 = new LinkedList("122333444455555");
        LinkedList result = LinkedList.multiply(num1, num2);
        assertEquals("13592604925913506171605", result.toString());
    }

    @Test
    public void testSimpleExponents(){
        LinkedList num1 = new LinkedList("2");
        int exponent = 5;
        LinkedList result = LinkedList.power(num1, exponent);
        assertEquals("32", result.toString());
    }

    @Test
    public void testZeroExponent(){
        LinkedList num1 = new LinkedList("2");
        int exponent = 0;
        LinkedList result = LinkedList.power(num1, exponent);
        assertEquals("1", result.toString());
    }

    @Test
    public void testMassiveExponent(){
        LinkedList num1 = new LinkedList("10");
        int exponent = 50;
        LinkedList result = LinkedList.power(num1, exponent);
        assertEquals("100000000000000000000000000000000000000000000000000", result.toString());
    }

}
