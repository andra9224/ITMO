package server.commands;

import server.repository.MovieRepository;

/**
 * Команда подсчёта фильмов с длиной меньше заданной.
 * Теперь считает по коллекции в памяти (MovieRepository).
 */
public class CountLessThanLengthCommand implements Command {
    private final MovieRepository movieRepo;

    public CountLessThanLengthCommand(MovieRepository movieRepo) {
        this.movieRepo = movieRepo;
    }

    @Override
    public Object execute(String[] args, Object data, String user, String passwordHash) {
        if (args.length < 1) {
            throw new IllegalArgumentException("Не указана длина.");
        }

        int target;
        try {
            target = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Длина должна быть целым числом.");
        }

        long count = movieRepo.countWithLengthLessThan(target);
        return "Количество фильмов с длиной < " + target + ": " + count;
    }

    @Override
    public String getDescription() {
        return "вывести количество элементов с length меньше заданного";
    }
}
