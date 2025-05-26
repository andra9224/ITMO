package commands;

import collection.MovieCollection;
import input.MovieInputParser;
import model.Movie;

/**
 * Команда добавления фильма, если он меньше текущего минимального элемента в коллекции.
 */
public class AddIfMinCommand implements Command {
    private final MovieCollection collection;
    private final MovieInputParser parser;

    /**
     * Конструктор.
     * @param collection коллекция фильмов
     * @param parser парсер для ввода нового фильма
     */
    public AddIfMinCommand(MovieCollection collection, MovieInputParser parser) {
        this.collection = collection;
        this.parser = parser;
    }

    /**
     * Выполняет команду: добавляет фильм, если он меньше текущего минимального.
     * @param args не используется
     */
    @Override
    public void execute(String[] args) {
        Movie movie = parser.parseMovie();
        Movie minMovie = collection.getMin();

        if (minMovie == null || movie.compareTo(minMovie) < 0) {
            collection.add(movie);
            System.out.println("Фильм добавлен, так как его значение меньше наименьшего элемента коллекции.");
        } else {
            System.out.println("Фильм не добавлен, так как его значение не меньше наименьшего элемента коллекции.");
        }
    }

    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "добавить новый элемент, если его значение меньше наименьшего элемента коллекции";
    }
}
