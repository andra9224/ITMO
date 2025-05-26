package commands;

import collection.MovieCollection;
import input.MovieInputParser;
import model.Movie;

/**
 * Команда добавления фильма, если он больше текущего максимального элемента в коллекции.
 */
public class AddIfMaxCommand implements Command {
    private final MovieCollection collection;
    private final MovieInputParser parser;

    /**
     * Конструктор.
     * @param collection коллекция фильмов
     * @param parser парсер для ввода нового фильма
     */
    public AddIfMaxCommand(MovieCollection collection, MovieInputParser parser) {
        this.collection = collection;
        this.parser = parser;
    }

    /**
     * Выполняет команду: добавляет фильм, если он больше текущего максимального.
     * @param args не используется
     */
    @Override
    public void execute(String[] args) {
        Movie movie = parser.parseMovie();
        Movie maxMovie = collection.getMax();

        if (maxMovie == null || movie.compareTo(maxMovie) > 0) {
            collection.add(movie);
            System.out.println("Фильм добавлен, так как его значение превышает наибольший элемент коллекции.");
        } else {
            System.out.println("Фильм не добавлен, так как его значение не превышает наибольший элемент коллекции.");
        }
    }

    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "добавить новый элемент, если его значение превышает наибольший элемент коллекции";
    }
}
