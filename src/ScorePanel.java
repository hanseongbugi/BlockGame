import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Image;

public class ScorePanel extends JPanel{
	private int score=0; //����
	private int scoreValue=10; //ȹ�� ����
	
	private JLabel textLabel= new JLabel("����");
	private JLabel scoreLabel= new JLabel(Integer.toString(score)); //������ ��Ÿ���� label
	private JLabel lifeText=new JLabel("����");
	private JLabel lifeImage[]=new JLabel[5]; //���� ��Ʈ�� ���� �迭
	private JLabel dathImage[]=new JLabel[5];//�� ��Ʈ�� ���� �迭
	private ImageIcon redHeart=new ImageIcon(getClass().getClassLoader().getResource("redHeart.jpg")); //������Ʈ�̹��� ����
	private ImageIcon changeRedHeart=null; //ũ�⸦ ������ �̹����� ���� ����
	private ImageIcon whiteHeart=new ImageIcon(getClass().getClassLoader().getResource("whiteHeart.jpg"));
	private ImageIcon changeWhiteHeart=null; //ũ�⸦ ������ �̹����� ���� ����
	private GamePanel gamePanel=null; //gamePanel���۷��� ����
	private GameFrame gameFrame=null;
	private RecordPanel recordPanel=null; //recordPanel ���۷��� ����
	private int life=5; //life�� 5�̴�.
	//�⺻ JOptionPane�̹����� �ҷ����� �ʾ� ���
	private ImageIcon qustionImg=new ImageIcon(getClass().getClassLoader().getResource("qustion.png"));
	private JLabel qustion=null; //JOptionPane�� ����� Label
	
	public ScorePanel(GameFrame gameFrame,RecordPanel recordPanel) {
		this.gameFrame=gameFrame;
		this.recordPanel=recordPanel;
		this.setBackground(Color.CYAN); //����� CYAN��
		setLayout(null); //����Ʈ ��ġ�����ڷ� �Ѵ�.
	
		Image redImage=redHeart.getImage(); //Image��ü�� 
		Image redImg=redImage.getScaledInstance(20,20,Image.SCALE_SMOOTH); //�̹��� ũ�⸦ �ٲ۴�.
		changeRedHeart=new ImageIcon(redImg); // redHeart������ ũ�⸦ �ٲ� �̹����� �����Ͽ� ����
		
		Image whiteImage=whiteHeart.getImage();
		Image whiteImg=whiteImage.getScaledInstance(20,20,Image.SCALE_SMOOTH); //�̹��� ũ�⸦ �ٲ۴�.
		changeWhiteHeart=new ImageIcon(whiteImg); // redHeart������ ũ�⸦ �ٲ� �̹����� �����Ͽ� ����

		//"����"��� ���� Label�� ��ġ,ũ�⸦ ���ϰ� ����
		textLabel.setSize(50,20);
		textLabel.setLocation(10,10);
		add(textLabel);
		
		//score�� ��ϵ� label�� ��ġ,ũ�⸦ ���ϰ� ����
		scoreLabel.setSize(100,20);
		scoreLabel.setLocation(70,10);
		add(scoreLabel);
		
		addLifeImage(); //��Ʈ�� �����Ѵ�.
		
		//"����"�̶�� ���� label�� ��ġ,ũ�⸦ ���ϰ� ����
		lifeText.setSize(50,20);
		lifeText.setLocation(10,50);
		add(lifeText);
		
		//qustionImg�� ũ�⸦ �����ϰ� 
		qustionImg=imageSetSize(qustionImg,25,25);
		//JLabel�� ���������Ͽ� ���� 
		qustion=new JLabel("����Ͻðڽ��ϱ�?",qustionImg,JLabel.LEFT);
	}
	//�̹��� ũ�⸦ �����ϴ� �޼ҵ�
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
			//���� ��Ʈ�� 5���� ���̰�
			lifeImage[i]=new JLabel(changeRedHeart);
			lifeImage[i].setLocation(50+(i*20),50);
			lifeImage[i].setSize(20,20);
			this.add(lifeImage[i]);
			
			//�� ��Ʈ�� 5���� visible false�� ���·� ���δ�.
			dathImage[i]=new JLabel(changeWhiteHeart);
			dathImage[i].setLocation(50+(i*20),50);
			dathImage[i].setSize(20,20);
			this.add(dathImage[i]);
			dathImage[i].setVisible(false);
		}
		
		
	}
	private void resetLifeImage() { //���� ��Ʈ�� 5�� �ٽ� ����.
		for(int i=0;i<lifeImage.length;i++) {
			dathImage[i].setVisible(false);
			lifeImage[i].setVisible(true);
		}
	}
	public void increase(String star) { //������� �⺻ ����
		if(star.equals("yellow")) 
			score+=scoreValue;
		else
			score+=(scoreValue*3);   //������� ���߸� ���� ������ 3�踦 �� �ش�.
		scoreLabel.setText(Integer.toString(score));
	}
	public boolean misMatch() {
		life-=1;
		
		
		lifeImage[life].setVisible(false); //������Ʈ �ϳ��� �����
		dathImage[life].setVisible(true); //�� ��Ʈ �ϳ��� ���̰� �Ѵ�.
		
		dathImage[life].getParent().repaint(); //�ٽñ׷� �ݿ��Ѵ�.
		
		if(life==0){ //life�� 0�� �Ǹ� ���� ����� ����ϰ� ���Ͽ� ���� �� ��� ������ ����
			recordPanel.setScore(score);
			recordPanel.recordFile();
			for(int i=0;i<gamePanel.wordPanel.length;i++)
				gamePanel.stopGame(i);
			recordPanel.showRecord();
		
			while(true) {
				//JOptionPane�� gameFrame��ġ�� ����, yes/no�� �޴´�.
				//Interrupted while load image�� 
				//JOptionPane�� ��ﶧ ���� ��µǾ� PLAIN_MESSAGE�� �Ͽ����ϴ�. 
			int result=JOptionPane.showConfirmDialog(gameFrame,qustion,"selection",
						JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE);
			if(result==JOptionPane.YES_OPTION) { //yes�� ������ �ٽ� ����
				 resetLife();
				 resetLifeImage();
				 resetScore();
				 gamePanel.gameLevel();
				 
				 return false;
				
			}
			else if(result==JOptionPane.NO_OPTION) //no�� ������ ���� 
				System.exit(0);
			}//â�� ������ optionPane�� �ٽ� �����˴ϴ�.
		}
		return true;
	}
	private void resetScore() { //score�� 0���� �ϰ� scoreLabel�� �Է�.
		score=0;
		scoreLabel.setText(Integer.toString(score));
	}
	public void resetLife() { //life�� 5�� �ʱ�ȭ
		life=5;
	}
	
	
}
