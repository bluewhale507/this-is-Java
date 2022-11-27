package sec08_interface.interface_implementaion.multi_implementation;

import sec08_interface.interface_implementaion.Television;

public class RemoteControlExample {
    public static void main(String[] args) {
        //인터페이스 변수 선언, null
        RemoteControl rc = null;
        Searchable searchable = null;
        //SmartTelevision 구현 클래스 타입 변수 tv 에 SmartTelevision 구현 객체 생성
        SmartTelevision tv = new SmartTelevision();

        //인터페이스 변수에 구현 객체 할당
        rc = tv;
        searchable = tv;

        rc.turnOn();
        rc.turnOff();
        rc.setVolume(5);

        searchable.search("https://www.bluewhale507.shop");
    }
}
