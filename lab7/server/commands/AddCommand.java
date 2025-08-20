package server.commands;

import common.model.Movie;
import server.db.MovieDAO;

/**
 * Команда добавления нового фильма в коллекцию.
 */
public class AddCommand implements Command {
    private final MovieDAO movieDAO;

    public AddCommand(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    @Override
    public Object execute(String[] args, Object data, String user, String passwordHash) throws Exception {
        if (!(data instanceof Movie movie)) {
            throw new IllegalArgumentException("Ожидался объект типа Movie");
        }

        long id = movieDAO.addMovie(movie, user);
        movie.setId(id);
        return "Фильм добавлен с ID: " + id;
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию";
    }
}
