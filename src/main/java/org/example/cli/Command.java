package org.example.cli;

public interface Command {
    String getCommandName();
    void execute();
}
