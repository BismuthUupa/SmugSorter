package date.animu.image_tagger.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            	.antMatchers( "/css/**", "/scripts/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login.html").usernameParameter("username").passwordParameter("password")
                
                .loginProcessingUrl("/login_data")
                .defaultSuccessUrl("/index.html")
               // .successForwardUrl("/index.html")
                .permitAll()
                .and()
            .logout()
                .permitAll()
            .and()
            	.csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("changeme").password("changeme").roles("USER").and()
                .withUser("changemetoo").password("changemetoo").roles("USER")/*.and()
                .withUser("").password("").roles("USER").and()
                .withUser("").password("").roles("USER")*/;
    }
}