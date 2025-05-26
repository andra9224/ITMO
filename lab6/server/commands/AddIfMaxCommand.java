package server.commands;

import server.collection.MovieCollection;
import common.model.Movie;

/**
 * Команда добавления фильма, если он больше текущего максимального элемента в коллекции.
 */
public class AddIfMaxCommand implements Command {
    private final MovieCollection collection;

    /**
     * Конструктор.
     * @param collection коллекция фильмов
     */
    public AddIfMaxCommand(MovieCollection collection) {
        this.collection = collection;

    }

    /**
     * Добавляет фильм, если он больше текущего максимального элемента коллекции.
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

        Movie maxMovie = collection.getMax();
        if (maxMovie == null || movie.compareTo(maxMovie) > 0) {
            collection.add(movie);
            return "Фильм добавлен (превышает максимальный элемент).";
        } else {
            return "Фильм не добавлен (не превышает максимальный элемент).";
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
