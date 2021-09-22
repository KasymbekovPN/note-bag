package ru.kpn.subscriptionManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

// TODO: 22.09.2021 add tests
public class BotSubscriptionManagerTest {

    private BotSubscriptionManager subscriptionManager;

    @BeforeEach
    void setUp() {
        subscriptionManager = new BotSubscriptionManager();
    }

    @Test
    void name() {

    }
}
