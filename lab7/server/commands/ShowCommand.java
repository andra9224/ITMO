package server.commands;


import common.model.Movie;
import server.db.MovieDAO;

import java.util.List;

/**
 * Команда отображения всех фильмов (без ограничений по владельцу).
 */
public class ShowCommand implements Command {
    private final MovieDAO movieDAO;

    public ShowCommand(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    @Override
    public Object execute(String[] args, Object data, String user, String passwordHash) throws Exception {
        List<Movie> movies = movieDAO.getAllMovies();
        return movies.isEmpty() ? "Коллекция пуста." : movies.stream().sorted().toList();
    }

    @Override
    public String getDescription() {
        return "вывести все элементы коллекции";
    }
}