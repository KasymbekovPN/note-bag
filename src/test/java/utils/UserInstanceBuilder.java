package utils;

import org.telegram.telegrambots.meta.api.objects.User;

public class UserInstanceBuilder {

    private Long id;

    public static UserInstanceBuilder builder(){
        return new UserInstanceBuilder();
    }

    public UserInstanceBuilder id(Long id){
        this.id = id;
        return this;
    }

    public User build(){
        User user = new User();
        setIdIntoUser(user);

        return user;
    }

    private void setIdIntoUser(User user) {
        if (id != null){
            user.setId(id);
        }
    }
}
