import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class IndexDataStructure{
	int indexOfSearch = 1;
	LinkedList<LinkedList<String>> dataStruc;

	public IndexDataStructure(){
		dataStruc = new LinkedList<>();
		setUpDataStruc();
	}

	private void setUpDataStruc(){
		for (int x = 0; x<26; x++){
			dataStruc.add(new LinkedList<>());
		}
	}

	public void add(String str){
		char temp = str.toLowerCase().charAt(0);
		dataStruc.get((int)temp-97).add(str);
	}

	public List<String> findList(String str){
		LinkedList app = new LinkedList();
		if(str.length() < indexOfSearch) return app;

		if(str.length()>0){
			char temp = str.toLowerCase().charAt(0);
			int test = (int)temp-97;
			LinkedList<String> set = test>=0? dataStruc.get(test): dataStruc.get(0);

			for(String e: set){
				if(e.startsWith(str)) app.add(e);
			}
		}
		return app;
	}

	public String toString() {
		String temp = "";
		for (LinkedList e: dataStruc)
			temp+=e.toString();
		return temp;
	}

	public static void main(String[] args) {
		IndexDataStructure base = new IndexDataStructure();
		try {
			String src = "src/main/resources/20k.txt";
			BufferedReader reader = new BufferedReader(new FileReader(src));
			for(String temp = reader.readLine(); temp!=null; temp=reader.readLine()){
				base.add(temp);
			}
		}catch (IOException e){
			System.out.println(e);
		}
		System.out.println(base);

		long start = System.nanoTime();
		System.out.println(base.findList("th"));
		System.out.println(base.findList("ty"));
		System.out.println(base.findList("Water"));
		System.out.println(base.findList("fire"));
		long end = System.nanoTime();

		System.out.println("Time:"+(new DecimalFormat("#0.00000000")).format((end-start)/10E9));
	}
}