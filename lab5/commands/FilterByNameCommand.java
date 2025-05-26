package commands;

import collection.MovieCollection;
import model.Movie;

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
     * Выполняет команду: выводит фильмы, названия которых начинаются с заданной подстроки.
     * @param args подстрока
     */
    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Не указана подстрока для поиска");
            return;
        }

        String prefix = args[0];
        if (collection.size() == 0) {
            System.out.println("Коллекция пуста.");
            return;
        }

        List<Movie> filtered = collection.getMovies().stream()
                .filter(m -> m.getName().startsWith(prefix))
                .sorted()
                .toList();

        if (filtered.isEmpty()) {
            System.out.println("Фильмы, начинающиеся с '" + prefix + "', не найдены.");
        } else {
            System.out.println("Фильмы, название которых начинается с '" + prefix + "':");
            filtered.forEach(System.out::println);
        }
    }

    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "вывести элементы, значение поля name которых начинается с заданной подстроки";
    }
}
