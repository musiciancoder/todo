
@EnableWebSecurity
public class DefaultSecurityConfig {

    /*
When the Autorization server receives a user request that is not authenticated, it will redirect him to a login page.
We need to handle that login page and also the authorization, server only is concerned about the security of the endpoints exposes.
It exposes as follows: oauth2/authorize, oauth2/token, etc. We need to ensure that by default, our complete application ( the complete authorization server ) is secured.
     */

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests->authorizeRequests.anyRequests().authenticated()).formLogin(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder bcryptPasswordEncoder() {
        return new BcryptPasswordEncoder();
    }

}