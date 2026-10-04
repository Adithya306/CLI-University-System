public class Tutor extends Student {

    private String specialization;

    public Tutor(String name, int ID, String specialization) {
        super(name, ID);
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
