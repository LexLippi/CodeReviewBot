package com.walle.code.configuration;

import com.walle.code.adapter.input.*;
import com.walle.code.adapter.output.javax.mail.JavaxMailIsEmailCorrectOutputPort;
import com.walle.code.adapter.output.javax.persistence.*;
import com.walle.code.adapter.output.jda.JdaSendMessageByDiscordIdOutputPortAdapter;
import com.walle.code.adapter.output.row_mapper.*;
import com.walle.code.adapter.output.row_wrapper.ReviewerRowTasksWrapper;
import com.walle.code.adapter.output.telegram.TelegramSendMessageOutputPortAdapter;
import com.walle.code.comparators.ReviewerRowWrapValueIncreaseComparator;
import com.walle.code.handler.discord.*;
import com.walle.code.handler.telegram.AddTelegramChatIdToUserHandler;
import com.walle.code.listener.DiscordMessageListener;
import com.walle.code.listener.TelegramNotificationBot;
import com.walle.code.port.output.*;
import com.walle.code.router.EventRouterAdapter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.support.TransactionOperations;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.persistence.EntityManager;
import javax.security.auth.login.LoginException;

@Configuration
public class MainConfiguration {
//    @Bean
//    @Profile("telegram")
//    public TelegramBotsApi telegramBotsApi(TelegramNotificationBot telegramNotificationBot) throws TelegramApiException {
//        var telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
//        telegramBotsApi.registerBot(telegramNotificationBot);
//        return telegramBotsApi;
//    }
//
//    @Bean
//    @Profile("telegram")
//    public TelegramNotificationBot telegramBot(@Value("${telegram.bot.token}") String botToken,
//                                               @Value("${telegram.bot.name}") String botName,
//                                               AddTelegramChatIdToUserHandler addTelegramChatIdToUserHandler) {
//        return new TelegramNotificationBot(botToken, botName, addTelegramChatIdToUserHandler);
//    }

//    @Bean
//    public AddTelegramChatIdToUserHandler addTelegramChatIdToUserHandler(EntityManager entityManager) {
//        return new AddTelegramChatIdToUserHandler("start", "Начать",
//                new AddTelegramChatIdToUserUseCaseAdapter(
//                        new JavaxPersistenceFindUserIdByTelegramNicknameOutputPortAdapter(entityManager),
//                        new JavaxPersistenceUpdateUserChatIdByIdOutputPortAdapter(entityManager)));
//    }

//    @Bean
//    public SendMessageOutputPort telegramSendMessageOutputPort(AbsSender absSender) {
//        return new TelegramSendMessageOutputPortAdapter(absSender);
//    }

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
    public ListenerAdapter discordMessageListenerAdapter(BeanFactory beanFactory) {
        return new DiscordMessageListener(new EventRouterAdapter(beanFactory.getBean(CreateSessionHandler.class),
                beanFactory.getBean(RegisterStudentHandler.class),
                beanFactory.getBean(RegisterReviewerHandler.class),
                beanFactory.getBean(ApproveReviewerHandler.class),
                beanFactory.getBean(ReviewCodeHandler.class),
                beanFactory.getBean(AddReviewerProgrammingLanguageHandler.class),
                beanFactory.getBean(DeleteProgrammingLanguageHandler.class),
                beanFactory.getBean(ApproveReviewerProgrammingLanguageHandler.class),
                beanFactory.getBean(AddEmailToUserHandler.class),
                beanFactory.getBean(CreateRequestToAddTelegramHandler.class),
                beanFactory.getBean(RegisterAdminHandler.class),
                beanFactory.getBean(ApproveAdminHandler.class)));
    }

