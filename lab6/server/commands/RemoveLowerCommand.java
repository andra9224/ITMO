package server.commands;

import server.collection.MovieCollection;
import common.model.Movie;

/**
 * Команда удаления всех элементов, меньших заданного.
 */
public class RemoveLowerCommand implements Command {
    private final MovieCollection collection;

    /**
     * Конструктор.
     * @param collection коллекция фильмов
     */
    public RemoveLowerCommand(MovieCollection collection) {
        this.collection = collection;
    }

    /**
     * Удаляет все фильмы, меньшие заданного.
     *
     * @param args не используются
     * @param data объект Movie, с которым сравниваются элементы
     * @return сообщение о количестве удалённых фильмов
     * @throws IllegalArgumentException если data не является Movie
     */
    @Override
    public Object execute(String[] args, Object data) {
        if (!(data instanceof Movie movie)) {
            throw new IllegalArgumentException("Ожидался объект типа Movie");
        }

        int removed = collection.removeLowerThan(movie);
        return "Удалено " + removed + " элементов, меньших заданного.";
    }


    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "удалить из коллекции все элементы, меньшие чем заданный";
    }
}
