class DivideExpression extends BinaryExpression
{
   public DivideExpression(final Expression lft, final Expression rht)
   {
      super(lft, rht, "/");
   }

   protected double _applyOperator(double leftVal, double rightVal){
      return leftVal / rightVal;
   }
}

