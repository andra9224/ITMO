package client.input.basic;

import java.util.Scanner;

/**
 * Парсер целочисленного ввода.
 */
public class IntInputParser extends InputParser<Integer> {
    /**
     * Конструктор.
     *
     * @param scanner источник пользовательского ввода
     */
    public IntInputParser(Scanner scanner) {

        super(scanner);
    }

    /**
     * Парсит положительное целое число.
     *
     * @param fieldName имя поля
     * @return число > 0
     */
    public Integer parsePositive(String fieldName) {

        return parseGreaterThan(0, fieldName);
    }

    /**
     * Парсит целое число, больше заданного минимума.
     *
     * @param min минимально допустимое значение
     * @param fieldName имя поля
     * @return число > min
     */
    public Integer parseGreaterThan(int min, String fieldName) {
        Integer value;
        do {
            value = parse(fieldName);
            if (value <= min) {
                System.out.println("Значение должно быть больше " + min + ". Повторите ввод:");
            }
        } while (value <= min);
        return value;
    }

    /**
     * Парсит целое число.
     *
     * @param fieldName имя поля для отображения в сообщениях
     * @return введённое целое число
     */
    @Override
    public Integer parse(String fieldName) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Неверный формат для " + fieldName + ". Введите целое число:");
            }
        }
    }
}
