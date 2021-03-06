package com.epam.esm.exception;

import com.epam.esm.dto.ApiErrorDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.annotations.Cache;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException e) throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        String errorMessage = "Access Denied, you don't have rules for this action!";

        ApiErrorDTO apiError = new ApiErrorDTO(
            HttpStatus.FORBIDDEN.toString(),errorMessage , request.getRequestURI());

        OutputStream out = response.getOutputStream();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        com.fasterxml.jackson.databind.ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, apiError);

        out.flush();
    }
}
