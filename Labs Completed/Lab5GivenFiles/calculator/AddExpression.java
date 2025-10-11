public class AddExpression extends BinaryExpression {
   public AddExpression(final Expression lft, final Expression rht)
   {
      super(lft, rht, "+");
   }

   protected double _applyOperator(double leftVal, double rightVal){
      return leftVal + rightVal;
   }

}
