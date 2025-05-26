package commands;

import collection.MovieCollection;

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
     * Выполняет команду: выводит все фильмы в отсортированном порядке.
     * @param args не используется
     */
    @Override
    public void execute(String[] args) {
        if (collection.size() == 0) {
            System.out.println("Коллекция пуста.");
            return;
        }

        System.out.println("Элементы коллекции (в алфавитном порядке):");
        collection.getMovies().stream()
                .sorted()
                .forEach(System.out::println);
    }

    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "вывести все элементы коллекции";
    }
}
