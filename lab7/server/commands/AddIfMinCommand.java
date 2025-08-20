package server.commands;

import common.model.Movie;
import server.db.MovieDAO;

import java.util.List;
import java.util.Optional;

/**
  * Команда добавляет фильм, если он меньше текущего минимума.
  */
public class AddIfMinCommand implements Command {
    private final MovieDAO movieDAO;

    public AddIfMinCommand(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    @Override
    public Object execute(String[] args, Object data, String user, String passwordHash) throws Exception {
        if (!(data instanceof Movie movie)) {
            throw new IllegalArgumentException("Ожидался объект типа Movie");
        }

        List<Movie> movies = movieDAO.getAllMovies();
        Optional<Movie> min = movies.stream().min(Movie::compareTo);

        if (min.isEmpty() || movie.compareTo(min.get()) < 0) {
            long id = movieDAO.addMovie(movie, user);
            movie.setId(id);
            return "Фильм добавлен (меньше минимального элемента).";
        } else {
            return "Фильм не добавлен (не меньше текущего минимума).";
        }
    }

    @Override
    public String getDescription() {
        return "добавить элемент, если он меньше минимального";
    }
}