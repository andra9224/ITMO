package server.commands;

import common.model.Movie;
import server.repository.MovieRepository;

import java.util.List;

/**
 * Команда отображения всех фильмов (без ограничений по владельцу).
 * Теперь читает данные из коллекции в памяти (MovieRepository).
 */
public class ShowCommand implements Command {
    private final MovieRepository movieRepo;

    public ShowCommand(MovieRepository movieRepo) {
        this.movieRepo = movieRepo;
    }

    @Override
    public Object execute(String[] args, Object data, String user, String passwordHash) {
        List<Movie> movies = movieRepo.all();
        return movies.isEmpty() ? "Коллекция пуста." : movies;
    }

    @Override
    public String getDescription() {
        return "вывести все элементы коллекции";
    }
}
