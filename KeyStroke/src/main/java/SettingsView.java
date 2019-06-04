import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Callback;

import javax.tools.Tool;

public class SettingsView extends GridPane {
	ComboBox<Integer> numOfWords;
	ComboBox<String> fontType;
	ComboBox<Integer> fontSize;
	ComboBox<Integer> indexBeforeSearch;
	ComboBox<String> themeOfProgram;
	CheckBox muteVoice;
	Button saveButton;
	int fontNum  = 16;
	String font = "Arial";


	 SettingsView(){
		numOfWords = new ComboBox<>(FXCollections.observableArrayList(4,5,6,7,8,9));
		fontType = new ComboBox<>(FXCollections.observableArrayList( Font.getFontNames()) );
		fontSize = new ComboBox<>(FXCollections.observableArrayList(16,18,20,22,24,26,28));
		indexBeforeSearch = new ComboBox<>(FXCollections.observableArrayList(1,2,3,4));
		themeOfProgram = new ComboBox<>(FXCollections.observableArrayList("Default","Dark","High Contrast"));
		muteVoice = new CheckBox();
		saveButton = new Button();

		Label label = new Label("Number of Words: ");
		label.setFont(new Font(font, fontNum));
		Tooltip tip = new Tooltip("The number of words suggested.");
		Tooltip.install(numOfWords, tip);
		this.add(label,0,0);
		this.add(numOfWords,1,0);
		numOfWords.setValue(9);
		numOfWords.setPrefWidth(182);

		label = new Label("Fonts Type: ");
		label.setFont(new Font(font, fontNum));
		tip = new Tooltip("Choose the font you want.");
		Tooltip.install(fontType, tip);
		this.add(label,0,1);
		this.add(fontType, 1,1);
		fontType.setValue("Arial");
		fontType.setCellFactory(
			new Callback<ListView<String>, ListCell<String>>() {
				public ListCell<String> call(ListView<String> param) {
					final ListCell<String> cell = new ListCell<String>() {
						{super.setPrefWidth(150); }
						public void updateItem(String item, boolean empty) {
							super.updateItem(item, empty);
							setText(item);
							setFont(new Font(item,25));
							setTextFill(Color.BLACK);
						}
					};
				return cell;
			}
		});

		label = new Label("Font Size: ");
		label.setFont(new Font(font, fontNum));
		tip = new Tooltip("The size of the font.");
		Tooltip.install(fontSize, tip);
		this.add(label, 0,2);
		this.add(fontSize,1,2);
		fontSize.setValue(24);
		fontSize.setPrefWidth(182);

		label = new Label("Letter for search: ");
		label.setFont(new Font(font, fontNum));
		tip = new Tooltip("Number of letters before word suggestions.");
		Tooltip.install(indexBeforeSearch, tip);
		this.add(label, 0, 3);
		this.add(indexBeforeSearch, 1, 3);
		indexBeforeSearch.setValue(2);
		indexBeforeSearch.setPrefWidth(182);

		label = new Label("Theme: ");
		label.setFont(new Font(font, fontNum));
		tip = new Tooltip("Change the look of the application.");
		Tooltip.install(themeOfProgram, tip);
		this.add(label, 0, 4);
		this.add(themeOfProgram, 1, 4);
		themeOfProgram.setValue("Default");
		themeOfProgram.setPrefWidth(182);

		label = new Label("Mute Voice: ");
		label.setFont(new Font(font, fontNum));
		tip = new Tooltip("Mute the reader voice.");
		Tooltip.install(muteVoice, tip);
		this.add(label, 0, 5);
		this.add(muteVoice, 1, 5);
		muteVoice.setSelected(false);

		this.add(saveButton,0,6);
		saveButton.setText("Save");

		this.setVgap(10);
		this.setHgap(10);
		this.setPadding(new Insets(10));
	}

}
