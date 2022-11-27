package sec08_interface.interface_inheritance;

public class Example {
    public static void main(String[] args) {
        ImplementaionC impl = new ImplementaionC();

        //인터페이스 변수에 구현객체 대입
        InterfaceA ia = impl;
        ia.methodA();
        System.out.println();

        InterfaceB ib = impl;
        ib.methodB();
        System.out.println();

        InterfaceC ic = impl;
        ic.methodC();
        System.out.println();
    }
}
