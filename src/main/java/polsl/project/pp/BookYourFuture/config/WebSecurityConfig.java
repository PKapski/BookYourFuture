package polsl.project.pp.BookYourFuture.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password("{noop}admin").roles("ADMIN")
                .and()
                .withUser("guest").password("{noop}guest").roles("GUEST");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/gowno").hasAnyRole("ADMIN")
                .antMatchers("/kupa").hasAnyRole("GUEST")
                .antMatchers("/").hasAnyRole("GUEST", "ADMIN")
                .and()
                .formLogin()
                .and()
                //.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                // .and()
                .csrf().disable();

/*
        http.authorizeRequests()
                .anyRequest()
                .fullyAuthenticated()
                .and()
                .httpBasic()
                .and().csrf().disable();*/
       // http.authorizeRequests().antMatchers("/").permitAll();
    }

}
