import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Image;

public class ScorePanel extends JPanel{
	private int score=0; //점수
	private int scoreValue=10; //획득 점수
	
	private JLabel textLabel= new JLabel("점수");
	private JLabel scoreLabel= new JLabel(Integer.toString(score)); //점수를 나타내는 label
	private JLabel lifeText=new JLabel("수명");
	private JLabel lifeImage[]=new JLabel[5]; //빨간 하트를 담을 배열
	private JLabel dathImage[]=new JLabel[5];//흰 하트를 담을 배열
	private ImageIcon redHeart=new ImageIcon(getClass().getClassLoader().getResource("redHeart.jpg")); //빨간하트이미지 생성
	private ImageIcon changeRedHeart=null; //크기를 조정한 이미지를 담을 변수
	private ImageIcon whiteHeart=new ImageIcon(getClass().getClassLoader().getResource("whiteHeart.jpg"));
	private ImageIcon changeWhiteHeart=null; //크기를 조정한 이미지를 담을 변수
	private GamePanel gamePanel=null; //gamePanel레퍼런스 변수
	private GameFrame gameFrame=null;
	private RecordPanel recordPanel=null; //recordPanel 레퍼런스 변수
	private int life=5; //life는 5이다.
	//기본 JOptionPane이미지가 불려오지 않아 사용
	private ImageIcon qustionImg=new ImageIcon(getClass().getClassLoader().getResource("qustion.png"));
	private JLabel qustion=null; //JOptionPane에 사용할 Label
	
	public ScorePanel(GameFrame gameFrame,RecordPanel recordPanel) {
		this.gameFrame=gameFrame;
		this.recordPanel=recordPanel;
		this.setBackground(Color.CYAN); //배경은 CYAN색
		setLayout(null); //디폴트 배치관리자로 한다.
	
		Image redImage=redHeart.getImage(); //Image객체로 
		Image redImg=redImage.getScaledInstance(20,20,Image.SCALE_SMOOTH); //이미지 크기를 바꾼다.
		changeRedHeart=new ImageIcon(redImg); // redHeart변수에 크기를 바꾼 이미지를 생성하여 삽입
		
		Image whiteImage=whiteHeart.getImage();
		Image whiteImg=whiteImage.getScaledInstance(20,20,Image.SCALE_SMOOTH); //이미지 크기를 바꾼다.
		changeWhiteHeart=new ImageIcon(whiteImg); // redHeart변수에 크기를 바꾼 이미지를 생성하여 삽입

		//"점수"라고 쓰인 Label의 위치,크기를 정하고 부착
		textLabel.setSize(50,20);
		textLabel.setLocation(10,10);
		add(textLabel);
		
		//score가 기록될 label의 위치,크기를 정하고 부착
		scoreLabel.setSize(100,20);
		scoreLabel.setLocation(70,10);
		add(scoreLabel);
		
		addLifeImage(); //하트를 부착한다.
		
		//"수명"이라고 쓰인 label의 위치,크기를 정하고 부착
		lifeText.setSize(50,20);
		lifeText.setLocation(10,50);
		add(lifeText);
		
		//qustionImg의 크기를 조절하고 
		qustionImg=imageSetSize(qustionImg,25,25);
		//JLabel을 왼쪽정렬하여 생성 
		qustion=new JLabel("계속하시겠습니까?",qustionImg,JLabel.LEFT);
	}
	//이미지 크기를 조절하는 메소드
	private ImageIcon imageSetSize(ImageIcon icon,int x,int y) {
		Image img=icon.getImage();
		Image setImg=img.getScaledInstance(x,y,Image.SCALE_SMOOTH);
		ImageIcon setIcon=new ImageIcon(setImg);
		return setIcon;
	}
	public void scoreEvent() {
		score+=10;
	}
	
	public void getGamePanel(GamePanel gamePanel) {
		this.gamePanel=gamePanel;
	}
	
	private void addLifeImage() {
		
		for(int i=0;i<lifeImage.length;i++) {
			//빨간 하트는 5개를 붙이고
			lifeImage[i]=new JLabel(changeRedHeart);
			lifeImage[i].setLocation(50+(i*20),50);
			lifeImage[i].setSize(20,20);
			this.add(lifeImage[i]);
			
			//흰 하트는 5개를 visible false인 상태로 붙인다.
			dathImage[i]=new JLabel(changeWhiteHeart);
			dathImage[i].setLocation(50+(i*20),50);
			dathImage[i].setSize(20,20);
			this.add(dathImage[i]);
			dathImage[i].setVisible(false);
		}
		
		
	}
	private void resetLifeImage() { //빨간 하트를 5개 다시 띄운다.
		for(int i=0;i<lifeImage.length;i++) {
			dathImage[i].setVisible(false);
			lifeImage[i].setVisible(true);
		}
	}
	public void increase(String star) { //노란별은 기본 점수
		if(star.equals("yellow")) 
			score+=scoreValue;
		else
			score+=(scoreValue*3);   //녹색별을 맞추면 원래 점수의 3배를 더 준다.
		scoreLabel.setText(Integer.toString(score));
	}
	public boolean misMatch() {
		life-=1;
		
		
		lifeImage[life].setVisible(false); //빨간하트 하나를 지우고
		dathImage[life].setVisible(true); //흰 하트 하나를 보이게 한다.
		
		dathImage[life].getParent().repaint(); //다시그려 반영한다.
		
		if(life==0){ //life가 0이 되면 게임 결과를 기록하고 파일에 저장 후 모든 스레드 종료
			recordPanel.setScore(score);
			recordPanel.recordFile();
			for(int i=0;i<gamePanel.wordPanel.length;i++)
				gamePanel.stopGame(i);
			recordPanel.showRecord();
		
			while(true) {
				//JOptionPane을 gameFrame위치에 생성, yes/no를 받는다.
				//Interrupted while load image가 
				//JOptionPane을 띄울때 마다 출력되어 PLAIN_MESSAGE로 하였습니다. 
			int result=JOptionPane.showConfirmDialog(gameFrame,qustion,"selection",
						JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE);
			if(result==JOptionPane.YES_OPTION) { //yes를 누르면 다시 시작
				 resetLife();
				 resetLifeImage();
				 resetScore();
				 gamePanel.gameLevel();
				 
				 return false;
				
			}
			else if(result==JOptionPane.NO_OPTION) //no를 누르면 종료 
				System.exit(0);
			}//창을 닫으면 optionPane이 다시 생성됩니다.
		}
		return true;
	}
	private void resetScore() { //score를 0으로 하고 scoreLabel에 입력.
		score=0;
		scoreLabel.setText(Integer.toString(score));
	}
	public void resetLife() { //life를 5로 초기화
		life=5;
	}
	
	
}
