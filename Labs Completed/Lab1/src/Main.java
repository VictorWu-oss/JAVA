public class Main {

    public static void main(String[] args) {
        // declaring and initializing some variables
        int x = 5;
        String y = "hello";
        float z = 9.8f;

        // printing the variables
        System.out.println("x: " + x + " " + "y: " + y + " " + "z: " + z);

        // a list (make an array in java)
        int[] nums = {3,6,-1,2};
        for (int v:nums){
            System.out.println(v);
        }

        // call a function
        int numFound = charCount(y, 'l');
        System.out.println("Found: " + numFound);

        // a counting for loop
        for (int i = 1; i<11; i++){
            System.out.print(i + " ");
        }
    }
    // function counts the given character in the given string
    // str str -> int
    // character stores single character
    public static int charCount(String s, char c) {
        int count = 0;
        // given y = "hello"
        // converts string to array of characters/ sequences or list of characters
        for (char ch : s.toCharArray()) {
            if (ch == c) {
                count++;
            }
        }
        return count;
    }
}



