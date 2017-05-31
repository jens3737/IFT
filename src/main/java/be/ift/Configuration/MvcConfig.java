package be.ift.Configuration;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Configuration
    public class ErrorConfig implements EmbeddedServletContainerCustomizer {
        @Override
        public void customize(ConfigurableEmbeddedServletContainer container) {
            container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"));
        }
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home"); //.setViewName("Home");
        registry.addViewController("/"); //.setViewName("Home");
        registry.addViewController("/hello").setViewName("Hello");
        registry.addViewController("/login").setViewName("Login");
        registry.addViewController("/403").setViewName("403");
        registry.addViewController("/404").setViewName("404");
        registry.addViewController("/stageopdracht").setViewName("stageopdracht");
        registry.addViewController("/stagiaires").setViewName("stagiaires");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**");

    }

    @Bean(name = "dataSource")
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/iftdb?useSSL=false");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("root");

        return driverManagerDataSource;
    }

}
