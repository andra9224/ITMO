package server.commands;

import server.collection.MovieCollection;

/**
 * Команда очистки коллекции от всех элементов.
 */
public class ClearCommand implements Command {
    private final MovieCollection collection;

    /**
     * Конструктор.
     * @param collection коллекция фильмов
     */
    public ClearCommand(MovieCollection collection) {
        this.collection = collection;
    }

    /**
     * Очищает коллекцию.
     *
     * @param args не используются
     * @param data не используется
     * @return сообщение об успешной очистке
     */
    @Override
    public Object execute(String[] args, Object data) {
        collection.clear();
        return "Коллекция очищена.";
    }

    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "очистить коллекцию";
    }
}
