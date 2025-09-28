package server.commands;

import server.collection.MovieCollection;
import common.model.Movie;

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
     * Выводит случайный фильм с минимальными сборами в США.
     *
     * @param args не используются
     * @param data не используется
     * @return объект Movie или сообщение, если таких фильмов нет
     */
    @Override
    public Object execute(String[] args, Object data) {
        if (collection.size() == 0) {
            return "Коллекция пуста.";
        }

        int minUsaBoxOffice = collection.getMovies().stream()
                .mapToInt(Movie::getUsaBoxOffice)
                .min()
                .orElse(0);

        List<Movie> candidates = collection.getMovies().stream()
                .filter(m -> m.getUsaBoxOffice() == minUsaBoxOffice)
                .toList();

        if (!candidates.isEmpty()) {
            return candidates.get(new Random().nextInt(candidates.size()));
        } else {
            return "Нет фильмов с минимальными кассовыми сборами в США.";
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
