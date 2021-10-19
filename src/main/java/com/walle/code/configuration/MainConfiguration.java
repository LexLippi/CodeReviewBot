package com.walle.code.configuration;

import com.walle.code.adapter.input.*;
import com.walle.code.adapter.output.javax.persistence.*;
import com.walle.code.adapter.output.jda.JdaSendMessageByDiscordIdOutputPortAdapter;
import com.walle.code.adapter.output.row_mapper.*;
import com.walle.code.handler.*;
import com.walle.code.port.output.*;
import com.walle.code.router.DiscordMessageRouter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.support.TransactionOperations;

import javax.persistence.EntityManager;
import javax.security.auth.login.LoginException;

@Configuration
public class MainConfiguration {
    @Bean
    @Profile("discord")
    public JDA jdaListener(@Value("${discord.bot.token}") String token,
                          @Qualifier("discordMessageListenerAdapter") ListenerAdapter
                                  discordMessageListenerAdapter) throws LoginException {
        return JDABuilder.createDefault(token)
                .addEventListeners(discordMessageListenerAdapter)
                .build();
    }

    @Bean
    @Profile("discord")
    public JDA jdaSender(@Value("${discord.bot.token}") String token) throws LoginException {
        return JDABuilder.createDefault(token)
                .build();
    }

    @Bean
    @Profile("discord")
    public ListenerAdapter discordMessageListenerAdapter(RegisterStudentHandler registerStudentHandler,
                                                         RegisterReviewerHandler registerReviewerHandler,
                                                         ApproveReviewerHandler approveReviewerHandler,
                                                         ReviewCodeHandler reviewCodeHandler) {
        return new DiscordMessageRouter(registerStudentHandler, registerReviewerHandler,
                approveReviewerHandler, reviewCodeHandler);
    }

//    // @todo: add findReviewerOutputPort bean
//    @Bean
//    public CreateSessionHandler createSessionHandler(EntityManager entityManager,
//                                                     FindReviewerOutputPort findReviewerOutputPort,
//                                                     SendMessageByDiscordIdOutputPort sendMessageByDiscordIdOutputPort,
//                                                     TransactionOperations transactionOperations,
//                                                     FindUserByDiscordIdOutputPort findUserByDiscordIdOutputPort,
//                                                     FindStudentByUserIdOutputPort findStudentByUserIdOutputPort) {
//        return new CreateSessionHandler(new CreateSessionUseCaseAdapter(
//                findUserByDiscordIdOutputPort,
//                findStudentByUserIdOutputPort,
//                new JavaxPersistenceFindProgrammingLanguageByNameOutputPortAdapter(entityManager,
//                        ProgrammingLanguageRowMapper.INSTANCE),
//                new JavaxPersistenceFindAdjustmentSessionByStudentIdAndProgrammingLanguageIdOutputPortAdapter(
//                        entityManager, SessionRowMapper.INSTANCE),
//                findReviewerOutputPort,
//                new JavaxPersistenceFindReviewerByIdOutputPortAdapter(entityManager, ReviewerRowMapper.INSTANCE),
//                new JavaxPersistenceFindUserByIdOutputPortAdapter(entityManager, UserRowMapper.INSTANCE),
//                new JavaxPersistenceInsertSessionOutputPortAdapter(entityManager),
//                new JavaxPersistenceInsertTaskOutputPortAdapter(entityManager),
//                sendMessageByDiscordIdOutputPort,
//                transactionOperations));
//    }

    @Bean
    public ReviewCodeHandler reviewCodeHandler(FindUserByNicknameOutputPort findUserByNicknameOutputPort,
                                               FindStudentByUserIdOutputPort findStudentByUserIdOutputPort,
                                               EntityManager entityManager,
                                               TransactionOperations transactionOperations,
                                               SendMessageByDiscordIdOutputPort sendMessageByDiscordIdOutputPort
                                               ) {
        return new ReviewCodeHandler(new ReviewCodeUseCaseAdapter(findUserByNicknameOutputPort,
                findStudentByUserIdOutputPort,
                new JavaxPersistenceFindReviewerByDiscordIdOutputPortAdapter(entityManager, ReviewerRowMapper.INSTANCE),
                new JavaxPersistenceFindCreatedSessionByStudentIdAndReviewerIdOutputPortAdapter(entityManager,
                        SessionRowMapper.INSTANCE),
                new JavaxPersistenceUpdateCreatedTaskBySessionIdOutputPortAdapter(entityManager),
                new JavaxPersistenceFinishAdjustmentSessionOutputPortAdapter(entityManager),
                transactionOperations,
                sendMessageByDiscordIdOutputPort));
    }

