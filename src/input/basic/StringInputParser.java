package input.basic;

import java.util.Scanner;

/**
 * Парсер строкового ввода.
 */
public class StringInputParser extends InputParser<String> {
    /**
     * Конструктор.
     *
     * @param scanner источник пользовательского ввода
     */
    public StringInputParser(Scanner scanner) {

        super(scanner);
    }

    /**
     * Считывает строку из ввода.
     *
     * @param fieldName не используется
     * @return введённая строка
     */
    @Override
    public String parse(String fieldName) {

        return scanner.nextLine().trim();
    }
}
