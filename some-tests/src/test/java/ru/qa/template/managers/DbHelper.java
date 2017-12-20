package ru.qa.template.managers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.qa.template.model.Unit2Data;
import ru.qa.template.model.Units2;

import java.util.List;

public class DbHelper {
    private final SessionFactory sessionFactory;

    public DbHelper() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml") // configures settings from hibernate.cfg.xml
                .build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public Units2 units2() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Unit2Data> result = session.createQuery( "from Unit2Data" ).list();
//        for (GroupData group : result) {
//            System.out.println(group);
//        }
        session.getTransaction().commit();
        session.close();

        return new Units2(result);
    }
}
