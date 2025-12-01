package controller;

import model.user.User;
import view.cli.CLIView;

/**
 * Abstract base controller class for all CLI controllers.
 * <p>
 * Actual controllers should extend this class and have to implement the {@link #start()} method
 * which is responsible for the user interaction loop. The {@code CLIView} passed to the
 * constructor is used as the view for the controller.
 * </p>
 *
 * @see view.cli.CLIView
 * @author Kuba Rodak (24436755)
 */
public abstract class Controller {
    CLIView view;
    User user;

    /**
     * Creates a new controller with the provided view.
     * @param view the view that will be used for this controller
     */
    public Controller(CLIView view, User user) {
        this.view = view;
        this.user = user;
    }

    /**
     * Starts the controller's interactive command loop.
     * Implementations of this method should display menus, read user commands,
     * and call the business logic methods inside the model.
     */
    public abstract void start();
}
