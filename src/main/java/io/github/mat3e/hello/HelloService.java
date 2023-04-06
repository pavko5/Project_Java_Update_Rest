package io.github.mat3e.hello;

import io.github.mat3e.lang.LangRepository;
import io.github.mat3e.lang.Lang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

class HelloService {
    static final String FALLBACK_NAME = "world";
    static final Lang FALLBACK_LANG = new Lang(1, "Hello", "en");
    private final Logger logger = LoggerFactory.getLogger(HelloService.class);

    private LangRepository repository;

    //konstruktor domyslny wola jeszcze nie istniejacy LangRepository
    public HelloService(LangRepository repository) {
        this.repository = repository;
    }

    public HelloService() {
        this(new LangRepository());
    }

    String prepareGreeting(String name, String lang) {
        Integer langId;
        try {
            langId = Optional.ofNullable(lang).map(Integer::valueOf).orElse(FALLBACK_LANG.getId());
        } catch (NumberFormatException e) {
            logger.warn("Non-numeric lang id used: " + lang);
            langId = FALLBACK_LANG.getId();
        }

        var welcomeMsg = repository.findById(langId).orElse(FALLBACK_LANG).getWelcomeMsg();
        var nameToWelcome = Optional.ofNullable(name).orElse(FALLBACK_NAME);
        return welcomeMsg + "" + nameToWelcome + "!";
    }
}
