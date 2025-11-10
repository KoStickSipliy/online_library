package org.example.cli;

public class ExitCommand implements Command{
    @Override
    public String getCommandName() {
        return "Exit";
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}
