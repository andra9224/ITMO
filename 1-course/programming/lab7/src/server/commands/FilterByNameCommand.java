package server.commands;

import common.model.Movie;
import server.repository.MovieRepository;

import java.util.List;

/**
 * Команда фильтрации фильмов по начальному имени.
 * Теперь читает данные из коллекции в памяти (MovieRepository).
 */
public class FilterByNameCommand implements Command {
    private final MovieRepository movieRepo;

    public FilterByNameCommand(MovieRepository movieRepo) {
        this.movieRepo = movieRepo;
    }

    @Override
    public Object execute(String[] args, Object data, String user, String passwordHash) {
        if (args.length < 1) {
            throw new IllegalArgumentException("Не указана подстрока.");
        }

        String prefix = args[0];
        List<Movie> matches = movieRepo.filterStartsWithName(prefix);

        return matches.isEmpty()
                ? "Нет фильмов, начинающихся с \"" + prefix + "\"."
                : matches;
    }

    @Override
    public String getDescription() {
        return "вывести элементы, значение поля name которых начинается с заданной подстроки";
    }
}
