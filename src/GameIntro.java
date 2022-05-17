import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
public class GameIntro extends JPanel{ //게임의 인트로 Panel
	private JLabel title=new JLabel("별 낙하 게임"); //게임의 제목
	private JLabel start=new JLabel("start"); //start Label
	private JLabel exit=new JLabel("exit"); //exit Label
	private GameFrame gameFrame=null; //gameFrame을 가르킬 레퍼런스 변수
	private ImageIcon icon=new ImageIcon(getClass().getClassLoader().getResource("intro.jpg")); //intro배경
	private Image img=icon.getImage(); //imageIcon을 Image객체로.
	public GameIntro(GameFrame gameFrame) {
		this.gameFrame=gameFrame;
		//intro panel의 크기는 JFrame의 크기와 같다 
		setSize(800,600);
		setLayout(null); //배치관리자는 디폴트 배치관리자
		
		//게임 재목의 font는 궁서체 60px로
		title.setFont(new Font("궁서",Font.BOLD,60));
		//글자 색은 Yellow
		title.setForeground(Color.YELLOW);
		//Label의 크기와 위치를 정한다.
		title.setSize(500,100);
		title.setLocation(230,100);
		
		//start Label의 font는 궁서체 40px
		start.setFont(new Font("궁서",Font.BOLD,40));
		//글자 색은 yellow
		start.setForeground(Color.YELLOW);
		//label의 위치와 크기를 정한다.
		start.setSize(150,50);
		start.setLocation(350,300);
		
		//exit Label의 font는 궁서체 40px
		exit.setFont(new Font("궁서",Font.BOLD,40));
		//글자 색은 검노란 색이다. 기존 노란색보다 어두은 RGB값을 사용
		exit.setForeground(new Color(153,153,0));
		//위치와 크기를 정한다.
		exit.setSize(150,50);
		exit.setLocation(360,350);
		add(title);
		add(start);
		add(exit);
		
		//start와 exit label에 actionListener를 부착
		start.addMouseListener(new IntroAction());
		exit.addMouseListener(new IntroAction());
	}
	//배경을 그리는 메소드(화면에 꽉차게 그린다)
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g); //패널을 지우는 기능
		g.drawImage(img,0,0,getWidth(),getHeight(),null); //img를 그립니다.
		
	}
	
	class IntroAction extends MouseAdapter{
		//start나 exit label을 클릭하면 호출
		@Override
		public void mousePressed(MouseEvent e) {
			JLabel la=(JLabel)e.getSource();
			//exit를 누르면 종료
			if(la.getText().equals("exit"))System.exit(0);
			//start를 누르면 게임에 필요한 panel들을 보이게 한다.
			else gameFrame.loadGame();
		}
	}
}
