package fdmc.repository;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.function.Function;

public abstract class BaseRepository {
    private final EntityManager entityManager;

    public BaseRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    protected <T> T executeTransaction(Function<EntityManager,T> func){
        T result = null;

        try {
            this.entityManager.getTransaction().begin();
            result = func.apply(this.entityManager);
            this.entityManager.getTransaction().commit();
        } catch (Exception e){
            this.entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
        return result;
    }
}
