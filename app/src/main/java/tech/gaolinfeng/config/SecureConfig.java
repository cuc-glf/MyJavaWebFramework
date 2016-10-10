package tech.gaolinfeng.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Created by gaolf on 16/10/8.
 */
@EnableWebSecurity
public class SecureConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling().authenticationEntryPoint(
                    (request, response, authException) -> response.sendRedirect("/notLogin")
                ).and()
                .logout().invalidateHttpSession(true)
                .and()
                .csrf().disable().formLogin().loginPage("/login")
                    .successForwardUrl("/loginSuccess").failureForwardUrl("/loginFailure").permitAll()
                .and()
                .authorizeRequests().antMatchers("/loginFailure", "/notLogin").permitAll()
                .and()
                .formLogin().permitAll()
                .and()
                .csrf().disable().authorizeRequests().antMatchers("/api/*").hasRole("USER")
                .and()
                .authorizeRequests().anyRequest().hasRole("USER");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
            .usersByUsernameQuery("select username,password, enabled from users where username=?")
            .authoritiesByUsernameQuery("select username, role from user_roles where username=?");
//        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
    }

}
