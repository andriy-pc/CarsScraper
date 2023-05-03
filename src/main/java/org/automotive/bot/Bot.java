package org.automotive.bot;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface Bot {

    Message sendMessage(String message, Long chatId);

}
