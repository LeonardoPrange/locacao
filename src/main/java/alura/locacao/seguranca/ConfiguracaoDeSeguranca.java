package alura.locacao.seguranca;

import alura.locacao.domain.repositories.UsuarioRepository;
import alura.locacao.seguranca.filters.AutenticacaoTokenFilter;
import alura.locacao.seguranca.services.CustomUserDetailsService;
import alura.locacao.seguranca.services.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
class ConfiguracoesDeSeguranca extends WebSecurityConfigurerAdapter {
    private final CustomUserDetailsService customUserDetailsService;
    private TokenService tokenService;
    private UsuarioRepository usuarioRepository;

    public ConfiguracoesDeSeguranca(CustomUserDetailsService customUserDetailsService, TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.customUserDetailsService = customUserDetailsService;
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    //Autenticação
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
    //Autorização
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/usuario").permitAll()
                .antMatchers(HttpMethod.POST, "/automovel").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/automovel").hasAnyAuthority("USUARIO", "ADMIN")
                .antMatchers(HttpMethod.GET, "/automovel/aluga").hasAuthority("USUARIO")
                .anyRequest().permitAll()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(
                        new AutenticacaoTokenFilter(tokenService, usuarioRepository),
                        UsernamePasswordAuthenticationFilter.class
                );
    }

    //Arquivos estáticos
    @Override
    public void configure(WebSecurity web) throws Exception {

    }
}
