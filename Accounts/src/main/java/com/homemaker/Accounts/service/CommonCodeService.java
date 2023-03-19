package com.homemaker.Accounts.service;

import com.homemaker.Accounts.entities.BaseDomain;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;

import java.util.LinkedHashMap;
import java.util.Map;

//@Service //not aware of.
public class CommonCodeService<T extends BaseDomain> {
    public static String SUCCESS="success";
    public static String MESSAGE="message";

    protected final ResponseEntity success(Object data) {
        return success(data, null);
    }
    protected final ResponseEntity success(Object data, String message) {
        Map<String, Object> resp = new LinkedHashMap<>();
        resp.put(SUCCESS, Boolean.TRUE);
        resp.put("rc", "0");
        if (StringUtils.isNotBlank(message)) {
            resp.put(MESSAGE, message);
        }

        if (data != null) {
            resp.put("data", data);
        }

        return ResponseEntity.ok().contentType( MediaType.APPLICATION_JSON).body(resp);
    }


    protected final ResponseEntity error(Object data) {
        return error(data, null);
    }
    protected final ResponseEntity error(Object data, String message) {
        Map<String, Object> resp = new LinkedHashMap<>();
        resp.put(SUCCESS, Boolean.FALSE);
        if (data != null) {
            resp.put("data", data);
        }
        if (StringUtils.isNotBlank(message)) {
            resp.put(MESSAGE, message);
        }

        return ResponseEntity.ok().contentType( MediaType.APPLICATION_JSON).body(resp);
    }
}
 