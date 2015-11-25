package com.hireright.job.juniorcandidate.tools;

import com.hireright.job.juniorcandidate.exception.ParsingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Класс, предназаначенный для хранения URL ссылки и текста, содержащегося по этой ссылке.
 *
 * @author Artur Yuzhanin.
 */
public class TextURLHolder {
    /**
     * Текст, полученный по ссылке.
     */
    private Text text = null;
    /**
     * СсылкаЮ необходимая для получения текста.
     */
    private URL url = null;

    /**
     * Конструктор.
     *
     * @param url ссылка на страницу в сети.
     */
    public TextURLHolder(URL url) {
        this.url = url;
    }

    /**
     * Функция для получаения чистого текста из веб-страницы.
     *
     * @throws ParsingException, если невозможно получить данные со страницы.
     */
    public void retrieveCleanTextFromURL() throws ParsingException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String inputLine;
            StringBuilder builder = new StringBuilder();
            while ((inputLine = reader.readLine()) != null) {
                builder.append(inputLine).append("\n");
            }
            reader.close();
            text = new Text(builder.toString());
            text.clean();
        } catch (IOException exception) {
            throw new ParsingException("I/O exception while retrieving data from URL " + url.toString() + " has occurred\n");
        }
    }

    public Text getText() {
        return text;
    }

    public URL getUrl() {
        return url;
    }
}
