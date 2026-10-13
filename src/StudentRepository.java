public interface StudentRepository {

    void  save (Student student);
    void displayAll();
    void findUserById(int targetId);
}
