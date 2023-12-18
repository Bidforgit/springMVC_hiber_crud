package com.anuar.dao;

import com.anuar.entity.Car;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CarDao {
    private final SessionFactory sessionFactory;

    public CarDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Car> getAllCars(){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select c from Car c").getResultList();
    }

    @Transactional(readOnly = true)
    public Car show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Car.class,id);
    }

    @Transactional
    public void saveCar(Car car) {
        Session session = sessionFactory.getCurrentSession();
        session.save(car);

    }
    @Transactional
    public void updateCar(int id, Car newCar) {
        Session session = sessionFactory.getCurrentSession();
        Car car = session.get(Car.class,id);
        car.setModel(newCar.getModel());

    }
    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Car.class,id));
    }
}
