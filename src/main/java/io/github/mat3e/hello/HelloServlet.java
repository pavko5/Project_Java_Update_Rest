package io.github.mat3e.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//konfiguracja Servletu, urlPatterns(wszystkie adresy url przekierowane do servletu)
@WebServlet(name = "Hello", urlPatterns = {"/api"})
public class HelloServlet extends HttpServlet {
    // zad , statyczne stawiamy wyrzej niż te związane z konkretną instancją
    private static final String NAME_PARAM = "name";
    private static final String LANG_PARAM = "lang";
    private final Logger logger = LoggerFactory.getLogger(HelloServlet.class);

    private HelloService service;

    /**
     * Servlet container needs it
     */
    @SuppressWarnings("unused")
    public HelloServlet() {     //konstruktor domyślny bezparametrowy
        this(new HelloService());
    }

    HelloServlet(HelloService service) {
        this.service = service;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Map<String,String[]> - wykorzystujemy do logowania
        logger.info("Got request with parameters" + req.getParameterMap());
        //req.getParameter(NAME_PARAM)- nie zwraca nulla, tylko mape, której nie można zmieniać - wynika z dokumentacji

        // wyniesienie parametrów do zmiennych
        var name = req.getParameter(NAME_PARAM);
        var lang = req.getParameter(LANG_PARAM);
        resp.getWriter().write(service.prepareGreeting(name, lang));
    }
}

