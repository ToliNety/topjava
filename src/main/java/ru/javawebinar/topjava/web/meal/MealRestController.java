package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class MealRestController {
    @Autowired
    private MealService service;

    public Meal save(Meal meal) throws NotFoundException {
        return service.save(meal, AuthorizedUser.id());
    }

    public void delete(int id) throws NotFoundException {
        service.delete(id, AuthorizedUser.id());
    }

    public Meal get(int id) throws NotFoundException {
        return service.get(id, AuthorizedUser.id());
    }


    public List<MealWithExceed> getAll() {
        return getAll(null, null, null, null);
    }

    public List<MealWithExceed> getAll(LocalDate startDate, LocalTime startTime, LocalTime endTime, LocalDate endDate) {
        return MealsUtil.getFilteredWithExceeded(
                service.getAll(startDate, endDate, AuthorizedUser.id()),
                startTime,
                endTime,
                AuthorizedUser.getCaloriesPerDay());
    }

}
