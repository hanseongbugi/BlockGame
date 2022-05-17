import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Container;
public class GamePanel extends JPanel{//game을 진행하는 Panel
	private int delay; //단어가 떨어지는 속도
	private JTextField input=new JTextField(40);//단어를 입력할 text field
	private boolean pauseGame=false;//게임의 상태를 나타낸다.
	
	private ScorePanel scorePanel=null;
	private EditPanel editPanel=null;
	private TextSource textSource=null;
	
	//private FallingWord thread =null;//스레드 레퍼런스 변수
	private JLabel wordLabel[]=new JLabel[5];
	//스레드 배얼
	private FallingWord th[]=new FallingWord[5];
	//단어 panel배열
	public WordPanel wordPanel[]=new WordPanel[5];

	private GameGroundPanel gameGround=new GameGroundPanel();
	
	
	public GamePanel(ScorePanel scorePanel,EditPanel editPanel,TextSource textSource) {
		
		
		this.scorePanel=scorePanel;
		this.editPanel=editPanel;
		this.textSource=textSource;
		
		//배치관리자는 borderLayout으로
		setLayout(new BorderLayout());
		//게임 배경이 되는 panel를 CENTER에 부착
		add(gameGround,BorderLayout.CENTER);
		//단어를 입력할 Panel을 SOUTH에 부착
		add(new InputPanel(),BorderLayout.SOUTH);
		
		//textField에 actionListener를 붙인다.
		input.addActionListener(new InputAction());

	}
	
	public void startGame(int index) {

		Container c=wordPanel[index].getParent();
		
		//게임이 멈추어 있었으면 현재 위치에서 다시 스레드를 만들어 단어를 이동
		if(getStatus()==true) {
			th[index]=new FallingWord(this,wordPanel[index],wordLabel[index],index,scorePanel,delay);
			th[index].start();
			return ;
		}
		
		
		String word=textSource.getRandomWord();
		//x는 0부터 (페널이 붙어있는 컨포넌트의 width-200) 사이의 랜덤 정수
		int x=(int)(Math.random()*(c.getWidth()-200));
		//y는 0-99사이의 정수
		int y=(int)(Math.random()*100);
		
		//다시시작 하는 경우를 대비하여 별을 다시 한번 바꾼다.
		wordPanel[index].changeStar();
		//wordPanel의 위치를 set하고
		wordPanel[index].setLocation(x,y); 
		//wordLabel의 배경을 투명하게 하고
		wordLabel[index].setOpaque(false);
		//font는 고딕체 20px
		wordLabel[index].setFont(new Font("고딕",Font.ITALIC,20));
		wordLabel[index].setForeground(Color.MAGENTA);
		//label에 text를 word로 한다, 
		wordLabel[index].setText(word);
		
		if(!wordPanel[index].isVisible())  //패널이 안보이는 상태면 보이도록 바꾼다.
			wordPanel[index].setVisible(true);
		
		c.repaint();
		
		//스레드 하나를 생성한다.	
		th[index]=new FallingWord(this,wordPanel[index],wordLabel[index],index,scorePanel,delay);
		//스레드 시작
		th[index].start();		
	}
	
	public void stopGame(int index) {
		FallingWord thread=th[index];
		//wordPael을 가린다.
		if(wordPanel[index].isVisible())wordPanel[index].setVisible(false);
		//스레드가 없으면 return
		if(thread==null)return;
		//스레드 종료
		thread.interrupt();
		thread=null;
	
	}
	

	
	public void pauseGame(int index) {
		//일시정지를 위해 스레드를 종료.
		FallingWord thread =th[index];
		if(thread ==null)return;
		thread.interrupt();
		thread=null;
	}
	public void gameStatus() {
		//게임의 상태를 바꾼다.
		if(pauseGame==false)pauseGame =true;
		else pauseGame=false;
	}
	public boolean getStatus() {
		//게임의 상태 반환 false면 처음부터 시작
		//true면 일시정지 상태
		return pauseGame;
	}

	 class GameGroundPanel extends JPanel{
		public JButton easy=new JButton("easy"); //delay=500
		public JButton normal=new JButton("normal"); //delay=100
		public JButton hard=new JButton("hard"); //delay=50
		private SkyPanel skyPanel=new SkyPanel();
		
