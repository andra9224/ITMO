package server.commands;

import common.model.Movie;
import server.db.MovieDAO;

import java.util.List;

/**
 * Команда вывода информации о коллекции.
 */
public class InfoCommand implements Command {
    private final MovieDAO movieDAO;

    public InfoCommand(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    @Override
    public Object execute(String[] args, Object data, String user, String passwordHash) throws Exception {
        List<Movie> movies = movieDAO.getAllMovies();
        return "Тип коллекции: PostgreSQL + память\n"
                + "Количество элементов: " + movies.size();
    }

    @Override
    public String getDescription() {
        return "вывести информацию о коллекции";
    }
}