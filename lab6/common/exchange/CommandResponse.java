package common.exchange;

import java.io.Serial;
import java.io.Serializable;

/**
 * Представляет ответ от сервера клиенту.
 * Включает сообщение, статус успеха и (опционально) возвращаемые данные.
 */
public class CommandResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Сообщение пользователю — результат выполнения команды.
     */
    private String message;

    /**
     * Успешность выполнения команды.
     */
    private boolean success;

    /**
     * Данные, возвращённые сервером. Может быть null.
     */
    private Object data;

    /**
     * Создаёт ответ сервера.
     *
     * @param message текстовое сообщение
     * @param success true, если команда выполнена успешно
     * @param data объект данных, возвращаемых клиенту
     */
    public CommandResponse(String message, boolean success, Object data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public Object getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setData(Object data) {
        this.data = data;
    }
}