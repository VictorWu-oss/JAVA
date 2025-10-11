public abstract class BinaryExpression implements Expression
{
    private final Expression lft;
    private final Expression rht;
    private final String operator;

    public BinaryExpression(final Expression lft, final Expression rht, String operator){
        this.lft = lft;
        this.rht = rht;
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "(" + lft.toString() + " " + operator + " " + rht.toString() + ")";
    }

    @Override
    public double evaluate(final Bindings bindings)
    {
        double leftVal = lft.evaluate(bindings);
        double rightVal = rht.evaluate(bindings);
        return _applyOperator(leftVal, rightVal);
    }

    // protected means it can be accessed in the same class, same package, if diff importing works, subclass(inheritance), but not unrelated classes
    // abstract means the subclasses of this class must implement certain methods in this case _applyOperator
    protected abstract double _applyOperator(double leftVal, double rightVal);

}
