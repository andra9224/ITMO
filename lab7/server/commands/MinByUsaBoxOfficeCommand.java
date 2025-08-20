package server.commands;

import common.model.Movie;
import server.db.MovieDAO;

import java.util.List;
import java.util.Random;

/**
 * Команда вывода любого фильма с минимальными кассовыми сборами.
 */
public class MinByUsaBoxOfficeCommand implements Command {
    private final MovieDAO movieDAO;

    public MinByUsaBoxOfficeCommand(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    @Override
    public Object execute(String[] args, Object data, String user, String passwordHash) throws Exception {
        List<Movie> all = movieDAO.getAllMovies();
        if (all.isEmpty()) return "Коллекция пуста.";

        int minBox = all.stream()
                .mapToInt(Movie::getUsaBoxOffice)
                .min()
                .orElse(0);

        List<Movie> candidates = all.stream()
                .filter(m -> m.getUsaBoxOffice() == minBox)
                .toList();

        return candidates.get(new Random().nextInt(candidates.size()));
    }

    @Override
    public String getDescription() {
        return "вывести любой объект с минимальным значением поля usaBoxOffice";
    }
}