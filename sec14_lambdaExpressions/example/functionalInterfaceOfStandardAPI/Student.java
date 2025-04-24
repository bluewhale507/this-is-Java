package sec14_lambdaExpressions.example.functionalInterfaceOfStandardAPI;

public class Student {
    private String name;
    private int englishScore;
    private int mathScore;

    private String sex;
    private int score;

    public Student(String name, int englishScore, int mathScore) {
        this.name = name;
        this.englishScore = englishScore;
        this.mathScore = mathScore;
    }

    public Student(String name, String sex, int score) {
        this.name = name;
        this.sex = sex;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getEnglishScore() {
        return englishScore;
    }

    public int getMathScore() {
        return mathScore;
    }

    public String getSex() {
        return sex;
    }

    public int getScore() {
        return score;
    }

}
