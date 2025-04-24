package sec14_lambdaExpressions.example.basicSyntax;

@FunctionalInterface
public interface MyFunctionalInterface {
    /* 매개변수가 없는 경우 */
    public void method();
    
    /* 매개변수가 있는 경우 */
    // public void method(int x);

    /* 리턴값이 있는 경우 */
    // public int method(int x, int y);
}
