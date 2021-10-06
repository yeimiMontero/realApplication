package com.udemy.backendninja.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
//Para habilitar la seguridad web
@EnableWebSecurity
//Clase de spring para guiarnos en la configuración
@EnableGlobalMethodSecurity(prePostEnabled = true)
//Permite escribir anotaciones para controlar el acceso a los metodos según la seguridad
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userService")
    private UserDetailsService userService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
                                            //Es el que nos va a cifrar el password en la bd
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                //Request que estan autorizadas sin ncesidad de login
                .antMatchers("/css/*", "/imgs/*").permitAll()
                .anyRequest().authenticated()
                .and()
        .formLogin().loginPage("/login").loginProcessingUrl("/logincheck")
        .usernameParameter("username").passwordParameter("password")
        .defaultSuccessUrl("/loginsuccess").permitAll()
        .and()
        .logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout")
        .permitAll();


    }



}
