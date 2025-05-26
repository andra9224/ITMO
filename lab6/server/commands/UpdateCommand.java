package server.commands;

import server.collection.MovieCollection;
import common.model.Movie;

/**
 * Команда обновления существующего фильма по ID.
 */
public class UpdateCommand implements Command {
    private final MovieCollection collection;

    /**
     * Конструктор.
     * @param collection коллекция фильмов
     */
    public UpdateCommand(MovieCollection collection) {
        this.collection = collection;
    }

    /**
     * Обновляет существующий фильм по ID.
     *
     * @param args массив с одним элементом — ID
     * @param data объект Movie, переданный клиентом
     * @return сообщение об успехе или ошибке обновления
     * @throws IllegalArgumentException если ID отсутствует или data не является Movie
     */
    @Override
    public Object execute(String[] args, Object data) {
        if (args.length < 1) {
            throw new IllegalArgumentException("Не указан ID.");
        }

        if (!(data instanceof Movie updatedMovie)) {
            throw new IllegalArgumentException("Ожидался объект типа Movie");
        }

        try {
            long id = Long.parseLong(args[0]);
            boolean success = collection.update(id, updatedMovie);
            return success
                    ? "Фильм с ID " + id + " успешно обновлён."
                    : "Фильм с ID " + id + " не найден.";
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неверный формат ID.");
        }
    }


    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "обновить значение элемента коллекции по id";
    }
}
