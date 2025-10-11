public abstract class Plant extends Active implements Transform{
    protected int health;

    public double getHealth() {
        return health;
    }

    @Override
    public void describe(){
        System.out.println(this.getId() + ": " + this.getClass() + " : " + this.health);
    }
}