    @Bean
    public ApproveAdminHandler approveAdminHandler(EntityManager entityManager,
                                                   FindAdminByDiscordUserIdOutputPort
                                                           findAdminByDiscordUserIdOutputPort,
                                                   SendMessageByDiscordIdOutputPort
                                                               sendMessageByDiscordIdOutputPort,
                                                   FindUserByNicknameOutputPort findUserByNicknameOutputPort,
                                                   TransactionOperations transactionOperations) {
        return new ApproveAdminHandler(new ApproveAdminUseCaseAdapter(findAdminByDiscordUserIdOutputPort,
                findUserByNicknameOutputPort,
                new JavaxPersistenceInsertAdminOutputPortAdapter(entityManager),
                sendMessageByDiscordIdOutputPort,
                transactionOperations));
    }

    @Bean
    public RegisterAdminHandler registerAdminHandler(FindUserByDiscordIdOutputPort findUserByDiscordIdOutputPort,
                                                     FindAdminByDiscordUserIdOutputPort
                                                             findAdminByDiscordUserIdOutputPort,
                                                     InsertUserOutputPort insertUserOutputPort,
                                                     SendMessageByDiscordIdOutputPort
                                                                 sendMessageByDiscordIdOutputPort,
                                                     TransactionOperations transactionOperations,
                                                     FindAdminsOutputPort findAdminsOutputPort,
                                                     FindUserByIdInOutputPort findUserByIdInOutputPort) {
        return new RegisterAdminHandler(new RegisterAdminUseCaseAdapter(findUserByDiscordIdOutputPort,
                findAdminByDiscordUserIdOutputPort, insertUserOutputPort, sendMessageByDiscordIdOutputPort,
                findAdminsOutputPort, findUserByIdInOutputPort, transactionOperations));
    }

    @Bean
    public CreateRequestToAddTelegramHandler createRequestToAddTelegramHandler(
            FindUserByDiscordIdOutputPort findUserByDiscordIdOutputPort,
            TransactionOperations transactionOperations,
            EntityManager entityManager) {
        return new CreateRequestToAddTelegramHandler(new CreateRequestToAddTelegramUseCaseAdapter(
            findUserByDiscordIdOutputPort, transactionOperations,
            new JavaxPersistenceInsertTelegramRequestOutputPortAdapter(entityManager)));
    }

    @Bean
    public AddEmailToUserHandler addEmailToUserHandler(EntityManager entityManager,
                                                       TransactionOperations transactionOperations) {
        return new AddEmailToUserHandler(new AddEmailToUserUseCaseAdapter(JavaxMailIsEmailCorrectOutputPort.INSTANCE,
                new JavaxPersistenceUpdateUserEmailByDiscordIdOutputPortAdapter(entityManager),
                transactionOperations));
    }

    @Bean
    public ApproveReviewerProgrammingLanguageHandler approveReviewerProgrammingLanguageHandler(
            FindAdminByDiscordUserIdOutputPort findAdminByDiscordUserIdOutputPort,
            FindUserByNicknameOutputPort findUserByNicknameOutputPort,
            FindReviewerByUserIdOutputPort findReviewerByUserIdOutputPort,
            FindProgrammingLanguageByAliasOutputPort findProgrammingLanguageByAliasOutputPort,
            InsertReviewerProgrammingLanguageOutputPort insertReviewerProgrammingLanguageOutputPort,
            TransactionOperations transactionOperations,
            SendMessageByDiscordIdOutputPort sendMessageByDiscordIdOutputPort
            ) {
        return new ApproveReviewerProgrammingLanguageHandler(new ApproveReviewerProgrammingLanguageUseCaseAdapter(
                findAdminByDiscordUserIdOutputPort,
                findUserByNicknameOutputPort,
                findReviewerByUserIdOutputPort,
                findProgrammingLanguageByAliasOutputPort,
                insertReviewerProgrammingLanguageOutputPort,
                transactionOperations,
                sendMessageByDiscordIdOutputPort));
    }

    @Bean
    public FindAdminByDiscordUserIdOutputPort findAdminByDiscordUserIdOutputPort(EntityManager entityManager) {
        return new JavaxPersistenceFindAdminByDiscordUserIdOutputPortAdapter(entityManager, AdminRowMapper.INSTANCE);
    }

