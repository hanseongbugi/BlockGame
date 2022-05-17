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
	
	//JMenuItem��ü 3���� ����
	private JMenuItem startItem = new JMenuItem("start");
	private JMenuItem stopItem = new JMenuItem("stop");
	private JMenuItem exitItem=new JMenuItem("exit");
	
	//JButton��ü 2�� ���� ToolBar����µ� ���
	private JButton startButton=new JButton("start");
	private JButton stopButton=new JButton("stop");
	
	//�̸��� �Է��� dialog�� ����ų ����
	private UserDialog dialog=null;
	//startButton�� stopButton�� ���� tool bar����
	private JToolBar tBar=new JToolBar();
	//JMenuItem���� ���� JMenuBar����
	private JMenuBar mBar = new JMenuBar();
	private JSplitPane hPane = new JSplitPane(); //JSplitpane�� hPane����
	private JSplitPane pPane= new JSplitPane(); //JSplitpane�� pPane����
	private JSplitPane srPane=new JSplitPane(); //scorePanel�� recordPanel�� ���̱� ���� JSplitPane����
	
	//���ӿ� �ʿ��� ���� �гε�
	private RecordPanel recordPanel =new RecordPanel();
	private TextSource textSource=new TextSource("word.txt");
	private ScorePanel scorePanel =new ScorePanel(this,recordPanel);
	private EditPanel editPanel =new EditPanel(textSource);
	private	GamePanel gamePanel=new GamePanel(scorePanel,editPanel, textSource);
	//gameIntro�� ���� JPanel�̴�.
	private GameIntro gameIntro=new GameIntro(this);
	
	public GameFrame() {
		setTitle("�� ���� ����");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //â�� ������ ���α׷� ����
		setSize(800,600); 
		
		Container c=getContentPane(); //contentPane�� ���
		c.add(gameIntro);//gameIntro�� ���δ�.
		
		splitPane(); //JSplitPane�� �����Ͽ� ����Ʈ���� CENTER�� ����
		makeMenu();//menu�� ����� ����
		makeToolBar();//toolbar�� ����� ���������� NORTH�� ����
		
		
		this.setResizable(false); //�������� ������ �� �������Ѵ�.
		
		//scorePanel�� �����ڸ� ȣ���� �� 
		//gamePanel�� ���� ���Ͽ� �Լ� ȣ��� gamePanel���ش�.
		scorePanel.getGamePanel(gamePanel); 
		
		//�÷��̾� �̸��� ��� dialog����
		dialog=new UserDialog(this,"���� ���� �Է�");
		
	
		setVisible(true);
		
	
	}
	public void loadGame() {//introȭ�鿡�� start�� ������ ȣ��
		
		hPane.setVisible(true);
		mBar.setVisible(true);
		tBar.setVisible(true);
		remove(gameIntro);
		revalidate();
		repaint();
	}
	private void splitPane() {//JSplitPane�� �̿��Ͽ� ����
		
		//���������� ����Ʈ�� BorderLayout�� CENTER�� ���δ�
		getContentPane().add(hPane,BorderLayout.CENTER); 
		//�������� hPane�� ������.
		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		//���ʿ� gamePanel�� ����
		hPane.setLeftComponent(gamePanel);
		//550�ȼ��� ���´�
		hPane.setDividerLocation(550);
		//�����ִ� ���� ������ �� �����Ѵ�.
		hPane.setEnabled(false);
		
		
	
		//���Ʒ��� pPane�� ������.
		pPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		//400�ȼ��� ������.
		pPane.setDividerLocation(400);
		//�Ʒ��ʿ� editPanel�� ����
		pPane.setBottomComponent(editPanel);
		
		//���Ʒ��� srPane�� ������
		srPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		//150�ȼ��� ������.
		srPane.setDividerLocation(150);
		//���ʿ��� scorePanel�� ���δ�.
		srPane.setTopComponent(scorePanel);
		//�Ʒ����� recordPanel�� ���δ�.
		srPane.setBottomComponent(recordPanel);
		srPane.setEnabled(false);
		
		
		pPane.setTopComponent(srPane);
		//hPane�� ������ ������Ʈ�� pPane�� ���δ�.
		hPane.setRightComponent(pPane);
		//�����ִ� ���� ������ �� �����Ѵ�.
		pPane.setEnabled(false);
		
		//intro�� ���� hPane�� �� �� ���� �Ѵ�.
		hPane.setVisible(false);
	}
	 
	private void makeMenu() {
		
		setJMenuBar(mBar);//menuBar�� mBar�� ���δ�.
		//game�̶� �̸��� menu�� ����
		JMenu fileMenu = new JMenu("Game");
		//menu�� item�� ���δ�.
		fileMenu.add(startItem);
		fileMenu.add(stopItem);
		//�и����� ���δ�.
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		
		//�޴��ٿ� menu�� ���δ�.
		mBar.add(fileMenu);
		
		//intro�� ���� �޴��ٸ� �����.
		mBar.setVisible(false);
		//�޴����� item�� actionListenr�� ���δ�.
		startItem.addActionListener(new StartAction(scorePanel));
		stopItem.addActionListener(new StopAction());
		exitItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
	}
	private void makeToolBar() { //toolbar�� ����� �������� NORTH�� ���δ�.
		//toolbar�� button�� ���δ�
		tBar.add(startButton); 
		tBar.add(stopButton);
		getContentPane().add(tBar,BorderLayout.NORTH);
		
		//button�� actionListener�� ���δ�.
		startButton.addActionListener(new StartAction(scorePanel));
		stopButton.addActionListener(new StopAction());
		
		//intro�� ���� �����.
		tBar.setVisible(false);
		
	}	
	private class StopAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			//������ �Ͻ������ϴ� action
			for(int i=0;i<gamePanel.wordPanel.length;i++)
				gamePanel.pauseGame(i);
			
			gamePanel.gameStatus();  //���ӿ� pause�� �Ǵ�.
		}
		
	}

	private class StartAction implements ActionListener{
		private ScorePanel scorePanel=null;
		public StartAction(ScorePanel scorePanel) {
			this.scorePanel=scorePanel;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			//start��ư action
			if(gamePanel.getStatus()) {
				//���� ���̸� �Ͻ������� Ǯ��
				for(int i=0;i<gamePanel.wordPanel.length;i++)
					gamePanel.startGame(i);
				gamePanel.gameStatus(); //pause���¸� Ǭ��.
			}//������ �����ϴ� ���̸� dialog�� ���̰� �Ѵ�.
			else dialog.setVisible(true);
		}
	}
	class UserDialog extends JDialog{ //������ ������ User�� �̸��� �Է��� Dialog
		private JButton okButton = new JButton("OK");
		private JTextField nameField=new JTextField(20);
		public UserDialog(JFrame frame,String title) {
			super(frame,title);//frame�� title�̶�� �������� ����
			setLayout(new FlowLayout());//layout�� flowlayout
			setSize(300,100);
			setResizable(false);//â�� Ű�� �� ����
			
			add(new JLabel("�̸��� �Է��ϰ� OK��ư�� ��������"));
			
			add(nameField);
			add(okButton);
			
			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//ok button�� ������
					//textField���� �̸��� ���
					String name=nameField.getText();
					//�̸��� ����Ѵ�.
					recordPanel.setName(name);
					//gameLevel�� ���� �� �ִ� ��ư�� ���̰� �Ѵ�.
					gamePanel.gameLevel();
					//dialog�� �����.
					dialog.setVisible(false);
				}
			});
		}
	}
	
}
