public class HorseAfter extends AnimalsAfter{
    private String breed;

    public HorseAfter(String name, String breed, int age){
        super(name, age);
        this.breed = breed;
    }

    public HorseAfter(String name, int age){
        this(name, "Mustang", age);
    }

    public String getBreed(){
        return breed;
    }

    @Override
    public String eat(){
        return "Horse eats carrots!";
    }

    @Override
    public String makeNoise(){
        return getName() + ", my horse roars!";
    }

}
