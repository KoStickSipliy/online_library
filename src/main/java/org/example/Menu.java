package org.example;

import org.example.IO.IO;
import org.example.cli.*;
import org.example.cli.*;

import java.util.*;


public class Menu {

    private static final List<Command> commandsList = List.of(
            new CreateBookCommand(),
            new CreateBookmarkCommand(),
            new DeleteBookCommand(),
            new DeleteBookmarkCommand(),
            new EditBookCommand(),
            new EditBookmarkCommand(),
            new GetAllBooksCommand(),
            new ExitCommand()
    );

    private final Map<Integer, Command> commandsMap;

    public Menu() {
        commandsMap = new HashMap<>();
        for (int i = 0; i < commandsList.size(); i++) {
            commandsMap.put(i + 1, commandsList.get(i));
        }
    }

    public void run() {
        while (true) {
            printAllCommands();
            Integer command = chooseCommand();
            if (command == null) {
                continue;
            }
            commandsMap.get(command).execute();
            IO.printEmptyLine();
        }
    }

    private void printAllCommands() {
        commandsMap.forEach((number, command) -> {
            IO.print("%d) %s".formatted(number, command.getCommandName()));
        });
    }

    private Integer chooseCommand() {
        try {
            Integer input = Integer.parseInt(IO.readLine("Choose command:"));
            if (!commandsMap.containsKey(input)) {
                IO.printError("No such command");
                return null;
            }
            return input;
        } catch (NumberFormatException e) {
            IO.printError("Wrong input format");
        }
        return null;
    }
}
