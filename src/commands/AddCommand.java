package commands;

import collection.MovieCollection;
import input.MovieInputParser;
import model.Movie;

/**
 * Команда добавления нового фильма в коллекцию.
 */
public class AddCommand implements Command {
    private final MovieCollection collection;
    private final MovieInputParser parser;

    /**
     * Конструктор.
     * @param collection коллекция фильмов
     * @param parser парсер для ввода нового фильма
     */
    public AddCommand(MovieCollection collection, MovieInputParser parser) {
        this.collection = collection;
        this.parser = parser;
    }

    /**
     * Выполняет команду: запрашивает ввод фильма и добавляет его в коллекцию.
     * @param args не используется
     */
    @Override
    public void execute(String[] args) {
        try {
            Movie movie = parser.parseMovie();
            collection.add(movie);
            System.out.println("Фильм добавлен с ID: " + movie.getId());
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении фильма: " + e.getMessage());
        }
    }

    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию";
    }
}
