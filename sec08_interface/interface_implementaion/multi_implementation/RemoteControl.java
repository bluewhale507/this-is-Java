package sec08_interface.interface_implementaion.multi_implementation;

public interface RemoteControl {
    //constant_field
    public int MAX_VOLUME = 10;
    public int MIN_VOLUME = 0;

    //abstract_method
    public void turnOn();
    public void turnOff();
    public void setVolume(int volume);

    //defalut_method
    default void setMute(boolean mute) {
        if(mute) {
            System.out.printf("무음 처리합니다.");
        } else {
            System.out.println("무음 해제합니다.");
        }
    }

    //static_method
    static void changeBattery() {
        System.out.println("건전지를 교환합니다.");
    }
}
