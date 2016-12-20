package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.meal.MealRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));

            testMealController(appCtx.getBean(MealRestController.class));

            /*AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(1, "userName", "email", "password", Role.ROLE_ADMIN));*/
        }
    }

    private static void testMealController(MealRestController mealController) {
        //List all for diff users and Date/Time
        List<MealWithExceed> mealList = mealController.getAll(null, LocalTime.of(13,00), LocalTime.of(21,00), null);
        mealList.forEach(System.out::println);
        mealList = mealController.getAll(LocalDate.of(2015, Month.MAY, 31), LocalTime.of(13,00), LocalTime.of(21,00), null);
        mealList.forEach(System.out::println);
        AuthorizedUser.setID(2);
        mealList = mealController.getAll();
        mealList.forEach(System.out::println);
        AuthorizedUser.setID(4);
        mealList = mealController.getAll(LocalDate.of(2015, Month.MAY, 30),null, null, LocalDate.of(2015, Month.MAY, 30));
        mealList.forEach(System.out::println);

        //Edit meals by diff users
        AuthorizedUser.setID(2);
        try {
            mealController.save(new Meal(3, LocalDateTime.now(), "after edit", 1500));
        } catch (NotFoundException ex) {
            System.out.println("NotFoundException: " + ex.getMessage());
        }
        AuthorizedUser.setID(1);
        mealController.save(new Meal(3, LocalDateTime.now(), "after edit", 1500));
        AuthorizedUser.setID(4);
        mealController.save(new Meal(4, LocalDateTime.now(), "after edit by admin", 2500));

        //Delete meals from diff users
        AuthorizedUser.setID(2);
        try {
            mealController.delete(5);
        } catch (NotFoundException ex) {
            System.out.println("NotFoundException: " + ex.getMessage());
        }
        AuthorizedUser.setID(1);
        mealController.delete(5);
        AuthorizedUser.setID(4);
        try {
            mealController.delete(5);
        } catch (NotFoundException ex) {
            System.out.println("NotFoundException: " + ex.getMessage());
        }
        AuthorizedUser.setID(6);
        try {
            mealController.delete(6);
        } catch (NotFoundException ex) {
            System.out.println("NotFoundException: " + ex.getMessage());
        }

        AuthorizedUser.setID(4);
        mealList = mealController.getAll();
        mealList.forEach(System.out::println);
    }
}
