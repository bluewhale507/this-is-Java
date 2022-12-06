package sec09_nested_ClassAndInterface.nestedInterface;

public class CallListener implements Button.OnClickListener {

    @Override
    public void onClick() {
        System.out.println("전화를 겁니다.");
    }
}
