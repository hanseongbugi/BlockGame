import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class TextSource { //단어의 파일을 읽고 기록하는 class
	private Vector<String> v =new Vector<String>(); //파일의 단어를 읽어서 저장하는 vector
	private String fileName; //파일의 이름
	public TextSource(String fileName) {
		this.fileName=fileName;
		//파일에서 단어를 읽어 벡터에 추가한다.
		try {
			Scanner fScanner = new Scanner(new FileReader(fileName));
			while(fScanner.hasNext()) {
				String word=fScanner.nextLine();
				v.add(word);
			}
			fScanner.close();
		}catch(IOException e) {
			System.out.println("file not found");
			System.exit(0);
		}
	}
	
	public String getRandomWord() {
		//게임에서 필요한 random 단어
		int index=(int)(Math.random()*v.size());
		return v.get(index);
	}
	public void saveWord(Vector<String> words) {
		//EditPanel에서 save한 단어는 파일에 추가하고 단어벡터에 추가하여 게임에서 사용
		try { 
			FileWriter fout=new FileWriter(fileName,true); //파일을 추가모드로 연다
			for(int i=0;i<words.size();i++) {
				String word=words.get(i);
				if(word.length()==0)continue; //i번째에 단어가 없으면 continue한다.
				fout.write(word,0,word.length()); //읽은 단어를 파일에 저장
				fout.write("\r\n",0,2); //한줄 띄기위해 \r\n을 파일에 저장
				v.add(word); //단어 벡터에 추가한다.
			}
			fout.close(); //파일을 닫는다.
		}catch(IOException e) {
			System.out.println("file not found");
			System.exit(0);
		}
	}
}
