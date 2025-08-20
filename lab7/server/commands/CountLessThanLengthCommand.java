package server.commands;

import server.db.MovieDAO;

/**
 * Команда подсчёта фильмов с длиной меньше заданной.
 */
public class CountLessThanLengthCommand implements Command {
    private final MovieDAO movieDAO;

    public CountLessThanLengthCommand(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    @Override
    public Object execute(String[] args, Object data, String user, String passwordHash) throws Exception {
        if (args.length < 1) throw new IllegalArgumentException("Не указана длина.");

        int target = Integer.parseInt(args[0]);
        long count = movieDAO.getAllMovies().stream()
                .filter(m -> m.getLength() < target)
                .count();

        return "Количество фильмов с длиной < " + target + ": " + count;
    }

    @Override
    public String getDescription() {
        return "вывести количество элементов с length меньше заданного";
    }
}