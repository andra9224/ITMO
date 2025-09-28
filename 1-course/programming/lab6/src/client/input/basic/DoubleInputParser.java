package client.input.basic;

import java.util.Scanner;

/**
 * Парсер вещественного числа (double).
 */
public class DoubleInputParser extends InputParser<Double> {
    /**
     * Конструктор.
     *
     * @param scanner источник пользовательского ввода
     */
    public DoubleInputParser(Scanner scanner) {

        super(scanner);
    }


    /**
     * Парсит значение типа double.
     *
     * @param fieldName имя поля
     * @return введённое значение double
     */
    @Override
    public Double parse(String fieldName) {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Неверный формат для " + fieldName + ". Введите дробное число:");
            }
        }
    }
}
