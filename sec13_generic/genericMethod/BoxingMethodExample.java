package sec13_generic.genericMethod;
import sec13_generic.genericType.Box;

public class BoxingMethodExample {
    public static void main(String[] args) {
        Box<Integer> box1 = Util.<Integer>boxing(100);
        int intValue = box1.get();

        Box<String> box2 = Util.boxing("홍길동");
        String strValue = box2.get();
    }
}
