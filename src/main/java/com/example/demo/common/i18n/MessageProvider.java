package com.example.demo.common.i18n;

import lombok.NonNull;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.Nullable;

import java.util.Locale;

public class MessageProvider {

    private static final String MSG_CODE_NOT_FOUND = "message.code-not-found";

    private final MessageSource messageSource;

    public MessageProvider(@NonNull MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getLocalizedMessage(@NonNull String codeOrMessage, @Nullable Object... args) {
        //noinspection ConstantConditions
        return messageSource.getMessage(
                codeOrMessage,
                args,
                String.format(codeOrMessage, args),
                LocaleContextHolder.getLocale()
        );
    }

    public String getLocalizedMessage(@NonNull MessageSourceResolvable resolvable) {
        try {
            return messageSource.getMessage(resolvable, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            return getLocalizedMessage(MSG_CODE_NOT_FOUND);
        }
    }

    public String getMessage(@NonNull String codeOrMessage, @Nullable Object... args) {
        //noinspection ConstantConditions
        return messageSource.getMessage(
                codeOrMessage,
                args,
                String.format(codeOrMessage, args),
                Locale.getDefault()
        );
    }

    public String getMessage(@NonNull MessageSourceResolvable resolvable) {
        try {
            return messageSource.getMessage(resolvable, Locale.getDefault());
        } catch (NoSuchMessageException e) {
            return getMessage(MSG_CODE_NOT_FOUND);
        }
    }
}
