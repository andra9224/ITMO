package server.commands;

import server.collection.MovieCollection;
import common.model.Movie;

/**
 * Команда добавления нового фильма в коллекцию.
 */
public class AddCommand implements Command {
    private final MovieCollection collection;

    /**
     * Конструктор.
     * @param collection коллекция фильмов
     */
    public AddCommand(MovieCollection collection) {
        this.collection = collection;

    }

    /**
     * Добавляет фильм в коллекцию, если передан корректный объект Movie.
     *
     * @param args не используются
     * @param data объект Movie, переданный клиентом
     * @return сообщение об успешном добавлении
     * @throws IllegalArgumentException если объект не является Movie
     */
    @Override
    public Object execute(String[] args, Object data) throws Exception {
        if (!(data instanceof Movie movie)) {
            throw new IllegalArgumentException("Ожидался объект типа Movie");
        }
        collection.add(movie);
        return "Фильм добавлен с ID: " + movie.getId();
    }


    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию";
    }
}
