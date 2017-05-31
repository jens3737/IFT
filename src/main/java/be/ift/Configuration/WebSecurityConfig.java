package be.ift.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery(
                        "select u.ACC_email, u.ACC_wachtwoord, u.ACC_active from tbluseraccount u where u.ACC_email=?")
                .authoritiesByUsernameQuery(
                        /*"SELECT u.ACC_email, u.ACC_rol FROM tbluseraccount u WHERE u.ACC_email=?");*/
                        "SELECT u.ACC_email, r.ROL_naam FROM tbluseraccount u, tblrol r WHERE u.ACC_ID_rol = r.ROL_ID and u.ACC_email=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/home", "/", "/stageopdrachten", "/stageopdracht/{\\d+}", "/begeleiders", "/begeleider/{\\d+}", "/stagiaires", "/stagiair/{\\d+}", "/analyse", "/scholen", "/school/{\\d+}", "/evaluatieformulier/{\\d+}")
                    .access("hasRole('ROLE_STAGECOORDINATOR') or hasRole('ROLE_BEGELEIDER') or hasRole('ROLE_HR') or hasRole('ROLE_GUEST')")
                .antMatchers( "/evaluatie", "/evaluatie/{\\d+}","/stageopdracht", "/analyse","/stagiair")
                    .access("hasRole('ROLE_STAGECOORDINATOR') or hasRole('ROLE_BEGELEIDER')")
                .antMatchers("/admin", "/adminpanel", "/stage", "/useraccount", "/begeleider", "/school", "/stagiair")
                    .access("hasRole('ROLE_STAGECOORDINATOR')")
                .antMatchers("/useraccount", "/useraccount/{\\d+}")
                    .access("hasRole('ROLE_STAGECOORDINATOR') or hasRole('ROLE_BEGELEIDER') or hasRole('ROLE_HR')")
                .antMatchers("/categorieform")
                    .permitAll()
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/home")
                .usernameParameter("username").passwordParameter("password")
                .and()
                .logout().logoutSuccessUrl("/login?logout")
                .and()

                .exceptionHandling().accessDeniedPage("/403")
                .and();
//                .csrf();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
