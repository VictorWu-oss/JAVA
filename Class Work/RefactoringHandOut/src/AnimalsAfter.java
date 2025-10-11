public class AnimalsAfter {
    private String name;
    private int age;

    public AnimalsAfter(String name, int age){
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }

    public String eat() {
        return "Animal eats something.";
    }

    public String makeNoise() {
        return "Animal makes noise.";
    }

    public static void main(String[] args){
        DogAfter pal = new DogAfter("Pal", 2);
        DogAfter rainy = new DogAfter("Rainy", "poodle", 3);

        CatAfter pishy = new CatAfter("Pishy", 1);
        CatAfter mimi = new CatAfter("Mimi", 2);

        HorseAfter fancy = new HorseAfter("Fancy", 4);
        HorseAfter star = new HorseAfter("Shire", "Star", 2);

        System.out.println(pal.eat());
        System.out.println(fancy.makeNoise());
        System.out.println(pishy.makeNoise());
    }


}
