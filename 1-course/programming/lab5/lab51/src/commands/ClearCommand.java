package commands;

import collection.MovieCollection;

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
     * Выполняет команду: очищает коллекцию.
     * @param args не используется
     */
    @Override
    public void execute(String[] args) {
        collection.clear();
        System.out.println("Коллекция успешно очищена.");
    }

    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "очистить коллекцию";
    }
}
