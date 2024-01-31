package sec07_inheritance.abstract_class;

import org.w3c.dom.ls.LSOutput;

import javax.crypto.spec.PSource;

public class Phone {
    public String owner;

    public Phone(String owner) {
        this.owner = owner;
    }

    public void turnOn() {
        System.out.println("폰 전원을 켭니다.");
    }

    public void turnOff() {
        System.out.println("폰 전원을 끕니다.");
    }

    public void boom() { System.out.println("폰이 폭발합니다."); }
}