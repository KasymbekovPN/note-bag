package utils;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

public class UpdateInstanceBuilder {

    private Long chatId;
    private String text;
    private User from;

    public UpdateInstanceBuilder chatId(Long chatId){
        this.chatId = chatId;
        return this;
    }

    public UpdateInstanceBuilder text(String text){
        this.text = text;
        return this;
    }

    public UpdateInstanceBuilder from(User from){
        this.from = from;
        return this;
    }

    public Update build(){
        Message message = new Message();
        message.setChat(createChat(chatId));
        message.setFrom(checkOrCreateFrom(this.from));
        message.setText(checkOrCreateText(this.text));

        Update update = new Update();
        update.setMessage(message);

        return update;
    }

    private String checkOrCreateText(String text) {
        return text == null ? "" : text;
    }

    private User checkOrCreateFrom(User from) {
        return from == null ? UserInstanceBuilder.builder().build() : from;
    }

    private Chat createChat(Long chatId) {
        Chat chat = new Chat();
        chat.setId(chatId == null ? 1L : chatId);
        return chat;
    }
}
