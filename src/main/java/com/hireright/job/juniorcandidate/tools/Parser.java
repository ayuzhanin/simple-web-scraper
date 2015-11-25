package com.hireright.job.juniorcandidate.tools;

import com.hireright.job.juniorcandidate.exception.ParsingException;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Класс Parser, предназначенный для парсинга аргументов командной строки.
 * Т.е. для получаения URL, откуда брать страничку(-и), вычленения слов, по которым будет производиться поиск в тексте страницы,
 * а также флагов, с которыми запускается приложение.
 * @author Artur Yuzhanin
 */
public class Parser {

    /**
     * URL(s): одиночный URL или файл, содержащий список URL.
     */
    private String pathToURL;
    /**
     * Нераспарсенные флаги, записанные в строке.
     */
    private String stringOfFlags;
    /**
     * Список слов, по которым будет производиться поиск в тексте.
     */
    private List<String> words;
    /**
     * Список URL, откуда тексты будут взяты (один или несколько).
     */
    private List<URL> URLs;

    /**
     * Здесь будем хранить флаги. Используем Map, чтобы можно было легко отмечать, указанные пользователем флаги.
     */
    public final static HashMap<Flag, Boolean> FLAGS = new HashMap<>();

    /**
     * Конструктор. Ничего особенного.
     */
    public Parser() {
        words = new ArrayList<>();
        URLs = new ArrayList<>();
    }

    /**
     * В этой секции записываем в FLAGS все легальные флаги. Ставим их как неотмеченные (false).
     */
    static {
        for (char flag : Flag.LEGAL_FLAGS) {
            FLAGS.put(new Flag(flag), false);
        }
    }

    /**
     * Получаем список URL.
     *
     * @return URLs.
     */
    public List<URL> getURLs() {
        return URLs;
    }

    /**
     * Аналогично получаем список слов для поиска.
     *
     * @return words.
     */
    public List<String> getWords() {
        return words;
    }

    /**
     * Функия для записи аргументов командной строки в List words.
     * Заменяем запятые на разделитель. И делим полученную строку на подстроки по данному разделителю.
     *
     * @param args аргументы командной строки.
     */
    private void putArgsToWords(String... args) {
        StringBuilder inputBuilder = new StringBuilder();
        char delimeter = '|';
        for (String arg : args) {
            inputBuilder.append(arg).append(" ");
        }

        String string = inputBuilder.toString().replaceAll("\\s*,\\s*", String.valueOf(delimeter));
        StringTokenizer tokenizer = new StringTokenizer(string, String.valueOf(delimeter));

        while (tokenizer.hasMoreElements()) {
            words.add((String) tokenizer.nextElement());
        }
    }

    /**
     * Функция для отделения последнего слова от флагов.
     * С началом работы приложения последнее слово (если слово составное(состоящиее из двух и более слов),
     * например St. Petersburg, - 2 слова, то нескоклько последних неотделимых слов), хранится вместе со строкой с флагами.
     * Их нужно разделить друг от друга.
     */
    private void splitLastWordsAndFlags() {
        String[] parts = words.get(words.size() - 1).split(" ");
        words.remove(words.size() - 1);
        StringBuilder flagsBuilder = new StringBuilder();
        int counter = 0;
        for (int index = parts.length - 1; index >= 0; index--) {
            String trimmed = parts[index].trim();
            if (trimmed.length() == 2 && isLegalFlag(trimmed.charAt(1))) {
                flagsBuilder.append(trimmed);
                counter++;
            } else {
                break;
            }
        }
        StringBuilder lastWords = new StringBuilder();
        for (int index = 0; index < parts.length - counter; index++) {
            lastWords.append(parts[index]).append(" ");
        }
        words.add(lastWords.toString().trim());
        stringOfFlags = flagsBuilder.toString().replaceAll("\\s*", "");
    }

    /**
     * Аналогично, функиця для отделения путя до URL(s) от первого слова.
     * Или первых слов, если слово составное.
     */
    private void splitPathAndFirstWords() {
        String str = words.get(0);
        words.remove(0);
        words.add(0, str.substring(str.indexOf(" ") + 1, str.length()));
        pathToURL = str.substring(0, str.indexOf(" "));
    }

    /**
     * Парсим строку с флагами, т.е. ычленяем введеные пользоваталем флаги.
     * Сначала избавляемся от лишних символов в строке. Потом проверяем флаг на допустимость.
     * Если флаг допустимый, то отмечаем флаг, как упомянутый пользователем.
     * В противном случае получаем некорректный ввол.
     *
     * @throws ParsingException, если получен некорректный ввод пользователя
     */
    private void parseFlags() throws ParsingException {
        stringOfFlags = stringOfFlags.replaceAll("(-|–)", "");
        for (int index = 0; index < stringOfFlags.length(); index++) {
            char currentFlag = stringOfFlags.charAt(index);
            if (isLegalFlag(currentFlag)) {
                setFlagMentioned(currentFlag);
            } else {
                throw new ParsingException("Unknown flag: " + currentFlag + "\n");
            }
        }
    }

    /**
     * Функция проверки введеного пользователем флага в символьном виде на легальность (корректность).
     *
     * @param flagToCheck флаг, который необходимо проверить в символьном виде.
     * @return true, если флаг допускается.
     */
    private boolean isLegalFlag(char flagToCheck) {
        Flag flag = new Flag(flagToCheck);
        return flag.isLegalFlag();
    }

    /**
     * Отмечаем флаг, как упомянутый пользователем.
     *
     * @param flagToMention флаг в символьном виде.
     */
    private void setFlagMentioned(char flagToMention) {
        FLAGS.put(new Flag(flagToMention), true);
    }

    /**
     * Парсим строку с путем до URL. Если строка, есть одиночный URL, то записываем его в URLs.
     * Если строка есть путь до файла со списком из нескольких URL, то вытаскиваем их из файла записываем туда же.
     *
     * @throws ParsingException в случае, если URL невалидна, путь до файла не существует, или IOException в ходе работы с потоками.
     */
    public void parseURLs() throws ParsingException {
        File file = new File(pathToURL);
        String inputLine = "";
        if (file.exists() && !file.isDirectory()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(pathToURL))) {
                while ((inputLine = reader.readLine()) != null) {
                    URLs.add(new URL(inputLine));
                }
                reader.close();
            } catch (MalformedURLException exception) {
                URLs.clear();
                throw new ParsingException(inputLine + " invalid URL found while retrieving URLs from file");
            } catch (FileNotFoundException exception) {
                URLs.clear();
                throw new ParsingException("File " + pathToURL + " not found");
            } catch (IOException exception) {
                URLs.clear();
                throw new ParsingException("IOException occurred");
            }
        } else {
            try {
                URLs.add(new URL(pathToURL));
            } catch (MalformedURLException exception) {
                URLs.clear();
                throw new ParsingException(pathToURL + " invalid URL");
            }
        }

    }

    /**
     * Функция для парсинга аргументов командной строки. (Собираем все функции выше вместе)
     *
     * @param args аргументы командной строки.
     * @throws IOException в случае возникнования неполадок.
     */
    public void parse(String... args) throws IOException {
        putArgsToWords(args);
        splitPathAndFirstWords();
        splitLastWordsAndFlags();
        parseFlags();
        parseURLs();
    }
}
