public class Tutor extends Student {

    private String specialization;

    public Tutor(String name, String specialization) {
        super(name);
        this.specialization = specialization;
    }

    public String getSpecialization(){
        return specialization;
    }

    @Override
    public String toString(){
        return super.toString() + " | Specialization: " + this. specialization;
    }
}