    @Bean
    public DeleteProgrammingLanguageHandler deleteProgrammingLanguageHandler(
            FindReviewerByDiscordIdOutputPort findReviewerByDiscordIdOutputPort,
            FindProgrammingLanguageByAliasOutputPort findProgrammingLanguageByAliasOutputPort,
            TransactionOperations transactionOperations,
            EntityManager entityManager) {
        return new DeleteProgrammingLanguageHandler(new DeleteProgrammingLanguageUseCaseAdapter(
                findReviewerByDiscordIdOutputPort,
                findProgrammingLanguageByAliasOutputPort,
                transactionOperations,
                new JavaxPersistenceDeleteReviewerProgrammingLanguageOutputPortAdapter(entityManager)));
    }

    @Bean
    public AddReviewerProgrammingLanguageHandler addReviewerProgrammingLanguageHandler(
            FindReviewerByDiscordIdOutputPort findReviewerByDiscordIdOutputPort,
            EntityManager entityManager,
            SendMessageByDiscordIdOutputPort sendMessageByDiscordIdOutputPort,
            FindAdminsOutputPort findAdminsOutputPort,
            FindUserByIdInOutputPort findUserByIdInOutputPort) {
        return new AddReviewerProgrammingLanguageHandler(new AddReviewerProgrammingLanguageUseCaseAdapter(
                findReviewerByDiscordIdOutputPort,
                new JavaxPersistenceReviewerProgrammingLanguageExistOutputPortAdapter(entityManager),
                sendMessageByDiscordIdOutputPort,
                findAdminsOutputPort,
                findUserByIdInOutputPort
                ));
    }

    @Bean
    public FindReviewerByDiscordIdOutputPort findReviewerByDiscordIdOutputPort(EntityManager entityManager) {
        return new JavaxPersistenceFindReviewerByDiscordIdOutputPortAdapter(entityManager, ReviewerRowMapper.INSTANCE);
    }

    @Bean
    public InsertReviewerProgrammingLanguageOutputPort insertReviewerProgrammingLanguageOutputPort(
            EntityManager entityManager) {
        return new JavaxPersistenceInsertReviewerProgrammingLanguageOutputPortAdapter(entityManager);
    }

    @Bean
    public CreateSessionHandler createSessionHandler(EntityManager entityManager,
                                                     SendMessageByDiscordIdOutputPort sendMessageByDiscordIdOutputPort,
                                                     TransactionOperations transactionOperations,
                                                     FindProgrammingLanguageByAliasOutputPort
																 findProgrammingLanguageByAliasOutputPort,
                                                     FindUserByDiscordIdOutputPort findUserByDiscordIdOutputPort,
                                                     FindStudentByUserIdOutputPort findStudentByUserIdOutputPort) {
        return new CreateSessionHandler(new CreateSessionUseCaseAdapter(
                findUserByDiscordIdOutputPort,
                findStudentByUserIdOutputPort,
				findProgrammingLanguageByAliasOutputPort,
                new JavaxPersistenceFindAdjustmentSessionByURLLinkOutputPortAdapter(
                        entityManager, SessionRowMapper.INSTANCE),
                new JavaxPersistenceFindReviewerByProgrammingLanguageOutputPortAdapter(entityManager,
                        ReviewerRowWrapValueIncreaseComparator.INSTANCE,
                        ReviewerRowTasksWrapper.INSTANCE,
                        ReviewerRowMapper.INSTANCE),
                new JavaxPersistenceFindReviewerByIdOutputPortAdapter(entityManager, ReviewerRowMapper.INSTANCE),
                new JavaxPersistenceFindUserByIdOutputPortAdapter(entityManager, UserRowMapper.INSTANCE),
                new JavaxPersistenceInsertSessionOutputPortAdapter(entityManager),
                new JavaxPersistenceInsertTaskOutputPortAdapter(entityManager),
                sendMessageByDiscordIdOutputPort,
                transactionOperations));
    }

