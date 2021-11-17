package com.walle.code.handler.discord;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Компонент для обработки команды !help.
 *
 * @author <a href="mailto:aleksey.bykov.01@mail.ru">Алексей Быков</a>.
 * @since 21.1.0
 */
@RequiredArgsConstructor
public final class GetHelperHandler {
    private static final String SUBMIT_PREFIX = "!submit [url_link]";
    private static final String SUBMIT_PREFIX_DESC = "Позволяет сдать задачу, создаётся сеанс код-ревью.";

    private static final String REGISTER_COMMAND = "!register";
    private static final String REGISTER_COMMAND_DESC = "Регистрация пользователя.";

    private static final String REGISTER_REVIEWER_COMMAND = "!reviewer";
    private static final String REGISTER_REVIEWER_COMMAND_DESC = "Регистрация как ревьюера. Требует подтверждения.";

    private static final String APPROVE_COMMAND_PREFIX = "!approve [nickname] [programmingLanguage1 ... ]";
    private static final String APPROVE_COMMAND_PREFIX_DESC = "Команда для подтверждения на роль ревьюера.";

    private static final String CODE_REVIEW_PREFIX = "!code_review [nickname] [status] [review]";
    private static final String CODE_REVIEW_PREFIX_DESC = "Отправка код-ревью.";

    private static final String ADD_PROGRAMMING_LANGUAGE_PREFIX = "!add_programming_language [nickname] [programmingLanguage]";
    private static final String ADD_PROGRAMMING_LANGUAGE_PREFIX_DESC = "Добавление ЯП у ревьюера. Требует подтверждения.";

    private static final String APPROVE_REVIEWER_PROGRAMMING_LANGUAGE = "!approve_reviewer_programming_language [nickname] [programmingLanguage]";
    private static final String APPROVE_REVIEWER_PROGRAMMING_LANGUAGE_DESC = "Подтверждение добавления ЯП у ревьюера.";

    private static final String DELETE_PROGRAMMING_LANGUAGE_PREFIX = "!delete_programming_language [programmingLanguage]";
    private static final String DELETE_PROGRAMMING_LANGUAGE_PREFIX_DESC = "Удаление ЯП из списка проверяемых.";

    private static final String ADD_EMAIL_PREFIX = "!add_email [e-mail]";
    private static final String ADD_EMAIL_PREFIX_DESC = "Добавляет почту для рассылки.";

    private static final String ADD_TELEGRAM_PREFIX = "!add_telegram [telegram]";
    private static final String ADD_TELEGRAM_PREFIX_DESC = "Добавляет telegram для рассылки.";

    private static final String REGISTER_ADMIN_COMMAND = "!admin";
    private static final String REGISTER_ADMIN_COMMAND_DESC = "Регистрация как администратора. Требует подтверждения.";

    private static final String APPROVE_ADMIN_REGISTER_COMMAND = "!approve_admin [nickname]";
    private static final String APPROVE_ADMIN_REGISTER_COMMAND_DESC = "Подтверждает пользователя на роль администратора.";

    private static final String HELP_PREFIX = "!help";
    private static final String HELP_PREFIX_DESC = "Вывод данной прекрасной справки ^__^";

    private static final String LINE_BREAK = "\n";
    private static final String STRING_SEPARATOR = " : ";


    /*
    * Данная жесть временно
    * После перенесётся в отдельный файл
    */

    @NonNull
    public String handle(@NonNull MessageReceivedEvent event) {
        return SUBMIT_PREFIX + STRING_SEPARATOR + SUBMIT_PREFIX_DESC + LINE_BREAK +
                REGISTER_COMMAND + STRING_SEPARATOR + REGISTER_COMMAND_DESC + LINE_BREAK +
                REGISTER_REVIEWER_COMMAND + STRING_SEPARATOR + REGISTER_REVIEWER_COMMAND_DESC + LINE_BREAK +
                APPROVE_COMMAND_PREFIX + STRING_SEPARATOR + APPROVE_COMMAND_PREFIX_DESC + LINE_BREAK +
                CODE_REVIEW_PREFIX + STRING_SEPARATOR + CODE_REVIEW_PREFIX_DESC + LINE_BREAK +
                ADD_PROGRAMMING_LANGUAGE_PREFIX + STRING_SEPARATOR +
                ADD_PROGRAMMING_LANGUAGE_PREFIX_DESC + LINE_BREAK +
                APPROVE_REVIEWER_PROGRAMMING_LANGUAGE + STRING_SEPARATOR +
                APPROVE_REVIEWER_PROGRAMMING_LANGUAGE_DESC + LINE_BREAK +
                DELETE_PROGRAMMING_LANGUAGE_PREFIX + STRING_SEPARATOR +
                DELETE_PROGRAMMING_LANGUAGE_PREFIX_DESC + LINE_BREAK +
                ADD_EMAIL_PREFIX + STRING_SEPARATOR + ADD_EMAIL_PREFIX_DESC + LINE_BREAK +
                ADD_TELEGRAM_PREFIX + STRING_SEPARATOR + ADD_TELEGRAM_PREFIX_DESC + LINE_BREAK +
                REGISTER_ADMIN_COMMAND + STRING_SEPARATOR + REGISTER_ADMIN_COMMAND_DESC + LINE_BREAK +
                APPROVE_ADMIN_REGISTER_COMMAND + STRING_SEPARATOR + APPROVE_ADMIN_REGISTER_COMMAND_DESC + LINE_BREAK +
                HELP_PREFIX + STRING_SEPARATOR + HELP_PREFIX_DESC;
    }
}
