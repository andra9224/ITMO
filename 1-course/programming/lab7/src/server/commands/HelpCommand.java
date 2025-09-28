package server.commands;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Команда вывода справки по всем доступным командам.
 * Отображает команды по разделам и в устойчивом порядке.
 */
public class HelpCommand implements Command {
    private final Map<String, Command> commands;

    /**
     * Конструктор.
     *
     * @param commands список всех зарегистрированных команд
     */
    public HelpCommand(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public Object execute(String[] args, Object data, String user, String passwordHash) {

        List<String> auth = List.of("register", "login");

        List<String> view = List.of(
                "show",
                "show_mine",
                "show_by_owner",
                "filter_starts_with_name",
                "min_by_usa_box_office",
                "count_less_than_length",
                "info"
        );

        List<String> modify = List.of(
                "add",
                "update",
                "remove_by_id",
                "remove_lower",
                "add_if_max",
                "add_if_min",
                "clear"
        );

        List<String> misc = List.of(
                "execute_script",
                "help"
                // "exit" — выполняется на клиенте, не серверная команда
        );

        Set<String> covered = new HashSet<>();
        covered.addAll(auth);
        covered.addAll(view);
        covered.addAll(modify);
        covered.addAll(misc);

        List<String> others = commands.keySet().stream()
                .filter(k -> !covered.contains(k))
                .sorted()
                .collect(Collectors.toList());

        StringBuilder sb = new StringBuilder();
        sb.append("Доступные команды\n");
        sb.append("-----------------\n\n");

        appendSection(sb, "Авторизация", auth);
        appendSection(sb, "Просмотр", view);
        appendSection(sb, "Модификация", modify);
        appendSection(sb, "Прочее", misc);

        if (!others.isEmpty()) {
            appendSection(sb, "Другие команды", others);
        }

        sb.append("\nПодсказки:\n");
        sb.append("  • Чтение коллекции доступно всем пользователям.\n");
        sb.append("  • Изменять/удалять объекты может только их владелец.\n");
        sb.append("  • Команда 'exit' завершает клиент и обрабатывается на стороне клиента.\n");

        return sb.toString();
    }

    private void appendSection(StringBuilder sb, String title, List<String> names) {
        List<String> present = names.stream()
                .filter(commands::containsKey)
                .sorted()
                .toList();

        if (present.isEmpty()) return;

        sb.append(title).append(":\n");
        for (String name : present) {
            Command cmd = commands.get(name);
            String desc = (cmd != null) ? cmd.getDescription() : "";
            sb.append(String.format("  %-24s - %s%n", name, desc));
        }
        sb.append('\n');
    }

    @Override
    public String getDescription() {
        return "вывести справку по доступным командам";
    }
}
