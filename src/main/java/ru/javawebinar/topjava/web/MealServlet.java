package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.Counter;
import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.MealLocalDB;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by tolikswx on 12.12.2016.
 */
public class MealServlet extends HttpServlet {
    private static MealDAO meals = MealLocalDB.getInstance();
    private static Counter counter = Counter.getCounter();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        //TODO Add validation data from add && edit form
        int calories = Integer.parseInt(request.getParameter("calories"));

        if (request.getParameter("id") == null || request.getParameter("id").isEmpty()) {
            meals.add(new Meal(
                    counter.getNextID(),
                    LocalDateTime.now(),
                    request.getParameter("description"),
                    calories));
        } else {
            meals.update(new Meal(
                    Integer.parseInt(request.getParameter("id")),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    calories));
        }
        response.sendRedirect("meal");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("edit") != null && !request.getParameter("edit").isEmpty()) {
            Meal meal = meals.getByID(Integer.parseInt(request.getParameter("edit")));

            if (meal != null) {
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/view/edit.jsp").forward(request, response);
                return;
            }

        } else if (request.getParameter("delete") != null && !request.getParameter("delete").isEmpty()) {
            meals.delete(Integer.parseInt(request.getParameter("delete")));
        }

        List<MealWithExceed> mealsWithExceedList = MealsUtil.getAllWithExceeded(meals.list(), 2000);

        request.setAttribute("mealsList", mealsWithExceedList);
        request.getRequestDispatcher("/view/meals.jsp").forward(request, response);
    }
}
