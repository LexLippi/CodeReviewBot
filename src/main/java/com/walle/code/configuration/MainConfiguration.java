package com.walle.code.configuration;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.security.auth.login.LoginException;

@Configuration
public class MainConfiguration {
    @Bean
    @Profile("discord")
    public JDA jdaBuilder(@Value("${discord.bot.token}") String token) throws LoginException {
        return JDABuilder.createDefault(token)
                .build();
    }
}
