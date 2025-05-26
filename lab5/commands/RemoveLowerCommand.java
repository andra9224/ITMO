package commands;

import collection.MovieCollection;
import input.MovieInputParser;
import model.Movie;

/**
 * Команда удаления всех элементов, меньших заданного.
 */
public class RemoveLowerCommand implements Command {
    private final MovieCollection collection;
    private final MovieInputParser parser;

    /**
     * Конструктор.
     * @param collection коллекция фильмов
     * @param parser парсер для ввода фильма, с которым сравнивают
     */
    public RemoveLowerCommand(MovieCollection collection, MovieInputParser parser) {
        this.collection = collection;
        this.parser = parser;
    }

    /**
     * Выполняет команду: удаляет все фильмы, меньшие введённого пользователем.
     * @param args не используется
     */
    @Override
    public void execute(String[] args) {
        Movie movie = parser.parseMovie();
        int removed = collection.removeLowerThan(movie);
        System.out.println("Удалено " + removed + " элементов, меньших чем заданный.");
    }

    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "удалить из коллекции все элементы, меньшие чем заданный";
    }
}
