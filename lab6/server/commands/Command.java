package server.commands;

/**
 * Интерфейс команды, которую может выполнить сервер.
 */
public interface Command {
    /**
     * Выполняет команду с переданными аргументами и данными.
     *
     * @param args строковые аргументы команды
     * @param data объект, переданный клиентом (например, Movie)
     * @return результат выполнения (может быть строкой, числом, объектом и т.д.)
     * @throws Exception если произошла ошибка при выполнении
     */
    Object execute(String[] args, Object data) throws Exception;

    /**
     * Возвращает описание команды.
     *
     * @return описание команды для справки
     */
    String getDescription();
}
