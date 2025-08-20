package common.exchange;

import java.io.Serial;
import java.io.Serializable;

/**
 * Передает запрос пользователя серверу.
 * Включает название команды, ее аргументы и объект данных.
 */
public class CommandRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Название команды
     */
    private String commandName;

    /**
     * Аргументы команды
     */
    private String[] arguments;

    /**
     * Объект данных, передаваемый с командой.
     */
    private Object data;

    /**
     * Имя пользователя
     */
    private String username;

    /**
     * Хэшированный пароль
     */
    private String passwordHash;

    /**
     * Создаёт новый объект запроса команды.
     *
     * @param commandName название команды
     * @param arguments массив аргументов
     * @param data объект данных (может быть null)
     */
    public CommandRequest(String commandName, String[] arguments, Object data) {
        this.commandName = commandName;
        this.arguments = arguments;
        this.data = data;
    }

    /**
     * Создаёт новый объект запроса команды.
     *
     * @param commandName название команды
     * @param arguments массив аргументов
     * @param data объект данных (может быть null)
     * @param username имя пользователя
     * @param passwordHash хэшированный пароль
     */
    public CommandRequest(String commandName, String[] arguments, Object data, String username, String passwordHash) {
        this.commandName = commandName;
        this.arguments = arguments;
        this.data = data;
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public String getCommandName() {
        return commandName;
    }

    public String[] getArguments() {
        return arguments;
    }

    public Object getData() {
        return data;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
