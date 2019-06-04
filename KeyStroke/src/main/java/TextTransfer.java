import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.Toolkit;
import java.io.*;

public final class TextTransfer implements ClipboardOwner {

	/** Simple test harness. */
	public static void main(String...  args){
		TextTransfer textTransfer = new TextTransfer();

		//display what is currently on the clipboard
		System.out.println("Clipboard contains:" + textTransfer.getClipboardContents());

		//change the contents and then re-display
		textTransfer.setClipboardContents("blah, blah, blah");
		System.out.println("Clipboard contains:" + textTransfer.getClipboardContents());
	}

	public void lostOwnership(Clipboard clipboard, Transferable contents){
		//do nothing
	}

	public void setClipboardContents(String string){
		StringSelection stringSelection = new StringSelection(string);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, this);
	}

	public String getClipboardContents() {
		String result = "";
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		//odd: the Object param of getContents is not currently used
		Transferable contents = clipboard.getContents(null);
		boolean hasTransferableText =
				(contents != null) &&
						contents.isDataFlavorSupported(DataFlavor.stringFlavor)
				;
		if (hasTransferableText) {
			try {
				result = (String)contents.getTransferData(DataFlavor.stringFlavor);
			}
			catch (UnsupportedFlavorException | IOException ex){
				ex.printStackTrace();
			}
		}
		return result;
	}
}