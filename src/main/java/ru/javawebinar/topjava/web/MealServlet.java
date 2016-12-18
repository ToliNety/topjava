package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);
    private ConfigurableApplicationContext appCtx;
    private MealRestController controller;
    private AdminRestController userController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        LOG.info("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));

        //init data at mealRepository
        MealRepository mealRepository = appCtx.getBean(MealRepository.class);
        mealRepository.initData();

        controller = appCtx.getBean(MealRestController.class);
        userController = appCtx.getBean(AdminRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));

        LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        controller.save(meal);
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            LOG.info("getAll");
            request.setAttribute("meals", controller.getAll());
            request.setAttribute("users", userController.getAll());
            request.getRequestDispatcher("/meals.jsp").forward(request, response);

        } else if ("delete".equals(action)) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            controller.delete(id);
            response.sendRedirect("meals");

        } else if ("create".equals(action) || "update".equals(action)) {
            final Meal meal = action.equals("create") ?
                    new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                    controller.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("meal.jsp").forward(request, response);

        } else if ("getAllFiltered".equals(action)) {
            LOG.info("getAllFiltered");
            LocalDate startDate = !request.getParameter("startDate").isEmpty() ?
                    LocalDate.parse(request.getParameter("startDate")) : null;
            LocalDate endDate = !request.getParameter("endDate").isEmpty() ?
                    LocalDate.parse(request.getParameter("endDate")) : null;
            LocalTime startTime = !request.getParameter("startTime").isEmpty() ?
                    LocalTime.parse(request.getParameter("startTime")) : null;
            LocalTime endTime = !request.getParameter("endTime").isEmpty() ?
                    LocalTime.parse(request.getParameter("endTime")) : null;
            request.setAttribute("meals", controller.getAll(startDate, startTime, endTime, endDate));
            request.setAttribute("users", userController.getAll());
            request.getRequestDispatcher("/meals.jsp").forward(request, response);

        } else if ("changeUser".equals(action)) {
            LOG.info("change user to userID = " + request.getParameter("user"));
            AuthorizedUser.setID(Integer.valueOf(request.getParameter("user")));
            response.sendRedirect("meals");
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }

    @Override
    public void destroy() {
        appCtx.close();
        super.destroy();
    }
}