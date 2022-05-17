import java.awt.Color;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import java.util.Scanner;


import javax.swing.JLabel;
import javax.swing.JPanel;

public class RecordPanel extends JPanel{ //������ ����ϰ� �����ִ� �г�
	private String fileName="record.txt";
	
	private JLabel rankTitle= new JLabel("����");	 //������� ����� Label
	private JLabel nameTitle= new JLabel("�̸�");	 //�̸��̶�� ����� Label
	private JLabel scoreTitle=new JLabel("����"); //������� ����� Label
	private JLabel rank[]=new JLabel[10]; //������ �� JLabel�迭
	private JLabel name[]=new JLabel[10]; //������ ���� �̸��� ����� JLabel�迭
	private JLabel score[]=new JLabel[10]; //������ ���� ������ ����� JLabel�迭
	private String userName=null; //������ �����ϴ� user�̸�
	private int userScore=0;	//������ ���� �� user�� ���
	//���Ͽ� ��ų� ����ϱ� ���� Vector
	private Vector<String>nameVector = new Vector<String>(10);	
	private Vector<Integer>scoreVector = new Vector<Integer>(10); 
	public RecordPanel() {
		setBackground(Color.CYAN); //����� CYAN��
		setLayout(null); //����Ʈ ��ġ ������
		
		addTitle();  //����, �̸�, ������ ��ġ�Ѵ�
		addRecord(); //����, �̸�, ������ ���� JLabel�� ��ġ
		showRecord(); //���Ͽ��� �̸���, ������ ������ ������� ��ġ
		
	}
	private void addTitle() {//����, �̸�, ������ ����
		rankTitle.setLocation(0,0);
		rankTitle.setSize(30,20);
		add(rankTitle);
		nameTitle.setLocation(50,0);
		nameTitle.setSize(50,20);
		add(nameTitle);
		scoreTitle.setLocation(170,0);
		scoreTitle.setSize(50,20);
		add(scoreTitle);
	}
	private void addRecord() { 
		//������ ��Ÿ���� JLabel�迭�� ����
		for(int i=0;i<rank.length;i++) {
			rank[i]=new JLabel(Integer.toString(i+1));
			rank[i].setLocation(0,(20*i)+20);
			rank[i].setSize(30,20);
			add(rank[i]);
		}
		//�̸��� ����� JLabel�迭 ����
		for(int i=0;i<name.length;i++) {
			name[i]=new JLabel("");
			name[i].setLocation(50,(20*i)+20);
			name[i].setSize(50,20);
			add(name[i]);
		}
		//������ ����� JLabel�迭 ����
		for(int i=0;i<name.length;i++) {
			score[i]=new JLabel("");
			score[i].setLocation(170,(20*i)+20);
			score[i].setSize(50,20);
			add(score[i]);
		}
	}
	public void setName(String name) {
		//������ �����ϴ� User�� �̸��� ����
		userName=name;
	}
	public void setScore(int score) {
		//������ ��ģ User�� ������ ����
		userScore=score;
	}
	private void sortVector() {
		//������ �ð� Vector�� ����
		int len=scoreVector.size();
		int i;
		for(i=0;i<len;i++) {
			//vector�� Ž���Ͽ� user�� ������ �� ������
			if(scoreVector.get(i)<userScore) 
				break;	
		}
		//������ ���� ���� ��
		if(i>=len) {
				scoreVector.add(userScore);
				nameVector.add(userName);
				//vector�� ũ�Ⱑ 10�� ������
				if(scoreVector.size()>name.length) {
					scoreVector.remove(scoreVector.size()-1);
					nameVector.remove(nameVector.size()-1);
				}
			}
		else {
			//�߰��� �������� ��
			scoreVector.add(i,userScore);
			nameVector.add(i,userName);
			//Vector�� ũ�Ⱑ 10�� ������
			if(scoreVector.size()>name.length) {
				scoreVector.remove(scoreVector.size()-1);
				nameVector.remove(nameVector.size()-1);
			}
		}
	}
	private void printRecord() {
		//�̸��� ������ JLabel�� �ۼ��Ѵ�.
		for(int i=0;i<nameVector.size();i++) 
			name[i].setText(nameVector.get(i));

		for(int i=0;i<scoreVector.size();i++) 
			score[i].setText(Integer.toString(scoreVector.get(i)));
			
	}
	public void showRecord() {
		//���Ͽ��� �̸��� ������ �����´�.
		try{
		Scanner fScanner = new Scanner(new FileReader(fileName));
		while(fScanner.hasNext()) {
			String user=fScanner.nextLine();
			user=user.trim();
			String userInfo[]=user.split(" ");
			String userName=userInfo[0];
			int userScore=Integer.parseInt(userInfo[1]);
			nameVector.add(userName);
			scoreVector.add(userScore);
		}
		printRecord();
		fScanner.close();
	}catch(IOException e) {
			System.out.println("file not found");
			System.exit(0);
		}
	}
	public void recordFile() {
		//���Ͽ� ����ϴ� �޼ҵ�
		sortVector();//����ϱ��� Vector�� �����Ѵ�.
		try { //������ �а� �� �ȿ� �ִ� �������� ���� ���� �� ������ ������ �ٲ㼭 ���Ͽ� ����
			FileWriter fout=new FileWriter("record.txt"); //������ ����
			for(int i=0;i<nameVector.size();i++) {
				String name=nameVector.get(i);
				String score=Integer.toString(scoreVector.get(i));
				if(name.equals(" "))continue;
				fout.write(name,0,name.length()); //���� �ܾ ���Ͽ� ����
				fout.write(" ",0,1);
				fout.write(score,0,score.length());
				fout.write("\r\n",0,2); //���� ������� \r\n�� ���Ͽ� ����
			}
		
			fout.close(); //������ �ݴ´�.
			//���Ͽ� ���� �� ����� Panel�� ���� ���� vector�� �����.
			nameVector.clear();
			scoreVector.clear();
		}catch(IOException e) {
			System.out.println("file not found");
			System.exit(0);
		}

	}
	
	
}
