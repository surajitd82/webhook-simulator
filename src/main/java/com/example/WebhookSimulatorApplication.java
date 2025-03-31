package com.example;

import org.apache.tomcat.util.net.SSLHostConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.Ssl;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@SpringBootApplication
public class WebhookSimulatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebhookSimulatorApplication.class, args);
    }


@Bean
public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainerCustomizer() {
    return factory -> {
        factory.addConnectorCustomizers(connector -> {
            connector.setScheme("https");
            connector.setSecure(true);
            connector.setPort(9443);
            connector.setProperty("keyAlias", "myalias");
            connector.setProperty("keystorePass", "netapp1!");
            connector.setProperty("keystoreFile", "/Users/surajitd/Project/MyWork/webhook-simulator/keystore.jks");
            connector.setProperty("clientAuth", "false");
            connector.setProperty("sslProtocol", "TLS");
            connector.setProperty("SSLEnabled", "true");
            connector.setProperty("enableLookups", "true");
            connector.setProperty("useIPVHosts", "true");
//            connector.setProperty("address", "49.43.242.161");
//            connector.setProperty("defaultSSLHostConfigName", "_default_");
            SSLHostConfig sslHostConfig = new SSLHostConfig();
//            sslHostConfig.setHostName( "_default_");
//            sslHostConfig.setProtocols("TLSv1.2");
        });
    };
}


/*@Bean
public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainerCustomizer() {
    return factory -> {
        Ssl ssl = new Ssl();
        ssl.setKeyAlias("myalias");
        ssl.setKeyPassword("netapp1!");
        ssl.setKeyStore("/Users/surajitd/Project/MyWork/webhook-simulator/keystore.jks");
        ssl.setKeyStoreType("PKCS12"); // e.g., "JKS" or "PKCS12"
        ssl.setEnabled(true);
        ssl.setCertificatePrivateKey("/Users/surajitd/Project/MyWork/webhook-simulator/privatekey.pem");
        factory.setSsl(ssl);
        factory.setPort(443);
    };
}*/

@RestController
@RequestMapping("/webhook")
class WebhookController {

//    ContentRepository contentRepository = new ContentRepository();
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void handleWebhook(@RequestBody String payload) {
        System.out.println("Received webhook payload: " + payload);
        ContentRepository.addToRepoList(payload);
        // Do something with the payload, such as processing or logging it
    }

    @GetMapping("/All")
    @ResponseStatus(HttpStatus.OK)
    public String getAllWebhooks() {
        String responses = ContentRepository.getRepoList().toString();
        System.out.println("All Webhooks: " + responses);
        return responses;
        // Do something with the payload, such as processing or logging it
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public String getRecentWebhook() {
        List<String> responses = ContentRepository.getRepoList();
        if(responses==null || responses.size()==0){
            return getnoWebhook();
        } else {
            System.out.println("Last Webhook: " + responses.get(responses.size() - 1));
            return responses.get(responses.size() - 1);
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String getnoWebhook() {
        return "{}";
    }
}
   /* @Configuration
    @EnableWebSecurity
    public static class SecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .requiresChannel()
                    .anyRequest()
                    .requiresSecure();
        }
    }*/
}