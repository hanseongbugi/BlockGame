import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class WordPanel extends JPanel{ //�������� �ܾ� Panel

		private YellowStar yellow=new YellowStar(); //�����
		private JLabel wordLabel=null; //�ܾ ���� Label
		private GreenStar green =new GreenStar(); //�����
		private int random=(int)(Math.random()*10); //��������� ��������� ���� random��
		public WordPanel(JLabel wordLabel) {
			//�ܾ ���� wordLabel�� gamePanel���� ���´�.
			this.wordLabel=wordLabel; 
			//wordPanel�� ũ��� ��ġ
			setSize(100,50); 
			setLocation(0,0);
			setOpaque(false); //�����ϰ� ����
			
			//10%�� Ȯ���� ������� �����ȴ�.
			if(random!=5) {
				this.add(yellow);
				
				yellow.add(this.wordLabel);
			}
			else {
				add(green);
				green.add(this.wordLabel);	
			}
		}
		public String getStar() {
			//��������� ��������� �˷��ִ� �޼ҵ�
			if(random==5)return "green";
			else return "yellow";
		}
		public void changeStar() {
			//�ܾ ���� ���� �� ���� �ٲٱ����� ȣ��Ǵ� �޼ҵ�
			int change=(int)(Math.random()*10); //���� �ٽ� ���ϰ�
			random=change;
			//������̳� ������� ����
			this.remove(wordLabel.getParent());
			//���� ���� ���� ���� ���� ���δ�.
			if(random!=5) {
				this.add(yellow);
				yellow.add(wordLabel);
			}
			else {
				this.add(green);
				green.add(wordLabel);
			}
							
		}
		//�����
		class YellowStar extends JPanel{
			ImageIcon icon=new ImageIcon(getClass().getClassLoader().getResource("yellowstar.png"));
			Image img=icon.getImage();
	
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g); //�г��� ����� ���
				g.drawImage(img,0,0,getWidth(),getHeight(),null); //img�� �׸��ϴ�.
		
			}
			
		}
		//�����
		class GreenStar extends JPanel{
			ImageIcon icon=new ImageIcon(getClass().getClassLoader().getResource("greenstar.png"));
			Image img=icon.getImage();
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g); //�г��� ����� ���
				g.drawImage(img,0,0,getWidth(),getHeight(),null); //img�� �׸��ϴ�.
		
			}
			
		}
	
}
