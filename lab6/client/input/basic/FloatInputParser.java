package client.input.basic;

import java.util.Scanner;

/**
 * Парсер ввода числа с плавающей точкой (float).
 */
public class FloatInputParser extends InputParser<Float> {
    /**
     * Конструктор.
     *
     * @param scanner источник пользовательского ввода
     */
    public FloatInputParser(Scanner scanner) {

        super(scanner);
    }

    /**
     * Парсит положительное значение float.
     *
     * @param fieldName имя поля
     * @return значение > 0
     */
    public Float parsePositive(String fieldName) {
        Float value;
        do {
            value = parse(fieldName);
            if (value <= 0) {
                System.out.println("Значение должно быть больше 0. Повторите ввод:");
            }
        } while (value <= 0);
        return value;
    }

    /**
     * Парсит число с плавающей точкой.
     *
     * @param fieldName имя поля
     * @return значение float
     */
    @Override
    public Float parse(String fieldName) {
        while (true) {
            try {
                return Float.parseFloat(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Неверный формат для " + fieldName + ". Введите число:");
            }
        }
    }
}
