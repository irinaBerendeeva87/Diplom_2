package org.example.user;
import java.util.Random;

abstract public class UserGenerator {
    private static final Random rnd = new Random();
    private static int getRnd() {
        return rnd.nextInt(100000);
    }

    public static User createDefaultUser() {
        return new User("mother" + getRnd() + "@yandex.ru", "daughter" + getRnd(), "Unreal" + getRnd());
    }

    public static User getWithPasswordOnly() {
        return new User(null, "next" + getRnd(), "Alex" + getRnd());
    }
    public static User getWithEmailOnly() {
        return new User("joey" + getRnd() + "@yandex.ru", null, "Alex" + getRnd());
    }
}