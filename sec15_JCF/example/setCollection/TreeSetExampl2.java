package sec15_JCF.example.setCollection;

import java.util.NavigableSet;
import java.util.TreeSet;

public class TreeSetExampl2 {
    public static void main(String[] args) {
        TreeSet<Integer> scores = new TreeSet<Integer>();
        scores.add(87);
        scores.add(98);
        scores.add(75);
        scores.add(95);
        scores.add(Integer.valueOf(80));

        NavigableSet<Integer> descendingSet = scores.descendingSet();
        for(Integer score : descendingSet) {
            System.out.print(score + " ");
        }
        System.out.println();

        NavigableSet<Integer> ascendingSet = descendingSet.descendingSet();

        for(Integer score : ascendingSet) {
            System.out.print(score + " ");
        }
    }
}
