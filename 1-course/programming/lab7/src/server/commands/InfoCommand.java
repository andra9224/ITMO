package server.commands;

import server.repository.MovieRepository;

/**
 * Команда вывода информации о коллекции.
 * Теперь использует коллекцию в памяти (MovieRepository), без обращений к БД.
 */
public class InfoCommand implements Command {
    private final MovieRepository movieRepo;

    public InfoCommand(MovieRepository movieRepo) {
        this.movieRepo = movieRepo;
    }

    @Override
    public Object execute(String[] args, Object data, String user, String passwordHash) {
        int count = movieRepo.all().size();
        return "Тип коллекции: in-memory (ConcurrentHashMap) + синхронизация с PostgreSQL\n" +
                "Количество элементов в памяти: " + count;
    }

    @Override
    public String getDescription() {
        return "вывести информацию о коллекции";
    }
}
