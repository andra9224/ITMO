package server.commands;

import server.collection.MovieCollection;

/**
 * Команда, считающая количество фильмов с длиной меньше заданной.
 */
public class CountLessThanLengthCommand implements Command {
    private final MovieCollection collection;

    /**
     * Конструктор.
     * @param collection коллекция фильмов
     */
    public CountLessThanLengthCommand(MovieCollection collection) {
        this.collection = collection;
    }

    /**
     * Считает количество фильмов с длиной меньше указанной.
     *
     * @param args массив с одним элементом — длиной
     * @param data не используется
     * @return сообщение с количеством найденных фильмов
     * @throws IllegalArgumentException если длина не указана или некорректна
     */
    @Override
    public Object execute(String[] args, Object data) {
        if (args.length < 1) {
            throw new IllegalArgumentException("Не указана длина для сравнения");
        }

        try {
            int length = Integer.parseInt(args[0]);
            long count = collection.getMovies().stream()
                    .filter(m -> m.getLength() < length)
                    .count();
            return "Количество фильмов с длиной меньше " + length + ": " + count;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неверный формат длины.");
        }
    }


    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "вывести количество элементов с length меньше заданного";
    }
}
