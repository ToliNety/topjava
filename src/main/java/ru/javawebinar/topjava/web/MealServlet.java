package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.MealInMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by tolikswx on 12.12.2016.
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);
    private static MealDAO meals = MealInMemory.getInstance();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        int calories = Integer.parseInt(request.getParameter("calories"));

        if (request.getParameter("id") == null || request.getParameter("id").isEmpty()) {
            meals.add(new Meal(0,LocalDateTime.now(),
                    request.getParameter("description"),
                    calories));
            LOG.debug("Meal added");
        } else {
            Meal mealToUpdate = new Meal(
                    Integer.parseInt(request.getParameter("id")),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    calories);
            meals.update(mealToUpdate);
            LOG.debug("Meal updated id="+mealToUpdate.getId());

        }
        response.sendRedirect("meal");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("action") != null) switch (request.getParameter("action")) {
            case "edit":
                Meal meal = meals.getByID(Integer.parseInt(request.getParameter("id")));
                if (meal != null) {
                    request.setAttribute("meal", meal);
                    request.getRequestDispatcher("/view/edit.jsp").forward(request, response);
                    LOG.debug("Redirected to edit form");
                    return;
                }
                break;
            case "delete":
                int mealID = Integer.parseInt(request.getParameter("id"));
                meals.delete(mealID);
                LOG.debug("Meal deleted id="+mealID);
                LOG.debug("Redirected to list all");
                response.sendRedirect("meal");
                return;
        }

        List<MealWithExceed> mealsWithExceedList =
                MealsUtil.getFilteredWithExceeded(meals.list(), LocalTime.MIN, LocalTime.MAX, 2000);

        request.setAttribute("mealsList", mealsWithExceedList);
        request.getRequestDispatcher("/view/meals.jsp").forward(request, response);
    }
}
