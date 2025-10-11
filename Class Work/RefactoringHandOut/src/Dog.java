public class Dog{
    public String name;
    private String breed;
    public int age;
    
    public Dog(String breed, String name, int age){
        this.name = name;
        this.breed = breed;
        this.age = age;
    }
    
    public Dog(String name, int age){
        this("Husky", name, age);
    }
}
