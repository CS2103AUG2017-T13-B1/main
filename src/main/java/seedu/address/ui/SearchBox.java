package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

public class SearchBox extends UiPart<Region> {

    private static final String ERROR_STYLE_CLASS = "error";

    private static final Logger logger = LogsCenter.getLogger(SearchBox.class);
    private final Logic logic;
    private static final String FXML = "SearchBox.fxml";



    @FXML
    private TextField searchTextField;

    public SearchBox (Logic logic){
        super(FXML);
        this.logic = logic;

        searchTextField.textProperty().addListener( (observable, oldValue, newValue) -> {

            if (newValue.equals("")){
                try{
                    CommandResult commandResult = logic.execute("list");
                    logger.info("Result: " + commandResult.feedbackToUser);
                    raise(new NewResultAvailableEvent(commandResult.feedbackToUser, false));

                } catch (CommandException | ParseException e) {
                    // handle command failure
                    setStyleToIndicateCommandFailure();
                    logger.info("Invalid command: list");
                    raise(new NewResultAvailableEvent(e.getMessage(), true));
                }
            }
            else{
                try{
                    CommandResult commandResult = logic.execute("pfind " + newValue);
                    logger.info("Result: " + commandResult.feedbackToUser);
                    raise(new NewResultAvailableEvent(commandResult.feedbackToUser, false));

                } catch (CommandException | ParseException e) {
                    // handle command failure
                    setStyleToIndicateCommandFailure();
                    logger.info("Invalid command: pfind " + newValue);
                    raise(new NewResultAvailableEvent(e.getMessage(), true));
                }
            }

        });
    }

    /**
     * Sets the search box style to use the default style.
     */
    private void setStyleToDefault() {
        searchTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the search box style to indicate a failed command.
     */

    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = searchTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

}
