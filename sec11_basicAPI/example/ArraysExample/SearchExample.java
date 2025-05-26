package sec11_basicAPI.example.ArraysExample;

import java.util.Arrays;

public class SearchExample {
    public static void main(String[] args) {
        int[] scores = {99, 97, 98};
        Arrays.sort(scores);
        for(int i=0;i<scores.length;i++){
            System.out.println("score["+i+"]="+scores[i]);
        }
        int index = Arrays.binarySearch(scores, 99);
        System.out.println("찾은 인덱스: " + index);

        String[] names = {"홍길동", "박동수", "김민수"};
        Arrays.sort(names);
        for(int i=0;i<names.length;i++){
            System.out.println("names["+i+"]="+names[i]);
        }
        index = Arrays.binarySearch(names, "홍길동");
        System.out.println("찾은 인덱스: " + index);

        Member m1 = new Member("홍길동");
        Member m2 = new Member("박동수");
        Member m3 = new Member("김민수");
        Member[] members = {m1, m2, m3};
        Arrays.sort(members);
        for(int i=0;i<members.length;i++){
            System.out.println("members["+i+"].name="+members[i].name);
        }
        index = Arrays.binarySearch(members, m1);
        System.out.println("찾은 인덱스: " + index);
    }
}