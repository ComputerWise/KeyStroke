import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javafx.application.*;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.awt.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller extends Application{
	private Model model;
	private VBox optionPane;
	private Label label;
	private Button settingsButton;
	private Button readSelected;
	private Scene mainScene;
	private SettingsView settingsView;
	private Scene settingsScene;
	private Stage prime;


	// for settings
	private int fontsize = 24;
	private String fontType = "Arial";
	private int numOfWords = 9;

	public void start(Stage prime) throws AWTException {
		this.prime = prime;
		model = new Model();
		optionPane = new VBox();
		settingsView = new SettingsView();
		GridPane mainView = new GridPane();
		mainScene = new Scene(mainView, 350, 500);
		Tooltip tip = new Tooltip();

		mainView.add(optionPane, 0,0,2,1);
		optionPane.setPrefHeight(Integer.MAX_VALUE);

		settingsScene = new Scene(settingsView, 350, 500);

		ImageView imageView = new ImageView(
				new Image(getClass().getResourceAsStream("settings_1108180.png")));
		imageView.setFitHeight(100);
		imageView.setFitWidth(100);

		settingsButton = new Button("" , imageView);
		mainView.add(settingsButton, 0,1);
		tip.setText("Goes into Settings");
		Tooltip.install(settingsButton, tip);

		ImageView imageView1 = new ImageView(new Image(getClass().getResourceAsStream("speak.png")));
		imageView1.setFitWidth(100);
		imageView1.setFitHeight(100);

		readSelected = new Button("", imageView1);
		mainView.add(readSelected, 1,1);
		tip = new Tooltip();
		tip.setText("F11 (plays whatever you selected)");
		Tooltip.install(readSelected, tip);

		handlers();
		Logger.getLogger(GlobalScreen.class.getPackage().getName()).setLevel(Level.OFF);


		try{
			GlobalScreen.registerNativeHook();
			GlobalScreen.addNativeKeyListener(new NativeKeyListener() {

				public void nativeKeyTyped(NativeKeyEvent nativeEvent) { }

				public void nativeKeyReleased(NativeKeyEvent nativeEvent) { }

				public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
					String temp = NativeKeyEvent.getKeyText(nativeEvent.getKeyCode());
					model.appendToWord(temp);
					printWord();
				}
			});
		}catch(NativeHookException e){e.printStackTrace();}
		prime.setScene(mainScene);
		prime.setTitle("Test");
		prime.setAlwaysOnTop(true);
		prime.getIcons().add(new Image(getClass().getResourceAsStream("art.png")));
		prime.show();
	}

	private void printWord(){
		List<String> arr = model.findCloseTo();
		Platform.runLater(()-> {
			optionPane.getChildren().clear();
			if(!arr.isEmpty()) {
				for (int index = 0; index < Math.min(numOfWords, arr.size()); index++) {
					label = new Label((index) + 1 + " " + arr.get(index));
					label.setFont(new Font(fontType, fontsize));
					label.setOnMouseClicked(ev -> model.writeThis(label.getText()));
					optionPane.getChildren().add(label);
				}
			}
		});
	}

	private void handlers(){
		settingsButton.setOnAction(event -> {
			if(prime.getScene().equals(mainScene))
				prime.setScene(settingsScene);
			else
				prime.setScene(mainScene);
		});

		settingsView.saveButton.setOnAction(ev->{
			switchScene(prime, mainScene);
			fontType = settingsView.fontType.getValue();
			fontsize = settingsView.fontSize.getValue();
			numOfWords = settingsView.numOfWords.getValue();
			model.getVoice().mute(settingsView.muteVoice.isSelected());
			model.base.indexOfSearch = settingsView.indexBeforeSearch.getValue();

			if(settingsView.themeOfProgram.getValue().equals("Dark")){mainScene.getStylesheets().add("mac_os.css");
			settingsScene.getStylesheets().add("mac_os.css");}
			if(settingsView.themeOfProgram.getValue().equals("Default")){mainScene.getStylesheets().clear();
			settingsScene.getStylesheets().clear();}
			if(settingsView.themeOfProgram.getValue().equals("High Contrast")){mainScene.getStylesheets().add("darkContrast.css");
			settingsScene.getStylesheets().add("darkContrast.css");}
			printWord();
		});

		readSelected.setOnAction(ev-> model.handleReading());
	}


	private void switchScene(Stage prime, Scene scene){
		prime.setScene(scene);
	}

	public void stop(){
		System.exit(0);
	}
	
	public static void main(String []args) { launch(args); }
}