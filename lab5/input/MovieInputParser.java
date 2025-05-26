package input;

import model.Movie;
import model.MovieGenre;
import input.basic.*;
import java.util.Scanner;

/**
 * Парсер для создания объекта {@link Movie}.
 * Использует вложенные парсеры координат и режиссёра.
 */
public class MovieInputParser {
    private final CoordinatesInputParser coordinatesParser;
    private final PersonInputParser personParser;
    private final StringInputParser stringParser;
    private final IntInputParser intParser;

    /**
     * Конструктор.
     *
     * @param scanner источник пользовательского ввода
     */
    public MovieInputParser(Scanner scanner) {
        this.stringParser = new StringInputParser(scanner);
        this.intParser = new IntInputParser(scanner);
        this.coordinatesParser = new CoordinatesInputParser(scanner);
        this.personParser = new PersonInputParser(scanner);
    }

    /**
     * Парсит информацию о фильме с проверкой валидности.
     *
     * @return объект {@link Movie}
     */
    public Movie parseMovie() {
        Movie movie = new Movie();

        movie.setName(stringParser.parseNonEmpty("название фильма"));

        System.out.println("Введите координаты:");
        movie.setCoordinates(coordinatesParser.parseCoordinates());

        System.out.println("Введите количество оскаров: ");
        movie.setOscarsCount(intParser.parsePositive("количество оскаров"));

        System.out.println("Введите кассовые сборы в США: ");
        movie.setUsaBoxOffice(intParser.parsePositive("кассовые сборы в США"));

        System.out.println("Введите длину фильма (в минутах): ");
        movie.setLength(intParser.parsePositive("длина фильма (в минутах)"));

        System.out.println("Выберите жанр:");
        MovieGenre.printGenres();
        movie.setGenre(parseMovieGenre());

        System.out.println("Введите информацию о режиссере:");
        movie.setDirector(personParser.parsePerson());

        return movie;
    }

    /**
     * Парсит жанр фильма с проверкой валидности
     * @return значение {@link MovieGenre}
     */
    private MovieGenre parseMovieGenre() {
        while (true) {
            try {
                String input = stringParser.parse("жанр").toUpperCase();
                return MovieGenre.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Неверный жанр.");
                MovieGenre.printGenres();
            }
        }
    }
}
