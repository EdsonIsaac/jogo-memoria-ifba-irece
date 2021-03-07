package br.edu.ifba.jogomemoria.infrastructure.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    public static final String[] PUBLIC_MATCHERS = {

        /* EndPoints */
        "/card/findAllToGame",

        /* Páginas */
        "/",
        "/index"
    };

    public static final String[] ROLE_MATCHERS_PANEL = {

        /* Páginas */
        "/painel"
    };

    public static final String[] ROLE_MATCHERS_CARDS = {

        /* EndPoints*/
        "/card/findAll",
        "/card/save",
        "/card/delete",

        /* Páginas */
        "/painel_cartas"
    };

    public static final String[] ROLE_MATCHERS_USERS = {

        /* EndPoints*/
        "/user/findAll",
        "/user/save",
        "/user/delete",

        /* Páginas */
        "/painel_usuarios"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()

            /* Páginas e EndPoints públicos */
            .antMatchers(PUBLIC_MATCHERS).permitAll()

            /* Páginas e EndPoints que necessitam de autorizações específicas */
            .antMatchers(ROLE_MATCHERS_PANEL).hasRole("PANEL")
            .antMatchers(ROLE_MATCHERS_CARDS).hasRole("PANEL_CARDS")
            .antMatchers(ROLE_MATCHERS_USERS).hasRole("PANEL_USERS")

            /* Indica a necessidade de autenticação nas requisições que não foram especificadas anteriormente */
            .anyRequest().authenticated()

            /* Configura a página de Login do site */
            .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()

            /* Configura o Logout do site */
            .and()
                .logout()
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/")
                    .permitAll()
        ;

        /* Desativa CORS e CSRF */
        http.cors().and().csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/imgs/**", "/js/**");
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder () {
        return new BCryptPasswordEncoder();
    }
}