package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;

import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


/**
 * Created by tolikswx on 31.01.2017.
 */
public class MealRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = MealRestController.REST_URL + '/';

    @Test
    public void getAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_EXCEED.contentListMatcher(
                        MealsUtil.getWithExceeded(MEALS, MealsUtil.DEFAULT_CALORIES_PER_DAY))));
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk());
        List<Meal> newMealList = new ArrayList<>(MEALS);
        newMealList.remove(MEAL1);
        MATCHER.assertCollectionEquals(newMealList, mealService.getAll(USER_ID));

    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = MealTestData.getUpdated();
        mockMvc.perform(put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());
        MATCHER.assertEquals(updated, mealService.get(updated.getId(), USER_ID));
    }

    @Test
    public void testCreate() throws Exception {
        Meal expected = new Meal(LocalDateTime.now(), "newMeal", 777);
        ResultActions action = mockMvc.perform(
                post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Meal returned = MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());

        MATCHER.assertEquals(expected, returned);

        List<Meal> newMealsList = new ArrayList<>(MEALS);
        newMealsList.add(expected);
        MATCHER.assertCollectionEquals(
                newMealsList.stream()
                        .sorted((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()))
                        .collect(Collectors.toList()),
                mealService.getAll(USER_ID));
    }

    @Test
    public void getBetween() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("startDate", "2015-05-31");
        params.add("startTime", "10:00:00");
        params.add("endDate", "2015-05-31");
        params.add("endTime", "20:00:00");

        TestUtil.print(mockMvc.perform(get(REST_URL + "/filter").params(params))
                .andExpect(MATCHER_EXCEED.contentListMatcher(
                        MealsUtil.getWithExceeded(Arrays.asList(MEAL6, MEAL5, MEAL4), MealsUtil.DEFAULT_CALORIES_PER_DAY))));


    }

}