    @Bean
    public ReviewCodeHandler reviewCodeHandler(FindUserByNicknameOutputPort findUserByNicknameOutputPort,
                                               FindStudentByUserIdOutputPort findStudentByUserIdOutputPort,
                                               EntityManager entityManager,
                                               FindReviewerByDiscordIdOutputPort findReviewerByDiscordIdOutputPort,
                                               TransactionOperations transactionOperations,
                                               SendMessageByDiscordIdOutputPort sendMessageByDiscordIdOutputPort
                                               ) {
        return new ReviewCodeHandler(new ReviewCodeUseCaseAdapter(findUserByNicknameOutputPort,
                findStudentByUserIdOutputPort,
                findReviewerByDiscordIdOutputPort,
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
                                                         FindAdminByDiscordUserIdOutputPort
                                                                 findAdminByDiscordUserIdOutputPort,
                                                         SendMessageByDiscordIdOutputPort
                                                                 sendMessageByDiscordIdOutputPort,
                                                         FindUserByNicknameOutputPort findUserByNicknameOutputPort,
                                                         FindProgrammingLanguageByAliasOutputPort
																	 findProgrammingLanguageByAliasOutputPort,
                                                         TransactionOperations transactionOperations,
                                                         InsertReviewerProgrammingLanguageOutputPort
                                                                     insertReviewerProgrammingLanguageOutputPort) {
        return new ApproveReviewerHandler(new ApproveReviewerUseCaseAdapter(
                findAdminByDiscordUserIdOutputPort,
                findUserByNicknameOutputPort,
                new JavaxPersistenceInsertReviewerOutputPortAdapter(entityManager),
                insertReviewerProgrammingLanguageOutputPort,
				findProgrammingLanguageByAliasOutputPort,
                sendMessageByDiscordIdOutputPort,
                transactionOperations));
    }

    @Bean
    public RegisterReviewerHandler registerReviewerHandler(FindUserByDiscordIdOutputPort findUserByDiscordIdOutputPort,
                                                           InsertUserOutputPort insertUserOutputPort,
                                                           SendMessageByDiscordIdOutputPort
                                                                       sendMessageByDiscordIdOutputPort,
                                                           TransactionOperations transactionOperations,
                                                           FindReviewerByUserIdOutputPort
                                                                       findReviewerByUserIdOutputPort,
                                                           FindAdminsOutputPort findAdminsOutputPort,
                                                           FindUserByIdInOutputPort findUserByIdInOutputPort) {
        return new RegisterReviewerHandler(new RegisterReviewerUseCaseAdapter(findUserByDiscordIdOutputPort,
                findReviewerByUserIdOutputPort,
                insertUserOutputPort,
                sendMessageByDiscordIdOutputPort,
                findAdminsOutputPort,
                findUserByIdInOutputPort,
                transactionOperations));
    }

    @Bean
    public FindUserByIdInOutputPort findUserByIdInOutputPort(EntityManager entityManager) {
        return new JavaxPersistenceFindUserByIdInOutputPortAdapter(entityManager, UserRowMapper.INSTANCE);
    }

    @Bean
    public FindAdminsOutputPort findAdminsOutputPort(EntityManager entityManager) {
        return new JavaxPersistenceFindAdminsOutputPortAdapter(entityManager, AdminRowMapper.INSTANCE);
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
    public FindReviewerByUserIdOutputPort findReviewerByUserIdOutputPort(EntityManager entityManager) {
        return new JavaxPersistenceFindReviewerByUserIdOutputPortAdapter(entityManager, ReviewerRowMapper.INSTANCE);
    }

    @Bean
    public FindProgrammingLanguageByAliasOutputPort findProgrammingLanguageByNameOutputPort(EntityManager
                                                                                                       entityManager) {
        return new JavaxPersistenceFindProgrammingLanguageByAliasOutputPortAdapter(entityManager,
                ProgrammingLanguageRowMapper.INSTANCE);
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
