package client.input;

import client.input.basic.DoubleInputParser;
import common.model.Coordinates;
import client.input.basic.IntInputParser;
import java.util.Scanner;

/**
 * Парсер координат для объекта {@link Coordinates}.
 */
public class CoordinatesInputParser {
    private final DoubleInputParser doubleParser;
    private final IntInputParser intParser;

    /**
     * Конструктор.
     *
     * @param scanner источник пользовательского ввода
     */
    public CoordinatesInputParser(Scanner scanner) {
        this.doubleParser = new DoubleInputParser(scanner);
        this.intParser = new IntInputParser(scanner);
    }

    /**
     * Парсит координаты X и Y с проверкой валидности.
     *
     * @return объект {@link Coordinates}
     */
    public Coordinates parseCoordinates() {
        Coordinates coords = new Coordinates();

        System.out.println("Введите координату X (дробное число): ");
        coords.setX(doubleParser.parse("координатa X (дробное число)"));

        System.out.println("Введите координату Y (целое число > -822): ");
        coords.setY(intParser.parseGreaterThan(-822, "координатa Y (целое число > -822)"));

        return coords;
    }
}
