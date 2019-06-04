import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.LinkedList;

public class Writer {
	private Robot robot;
	TextTransfer textTransfer;
	public Writer() throws AWTException{
		robot = new Robot();
		textTransfer = new TextTransfer();
	}

	public void writeThis(String str){
		int k;
		switchProgram();

		for (char e: str.toCharArray()){
			k = KeyStroke.getKeyStroke(Character.toUpperCase(e),1).getKeyCode();
			robot.keyPress(k);
			robot.keyRelease(k);
		}
	}

	public void writeThis(int k, LinkedList<String> list, String word)throws InterruptedException{
		robot.keyPress(KeyEvent.VK_BACK_SPACE);
		robot.keyRelease(KeyEvent.VK_BACK_SPACE);
		Thread.sleep(100);
		if(k>=0 && k<list.size())
			for (char e: list.get(k).replace(word,"").toCharArray()){
				k = KeyStroke.getKeyStroke(Character.toUpperCase(e),1).getKeyCode();
				robot.keyPress(k);
				robot.keyRelease(k);
			}
	}

	private void switchProgram(){
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_TAB);

		robot.keyRelease(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_ALT);
		try{ Thread.sleep(100);}catch (InterruptedException e){e.printStackTrace();}
	}

	private void copySelected(boolean bool){
		if(bool)
			switchProgram();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_C);

		robot.keyRelease(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		try{ Thread.sleep(100);}catch (InterruptedException e){e.printStackTrace();}
	}

	public String saveCopyRestore(boolean bool){
		String temp = textTransfer.getClipboardContents();
		copySelected(bool);
		String temp2 = textTransfer.getClipboardContents();
		textTransfer.setClipboardContents(temp);
		return temp2;
	}

	public static void main(String[] args) throws IOException, InterruptedException, AWTException {
//		Runtime run = Runtime.getRuntime();
//		run.exec("notepad.exe");
//
//
//		Thread.sleep(2000);
//
//		Writer writer = new Writer();
//		writer.writeThis("henri is the best from east to west");

	}
} 