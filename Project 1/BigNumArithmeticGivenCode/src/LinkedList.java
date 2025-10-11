public class LinkedList {
    private Node head;

    private static class Node{
        int digit;
        Node next;

        Node(int digit) {
            this.digit = digit;
        }
    }

    // Constructors
    public LinkedList(){
        this.head = null;
    }

    // Build number from string // Build the linked list
    public LinkedList(String number){
        number = number.trim();                     // Trim Whitespace
        number = removeLeadingZeros(number);        // Remove leading zeros, if it's empty set to 0

        if (number.isEmpty()){
            number = "0";
        }

        // For each char:
        for(int i = 0; i < number.length(); i++){
            char c = number.charAt(i);

            // convert char -> digit(int)
            int digit = Character.getNumericValue(c);

            // create node with that digit & insert node at the head of the linked list
            Node node = new Node(digit);    // Create new node object to store a char
            node.next = this.head;          // Point new node object to current head, effectively linking newNode -> Current Head
            this.head = node;               // Update head to point to new node, like a stack, ->newNode(current head) -> blah
        }
    }

    // Method to remove leading zeros
    public static String removeLeadingZeros(String str){
        int x = str.length();
        int y = 0;

        // Find index of first non-zero char
        while(y < x && str.charAt(y) == '0'){
            y++;
        }

        // Store result between the ranges
        String r = str.substring(y, x);

        if (r.isEmpty()){
            r = "0";
        }
        return r;
    }

    // METHODS
    // Addition
    public static LinkedList add(LinkedList a, LinkedList b){
        Node p1 = a.head;
        Node p2 = b.head;
        int carry = 0;                          // Keep track of overflow
        LinkedList result = new LinkedList();   // Resulting list
        Node resultTail = null;                 // Keep track of last node added

        // Keep looping as long as there are digits left in either numbers OR carry is not zero(still have to carry to add)
        while(p1 != null || p2 != null || carry !=0){

            // Extract digits
            int digit1 = 0;
            int digit2 = 0;
            if (p1 != null){
                digit1 = p1.digit;
            }
            if (p2 != null){
                digit2 = p2.digit;
            }

            int sum = digit1 + digit2 + carry;
            int newDigit = sum % 10;            // Separate digits >=10
            carry = sum / 10;                   // Carry increases depending

            // New node to hold digit of the result
            Node newNode = new Node(newDigit);

            // Add the newDigit to the list after carrying
            // Initially set first digit as both the head and tail
            if(result.head == null){
                result.head = newNode;
                resultTail = newNode;
            }
            // Otherwise link the new node to tail remember (Head --> Tail), add to the right/ tail
            else {
                resultTail.next = newNode;
                resultTail = newNode;
            }

            // Proceed to the next node if there is one
            if(p1 != null){
                p1 = p1.next;
            }

            if(p2 != null){
                p2 = p2.next;
            }
        }
        return result;
    }


    // Multiplication
    // GOAL: // Multiply each digit in 1 number to every other digit in the other number. Add up linked lists, use existing add() to get product.
    public static LinkedList multiply(LinkedList a, LinkedList b){
        // Default to 0 if being multiplied by 0
        if ((a.head != null && a.head.digit ==0 && a.head.next == null || b.head != null && b.head.digit ==0 && b.head.next == null)) return new LinkedList("0");

        LinkedList result = new LinkedList("0");   // List for the final product
        Node p1 = a.head;
        int position = 0;                                 // Keep track of how many zeros to add/ the shift when doing manual multiplication

        // lOOp through each digit in A
        while (p1 != null){
            LinkedList sum = new LinkedList();            // Stores partial results for each digit in a
            Node zeroTail = null;                         // Tracks end of zeros
            Node partialTail = null;                      // Tracks end of the partial product

            for (int i = 0; i < position; i++){           // Initially no padding needed
                Node zeroNode = new Node(0);         // But depending on digits place it will need shifting
                if (sum.head == null) {
                    sum.head = zeroNode;
                    zeroTail = zeroNode;
                    partialTail = zeroNode;
                }
                else {
                    zeroTail.next = zeroNode;
                    zeroTail = zeroNode;
                    partialTail = zeroTail;
                }
            }

            Node p2 = b.head;
            int carry = 0;

            while (p2 != null) {                            // Inner Loop to now multiply each digit in list b to current digit in a
                int product = p1.digit * p2.digit + carry;
                int newDigit = product % 10;                // Add this to the partial sum list/ Digit to be stored
                carry = product / 10;                       // Carry if needed

                Node newNode = new Node(newDigit);          // Add the newDigit to the list after carrying
                if (sum.head == null) {                     // Initially set first digit as both the head and tail
                    sum.head = newNode;
                    partialTail = newNode;
                }
                // Otherwise link the new node to tail remember (Head --> Tail), add to the right/ tail
                else {
                    partialTail.next = newNode;
                    partialTail = newNode;
                }
                // Proceed to the next node in List B
                p2 = p2.next;

            }

            // Add carry to the sum, remaining carry as a new node
            if (carry > 0){
                partialTail.next = new Node(carry);
                partialTail = partialTail.next;
            }

            // Add current partial sum to the overall result
            result = add(result, sum);
            p1 = p1.next;
            position++;
        }
        // Add them up in the end
        return result;
    }

    // Exponents using squaring
    public static LinkedList power(LinkedList a, int exponent){
        if (exponent < 0){
            System.err.println("Cannot perform negative exponents at this time");
        }

        if (exponent == 0){
            return new LinkedList("1");
        }

        // Even exponent
        // (x^2)^n/2
        else if(exponent % 2 == 0){
            LinkedList squared = multiply(a,a);
            return power(squared, exponent/2);
        }

        // Last case scenario is when it is odd
        //x(x^2)^(n-1)/2
        else{
            LinkedList squared = multiply(a,a);
            LinkedList halfPower = power(squared, ((exponent - 1)/2));
            return multiply(a, halfPower);
        }
    }

    // Returns result as readable number
    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        Node current = this.head;
        while(current != null){
            result.insert(0, current.digit);  // insert at index 0 (reverses the list)
            current = current.next;
        }

        int i =0;
        while(i < result.length() - 1 && result.charAt(i) == '0'){
            i++;
        }

        return result.toString();
    }

}