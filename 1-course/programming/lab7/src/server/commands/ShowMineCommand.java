package server.commands;

import common.model.Movie;
import server.repository.MovieRepository;

import java.util.List;

/**
 * Команда вывода всех фильмов текущего пользователя (владельца).
 * Чтение из памяти.
 */
public class ShowMineCommand implements Command {
    private final MovieRepository movieRepo;

    public ShowMineCommand(MovieRepository movieRepo) {
        this.movieRepo = movieRepo;
    }

    @Override
    public Object execute(String[] args, Object data, String user, String passwordHash) {
        List<Movie> myMovies = movieRepo.byOwner(user);
        return myMovies.isEmpty() ? "У вас нет фильмов." : myMovies;
    }

    @Override
    public String getDescription() {
        return "вывести все элементы, принадлежащие текущему пользователю";
    }
}
