package sec06_class;

public class ComputerExample {
    public static void main(String[] args) {
        Computer computer = new Computer();

        // 사전에 생성한 배열 매개변수
        int[] values1 = {1, 2, 3};
        int result1 = computer.sum1(values1);
        System.out.println("result1: " + result1);

        // 매개변수 자리에서 생성한 배열 매개변수
        int result2 = computer.sum1(new int[] {1, 2, 3, 4, 5});
        System.out.println("result2: " + result2);

        // 가변인자 활용 - 리스트 넘겨주기
        int result3 = computer.sum2(1, 2, 3);
        System.out.println("result3: " + result3);

        // 가변인자 활용 - 리스트 넘겨주기
        int result4 = computer.sum2(1, 2, 3, 4, 5);
        System.out.println("result4: " + result4);
    }
}

class Computer {
    int sum1(int [] values) {
        int sum = 0;
        for(int i=0;i<values.length;i++) {
            sum += values[i];
        }
        return sum;
    }

    int sum2(int ... values) {
        int sum = 0;
        for(int i=0;i<values.length;i++) {
            sum += values[i];
        }
        return sum;
    }
}

