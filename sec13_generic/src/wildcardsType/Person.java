package sec13_generic.src.wildcardsType;

public class Person {
    private String name;

    public Person(String name) { this.name = name; }
    public String getName() { return name; }
    @Override
    public String toString() { return name; }

}
