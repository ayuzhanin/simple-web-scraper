package com.hireright.job.juniorcandidate.tools;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 *  ласс, предназначенный дл€ ¬еб-скрапинга. —воеобразный контейнер. ¬се написанные классы так или иначе содержатс€ в нем.
 *
 * @author Artur Yuzhanin.
 */
public class SimpleWebScraper {

    /**
     * —писок держателей пар URL и текстов, полученных по этим URL.
     */
    private List<TextURLHolder> holders = null;
    /**
     * ѕарсер, занимающийс€ разбором ввода пользовател€.
     */
    private Parser parser = null;
    /**
     * ќбработчик текстов, которые запрашивает пользователь по URL.
     */
    private TextProcessor processor = null;

    /**
     *  онструктор, ничего особенного.
     */
    public SimpleWebScraper() {
        holders = new ArrayList<>();
        parser = new Parser();
        processor = new TextProcessor();
    }

    /**
     * ‘ункци€ дл€ запуска скрапера.
     * «апускаем парсер. «атем получаем все держатели пар URL и текстов.
     * «апускаем каждый держатель и получаем тексты по URL, которые храним в держател€х.
     *
     * @param args аргументы командной строки.
     * @throws IOException в случае возникновени€ ошибок.
     */
    public void process(String... args) throws IOException {
        parser.parse(args);
        holders.addAll(parser.getURLs().stream().map(TextURLHolder::new).collect(Collectors.toList()));

        for (TextURLHolder holder : holders) {
            holder.retrieveCleanTextFromURL();
        }

        processor.run(holders, parser.getWords());
    }

}
