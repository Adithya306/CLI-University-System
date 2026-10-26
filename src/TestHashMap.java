public class TestHashMap {

    static void main() {
        Student s1 = new Student(101,"Garuka");
        Student s2 = new Student(101,"Garuka");

        System.out.println(s1.equals(s2));
        System.out.println(s1.hashCode() == s2.hashCode());
    }
}
