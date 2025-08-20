package server.commands;

import common.model.Movie;
import server.db.MovieDAO;

import java.util.List;
import java.util.Optional;

/**
 * Команда добавляет фильм, если он превышает текущий максимум (по compareTo).
 */
public class AddIfMaxCommand implements Command {
    private final MovieDAO movieDAO;

    public AddIfMaxCommand(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    @Override
    public Object execute(String[] args, Object data, String user, String passwordHash) throws Exception {
        if (!(data instanceof Movie movie)) {
            throw new IllegalArgumentException("Ожидался объект типа Movie");
        }

        List<Movie> movies = movieDAO.getAllMovies();
        Optional<Movie> max = movies.stream().max(Movie::compareTo);

        if (max.isEmpty() || movie.compareTo(max.get()) > 0) {
            long id = movieDAO.addMovie(movie, user);
            movie.setId(id);
            return "Фильм добавлен (превышает максимальный элемент).";
        } else {
            return "Фильм не добавлен (не превышает текущий максимум).";
        }
    }

    @Override
    public String getDescription() {
        return "добавить элемент, если он превышает максимальный";
    }
}