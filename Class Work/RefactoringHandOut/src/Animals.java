public class Animals {
    public static void main(String[] args){
        Dog pal = new Dog("Pal", 2);
        Dog rainy = new Dog("Rainy", "poodle", 3);

        Cat pishy = new Cat("Pishy", 1);
        Cat mimi = new Cat("Mimi", 2);

        Horse fancy = new Horse("Fancy", 4);
        Horse star = new Horse("Shire", "Star",2);
        System.out.println(eat(1));
        System.out.println(makeNoise(fancy));
        System.out.println(makeNoise(pishy));
    }

    public static String eat(int animal){
        switch (animal){
            case 1:
                return "Dog eats dog food!";
            case 2:
                return "Cat eats cat food!";
            case 3:
                return "Horse eats carrot!";
            default:
                return "other animals eat their own food!";
        }
    }

    public static String makeNoise(Object obj){
        if (obj instanceof Dog){
            return ((Dog) obj).name + ", my dog barks!";
        }
        if (obj instanceof Cat){
            return ((Cat) obj).name + ", my cat meows!";
        }
        if (obj instanceof Horse){
            return ((Horse) obj).name + ", my horse roars!";
        }
        return "This object makes some noise!";
    }
}

