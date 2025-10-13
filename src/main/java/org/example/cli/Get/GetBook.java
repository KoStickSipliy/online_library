package org.example.cli.Get;

import org.example.cli.Command;

public class GetBook implements Command {
    // поле с сервисом

    public GetBook() {
        //объявление сервиса
    }

    @Override
    public void execute() {
        //вызов метода из сервиса
    }

    @Override
    public String getCommandName() {
        return "Get all departments";
    }
}
