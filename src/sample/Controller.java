package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Controller implements Initializable {

	@FXML
	ListView todoList;

	@FXML
	TextField todoText;

	ObservableList<ToDoItem> todoItems = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		todoList.setItems(todoItems);
	}

	public void addOnEnter(KeyEvent e) {
		if(e.getCode().equals(KeyCode.ENTER)) {
			System.out.println("Adding item...");
			todoItems.add(new ToDoItem(todoText.getText()));
			todoText.setText("");
			saveList();
		}
	}

	public void addItem() {
		System.out.println("Adding item ...");
		todoItems.add(new ToDoItem(todoText.getText()));
		todoText.setText("");
		saveList();
	}

	public void removeItem() {
		System.out.println("Removing item ...");
		ToDoItem todoItem = (ToDoItem)todoList.getSelectionModel().getSelectedItem();
		System.out.println("Removing " + todoItem.text + " ...");
		todoItems.remove(todoItem);
		saveList();
	}

	public void toggleItem() {
		System.out.println("Toggling item ...");
		ToDoItem todoItem = (ToDoItem)todoList.getSelectionModel().getSelectedItem();
		if (todoItem != null) {
			todoItem.isDone = !todoItem.isDone;
			todoList.setItems(null);
			todoList.setItems(todoItems);
			saveList();
		}
	}

	public void allDone() {
		System.out.println("Everything done...");
		for (ToDoItem item : todoItems) {
			if (item != null) {
				item.isDone = true;
				todoList.setItems(null);
				todoList.setItems(todoItems);
				saveList();
			}
		}
	}

	public void noneDone() {
		System.out.println("Nothing is done!");
		for (ToDoItem item : todoItems) {
			if (item != null) {
				item.isDone = false;
				todoList.setItems(null);
				todoList.setItems(todoItems);
				saveList();
			}
		}

	}

	public void toggleAll() {
		System.out.println("Toggling...");
		for (ToDoItem item : todoItems) {
			if (item != null) {
				item.isDone = !item.isDone;
				todoList.setItems(null);
				todoList.setItems(todoItems);
				saveList();
			}
		}
	}

	public void saveList() {
		try {
			File listFile = new File("list.txt");
			FileWriter listWriter = new FileWriter(listFile);
			for (ToDoItem item : todoItems) {
				listWriter.write(item.text);
				if (item.isDone) {
					listWriter.write(".true\n");
				} else {
					listWriter.write(".false\n");
				}
			}
			listWriter.close();
		}
		catch(IOException ioEx){
			ioEx.printStackTrace();
		}

	}

	public void readList() {
		try { File listFile = new File("list.txt");
			Scanner listScanner = new Scanner(listFile);
			while(listScanner.hasNext()) {
				for (ToDoItem item : todoItems) {
					if (item != null) {
						todoItems.add(new ToDoItem(listScanner.nextLine()));
					}
				}
			}
		}
		catch (FileNotFoundException exception) {

		}
	}
}
