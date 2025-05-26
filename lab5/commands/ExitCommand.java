package commands;

/**
 * Команда завершения работы программы без сохранения.
 */
public class ExitCommand implements Command {
    /**
     * Выполняет команду: завершает выполнение программы.
     * @param args не используется
     */
    @Override
    public void execute(String[] args) {
        System.out.println("Завершение программы.");
        System.exit(0);
    }

    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "завершить программу (без сохранения)";
    }
}
