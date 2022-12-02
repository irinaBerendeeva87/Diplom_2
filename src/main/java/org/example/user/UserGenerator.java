package org.example.user;

import java.util.Random;

public class UserGenerator {
    private static final Random rnd = new Random();
    private static int getRnd(){
        return rnd.nextInt(1000);
    }

    public static final User defaultUser =  new User("joey"+getRnd()+"@yandex.ru", "next"+getRnd(), "Alex"+getRnd());

    public static User getWithPasswordOnly (){
        return new User(null,"next"+getRnd(),"Alex"+getRnd());
    }

    public static User getWithLoginOnly (){
        return new User("joey"+getRnd()+"@yandex.ru", null, "Alex"+getRnd());
    }
}
