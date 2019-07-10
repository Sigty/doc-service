package com.itacademy.web.initializer;

import com.itacademy.service.config.ServiceConfig;
import com.itacademy.web.config.SecurityConfig;
import com.itacademy.web.config.WebConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import static java.nio.charset.StandardCharsets.UTF_8;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    private static final String SERVLET_MAPPING = "/";

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{ServiceConfig.class, SecurityConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{SERVLET_MAPPING};
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.setRequestCharacterEncoding(UTF_8.name());
        servletContext.setResponseCharacterEncoding(UTF_8.name());
        super.onStartup(servletContext);
    }
}