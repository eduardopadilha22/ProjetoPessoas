package com.jdevs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // classe de configuração
@EnableWebSecurity // entende que isso é uma classe para segurança
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {
    @Autowired
    ImplementacaoUserDetailsService implementacaoUserDetailsService;

    @Override // configura as solicitaçoes de acesso pelo http
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()// desativa as configurações padrão de memória do spring
        .authorizeRequests()// permitir restringir acessos//
        .antMatchers(HttpMethod.GET,"/" ).permitAll() // qualquer usuário acessa a pagina inicial
                .antMatchers(HttpMethod.GET,"/registerperson" ).hasAnyRole("ADMIN")
        .anyRequest().authenticated()
                .and().formLogin().permitAll() // qualquer usuário
                .loginPage("/login")
                .defaultSuccessUrl("/registerperson")
                .failureUrl("/login?error=true")
        .and().logout() // mapeia url de logout e invalida o usuário autenticado
                .logoutSuccessUrl("/login")
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }

    @Override // cria autenticação do usuário com banco de dados ou em memoria
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(implementacaoUserDetailsService) // autenticação com banco de dados
                .passwordEncoder(new BCryptPasswordEncoder());
      /*
      //autenticação em memoria ,
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())// criptografando a senha
        .withUser("eduardo")
                .password("$2a$10$jskaBby0vjt/fCfhb7XOA.xAFXe132o5WYGBuDetB1KOOf9Pd49BC")
                .roles("ADMIN");*/
    }

    @Override // ignora URL especificas
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/materialize/**");
    }
}
