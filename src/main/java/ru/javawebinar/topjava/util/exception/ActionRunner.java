package ru.javawebinar.topjava.util.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;

import java.util.function.Supplier;

import static ru.javawebinar.topjava.util.ValidationUtil.checkConstraintEx;

/**
 * Created by tolikswx on 21.02.2017.
 */
public class ActionRunner {
    public String handleException(BindingResult result, String view, Supplier<String> supplier) {
        try {
            return supplier.get();
        } catch (DataIntegrityViolationException ex) {
            String msg = checkConstraintEx(ex);
            if (msg != null) {
                result.rejectValue("email", "", msg);
                return view;
            }
            throw ex;
        }
    }
}
