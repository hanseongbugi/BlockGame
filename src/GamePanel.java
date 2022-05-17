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
public class GamePanel extends JPanel{//game�� �����ϴ� Panel
	private int delay; //�ܾ �������� �ӵ�
	private JTextField input=new JTextField(40);//�ܾ �Է��� text field
	private boolean pauseGame=false;//������ ���¸� ��Ÿ����.
	
	private ScorePanel scorePanel=null;
	private EditPanel editPanel=null;
	private TextSource textSource=null;
	
	//private FallingWord thread =null;//������ ���۷��� ����
	private JLabel wordLabel[]=new JLabel[5];
	//������ ���
	private FallingWord th[]=new FallingWord[5];
	//�ܾ� panel�迭
	public WordPanel wordPanel[]=new WordPanel[5];

	private GameGroundPanel gameGround=new GameGroundPanel();
	
	
	public GamePanel(ScorePanel scorePanel,EditPanel editPanel,TextSource textSource) {
		
		
		this.scorePanel=scorePanel;
		this.editPanel=editPanel;
		this.textSource=textSource;
		
		//��ġ�����ڴ� borderLayout����
		setLayout(new BorderLayout());
		//���� ����� �Ǵ� panel�� CENTER�� ����
		add(gameGround,BorderLayout.CENTER);
		//�ܾ �Է��� Panel�� SOUTH�� ����
		add(new InputPanel(),BorderLayout.SOUTH);
		
		//textField�� actionListener�� ���δ�.
		input.addActionListener(new InputAction());

	}
	
	public void startGame(int index) {

		Container c=wordPanel[index].getParent();
		
		//������ ���߾� �־����� ���� ��ġ���� �ٽ� �����带 ����� �ܾ �̵�
		if(getStatus()==true) {
			th[index]=new FallingWord(this,wordPanel[index],wordLabel[index],index,scorePanel,delay);
			th[index].start();
			return ;
		}
		
		
		String word=textSource.getRandomWord();
		//x�� 0���� (����� �پ��ִ� ������Ʈ�� width-200) ������ ���� ����
		int x=(int)(Math.random()*(c.getWidth()-200));
		//y�� 0-99������ ����
		int y=(int)(Math.random()*100);
		
		//�ٽý��� �ϴ� ��츦 ����Ͽ� ���� �ٽ� �ѹ� �ٲ۴�.
		wordPanel[index].changeStar();
		//wordPanel�� ��ġ�� set�ϰ�
		wordPanel[index].setLocation(x,y); 
		//wordLabel�� ����� �����ϰ� �ϰ�
		wordLabel[index].setOpaque(false);
		//font�� ���ü 20px
		wordLabel[index].setFont(new Font("���",Font.ITALIC,20));
		wordLabel[index].setForeground(Color.MAGENTA);
		//label�� text�� word�� �Ѵ�, 
		wordLabel[index].setText(word);
		
		if(!wordPanel[index].isVisible())  //�г��� �Ⱥ��̴� ���¸� ���̵��� �ٲ۴�.
			wordPanel[index].setVisible(true);
		
		c.repaint();
		
		//������ �ϳ��� �����Ѵ�.	
		th[index]=new FallingWord(this,wordPanel[index],wordLabel[index],index,scorePanel,delay);
		//������ ����
		th[index].start();		
	}
	
	public void stopGame(int index) {
		FallingWord thread=th[index];
		//wordPael�� ������.
		if(wordPanel[index].isVisible())wordPanel[index].setVisible(false);
		//�����尡 ������ return
		if(thread==null)return;
		//������ ����
		thread.interrupt();
		thread=null;
	
	}
	

	
	public void pauseGame(int index) {
		//�Ͻ������� ���� �����带 ����.
		FallingWord thread =th[index];
		if(thread ==null)return;
		thread.interrupt();
		thread=null;
	}
	public void gameStatus() {
		//������ ���¸� �ٲ۴�.
		if(pauseGame==false)pauseGame =true;
		else pauseGame=false;
	}
	public boolean getStatus() {
		//������ ���� ��ȯ false�� ó������ ����
		//true�� �Ͻ����� ����
		return pauseGame;
	}

	 class GameGroundPanel extends JPanel{
		public JButton easy=new JButton("easy"); //delay=500
		public JButton normal=new JButton("normal"); //delay=100
		public JButton hard=new JButton("hard"); //delay=50
		private SkyPanel skyPanel=new SkyPanel();
		
