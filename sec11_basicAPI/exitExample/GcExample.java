package sec11_basicAPI.exitExample;

public class GcExample {
	
	public static void main(String[] args) {
		Employee emp;
		
		emp = new Employee(1);
		emp = null;
		emp = new Employee(2);
		emp = new Employee(3);
		
		System.out.println("emp가 최종적으로 참조하는 사원번호 : "+emp.eno);
		System.gc();
		//main함수 종료시 jvm이 종료되고, 메모리도 해제됨. 
		emp.getClass();
	}
}
