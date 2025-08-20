package server.commands;

/**
 * Интерфейс команды, выполняемой сервером.
 */
public interface Command {
    /**
     * Выполняет команду.
     *
     * @param args         аргументы команды
     * @param data         объект данных (например, Movie)
     * @param user         имя пользователя
     * @param passwordHash хеш пароля
     * @return результат выполнения
     * @throws Exception если произошла ошибка
     */
    Object execute(String[] args, Object data, String user, String passwordHash) throws Exception;

    /**
     * Возвращает описание команды.
     */
    String getDescription();
}
