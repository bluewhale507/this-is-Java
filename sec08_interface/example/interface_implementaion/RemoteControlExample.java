package sec08_interface.example.interface_implementaion;

import sec08_interface.example.interface_declaration.RemoteControl;

public class RemoteControlExample {
    public static void main(String[] args) {
        RemoteControl rc;
        rc = new Television();
        rc = new Audio();
    }
}
