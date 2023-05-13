package de.hsrm.mi.web.projekt.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;


@Configuration
public class InternationalConfiguration implements WebMvcConfigurer{
    //findet es nicht in der application.properties
    //überschreibt die automatisch konfigurierte MessageSource-Bean von Spring Boot
    //Der Basenamen der Eigenschaften-Datei wird direkt in der messageSource-Bean angegeben, und spring.messages.basename wird ignoriert
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages"); //hier wird der basename für die Eigenschaften-Datei gesetzt
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }


    //Session-spezifische Spracheinstellungen (Benutzersitzung (Session))
    @Bean
    public SessionLocaleResolver localeResolver() {
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        // Optional: Browser-Präferenz übersteuern
        //resolver.setDefaultLocale(Locale.GERMANY);
        return resolver;
        }

    //gewünschten Request-Parameter für Sprachumschaltung überwacht
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("sprache");
        return interceptor;
    }

    //um Anfragen abzufangen und zu bearbeiten //Konkret fügt er den LocaleChangeInterceptor hinzu, der dafür zuständig ist, die angeforderte Sprache in einer Anfrage zu erkennen und die entsprechende Locale-Instanz zu setzen
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

}