		public GameGroundPanel() {//������ ����� �Ǵ� �г�
			//��ġ�����ڴ� ����Ʈ ��ġ������
			setLayout(null);
		
			//easy��ư�� ��ġ�� ũ�⸦ ���ϰ� ���δ�.
			easy.setLocation(30,150);
			easy.setSize(80,50);
			add(easy);
			//normal��ư�� ��ġ�� ũ�⸦ ���ϰ� ���δ�.
			normal.setLocation(230,150);
			normal.setSize(80,50);
			add(normal);
			//hard��ư�� ��ġ�� ũ�⸦ ���ϰ� ���δ�.
			hard.setLocation(430,150);
			hard.setSize(80,50);
			add(hard);
			//��� ���̵� ��ư�� �����.
			easy.setVisible(false);
			normal.setVisible(false);
			hard.setVisible(false);
			
			for(int i=0;i<5;i++) {
				//wordLabel�迭�� JLabel�� ����Ű���Ѵ�.
				wordLabel[i]=new JLabel("");
				//wordPanel�� �����ڸ� ȣ�� wordLabel�� �Ű��� �ش�.
				wordPanel[i]=new WordPanel(wordLabel[i]);
				//������ wordPanel�� ���̰�
				add(wordPanel[i]);
				//������ �ʰ� �Ѵ�.
				wordPanel[i].setVisible(false);
			}
			this.addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(ComponentEvent e) {
					//������Ʈ�� ũ�Ⱑ ��ȭ�Ͽ��� �� ����
					//skyPanel�� ��ġ�� ũ�⸦ �ְ�
					skyPanel.setLocation(0,0);
					skyPanel.setSize(GameGroundPanel.this.getWidth(),GameGroundPanel.this.getHeight());
					//���δ�.
					add(skyPanel);
				
				}
			});
			//���̵� ��ư�� actionListener�� ���δ�.
			easy.addActionListener(new GameLevelAction());
			normal.addActionListener(new GameLevelAction());
			hard.addActionListener(new GameLevelAction());
		
		}
		
		class SkyPanel extends JPanel{//���ϴ� ��� panel
			ImageIcon icon=new ImageIcon(getClass().getClassLoader().getResource("background.jpg"));
			Image img=icon.getImage();
		
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g); //�г��� ����� ���
				
				g.drawImage(img,0,0,getWidth(),getHeight(),null); //img�� �׸��ϴ�.
			
			}
		}
		class GameLevelAction implements ActionListener{
			//���̵� ��ư�� ������ ȣ��
			@Override
			public void actionPerformed(ActionEvent e) {
				//���� button�� ���
				JButton button=(JButton)e.getSource();
				//�� ��ư�� �θ� ��´�.
				Container c=button.getParent();
				//button�� text�� level������ ����Ű�� �Ѵ�.
				String level=button.getText();
				if(level.equals("easy")) {
					//easy�� ��� 0.5�ʾ� ������
					delay=500;	
				}
				else if(level.equals("normal")) {
					//normal�� ��� 0.1�ʾ� ������
					delay=100;	
				}
				else {
					//hard�� ��� 0.05�ʾ� ������
					delay=50;	
				}
				//��� ��ư�� �����.
				easy.setVisible(false);
				normal.setVisible(false);
				hard.setVisible(false);
				//gameGround�г��� �ٽ� �׸���.
				c.repaint();
				//life�� �ʱ�ȭ
				scorePanel.resetLife();
				//������ ����
				for(int i=0;i<5;i++)
					startGame(i);
				

			}
		}
		
	}
	
	public void gameLevel() {
		 //��� ���̵� ��ư�� ���̰� �Ѵ�.
		gameGround.easy.setVisible(true);
		gameGround.normal.setVisible(true);
		gameGround.hard.setVisible(true);
	
	}
	

	class InputPanel extends JPanel{ //inputPanel�� ����
		
		public InputPanel() {
			setLayout(new FlowLayout());//��ġ�����ڴ� FlowLayout
			setBackground(new Color(153,76,0));//panel�� ����� �����迭
			add(input);//JTextField�� ���δ�.
			
		}	
	}
	
	class InputAction implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JTextField t=(JTextField)e.getSource();
			String inword=t.getText();
			if(getStatus()==true) { //������ ���������� textField�� ����� return�Ѵ�.
				t.setText("");
				return ;
			}
			for(int i=0;i<wordLabel.length;i++) {
				
				if(wordLabel[i].getText().equals(inword)) { //���߱� ����
					//���� �ø���
					scorePanel.increase(wordPanel[i].getStar());
					stopGame(i);
					wordPanel[i].changeStar();
					startGame(i);
				
					//inputâ �����
					t.setText(""); 
					}
				}
			}
	}
		
}
