//Custom configuration for our authorization server
//@Configuration
public class AuthorizationServerConfiguration throws Exception{

@Autowired
private UserDetailsService userDetailsService

@Value("${keyFile}")
private String keyFile;

@Value("${password}")
private String password

@Value("${alias}")
private String alias;

@Value("${providerUrl}")
private String providerUrl;

@Bean
@Order(Ordered.HIGHEST_PRECEDENCE)
public SecurityFilterChain suthServerSecurityFilterChain(HttpSecurity http){
        Oauth2AuthorizationServerConfiguration.applyDefaultSecurity(http); //aplica seguridad por defecto de oauth, arroja una excepcion, por eso el throws
        return http.userDetailsService(userDetailsService).formLogin(Customizer.withDefaults().build()); //se retorna un filtro de seguridad. Se usa formLogin.
        }

        /*
        So with three methods, we have configured the JWT decoder.The first step was to build the JWK set, then theJWK source using that JWT or JWK set.
And finally, the JWT decoder by using war to authorization configurations JWT automatically
with the JWK source, and it will create the decoder which knows how to use the keys, How to use the private and public keys  internally          */

@Bean
public  JwtDecoder jwtDecoder(JWTSource<SecurityContext>jwtSource){ //
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwtSource);
        }

@Bean
public JWTSource<SecurityContext> jwtSource()throws KeyStoreException,NoSuchAlgorithmException,CertificateException,IOException{
        JWKSet jwtSet=buildJWKSet();
        return(jwtSelector,securityContext)->jwtSelector.select(JWKSet);
        }

//Este es el método que setea el keystore (q es donde almacenamos las llaves) a JWT
private JWKSet buildJWKSet()throws KeyStoreException,NoSuchAlgoritmException,CertificateException{ //
        KeyStore keystore=KeyStore.getInstance("pkcs12");
        try(InputStream fis=this.getClass().getResourceAsStream(keyFile);){
        keysStore.load(fis,alias.toCharArray());
        return JWKSet.load(keyStore,new PasswordLookup(){ //se le pasa por parametro el keystore y el passowrd para ingresar al keystore que setiamos en la clase "the java keytool"

        @Override
        public char[]lookupPassword(String name){
        return password.toCharArray();
        }
        }
        );
        }

        }

        //Al parecer esto es la configuracion del provider para que reconozca la url http://localhost:8080 como provide (no explicó nada mas)
            @Bean
            AuthorizationServerSettings authorizationServerSettings() {
            return  AuthorizationServerSettings.builder().issuer(providerUrl).build();
               }

               //Esto es para registrar la aplicacion cliente
               @Bean
               public RegisteredClientRepository registeredClientRepository() {
                RegisteredClient registeredClient = RegisteredClient.withId("couponservice")
                .clientId("couponclientapp")
                .clientSecret("9999")
                .clientAuthorizationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE) //vamos a ocupar este grandtype...
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN) //...mas este este grandtype
                .redirectUri("https://oidcdebugger.com/debug") //Oicdebugger nos permitirá obtener un authorization code  cuando nos loguiemos desde el navegador.
                .scope("read")
                .scope("write")
                .build();
                return new InMemoryRegisteredClientRepository(registeredClient);
        }

        }