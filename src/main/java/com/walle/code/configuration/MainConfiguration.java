package com.walle.code.configuration;

import com.walle.code.listener.DiscordMessageListenerAdapter;
import com.walle.code.port.input.CreateSessionUseCase;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.security.auth.login.LoginException;

@Configuration
public class MainConfiguration {
    @Bean
    @Profile("discord")
    public JDA jdaBuilder(@Value("${discord.bot.token}") String token,
                          @Qualifier("discordMessageListenerAdapter") ListenerAdapter
                                  discordMessageListenerAdapter) throws LoginException {
        return JDABuilder.createDefault(token)
                .addEventListeners(discordMessageListenerAdapter)
                .build();
    }

    @Bean
    @Profile("discord")
    // @todo: add createSessionUseCase bean
    public ListenerAdapter discordMessageListenerAdapter(CreateSessionUseCase createSessionUseCase) {
        return new DiscordMessageListenerAdapter(createSessionUseCase);
    }

    @Bean
    @Profile("database")
    public EntityManagerFactory entityManagerFactory(@Value("${persistence.unit.name}") String persistenceUnitName) {
        return Persistence.createEntityManagerFactory(persistenceUnitName);
    }

    @Bean
    @Profile("database")
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }
}
