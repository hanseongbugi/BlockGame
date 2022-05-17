import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class WordPanel extends JPanel{ //떨어지는 단어 Panel

		private YellowStar yellow=new YellowStar(); //노란별
		private JLabel wordLabel=null; //단어가 쓰일 Label
		private GreenStar green =new GreenStar(); //녹색별
		private int random=(int)(Math.random()*10); //노란별일지 녹색별일지 정할 random수
		public WordPanel(JLabel wordLabel) {
			//단어가 쓰일 wordLabel을 gamePanel에서 얻고온다.
			this.wordLabel=wordLabel; 
			//wordPanel의 크기와 위치
			setSize(100,50); 
			setLocation(0,0);
			setOpaque(false); //투명하게 설정
			
			//10%의 확률로 녹색별이 생성된다.
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
			//녹색별인지 노란별인지 알려주는 메소드
			if(random==5)return "green";
			else return "yellow";
		}
		public void changeStar() {
			//단어가 새로 생길 때 별을 바꾸기위해 호출되는 메소드
			int change=(int)(Math.random()*10); //수를 다시 정하고
			random=change;
			//노란별이나 녹색별을 때고
			this.remove(wordLabel.getParent());
			//새로 정한 수에 의해 별을 붙인다.
			if(random!=5) {
				this.add(yellow);
				yellow.add(wordLabel);
			}
			else {
				this.add(green);
				green.add(wordLabel);
			}
							
		}
		//노란별
		class YellowStar extends JPanel{
			ImageIcon icon=new ImageIcon(getClass().getClassLoader().getResource("yellowstar.png"));
			Image img=icon.getImage();
	
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g); //패널을 지우는 기능
				g.drawImage(img,0,0,getWidth(),getHeight(),null); //img를 그립니다.
		
			}
			
		}
		//녹색별
		class GreenStar extends JPanel{
			ImageIcon icon=new ImageIcon(getClass().getClassLoader().getResource("greenstar.png"));
			Image img=icon.getImage();
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g); //패널을 지우는 기능
				g.drawImage(img,0,0,getWidth(),getHeight(),null); //img를 그립니다.
		
			}
			
		}
	
}
