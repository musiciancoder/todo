
//Custom configuration for our authorization server
@Configuration
public class AuthorizationServerConfiguration throws Exception {

    @Autowired
    private UserDetailsService userDetailsService

    @Value("${keyFile}")
    private String keyFile;

   @Value("${password}")
   private String password

   @Value("${alias}")
   private String alias;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain suthServerSecurityFilterChain(HttpSecurity http){
        Oauth2AuthorizationServerConfiguration.applyDefaultSecurity(http); //aplica seguridad por defecto de oauth, arroja una excepcion, por eso el throws
        return http.userDetailsService(userDetailsService).formLogin(Customizer.withDefaults().build()); //se retorna un filtro de seguridad. Se usa formLogin.
    }

    //Este es el método que setea el keystore (q es donde almacenamos las llaves) a JWT
    private JWKSet buildJWKSet() throws KeyStoreException, NoSuchAlgoritmException, CertificateException{ //
        KeyStore keystore = KeyStore.getInstance("pkcs12");
        InputStream fis = this.getClass().getResourceAsStream(keyFile);
        keysStore.load(fis,alias.toCharArray());
        return JWKSet.load(keyStore,new PasswordLookup(){ //se le pasa por parametro el keystore y el passowrd para ingresar al keystore que setiamos en la clase "the java keytool"

            @Override
            public char [] lookupPassword (String name){
                return password.toCharArray();
        }
        }
        )
        }

}