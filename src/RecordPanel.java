import java.awt.Color;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import java.util.Scanner;


import javax.swing.JLabel;
import javax.swing.JPanel;

public class RecordPanel extends JPanel{ //순위를 기록하고 보여주는 패널
	private String fileName="record.txt";
	
	private JLabel rankTitle= new JLabel("순위");	 //순위라고 출력할 Label
	private JLabel nameTitle= new JLabel("이름");	 //이름이라고 출력할 Label
	private JLabel scoreTitle=new JLabel("점수"); //점수라고 출력할 Label
	private JLabel rank[]=new JLabel[10]; //순위를 쓸 JLabel배열
	private JLabel name[]=new JLabel[10]; //순위에 따라 이름을 기록할 JLabel배열
	private JLabel score[]=new JLabel[10]; //순위에 따라 점수를 기록할 JLabel배열
	private String userName=null; //게임을 진행하는 user이름
	private int userScore=0;	//게임이 끝난 후 user의 기록
	//파일에 얻거나 기록하기 위한 Vector
	private Vector<String>nameVector = new Vector<String>(10);	
	private Vector<Integer>scoreVector = new Vector<Integer>(10); 
	public RecordPanel() {
		setBackground(Color.CYAN); //배경은 CYAN색
		setLayout(null); //다폴트 배치 관리자
		
		addTitle();  //순위, 이름, 점수를 배치한다
		addRecord(); //점수, 이름, 점수를 담을 JLabel을 배치
		showRecord(); //파일에서 이름과, 점수를 가져와 순위대로 배치
		
	}
	private void addTitle() {//순위, 이름, 점수를 부착
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
		//순위를 나타내는 JLabel배열을 부착
		for(int i=0;i<rank.length;i++) {
			rank[i]=new JLabel(Integer.toString(i+1));
			rank[i].setLocation(0,(20*i)+20);
			rank[i].setSize(30,20);
			add(rank[i]);
		}
		//이름을 기록할 JLabel배열 부착
		for(int i=0;i<name.length;i++) {
			name[i]=new JLabel("");
			name[i].setLocation(50,(20*i)+20);
			name[i].setSize(50,20);
			add(name[i]);
		}
		//점수를 기록할 JLabel배열 부착
		for(int i=0;i<name.length;i++) {
			score[i]=new JLabel("");
			score[i].setLocation(170,(20*i)+20);
			score[i].setSize(50,20);
			add(score[i]);
		}
	}
	public void setName(String name) {
		//게임을 진행하는 User의 이름을 저장
		userName=name;
	}
	public void setScore(int score) {
		//게임을 마친 User의 점수를 저장
		userScore=score;
	}
	private void sortVector() {
		//순위에 맡게 Vector를 정렬
		int len=scoreVector.size();
		int i;
		for(i=0;i<len;i++) {
			//vector를 탐색하여 user의 점수가 더 높으면
			if(scoreVector.get(i)<userScore) 
				break;	
		}
		//벡터의 끝에 붙일 때
		if(i>=len) {
				scoreVector.add(userScore);
				nameVector.add(userName);
				//vector의 크기가 10이 넘으면
				if(scoreVector.size()>name.length) {
					scoreVector.remove(scoreVector.size()-1);
					nameVector.remove(nameVector.size()-1);
				}
			}
		else {
			//중간에 빠져나올 떄
			scoreVector.add(i,userScore);
			nameVector.add(i,userName);
			//Vector의 크기가 10을 넘으면
			if(scoreVector.size()>name.length) {
				scoreVector.remove(scoreVector.size()-1);
				nameVector.remove(nameVector.size()-1);
			}
		}
	}
	private void printRecord() {
		//이름과 점수를 JLabel에 작성한다.
		for(int i=0;i<nameVector.size();i++) 
			name[i].setText(nameVector.get(i));

		for(int i=0;i<scoreVector.size();i++) 
			score[i].setText(Integer.toString(scoreVector.get(i)));
			
	}
	public void showRecord() {
		//파일에서 이름과 점수를 가져온다.
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
		//파일에 기록하는 메소드
		sortVector();//기록하기전 Vector를 정렬한다.
		try { //파일을 읽고 그 안에 있는 정보보다 현재 값이 더 작으면 순서를 바꿔서 파일에 삽입
			FileWriter fout=new FileWriter("record.txt"); //파일을 연다
			for(int i=0;i<nameVector.size();i++) {
				String name=nameVector.get(i);
				String score=Integer.toString(scoreVector.get(i));
				if(name.equals(" "))continue;
				fout.write(name,0,name.length()); //읽은 단어를 파일에 저장
				fout.write(" ",0,1);
				fout.write(score,0,score.length());
				fout.write("\r\n",0,2); //한줄 띄기위해 \r\n을 파일에 저장
			}
		
			fout.close(); //파일을 닫는다.
			//파일에 저장 후 기록을 Panel에 띄우기 위해 vector를 지운다.
			nameVector.clear();
			scoreVector.clear();
		}catch(IOException e) {
			System.out.println("file not found");
			System.exit(0);
		}

	}
	
	
}
