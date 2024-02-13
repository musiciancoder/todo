
//Custom configuration for our authorization server
@Configuration
public class AuthorizationServerConfiguration throws Exception {

    @Autowired
    private UserDetailsService userDetailsService

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain suthServerSecurityFilterChain(HttpSecurity http){
        Oauth2AuthorizationServerConfiguration.applyDefaultSecurity(http); //aplica seguridad por defecto de oauth, arroja una excepcion, por eso el throws
        return http.userDetailsService(userDetailsService).formLogin(Customizer.withDefaults().build()); //se retorna un filtro de seguridad. Se usa formLogin.
    }

}