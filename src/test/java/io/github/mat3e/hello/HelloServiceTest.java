package io.github.mat3e.hello;

import static org.junit.Assert.assertEquals;

import io.github.mat3e.lang.LangRepository;
import io.github.mat3e.lang.Lang;
import org.junit.Test;

import java.util.Optional;

public class HelloServiceTest {
    private final static String WELCOME = "Hello";
    private final static String FALLBACK_ID_WELCOME = "Hola";

    @Test
    //null - to input, test_preparegreeting - to co testujemy, returnsGreeting.. -wynik
    public void test_prepareGreeting_nullName_returnsGreetingWithFallbackName() {
        //given
        var mockRepository = alwaysReturningHelloRepository();
        var SUT = new HelloService(mockRepository); //przekazanie mockRepository do HelloService - wszedzie gdzie uzyte Repository to bedzie uzyte mockRepository

        //when
        var result = SUT.prepareGreeting(null, "-1");

        //then
        assertEquals(WELCOME + "" + HelloService.FALLBACK_NAME + "!", result);
    }

    @Test
    public void test_prepareGreeting_name_returnsGreetingWithName() {
        //given
        var mockRepository = alwaysReturningHelloRepository();
        var SUT = new HelloService(mockRepository);
        String name = "test";

        // when
        var result = SUT.prepareGreeting(name, "-1");

        //then
        assertEquals(WELCOME + "" + name + "!", result);
    }

    @Test
    public void test_prepareGreeting_nullLang_returnsGreetingWithFallbackIdLang() {
        //given
        var mockRepository = fallbackLangIdRepository();
        var SUT = new HelloService(mockRepository);

        // when
        var result = SUT.prepareGreeting(null, null);

        //then
        assertEquals(FALLBACK_ID_WELCOME + "" + HelloService.FALLBACK_NAME + "!", result);
    }

    @Test
    public void test_prepareGreeting_textLang_returnsGreetingWithFallbackIdLang() {
        //given
        var mockRepository = fallbackLangIdRepository();
        var SUT = new HelloService(mockRepository);
        String name = "test";

        // when
        var result = SUT.prepareGreeting(null, "abc");

        //then
        assertEquals(FALLBACK_ID_WELCOME + "" + HelloService.FALLBACK_NAME + "!", result);
    }

    // test - zwraca pustego Optional. W assert uzyte HelloService.FALLBACK_LANG.getWelcomeMsg()
    @Test
    public void test_prepareGreeting_nonExistingLang_returnsGreetingWithFallbackLang() {
        //given
        var mockRepository = new LangRepository() {
            @Override
            public Optional<Lang> findById(Integer id) {
                return Optional.empty();
            }
        };
        var SUT = new HelloService(mockRepository);
        String name = "test";

        // when
        var result = SUT.prepareGreeting(null, "-1"); // -1 (zeby nie dostawać fallbackid - jakby był null zostalo by przkezane fallbackid)

        //then
        assertEquals(HelloService.FALLBACK_LANG.getWelcomeMsg() + "" + HelloService.FALLBACK_NAME + "!", result);
    }

    private LangRepository fallbackLangIdRepository() {
        return new LangRepository() {
            @Override
            public Optional<Lang> findById(Integer id) {
                if (id.equals(HelloService.FALLBACK_LANG.getId())) {
                    return Optional.of(new Lang(null, FALLBACK_ID_WELCOME, null));
                }
                return Optional.empty();
            }
        };
    }

    private LangRepository alwaysReturningHelloRepository() {
        return new LangRepository() {
            @Override
            public Optional<Lang> findById(Integer id) {
                return Optional.of(new Lang(null, WELCOME, null));
            }
        };
    }

}
