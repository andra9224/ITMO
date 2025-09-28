package server.commands;

import server.collection.MovieCollection;
import common.model.Movie;

import java.util.List;

/**
 * Команда фильтрации фильмов по началу имени.
 */
public class FilterByNameCommand implements Command {
    private final MovieCollection collection;

    /**
     * Конструктор.
     * @param collection коллекция фильмов
     */
    public FilterByNameCommand(MovieCollection collection) {
        this.collection = collection;
    }

    /**
     * Фильтрует фильмы, название которых начинается с указанной подстроки.
     *
     * @param args массив с одним элементом — началом имени
     * @param data не используется
     * @return список подходящих фильмов или сообщение, если не найдено
     * @throws IllegalArgumentException если подстрока не указана
     */
    @Override
    public Object execute(String[] args, Object data) {
        if (args.length < 1) {
            throw new IllegalArgumentException("Не указана подстрока.");
        }

        String prefix = args[0];
        List<Movie> filtered = collection.getMovies().stream()
                .filter(m -> m.getName().startsWith(prefix))
                .sorted()
                .toList();

        return filtered.isEmpty()
                ? "Нет фильмов, начинающихся с '" + prefix + "'."
                : filtered;
    }


    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "вывести элементы, значение поля name которых начинается с заданной подстроки";
    }
}
