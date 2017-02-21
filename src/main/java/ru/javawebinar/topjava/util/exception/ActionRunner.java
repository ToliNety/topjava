package ru.javawebinar.topjava.util.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import ru.javawebinar.topjava.AuthorizedUser;

import static ru.javawebinar.topjava.util.ValidationUtil.checkEmailConstraintEx;

/**
 * Created by tolikswx on 21.02.2017.
 */
public class ActionRunner {
    public String handleException(BindingResult result, String view, Action action) {
        try {
            return action.run();
        } catch (DataIntegrityViolationException ex) {
            String msg = checkEmailConstraintEx(ex);
            if (msg != null) {
                result.rejectValue("email", "", msg);
                return view;
            }
            throw ex;
        }
    }
}
