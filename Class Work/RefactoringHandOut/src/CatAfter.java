public class CatAfter extends AnimalsAfter{
    public CatAfter(String name, int age){
        super(name, age);
    }

    @Override
    public String eat(){
        return "Cat eats cat food!";
    }

    @Override
    public String makeNoise(){
        return getName() + ", my cat meows!";
    }
}
