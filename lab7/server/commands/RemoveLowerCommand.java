package server.commands;

import common.model.Movie;
import server.db.MovieDAO;

import java.util.List;

/**
 * Удаляет все фильмы пользователя, которые меньше заданного.
 */
public class RemoveLowerCommand implements Command {
    private final MovieDAO movieDAO;

    public RemoveLowerCommand(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    @Override
    public Object execute(String[] args, Object data, String user, String passwordHash) throws Exception {
        if (!(data instanceof Movie movie)) {
            throw new IllegalArgumentException("Ожидался объект типа Movie");
        }

        List<Movie> userMovies = movieDAO.getMoviesByOwner(user);
        List<Long> toRemove = userMovies.stream()
                .filter(m -> m.compareTo(movie) < 0)
                .map(Movie::getId)
                .toList();

        int count = 0;
        for (Long id : toRemove) {
            if (movieDAO.removeById(id, user)) count++;
        }

        return "Удалено " + count + " элементов, меньших заданного.";
    }

    @Override
    public String getDescription() {
        return "удалить из коллекции все элементы, меньшие чем заданный (только свои)";
    }
}