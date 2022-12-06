package sec09_nested_ClassAndInterface.nestedClass.nestedClass_declarationAndAccess;

public class OutterExample {
    public static void main(String[] args) {
        Outter outter = new Outter();
        Outter.Nested nested = outter.new Nested();
        nested.print();
    }
}