    @Bean
    public FindUserByNicknameOutputPort findUserByNicknameOutputPort(EntityManager entityManager) {
        return new JavaxPersistenceFindUserByNicknameOutputPortAdapter(entityManager, UserRowMapper.INSTANCE);
    }

    @Bean
    public ApproveReviewerHandler approveReviewerHandler(EntityManager entityManager,
                                                         SendMessageByDiscordIdOutputPort
                                                                 sendMessageByDiscordIdOutputPort,
                                                         FindUserByNicknameOutputPort findUserByNicknameOutputPort,
                                                         TransactionOperations transactionOperations) {
        return new ApproveReviewerHandler(new ApproveReviewerUseCaseAdapter(
                new JavaxPersistenceFindAdminByDiscordUserIdOutputPortAdapter(entityManager, AdminRowMapper.INSTANCE),
                findUserByNicknameOutputPort,
                new JavaxPersistenceInsertReviewerOutputPortAdapter(entityManager),
                sendMessageByDiscordIdOutputPort,
                transactionOperations));
    }

    @Bean
    public RegisterReviewerHandler registerReviewerHandler(FindUserByDiscordIdOutputPort findUserByDiscordIdOutputPort,
                                                           EntityManager entityManager,
                                                           InsertUserOutputPort insertUserOutputPort,
                                                           SendMessageByDiscordIdOutputPort
                                                                       sendMessageByDiscordIdOutputPort,
                                                           TransactionOperations transactionOperations) {
        return new RegisterReviewerHandler(new RegisterReviewerUseCaseAdapter(findUserByDiscordIdOutputPort,
                new JavaxPersistenceFindReviewerByUserIdOutputPortAdapter(entityManager, ReviewerRowMapper.INSTANCE),
                insertUserOutputPort,
                sendMessageByDiscordIdOutputPort,
                new JavaxPersistenceFindAdminsOutputPortAdapter(entityManager, AdminRowMapper.INSTANCE),
                new JavaxPersistenceFindUserByIdInOutputPortAdapter(entityManager, UserRowMapper.INSTANCE),
                transactionOperations));
    }

    @Bean
    public RegisterStudentHandler registerStudentHandler(FindUserByDiscordIdOutputPort findUserByDiscordIdOutputPort,
                                                         FindStudentByUserIdOutputPort findStudentByUserIdOutputPort,
                                                         EntityManager entityManager,
                                                         TransactionOperations transactionOperations,
                                                         InsertUserOutputPort insertUserOutputPort) {
        return new RegisterStudentHandler(new RegisterStudentUseCaseAdapter(findUserByDiscordIdOutputPort,
                findStudentByUserIdOutputPort,
                new JavaxPersistenceInsertStudentOutputPortAdapter(entityManager),
                insertUserOutputPort,
                transactionOperations));
    }

    @Bean
    public SendMessageByDiscordIdOutputPort sendMessageByDiscordIdOutputPort(@Qualifier("jdaSender") JDA jda) {
        return new JdaSendMessageByDiscordIdOutputPortAdapter(jda);
    }

    @Bean
    public InsertUserOutputPort insertUserOutputPort(EntityManager entityManager) {
        return new JavaxPersistenceInsertUserOutputPortAdapter(entityManager);
    }

    @Bean
    public FindUserByDiscordIdOutputPort findUserByDiscordIdOutputPort(EntityManager entityManager) {
        return new JavaxPersistenceFindUserByDiscordIdOutputPortAdapter(entityManager, UserRowMapper.INSTANCE);
    }

    @Bean
    public FindStudentByUserIdOutputPort findStudentByUserIdOutputPort(EntityManager entityManager) {
        return new JavaxPersistenceFindStudentByUserIdOutputPortAdapter(entityManager, StudentRowMapper.INSTANCE);
    }
}
