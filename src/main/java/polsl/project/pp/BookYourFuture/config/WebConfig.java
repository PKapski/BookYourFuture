package polsl.project.pp.BookYourFuture.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan("polsl.project.pp.BookYourFuture.controller")
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

}
