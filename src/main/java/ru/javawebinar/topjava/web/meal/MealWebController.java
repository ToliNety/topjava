package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by tolikswx on 24.01.2017.
 */
@Controller
@RequestMapping(value = "/meals")
public class MealWebController extends AbstractMealController {

    @RequestMapping(method = RequestMethod.GET)
    public String meals(Model model) {
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String meals(HttpServletRequest request, Model model) {
        LocalDate startDate = DateTimeUtil.parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = DateTimeUtil.parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = DateTimeUtil.parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = DateTimeUtil.parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals", super.getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public String meal(@PathVariable int id, Model model) {
        final Meal meal;
        if (id == 0)
            meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        else meal = super.get(id);
        model.addAttribute("meal", meal);
        return "meal";
    }

    @RequestMapping(path = "/save", method = RequestMethod.POST)
    public String saveMeal(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");

        final Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));

        if (request.getParameter("id").isEmpty()) {
            LOG.info("Create {}", meal);
            super.create(meal);
        } else {
            LOG.info("Update {}", meal);
            super.update(meal, Integer.valueOf(request.getParameter("id")));
        }
        return "redirect:/meals";
    }

    @RequestMapping(path = "/delete/{id}", method = RequestMethod.GET)
    public String mealDelete(@PathVariable int id) {
        super.delete(id);
        return "redirect:/meals";
    }
}
