import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Creates and hold bank of suggested words and next-words. Which will be returned if they
 * Match the query which we are getting from Controller
 *
 * 1.Takes the current words from control
 * 2.Search bank for suggestion words
 * 3.Return list of suggested words
 *
 * 4.Reach Structure of sentence
 * 5.Search bank for suggested next words
 * 6.Returns list of suggested next words
 */
public class Model {
    private String word;
    public IndexDataStructure base;
    public Writer writer;
    public String getWord(){return word;}
    public Voice speaker;
    private String voice;

    public Voice getVoice(){return speaker;}

    public Model() throws AWTException {
        word = "";
        base = new IndexDataStructure();
        readDataBase();
        writer = new Writer();
        voice = "kevin16";
        speaker = new Voice(voice);
    }

    private void readDataBase(){
        try {
            InputStreamReader test =
                    new InputStreamReader(getClass().getClassLoader().getResourceAsStream("20k.txt")) ;
            BufferedReader reader = new BufferedReader(test);


            for(String temp = reader.readLine(); temp!=null; temp=reader.readLine()){
                base.add(temp);
            }

        }catch (IOException e){
            System.out.println(e);
        }

    }

    public void appendToWord(String e){
    	if(!(e.equals("Space") || e.equals("Backspace"))) speaker.say(e);
        Character k = cleaner(e);
        if(k != null)
            word+=Character.toLowerCase(k);
    }

    public List<String> findCloseTo(){
		return base.findList(word);
    }

    private void handleSpace(){
    	speaker.say(word);
        word = "";
    }

    public void handleBackSpace(){
        if(word.length()>0){
            word = word.substring(0,word.length()-1);
        }
    }

    @SuppressWarnings("unchecked")
    public void selectedWord(int k){
    	LinkedList<String> temp = (LinkedList<String>)findCloseTo();
    	try{
    		writer.writeThis(k,temp,word);
    	}catch (InterruptedException e){e.printStackTrace();}

    	if(!temp.isEmpty())
    		speaker.say(temp.get(k));
    }

    public void writeThis(String str){
        writer.writeThis(str.replace(getWord(),""));
        speaker.say(str);
    }

    private Character cleaner(String str){
        HashMap specialKey = new HashMap<String, Character>();
        //specialKey.put("Tab",'\t');
        specialKey.put("Period",'.');
        specialKey.put("Comma",',');
        if(str.length()>1 ){
            if(str.equals("Space")){
                handleSpace();
                return null;
            }else if(str.equals("Backspace")){
                handleBackSpace();
                return null;
            }else if(str.equals("Enter")){
            	handleSpace();
            	return null;
			}else if(str.equals("F11")){
            	handleReading(false);
            	return null;
            }
            return (Character) specialKey.get(str);
        }


        if(Character.isDigit(str.charAt(0))){
            int k = Integer.parseInt(str)-1;
            if(findCloseTo().size() > k)
                selectedWord(k); return null;
        }
        else return str.charAt(0);
    }

    public void handleReading(){
    	String k = writer.saveCopyRestore(true);
		System.out.println(k);
		//speaker.say(writer.saveCopyRestore());
		speaker.say(k);
		handleBackSpace();
	}

	public void handleReading(boolean bool){
		String k = writer.saveCopyRestore(bool);
		System.out.println(k);
		//speaker.say(writer.saveCopyRestore());
		speaker.say(k);
		word = "";
	}

    public static void main(String[] args) throws AWTException{
        Model model = new Model();
		model.word = "wat";
		System.out.println(model.findCloseTo());
    }
}
