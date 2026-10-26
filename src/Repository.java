import java.util.List;

public interface Repository<T,ID> {

    void save(T entity);
    List<T> findAll();
    T findById(ID id);
    void updateById(ID id, String newName);
    void deleteById(ID id);
}
