package com.anuar.dao;


import com.anuar.entity.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

@Component
public class TaskDAO {

    private final SessionFactory sessionFactory;

    public TaskDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Task> getAllTasks(){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select c from Task c").getResultList();
    }

    @Transactional(readOnly = true)
    public List<Task> getTasksByFilter(String filter){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select c from Task c where status = :filter")
                .setParameter("filter",filter)
                .getResultList();
    }


    @Transactional(readOnly = true)
    public Task show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Task.class,id);
    }

    @Transactional
    public void saveTask(Task task) {
        Session session = sessionFactory.getCurrentSession();
        session.save(task);
    }
    @Transactional
    public void updateTask(int id, Task newTask) {
        Session session = sessionFactory.getCurrentSession();
        Task task = session.get(Task.class,id);

        task.setName(newTask.getName());
        task.setDescription(newTask.getDescription());
        task.setStatus(newTask.getStatus());

        // Set the date to the current time in Kazakhstan timezone
        Calendar calendar = Calendar.getInstance();
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Almaty"); // Kazakhstan timezone
        calendar.setTimeZone(timeZone);

        Timestamp currentTimestamp = new Timestamp(calendar.getTimeInMillis());
        task.setDate(currentTimestamp);

    }
    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Task.class,id));
    }

}
