package org.example.cli;

import org.example.DBManager.PostgreSQLManager;
import org.example.IO.IO;

public class ExitCommand implements Command{
    @Override
    public String getCommandName() {
        return "Exit";
    }

    @Override
    public void execute() {
        try {
            // Try to close DB connection gracefully before exiting
            PostgreSQLManager.getInstance().closeConnection();
        } catch (Exception e) {
            IO.printError("Error while closing DB connection on exit: " + e.getMessage());
        }
        System.exit(0);
    }
}
