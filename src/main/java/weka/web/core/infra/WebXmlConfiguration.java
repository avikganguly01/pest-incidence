package weka.web.core.infra;

import javax.servlet.Servlet;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sun.jersey.spi.spring.container.servlet.SpringServlet;

@Configuration
public class WebXmlConfiguration {
	
	@Bean
    public ServletRegistrationBean jersey() {
        Servlet jerseyServlet = new SpringServlet();
        ServletRegistrationBean jerseyServletRegistration = new ServletRegistrationBean();
        jerseyServletRegistration.addInitParameter("com.sun.jersey.config.property.packages",
        		"weka.web.api");
        jerseyServletRegistration.setServlet(jerseyServlet);
        jerseyServletRegistration.addUrlMappings("/api/v1/*");
        jerseyServletRegistration.setName("jersey-servlet");
        jerseyServletRegistration.setLoadOnStartup(1);
        jerseyServletRegistration.addInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");
        jerseyServletRegistration.addInitParameter("com.sun.jersey.spi.container.ContainerResponseFilters",
                ResponseCorsFilter.class.getName());
        jerseyServletRegistration.addInitParameter("com.sun.jersey.config.feature.DisableWADL", "true");
        return jerseyServletRegistration;
    }

}
