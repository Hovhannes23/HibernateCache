package com.example.hibernatecache;

import com.example.hibernatecache.entity.City;
import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.query.Query;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class HibernateCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(HibernateCacheApplication.class, args);
        City berlin = new City();
        berlin.setName("Berlin");
        berlin.setPopulation(3_500_000L);
        City praga = new City();
        praga.setName("Praga");
        praga.setPopulation(1_350_000L);
        City london = new City();
        london.setName("London");
        london.setPopulation(5_800_000L);

        // save city to db
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        session.save("berlin", berlin);
        session.save("praga", praga);
        session.save("london", london);
        session.close();


        // get city in session1
        Session session1 = factory.openSession();
        Transaction transaction = session1.beginTransaction();

        City city1 = session1.get(City.class, 1);
        System.out.println(city1);
        city1.setName("Marsel");
        session1.persist(city1);
        // check 1-st level cache
        City city1_2 = session1.get(City.class, 1);
        System.out.println(city1_2);
        transaction.commit();

        // get city in session2
        Session session2 = factory.openSession();
        City city2 = session2.get(City.class, 1);
        System.out.println(city2);
        session2.close();



        // query cache
        Session session3 = factory.openSession();
        Query query = session3.createQuery("FROM City c WHERE c.population > :population");
        query.setParameter("population", 1_400_000L);
        query.setCacheable(true);
        System.out.println(query.list());

        // в той же сессии создаем запрос с тем же синтаксисом и значением параметра.
        // Ожидаемый результат: данные из кэша
        Query query2 = session3.createQuery("FROM City c WHERE c.population > :population");
        query2.setParameter("population", 1_400_000L);
        query2.setCacheable(true);
        System.out.println(query2.list());

        // в той же сессии создаем запрос с тем же синтаксисом и меняем значение параметра.
        // Ожидаемый результат: данные из БД
        Query query3 = session3.createQuery("FROM City c WHERE c.population > :population");
        query3.setParameter("population", 1_444_000L);
        query3.setCacheable(true);
        System.out.println(query3.list());

        // в другой сессии создаем запрос с тем же синтаксисом и значением параметра.
        // Ожидаемый результат: данные из кэша
        Session session4 = factory.openSession();
        Query query1 = session4.createQuery("FROM City c WHERE c.population > :population");
        query1.setParameter("population", 1_400_000L);
        query1.setCacheable(true);
        System.out.println(query1.list());

    }

}
