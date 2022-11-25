package sec07_inheritance.inheritance;

public class DmbCellPhone extends CellPhone {

	int channel;

	DmbCellPhone(String model, String color, int channel) {
		//부모 생성자는 자식생성자 맨 첫 줄에서 호출된다.
		//부모 생성자가 기본생성자인경우 super(); 생략가능, but 부모생성자가 매개변수가 있는 생성자를 가질경우에는 자식생성자에서 super(para1..) 을 호출해야한다.
		this.model = model;
		this.color = color;
	}

	void turnOnDmb() { System.out.println("채널 "+ channel + "번 Dmb 방송 수신을 시작합니다.");}
	void changeChannelDmb(int channel) {
		this.channel = channel;
		System.out.println("채널 "+channel+"번 으로 바꿉니다.");
	}
	void turnOffDmb() {
		System.out.println("DMB 방송을 멈춥니다.");
	}
}
