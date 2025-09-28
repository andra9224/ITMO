package server.commands;

import server.collection.MovieCollection;
import common.model.Movie;

/**
 * Команда добавления фильма, если он меньше текущего минимального элемента в коллекции.
 */
public class AddIfMinCommand implements Command {
    private final MovieCollection collection;

    /**
     * Конструктор.
     * @param collection коллекция фильмов
     */
    public AddIfMinCommand(MovieCollection collection) {
        this.collection = collection;
    }

    /**
     * Добавляет фильм, если он меньше текущего минимального элемента коллекции.
     *
     * @param args не используются
     * @param data объект Movie, переданный клиентом
     * @return сообщение о результате добавления
     * @throws IllegalArgumentException если объект не является Movie
     */
    @Override
    public Object execute(String[] args, Object data) {
        if (!(data instanceof Movie movie)) {
            throw new IllegalArgumentException("Ожидался объект типа Movie");
        }

        Movie minMovie = collection.getMin();
        if (minMovie == null || movie.compareTo(minMovie) < 0) {
            collection.add(movie);
            return "Фильм добавлен (меньше минимального элемента).";
        } else {
            return "Фильм не добавлен (не меньше минимального элемента).";
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
