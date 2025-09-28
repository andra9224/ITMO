package input;

import model.Person;
import input.basic.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Парсер информации о режиссёре — объект {@link Person}.
 */
public class PersonInputParser {
    private final StringInputParser stringParser;
    private final FloatInputParser floatParser;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    /**
     * Конструктор.
     *
     * @param scanner источник пользовательского ввода
     */
    public PersonInputParser(Scanner scanner) {
        this.stringParser = new StringInputParser(scanner);
        this.floatParser = new FloatInputParser(scanner);
        dateFormat.setLenient(false);
    }

    /**
     * Парсит информацию о режиссёре с проверкой валидности.
     *
     * @return объект {@link Person}
     */
    public Person parsePerson() {
        Person person = new Person();

        person.setName(stringParser.parseNonEmpty("имя режиссера"));

        Date birthday = null;
        while (true) {
            System.out.println("Введите дату рождения режиссера (дд-мм-гггг) или оставьте пустым: ");
            String input = stringParser.parse("дату рождения");
            if (input.isEmpty()) break;
            try {
                birthday = dateFormat.parse(input);
                break;
            } catch (Exception e) {
                System.out.println("Неверный формат даты.");
            }
        }
        person.setBirthday(birthday);

        System.out.println("Введите рост режиссера (в метрах): ");
        person.setHeight(floatParser.parsePositive("рост режиссера (в метрах)"));

        System.out.println("Введите вес режиссера (в кг, целое число) или оставьте пустым: ");
        String weightInput = stringParser.parse("вес режиссера");
        if (!weightInput.isEmpty()) {
            try {
                person.setWeight(Integer.parseInt(weightInput));
            } catch (NumberFormatException e) {
                System.out.println("Неверный формат веса. Вес не установлен.");
            }
        }

        return person;
    }
}
