package server.commands;

import common.model.Movie;
import server.repository.MovieRepository;

import java.util.Optional;

/**
 * Команда вывода любого фильма с минимальными кассовыми сборами.
 * Теперь читает из коллекции в памяти (MovieRepository).
 */
public class MinByUsaBoxOfficeCommand implements Command {
    private final MovieRepository movieRepo;

    public MinByUsaBoxOfficeCommand(MovieRepository movieRepo) {
        this.movieRepo = movieRepo;
    }

    @Override
    public Object execute(String[] args, Object data, String user, String passwordHash) {
        Optional<Movie> min = movieRepo.anyWithMinUsaBoxOffice();
        return min.<Object>map(m -> m).orElse("Коллекция пуста.");
    }

    @Override
    public String getDescription() {
        return "вывести любой объект с минимальным значением поля usaBoxOffice";
    }
}
