package sec08_interface.interface_implementaion.anonymous_implementaion_object;

import sec08_interface.interface_declaration.RemoteControl;

public class RemoteControlExample {
    /*
    익명구현객체 : 일회성의 구현객체가 필요할시 이용한다.
    - 인터페이스의 모든 추상메소드들을 재정의하는 실체 메소드가 있어야 한다.
    - 추가적으로 필드, 메서드를 선언할 수 있지만, 익명객체 안에서만 사용할 수 있고,
      인터페이스 변수로 접근할 수 없다.
    */
    RemoteControl rc = new RemoteControl() {
        private int volume;

        @Override
        public void turnOn() {
            System.out.println("TV를 켭니다.");
        }

        @Override
        public void turnOff() {
            System.out.println("TV를 끕니다.");

        }

        @Override
        public void setVolume(int volume) {
            if(volume>RemoteControl.MAX_VOLUME) {
                this.volume = RemoteControl.MAX_VOLUME;
            } else if (volume<RemoteControl.MIN_VOLUME) {
                this.volume = RemoteControl.MIN_VOLUME;
            } else {
                this.volume = volume;
            }
            System.out.println("현재 Audio 볼륨 : "+this.volume);
        }
    };
}
