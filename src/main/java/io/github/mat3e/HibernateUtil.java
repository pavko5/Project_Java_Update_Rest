package io.github.mat3e;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

//klasa odpowiadajaca za tworzenie fabryki sesji
public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory(); //pole finalne musi dostać wartość swoją, czyli buildSessionFactory():

    //metoda statyczna dostępna na zewnątrz
    static void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    //metoda dostepu do pola, które bedzie zawsze zbudowane, ważne aby tutaj był dostep na zewnatrz
   public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private static SessionFactory buildSessionFactory() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            return new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw e; //wyjatek wyrzucony tutaj - najwyzej apka nie wystartuje
        }
    }


    // tworzenie prywatnego konstruktora - nikt nie utworzy obiektu z tej klasy, nie można dziedziczyć
    private HibernateUtil() {
    }
}
