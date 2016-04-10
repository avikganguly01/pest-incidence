package weka.web;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.DispatcherServlet;

import weka.web.core.infra.WebXmlConfiguration;


@EnableTransactionManagement(proxyTargetClass = true)
//@EnableJpaRepositories("weka.web.*")
@Import({ WebXmlConfiguration.class})
@ComponentScan(basePackages = { "weka.web.*" })
@EnableAutoConfiguration
public class Configuration {
	
	@Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        tomcat.setContextPath(getContextPath());
        return tomcat;
    }
	
	private String getContextPath() {
        return "/pest-incidence";
    }
	
	@Bean   
    public DispatcherServlet dispatcherServlet() {
        DispatcherServlet ds = new DispatcherServlet();
        ds.setThrowExceptionIfNoHandlerFound(true);
        return ds;
    }

}
