package sec07_inheritance.final_method;

public class SportsCar extends Car{

    @Override
    public void speedUp() { speed += 10; }

    /* final method는 상속 불가능
    @Override
    public void stop() {
        System.out.println("스포츠카 멈춤");
        speed = 0;
    }
    */
}