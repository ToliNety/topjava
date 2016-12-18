package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
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

            //init data at mealRepository
            MealRepository mealRepository = appCtx.getBean(MealRepository.class);
            mealRepository.initData();

            testMealService(appCtx.getBean(MealService.class));

            /*AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(1, "userName", "email", "password", Role.ROLE_ADMIN));*/
        }
    }

    private static void testMealService(MealService mealService) {
        //List all for diff users
        List<Meal> mealList = mealService.getAll(1);
        mealList.forEach(System.out::println);
        mealList = mealService.getAll(2);
        mealList.forEach(System.out::println);
        mealList = mealService.getAll(4);
        mealList.forEach(System.out::println);

        //Edit meals for diff users
        mealService.save(new Meal(3, LocalDateTime.now(), "after edit", 1500), 2);
        mealService.save(new Meal(3, LocalDateTime.now(), "after edit", 1500), 1);
        mealService.save(new Meal(4, LocalDateTime.now(), "after edit by admin", 2500), 4);

        //Delete meals for diff users
        mealService.delete(5, 2);
        mealService.delete(5, 1);
        mealService.delete(5, 4);
        mealService.delete(6, 6);

        mealList = mealService.getAll(4);
        mealList.forEach(System.out::println);
    }
}
