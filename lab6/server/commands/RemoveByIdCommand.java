package server.commands;

import server.collection.MovieCollection;

/**
 * Команда удаления фильма из коллекции по его ID.
 */
public class RemoveByIdCommand implements Command {
    private final MovieCollection collection;

    /**
     * Конструктор.
     * @param collection коллекция фильмов
     */
    public RemoveByIdCommand(MovieCollection collection) {
        this.collection = collection;
    }

    /**
     * Удаляет фильм по указанному ID.
     *
     * @param args массив из одного элемента — ID
     * @param data не используется
     * @return сообщение о результате удаления
     * @throws IllegalArgumentException если ID не указан или некорректен
     */
    @Override
    public Object execute(String[] args, Object data) {
        if (args.length < 1) {
            throw new IllegalArgumentException("ID не указан");
        }

        try {
            long id = Long.parseLong(args[0]);
            if (collection.removeById(id)) {
                return "Фильм с ID " + id + " успешно удалён.";
            } else {
                return "Фильм с ID " + id + " не найден.";
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID должен быть числом.");
        }
    }


    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "удалить элемент из коллекции по id";
    }
}
