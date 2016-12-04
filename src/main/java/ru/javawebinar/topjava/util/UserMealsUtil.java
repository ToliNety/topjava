package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        List<UserMealWithExceed> resultList = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

        //print all results
        resultList.forEach(System.out::println);

    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        Map<LocalDate, Integer> caloriesByDay =
                mealList.stream()
                        .collect(Collectors.toMap(
                                item -> item.getDateTime().toLocalDate(),
                                item -> item.getCalories(),
                                (cal1, cal2) -> cal1 + cal2
                        ));

        List<UserMealWithExceed> resultList =
                mealList.stream()
                        .filter(item -> TimeUtil.isBetween(item.getDateTime().toLocalTime(), startTime, endTime))
                        .map(item -> {
                            int userMealCaloriesByDay = caloriesByDay.get(item.getDateTime().toLocalDate());
                            boolean exceed = false;
                            if (userMealCaloriesByDay <= caloriesPerDay) exceed = true;

                            return new UserMealWithExceed(item.getDateTime(), item.getDescription(),
                                    item.getCalories(), exceed);
                        })
                        .collect(Collectors.toList());

        return resultList;
    }
}
