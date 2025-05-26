package commands;

import collection.MovieCollection;
import model.Movie;

import java.util.List;
import java.util.Random;

/**
 * Команда вывода любого фильма с минимальным значением поля {@code usaBoxOffice}.
 */
public class MinByUsaBoxOfficeCommand implements Command {
    private final MovieCollection collection;

    /**
     * Конструктор.
     * @param collection коллекция фильмов
     */
    public MinByUsaBoxOfficeCommand(MovieCollection collection) {
        this.collection = collection;
    }

    /**
     * Выполняет команду: находит минимальное значение поля {@code usaBoxOffice}
     * и выводит случайный фильм с этим значением.
     * @param args не используется
     */
    @Override
    public void execute(String[] args) {
        if (collection.size() == 0) {
            System.out.println("Коллекция пуста.");
            return;
        }

        int minUsaBoxOffice = collection.getMovies().stream()
                .mapToInt(Movie::getUsaBoxOffice)
                .min()
                .orElse(0);

        List<Movie> candidates = collection.getMovies().stream()
                .filter(m -> m.getUsaBoxOffice() == minUsaBoxOffice)
                .toList();

        if (!candidates.isEmpty()) {
            Movie selected = candidates.get(new Random().nextInt(candidates.size()));
            System.out.println("Фильм с минимальными кассовыми сборами в США:");
            System.out.println(selected);
        } else {
            System.out.println("Нет фильмов с минимальными кассовыми сборами в США.");
        }
    }

    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "вывести любой объект с минимальным значением поля usaBoxOffice";
    }
}
