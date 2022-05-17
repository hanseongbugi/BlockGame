import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class TextSource { //�ܾ��� ������ �а� ����ϴ� class
	private Vector<String> v =new Vector<String>(); //������ �ܾ �о �����ϴ� vector
	private String fileName; //������ �̸�
	public TextSource(String fileName) {
		this.fileName=fileName;
		//���Ͽ��� �ܾ �о� ���Ϳ� �߰��Ѵ�.
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
		//���ӿ��� �ʿ��� random �ܾ�
		int index=(int)(Math.random()*v.size());
		return v.get(index);
	}
	public void saveWord(Vector<String> words) {
		//EditPanel���� save�� �ܾ�� ���Ͽ� �߰��ϰ� �ܾ�Ϳ� �߰��Ͽ� ���ӿ��� ���
		try { 
			FileWriter fout=new FileWriter(fileName,true); //������ �߰����� ����
			for(int i=0;i<words.size();i++) {
				String word=words.get(i);
				if(word.length()==0)continue; //i��°�� �ܾ ������ continue�Ѵ�.
				fout.write(word,0,word.length()); //���� �ܾ ���Ͽ� ����
				fout.write("\r\n",0,2); //���� ������� \r\n�� ���Ͽ� ����
				v.add(word); //�ܾ� ���Ϳ� �߰��Ѵ�.
			}
			fout.close(); //������ �ݴ´�.
		}catch(IOException e) {
			System.out.println("file not found");
			System.exit(0);
		}
	}
}
