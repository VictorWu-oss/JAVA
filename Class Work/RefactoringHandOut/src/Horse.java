public class Horse {
    public String name;
    private String breed;
    public int age;

    public Horse(String name, String breed, int age){
        this.name = name;
        this.breed = breed;
        this.age = age;
    }

    public Horse(String name, int age){
        this("Mustang", name, age);
    }
}
