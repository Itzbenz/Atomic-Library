import Atom.Annotation.Antonation;

public class Person {

    private int age;

    private String name;

    @Antonation.BuilderProperty
    public void setAge(int age) {
        this.age = age;
    }

    @Antonation.BuilderProperty
    public void setName(String name) {
        this.name = name;
    }

    // getters …

}