public interface StudentRepository {

    void  save (Student student);
    void displayAll();
    void findUserById(int targetId);
    void updateUserById(int targetId, String newName);
    void deleteUserById(int targetId);


}
