package sec08_interface.example.interface_implementaion;

import sec08_interface.example.interface_declaration.RemoteControl;

public class Television implements RemoteControl {
    private int volume;

    /*
    추상 메서드의 실체 메서드 : 인터페이스의 모든 메서드는 기본적으로 public 접근제한을 갖기때문에
    public보다 더 높은 접근 제어자로 작성할 수 없다.(public 명시적으로 작성해야함. 생략-default 접근제어)
    */
    @Override
    public void turnOn() {
        System.out.println("TV를 켭니다.");
    }

    @Override
    public void turnOff() {
        System.out.println("TV를 끕니다.");
    }

    //인터페이스의 상수를 이용해서 volume 필드의 값을 제한
    @Override
    public void setVolume(int volume) {
        if(volume>RemoteControl.MAX_VOLUME) {
            this.volume = RemoteControl.MAX_VOLUME;
        } else if (volume<RemoteControl.MIN_VOLUME) {
            this.volume = RemoteControl.MIN_VOLUME;
        } else {
            this.volume = volume;
        }
        System.out.println("현재 TV 볼륨 : "+this.volume);
    }

}
