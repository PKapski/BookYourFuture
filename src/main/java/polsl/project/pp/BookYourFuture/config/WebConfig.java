package polsl.project.pp.BookYourFuture.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.logging.Logger;

@Configuration
@EnableWebMvc
@ComponentScan("polsl.project.pp.BookYourFuture.controller")
@PropertySource("classpath:application.properties")
public class WebConfig implements WebMvcConfigurer {

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/" };


   // @Override
   // public void configureViewResolvers(ViewResolverRegistry registry) {
       // registry.jsp().prefix("/WEB-INF/jsp/").suffix(".jsp");
   // }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");
        //registry.addResourceHandler("/**/*.js").addResourceLocations("classpath:/static/");
        //registry.addResourceHandler("/**/*.css").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/**")
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
        //classpath:/META-INF/resources/webjars/
    }
    //}
/*    @Bean
    public DataSource securityDataSource(){

        //create connection pool
        ComboPooledDataSource securityDataSource = new ComboPooledDataSource();

*//*        try {
            securityDataSource.setDriverClass(env.getProperty("spring.datasource.driver-class-name"));
        } catch (PropertyVetoException exc){
            throw new RuntimeException(exc);
        }*//*
        securityDataSource.setJdbcUrl(env.getProperty("spring.datasource.url"));
        securityDataSource.setUser(env.getProperty("spring.datasource.username"));
        securityDataSource.setPassword(env.getProperty("spring.datasource.password"));




        return securityDataSource;
    }*/

}
