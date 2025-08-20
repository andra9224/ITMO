package server.commands;

import common.model.Movie;
import server.db.MovieDAO;

import java.util.List;

/**
 * Команда фильтрации фильмов по начальному имени.
 */
public class FilterByNameCommand implements Command {
    private final MovieDAO movieDAO;

    public FilterByNameCommand(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    @Override
    public Object execute(String[] args, Object data, String user, String passwordHash) throws Exception {
        if (args.length < 1) throw new IllegalArgumentException("Не указана подстрока.");

        String prefix = args[0];
        List<Movie> matches = movieDAO.getAllMovies().stream()
                .filter(m -> m.getName().startsWith(prefix))
                .sorted()
                .toList();

        return matches.isEmpty()
                ? "Нет фильмов, начинающихся с \"" + prefix + "\"."
                : matches;
    }

    @Override
    public String getDescription() {
        return "вывести элементы, значение поля name которых начинается с заданной подстроки";
    }
}