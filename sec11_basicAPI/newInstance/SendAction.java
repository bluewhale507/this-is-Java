package sec11_basicAPI.newInstance;

public class SendAction implements Action{

	public SendAction () {}

	public SendAction (String ActionName) {}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		System.out.println("데이터를 보냅니다.");
	}
	
}
