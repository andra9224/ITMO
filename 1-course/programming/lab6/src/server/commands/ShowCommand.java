package server.commands;

import server.collection.MovieCollection;

/**
 * Команда отображения всех фильмов из коллекции в алфавитном порядке.
 */
public class ShowCommand implements Command {
    private final MovieCollection collection;

    /**
     * Конструктор.
     * @param collection коллекция фильмов
     */
    public ShowCommand(MovieCollection collection) {
        this.collection = collection;
    }

    /**
     * Возвращает отсортированный список всех фильмов в коллекции.
     *
     * @param args не используются
     * @param data не используется
     * @return список фильмов или сообщение, если коллекция пуста
     */
    @Override
    public Object execute(String[] args, Object data) {
        if (collection.size() == 0) {
            return "Коллекция пуста.";
        }

        return collection.getMovies().stream()
                .sorted()
                .toList();
    }


    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "вывести все элементы коллекции";
    }
}
