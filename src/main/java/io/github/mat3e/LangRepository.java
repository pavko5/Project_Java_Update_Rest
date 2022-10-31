package io.github.mat3e;
import java.util.Optional;

public class LangRepository {

    Optional<Lang> findById(Integer id) {
        //przypisanie do zmiennej
        var session = HibernateUtil.getSessionFactory().openSession();
        //tworzenie nowej tranzakcji ( mozna przypisac do zmiennej)
        var transaction = session.beginTransaction();
        //przypisanie do zmiennej result to co zwroci - metoda - jaka klasa ma byc zwrocona dla jakiego id
        var result = session.get(Lang.class, id);
        transaction.commit();
        session.close();
        return Optional.ofNullable(result);
    }
}
