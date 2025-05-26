package client.input.basic;

import java.util.Scanner;

/**
 * Абстрактный базовый класс для всех парсеров пользовательского ввода.
 *
 * @param <T> тип данных, который должен быть распознан
 */
public abstract class InputParser<T> {
    protected final Scanner scanner;

    /**
     * Конструктор парсера.
     *
     * @param scanner источник пользовательского ввода
     */
    public InputParser(Scanner scanner) {

        this.scanner = scanner;
    }

    /**
     * Парсит значение указанного поля.
     *
     * @param fieldName имя поля (для вывода подсказок и сообщений)
     * @return распознанное значение типа T
     */
    public abstract T parse(String fieldName);

    /**
     * Парсит значение поля, гарантируя, что оно не пустое (null или пустая строка).
     *
     * @param fieldName имя поля
     * @return валидное непустое значение
     */
    public T parseNonEmpty(String fieldName) {
        T value;
        do {
            System.out.println("Введите " + fieldName + ": ");
            value = parse(fieldName);
            if (value == null || (value instanceof String && ((String)value).isEmpty())) {
                System.out.println("Поле не может быть пустым. Повторите ввод:");
            }
        } while (value == null || (value instanceof String && ((String)value).isEmpty()));
        return value;
    }
}
