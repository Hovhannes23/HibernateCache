package com.example.hibernatecache;

import com.example.hibernatecache.entity.City;
import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionFactoryImpl;
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
        berlin.setName("Praga");
        berlin.setPopulation(1_35_000L);
        City london = new City();
        berlin.setName("London");
        berlin.setPopulation(5_800_000L);

        // save city to db
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        session.save("berlin", berlin);
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
    }

}
