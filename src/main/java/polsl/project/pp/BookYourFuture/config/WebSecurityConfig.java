package polsl.project.pp.BookYourFuture.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource securityDataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
/*        auth.inMemoryAuthentication()
                .withUser("admin").password("{noop}admin").roles("ADMIN")
                .and()
                .withUser("guest").password("{noop}guest").roles("GUEST");*/
        auth.eraseCredentials(false);
        auth.jdbcAuthentication()

                .dataSource(securityDataSource)
                .usersByUsernameQuery("SELECT login as username, password, true" + " from users where login=?")
                .authoritiesByUsernameQuery("SELECT login as username, role" + " from users where login=?");



    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/adminpage").hasAnyRole("ADMIN")
                .antMatchers("/guestpage").hasAnyRole("GUEST")
                //.antMatchers("/").hasAnyRole("GUEST", "ADMIN")
                .antMatchers("/").permitAll()
                .and()
                .formLogin()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/")
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
