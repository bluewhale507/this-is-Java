package sec08_interface.interface_elements_use;

import sec08_interface.interface_declaration.RemoteControl;

public class RemoteControlExample {
    public static void main(String[] args) {
        RemoteControl rc = null;

        //Television객체를 인터페이스 타입에 대입
        rc = new Television();
        //Television에서 Override된 추상 메서드 사용
        rc.turnOn();
        //인터페이스에서 기본제공하는 default 메서드 사용
        rc.setMute(true);

        //Audio객체를 인터페이스 타입에 대입
        rc = new Audio();
        //Audio에서 Override된 추상 메서드 사용
        rc.turnOn();
        //Audio에서 Override된 default 메서드 사용
        rc.setMute(true);

        RemoteControl.changeBattery();
    }
}
