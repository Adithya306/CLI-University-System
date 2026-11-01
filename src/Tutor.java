import java.util.Objects;

public class Tutor extends Student {

    private String specialization;

    public Tutor(String name, String specialization) {
        super(name);
        this.specialization = specialization;
    }

    public Tutor(int id, String name, String specialization) {
        super(id, name);
        this.specialization = specialization;
    }

    public String getSpecialization(){
        return specialization;
    }

    @Override
    public String toString(){
        return super.toString() + " | Specialization: " + this. specialization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Tutor tutor = (Tutor) o;
        return Objects.equals(specialization, tutor.specialization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), specialization);
    }
}
