package sec02_primitiveType;

public class FromIntToFloat {
    public static void main(String[] args) {
        int num1 = 1234567890;
        int num2 = 1234567890;

        float num3 = num2;
        num2 = (int) num3;

        int result = num1 - num2;
        System.out.println(result);
        // 0이 출력되지 않는다. num1, num2는 가수부가 23bit인 float 자료형으로는 표현할 수 없기 때문에
        // 근사치로 표현되므로 다시 int로 변환하면 원래의 int값을 얻지 못한다.
    }
}
