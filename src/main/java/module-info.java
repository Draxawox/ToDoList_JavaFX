module com.example.todolist {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.karolw.todolist to javafx.fxml;
    exports com.karolw.todolist;
}