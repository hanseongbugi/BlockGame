import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
public class GameIntro extends JPanel{ //������ ��Ʈ�� Panel
	private JLabel title=new JLabel("�� ���� ����"); //������ ����
	private JLabel start=new JLabel("start"); //start Label
	private JLabel exit=new JLabel("exit"); //exit Label
	private GameFrame gameFrame=null; //gameFrame�� ����ų ���۷��� ����
	private ImageIcon icon=new ImageIcon(getClass().getClassLoader().getResource("intro.jpg")); //intro���
	private Image img=icon.getImage(); //imageIcon�� Image��ü��.
	public GameIntro(GameFrame gameFrame) {
		this.gameFrame=gameFrame;
		//intro panel�� ũ��� JFrame�� ũ��� ���� 
		setSize(800,600);
		setLayout(null); //��ġ�����ڴ� ����Ʈ ��ġ������
		
		//���� ����� font�� �ü�ü 60px��
		title.setFont(new Font("�ü�",Font.BOLD,60));
		//���� ���� Yellow
		title.setForeground(Color.YELLOW);
		//Label�� ũ��� ��ġ�� ���Ѵ�.
		title.setSize(500,100);
		title.setLocation(230,100);
		
		//start Label�� font�� �ü�ü 40px
		start.setFont(new Font("�ü�",Font.BOLD,40));
		//���� ���� yellow
		start.setForeground(Color.YELLOW);
		//label�� ��ġ�� ũ�⸦ ���Ѵ�.
		start.setSize(150,50);
		start.setLocation(350,300);
		
		//exit Label�� font�� �ü�ü 40px
		exit.setFont(new Font("�ü�",Font.BOLD,40));
		//���� ���� �˳�� ���̴�. ���� ��������� ����� RGB���� ���
		exit.setForeground(new Color(153,153,0));
		//��ġ�� ũ�⸦ ���Ѵ�.
		exit.setSize(150,50);
		exit.setLocation(360,350);
		add(title);
		add(start);
		add(exit);
		
		//start�� exit label�� actionListener�� ����
		start.addMouseListener(new IntroAction());
		exit.addMouseListener(new IntroAction());
	}
	//����� �׸��� �޼ҵ�(ȭ�鿡 ������ �׸���)
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g); //�г��� ����� ���
		g.drawImage(img,0,0,getWidth(),getHeight(),null); //img�� �׸��ϴ�.
		
	}
	
	class IntroAction extends MouseAdapter{
		//start�� exit label�� Ŭ���ϸ� ȣ��
		@Override
		public void mousePressed(MouseEvent e) {
			JLabel la=(JLabel)e.getSource();
			//exit�� ������ ����
			if(la.getText().equals("exit"))System.exit(0);
			//start�� ������ ���ӿ� �ʿ��� panel���� ���̰� �Ѵ�.
			else gameFrame.loadGame();
		}
	}
}
