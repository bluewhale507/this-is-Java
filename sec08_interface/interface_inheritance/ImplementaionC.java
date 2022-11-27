package sec08_interface.interface_inheritance;

public class ImplementaionC implements InterfaceC {
    @Override
    public void methodA() {
        System.out.println("ImplementationC-methodA() 실행");
    }

    @Override
    public void methodB() {
        System.out.println("ImplementationC-methodB() 실행");
    }

    @Override
    public void methodC() {
        System.out.println("ImplementationC-methodC() 실행");
    }
}
