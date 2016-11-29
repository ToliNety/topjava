package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        List<UserMealWithExceed> resultList = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
        for (UserMealWithExceed item :
                resultList) {
            System.out.println(item.toString());
        }

    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        Map<LocalDate, Integer> caloriesByDay = new HashMap<>();

        for (UserMeal userMeal :
                mealList) {
            LocalDate userMealDate = userMeal.getDateTime().toLocalDate();
            if (caloriesByDay.containsKey(userMealDate))
                caloriesByDay.put(userMealDate, caloriesByDay.get(userMealDate)+userMeal.getCalories());
            else caloriesByDay.put(userMealDate, userMeal.getCalories());
        }

        List<UserMealWithExceed> resultList = new LinkedList<>();

        for (UserMeal userMeal :
                mealList) {
            LocalTime userMealTime = userMeal.getDateTime().toLocalTime();
            if (userMealTime.isAfter(startTime)&&userMealTime.isBefore(endTime)){
                int userMealCaloriesByDay = caloriesByDay.get(userMeal.getDateTime().toLocalDate());
                boolean exceed=false;
                if (userMealCaloriesByDay<=caloriesPerDay) exceed=true;
                resultList.add(
                        new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(),
                                userMeal.getCalories(), exceed));
            }
        }

        return resultList;
    }
}
