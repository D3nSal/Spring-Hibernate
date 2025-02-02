package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User findUserByCar(String model, String series) {
      User user=null;
      try {
         Car car = (Car) sessionFactory.getCurrentSession().createQuery("from Car where model = :model and series = :series" ).setParameter("model", model).setParameter("series", series).getSingleResult();
         user =  car.getUser();
      } catch (RuntimeException e) {
         System.out.println("Проверьте правильность введённых данных об автомобиле!");
      }
      return user;
   }
}
