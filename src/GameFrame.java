import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GameFrame extends JFrame{
	
	//JMenuItem객체 3개를 생성
	private JMenuItem startItem = new JMenuItem("start");
	private JMenuItem stopItem = new JMenuItem("stop");
	private JMenuItem exitItem=new JMenuItem("exit");
	
	//JButton객체 2개 생성 ToolBar만드는데 사용
	private JButton startButton=new JButton("start");
	private JButton stopButton=new JButton("stop");
	
	//이름을 입력할 dialog를 가르킬 변수
	private UserDialog dialog=null;
	//startButton과 stopButton을 붙일 tool bar생성
	private JToolBar tBar=new JToolBar();
	//JMenuItem들을 붙일 JMenuBar생성
	private JMenuBar mBar = new JMenuBar();
	private JSplitPane hPane = new JSplitPane(); //JSplitpane인 hPane생성
	private JSplitPane pPane= new JSplitPane(); //JSplitpane인 pPane생성
	private JSplitPane srPane=new JSplitPane(); //scorePanel과 recordPanel을 붙이기 위해 JSplitPane생성
	
	//게임에 필요한 각종 패널들
	private RecordPanel recordPanel =new RecordPanel();
	private TextSource textSource=new TextSource("word.txt");
	private ScorePanel scorePanel =new ScorePanel(this,recordPanel);
	private EditPanel editPanel =new EditPanel(textSource);
	private	GamePanel gamePanel=new GamePanel(scorePanel,editPanel, textSource);
	//gameIntro를 위한 JPanel이다.
	private GameIntro gameIntro=new GameIntro(this);
	
	public GameFrame() {
		setTitle("별 낙하 게임");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //창을 닫으면 프로그램 종료
		setSize(800,600); 
		
		Container c=getContentPane(); //contentPane을 얻어
		c.add(gameIntro);//gameIntro를 붙인다.
		
		splitPane(); //JSplitPane을 생성하여 컨탠트팬의 CENTER에 부착
		makeMenu();//menu를 만들어 부착
		makeToolBar();//toolbar를 만들어 컨탠츠팬의 NORTH에 부착
		
		
		this.setResizable(false); //프레임을 조절할 수 없도록한다.
		
		//scorePanel의 생성자를 호출할 때 
		//gamePanel을 주지 못하여 함수 호출로 gamePanel을준다.
		scorePanel.getGamePanel(gamePanel); 
		
		//플레이어 이름을 얻는 dialog생성
		dialog=new UserDialog(this,"유저 정보 입력");
		
	
		setVisible(true);
		
	
	}
	public void loadGame() {//intro화면에서 start를 누르면 호출
		
		hPane.setVisible(true);
		mBar.setVisible(true);
		tBar.setVisible(true);
		remove(gameIntro);
		revalidate();
		repaint();
	}
	private void splitPane() {//JSplitPane을 이용하여 분할
		
		//컨탠츠팬의 디폴트인 BorderLayout의 CENTER에 붙인다
		getContentPane().add(hPane,BorderLayout.CENTER); 
		//수평으로 hPane을 나눈다.
		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		//왼쪽에 gamePanel을 부착
		hPane.setLeftComponent(gamePanel);
		//550픽셀로 나는다
		hPane.setDividerLocation(550);
		//쪼재주는 선을 움직일 수 없게한다.
		hPane.setEnabled(false);
		
		
	
		//위아래로 pPane을 나눈다.
		pPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		//400픽셀로 나눈다.
		pPane.setDividerLocation(400);
		//아래쪽에 editPanel을 부착
		pPane.setBottomComponent(editPanel);
		
		//위아래로 srPane을 나눈다
		srPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		//150픽셀로 나눈다.
		srPane.setDividerLocation(150);
		//위쪽에는 scorePanel을 붙인다.
		srPane.setTopComponent(scorePanel);
		//아래쪽은 recordPanel을 붙인다.
		srPane.setBottomComponent(recordPanel);
		srPane.setEnabled(false);
		
		
		pPane.setTopComponent(srPane);
		//hPane의 오른쪽 컴포넌트에 pPane을 붙인다.
		hPane.setRightComponent(pPane);
		//쪼재주는 선을 움직일 수 없게한다.
		pPane.setEnabled(false);
		
		//intro를 위해 hPane을 볼 수 없게 한다.
		hPane.setVisible(false);
	}
	 
	private void makeMenu() {
		
		setJMenuBar(mBar);//menuBar로 mBar를 붙인다.
		//game이란 이름의 menu를 생성
		JMenu fileMenu = new JMenu("Game");
		//menu에 item을 붙인다.
		fileMenu.add(startItem);
		fileMenu.add(stopItem);
		//분리선을 붙인다.
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		
		//메뉴바에 menu를 붙인다.
		mBar.add(fileMenu);
		
		//intro를 위해 메뉴바를 숨긴다.
		mBar.setVisible(false);
		//메뉴바의 item에 actionListenr를 붙인다.
		startItem.addActionListener(new StartAction(scorePanel));
		stopItem.addActionListener(new StopAction());
		exitItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
	}
	private void makeToolBar() { //toolbar를 만들어 컨탠츠팬 NORTH애 붙인다.
		//toolbar에 button을 붙인다
		tBar.add(startButton); 
		tBar.add(stopButton);
		getContentPane().add(tBar,BorderLayout.NORTH);
		
		//button에 actionListener를 붙인다.
		startButton.addActionListener(new StartAction(scorePanel));
		stopButton.addActionListener(new StopAction());
		
		//intro를 위해 숨긴다.
		tBar.setVisible(false);
		
	}	
	private class StopAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			//게임을 일시정지하는 action
			for(int i=0;i<gamePanel.wordPanel.length;i++)
				gamePanel.pauseGame(i);
			
			gamePanel.gameStatus();  //게임에 pause를 건다.
		}
		
	}

	private class StartAction implements ActionListener{
		private ScorePanel scorePanel=null;
		public StartAction(ScorePanel scorePanel) {
			this.scorePanel=scorePanel;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			//start버튼 action
			if(gamePanel.getStatus()) {
				//게임 중이면 일시정지를 풀고
				for(int i=0;i<gamePanel.wordPanel.length;i++)
					gamePanel.startGame(i);
				gamePanel.gameStatus(); //pause상태를 푼다.
			}//게임을 시작하는 것이면 dialog를 보이게 한다.
			else dialog.setVisible(true);
		}
	}
	class UserDialog extends JDialog{ //게임을 진할할 User의 이름을 입력할 Dialog
		private JButton okButton = new JButton("OK");
		private JTextField nameField=new JTextField(20);
		public UserDialog(JFrame frame,String title) {
			super(frame,title);//frame에 title이라는 제목으로 생성
			setLayout(new FlowLayout());//layout은 flowlayout
			setSize(300,100);
			setResizable(false);//창을 키울 수 없다
			
			add(new JLabel("이름을 입력하고 OK버튼을 누르세요"));
			
			add(nameField);
			add(okButton);
			
			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//ok button을 누르면
					//textField에서 이름을 얻고
					String name=nameField.getText();
					//이름을 기록한다.
					recordPanel.setName(name);
					//gameLevel을 정할 수 있는 버튼을 보이게 한다.
					gamePanel.gameLevel();
					//dialog를 숨긴다.
					dialog.setVisible(false);
				}
			});
		}
	}
	
}
