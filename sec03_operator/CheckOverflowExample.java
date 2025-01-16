package sec03_operator;

public class CheckOverflowExample {
    public static void main(String[] args) {
        try {
            int result = safeAdd(2000000000, 200000000);
            System.out.println(result);
        } catch(ArithmeticException e) {
            System.out.println("오버플로우가 발생하여 정확하계 계산할 수 없음.");
        }
    }

    public static int safeAdd(int left, int right) {
        if(right>0) {
            // 덧셈 (left + right > Integer.MAX_VALUE 식에서 right를 이항)
            if (left > (Integer.MAX_VALUE - right)) {
                throw new ArithmeticException("오버플로우 발생");
            }
        }else{
            // 뺄셈
            if(left<(Integer.MIN_VALUE - right)) {
                throw new ArithmeticException("오버플로우 발생");
            }
        }
        return left + right;
    }
}
