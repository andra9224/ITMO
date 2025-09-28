package server.commands;

import common.model.Movie;
import server.repository.MovieRepository;

import java.util.List;

/**
 * Команда вывода всех фильмов по указанному логину владельца.
 * Использование: show_by_owner <login>
 * Чтение из памяти.
 */
public class ShowByOwnerCommand implements Command {
    private final MovieRepository movieRepo;

    public ShowByOwnerCommand(MovieRepository movieRepo) {
        this.movieRepo = movieRepo;
    }

    @Override
    public Object execute(String[] args, Object data, String currentUser, String passwordHash) {
        if (args.length < 1 || args[0].isBlank()) {
            throw new IllegalArgumentException("Укажите логин владельца: show_by_owner <login>");
        }

        String ownerLogin = args[0];
        List<Movie> movies = movieRepo.byOwner(ownerLogin);
        return movies.isEmpty()
                ? "У пользователя '" + ownerLogin + "' нет фильмов."
                : movies;
    }

    @Override
    public String getDescription() {
        return "вывести элементы, принадлежащие указанному пользователю: show_by_owner <login>";
    }
}
