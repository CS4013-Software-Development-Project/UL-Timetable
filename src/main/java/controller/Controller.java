package controller;

import view.cli.CLIView;

public abstract class Controller {
    CLIView view;

    public Controller(CLIView view) {
        this.view = view;
    }

    public abstract void start();
}
