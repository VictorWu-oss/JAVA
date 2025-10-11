public class DogAfter extends AnimalsAfter {
    private String breed;

    public DogAfter(String breed, String name, int age){
        super(name, age);
        this.breed = breed;
    }

    public DogAfter(String name, int age){
        this("Husky", name, age);
    }

    public String getBreed() { return breed; }

    @Override
    public String eat() {
        return "Dog eats dog food!";
    }

    @Override
    public String makeNoise() {
        return getName() + ", my dog barks!";
    }
}