		public GameGroundPanel() {//게임의 배경이 되는 패널
			//배치관리자는 디폴트 배치관리자
			setLayout(null);
		
			//easy버튼의 위치와 크기를 정하고 붙인다.
			easy.setLocation(30,150);
			easy.setSize(80,50);
			add(easy);
			//normal버튼의 위치와 크기를 정하고 붙인다.
			normal.setLocation(230,150);
			normal.setSize(80,50);
			add(normal);
			//hard버튼의 위치와 크기를 정하고 붙인다.
			hard.setLocation(430,150);
			hard.setSize(80,50);
			add(hard);
			//모든 난이도 버튼을 숨긴다.
			easy.setVisible(false);
			normal.setVisible(false);
			hard.setVisible(false);
			
			for(int i=0;i<5;i++) {
				//wordLabel배열이 JLabel을 가르키게한다.
				wordLabel[i]=new JLabel("");
				//wordPanel의 생성자를 호출 wordLabel을 매개로 준다.
				wordPanel[i]=new WordPanel(wordLabel[i]);
				//생성한 wordPanel을 붙이고
				add(wordPanel[i]);
				//보이지 않게 한다.
				wordPanel[i].setVisible(false);
			}
			this.addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(ComponentEvent e) {
					//컴포넌트의 크기가 변화하였을 때 실행
					//skyPanel의 위치와 크기를 주고
					skyPanel.setLocation(0,0);
					skyPanel.setSize(GameGroundPanel.this.getWidth(),GameGroundPanel.this.getHeight());
					//붙인다.
					add(skyPanel);
				
				}
			});
			//난이도 버튼에 actionListener를 붙인다.
			easy.addActionListener(new GameLevelAction());
			normal.addActionListener(new GameLevelAction());
			hard.addActionListener(new GameLevelAction());
		
		}
		
		class SkyPanel extends JPanel{//밤하늘 배경 panel
			ImageIcon icon=new ImageIcon(getClass().getClassLoader().getResource("background.jpg"));
			Image img=icon.getImage();
		
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g); //패널을 지우는 기능
				
				g.drawImage(img,0,0,getWidth(),getHeight(),null); //img를 그립니다.
			
			}
		}
		class GameLevelAction implements ActionListener{
			//난이도 버튼을 누르면 호출
			@Override
			public void actionPerformed(ActionEvent e) {
				//누른 button을 얻고
				JButton button=(JButton)e.getSource();
				//그 버튼의 부모를 얻는다.
				Container c=button.getParent();
				//button의 text를 level변수가 가르키게 한다.
				String level=button.getText();
				if(level.equals("easy")) {
					//easy일 경우 0.5초씩 떨어짐
					delay=500;	
				}
				else if(level.equals("normal")) {
					//normal일 경우 0.1초씩 떨어짐
					delay=100;	
				}
				else {
					//hard일 경우 0.05초씩 떨어짐
					delay=50;	
				}
				//모든 버튼을 숨긴다.
				easy.setVisible(false);
				normal.setVisible(false);
				hard.setVisible(false);
				//gameGround패널을 다시 그린다.
				c.repaint();
				//life를 초기화
				scorePanel.resetLife();
				//개임을 시작
				for(int i=0;i<5;i++)
					startGame(i);
				

			}
		}
		
	}
	
	public void gameLevel() {
		 //모든 난이도 버튼을 보이게 한다.
		gameGround.easy.setVisible(true);
		gameGround.normal.setVisible(true);
		gameGround.hard.setVisible(true);
	
	}
	

	class InputPanel extends JPanel{ //inputPanel을 생성
		
		public InputPanel() {
			setLayout(new FlowLayout());//배치관리자는 FlowLayout
			setBackground(new Color(153,76,0));//panel의 배경은 갈색계열
			add(input);//JTextField를 붙인다.
			
		}	
	}
	
	class InputAction implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JTextField t=(JTextField)e.getSource();
			String inword=t.getText();
			if(getStatus()==true) { //게임이 멈춰있으면 textField를 지우고 return한다.
				t.setText("");
				return ;
			}
			for(int i=0;i<wordLabel.length;i++) {
				
				if(wordLabel[i].getText().equals(inword)) { //맞추기 성공
					//점수 올리기
					scorePanel.increase(wordPanel[i].getStar());
					stopGame(i);
					wordPanel[i].changeStar();
					startGame(i);
				
					//input창 지우기
					t.setText(""); 
					}
				}
			}
	}
		
}
