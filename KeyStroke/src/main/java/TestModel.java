import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestModel {
    static Model model;
    public static void main(String[] args)  throws AWTException {
        model = new Model();
        Logger.getLogger(GlobalScreen.class.getPackage().getName()).setLevel(Level.OFF);
        try {

            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(new NativeKeyListener() {

                public void nativeKeyTyped(NativeKeyEvent nativeEvent) { }

                public void nativeKeyReleased(NativeKeyEvent nativeEvent) {

                    model.appendToWord(NativeKeyEvent.getKeyText(nativeEvent.getKeyCode()));

                    System.out.println("Word: "+model.getWord());
                    System.out.println("Key: "+NativeKeyEvent.getKeyText(nativeEvent.getKeyCode()));
                    System.out.println("Search: "+model.findCloseTo());
					System.out.println("=======================================================================");

                }

                public void nativeKeyPressed(NativeKeyEvent nativeEvent) { }
            });
        }
        catch (NativeHookException e) {
            e.printStackTrace();
        }
    }
}