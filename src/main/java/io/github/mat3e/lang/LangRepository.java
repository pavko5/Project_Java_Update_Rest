package io.github.mat3e.lang;

import io.github.mat3e.HibernateUtil;
import io.github.mat3e.lang.Lang;

import java.util.List;
import java.util.Optional;

public class LangRepository {

    //metoda bezparametrowa zwracająca liste - Langów
    public List<Lang> findAll() {
        //przypisanie do zmiennej
        var session = HibernateUtil.getSessionFactory().openSession();
        //tworzenie nowej tranzakcji ( mozna przypisac do zmiennej)
        var transaction = session.beginTransaction();

        var result = session.createQuery("from Lang", Lang.class).list(); // zwrocenie do klasy Lang, potem wywolujemy liste

        transaction.commit();
        session.close();
        return result;
    }

    // ramy działania z baza danych, odczyt i zapis
    public Optional<Lang> findById(Integer id) {
        //przypisanie do zmiennej
        var session = HibernateUtil.getSessionFactory().openSession();
        //tworzenie nowej tranzakcji ( mozna przypisac do zmiennej)
        var transaction = session.beginTransaction();
        //przypisanie do zmiennej result to co zwroci - metoda - jaka klasa ma byc zwrocona dla jakiego id
        var result = Optional.ofNullable(session.get(Lang.class, id));  //Encja pobrana - result jest zarzadzany przez Hibernate
        transaction.commit();
        session.close();
        return result;
    }
}
