package com.karolw.todolist;

import com.karolw.todolist.datamodel.ToDoItem;
import com.karolw.todolist.datamodel.TodoData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class Controller {

    private List<ToDoItem> todoItems;
    @FXML
    private ListView<ToDoItem> todoListView;

    @FXML
    private TextArea itemDetailsTextArea;

    @FXML
    private Label deadlineLabel;

    @FXML
    private BorderPane mainBorderPane;



    public void initialize() {
//        ToDoItem item1 = new ToDoItem("Mail birthday card", "buy card" , LocalDate.of(2021, Month.JANUARY, 14));
//        todoItems = new ArrayList<>();
//        todoItems.add(item1);
//
//        TodoData.getInstance().setTodoItems(todoItems);

        todoListView.getSelectionModel().selectedItemProperty().addListener((observableValue, toDoItem, t1) -> {
            if (t1 != null) {
                ToDoItem item = todoListView.getSelectionModel().getSelectedItem();
                itemDetailsTextArea.setText(item.getDetails());
                DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d, yyyy");   // "d M yy");
                deadlineLabel.setText(df.format(item.getDeadline()));
            }
        });

        todoListView.getItems().setAll(TodoData.getInstance().getTodoItems());
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        todoListView.getSelectionModel().selectFirst();
    }

    @FXML
    public void showNewItemDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("todoItemDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DialogController controller = fxmlLoader.getController();
            ToDoItem newItem = controller.processResult();
            todoListView.getItems().setAll(TodoData.getInstance().getTodoItems());
            todoListView.getSelectionModel().select(newItem);
            System.out.println("OK pressed");
        } else {
            System.out.println("Cancel pressed");
        }
    }

    @FXML
    public void handleClickListView() {
        ToDoItem item = todoListView.getSelectionModel().getSelectedItem();
        itemDetailsTextArea.setText(item.getDetails());
        deadlineLabel.setText(item.getDeadline().toString());
/*        StringBuilder sb = new StringBuilder(item.getDetails());
        sb.append("\n\n\n\n");
        sb.append("Due: ");
        sb.append(item.getDeadline().toString());
        itemDetailsTextArea.setText(sb.toString());*/
    }
}