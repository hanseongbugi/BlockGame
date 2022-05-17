import java.awt.Component;
import java.awt.Container;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class FallingWord extends Thread{ //떨어지는 단어를 위한 Thread
		//필요한 Panel을 가르키기위한 레퍼런스 변수
		private GamePanel gamePanel=null;
		private JLabel word=null;
		private JPanel wordPanel=null;
		private ScorePanel scorePanel=null;

		private int delay;//떨어지는 속도
		private int index;
		public FallingWord(GamePanel gamePanel,JPanel wordPanel,JLabel word,int index,ScorePanel scorePanel,int delay) {
			this.gamePanel=gamePanel;
			this.wordPanel=wordPanel;
			this.word=word;
			this.index=index;
			this.scorePanel=scorePanel;
	
			this.delay=delay;
		}
		
		@Override
		public void run() {
			while(true) {
				try {
			
					sleep(delay); //delay만큼 자고
					Container c=wordPanel.getParent();
					int y = wordPanel.getY() + 5;  //단어의 y값을 얻고 5만큼 이동
					if(y >= c.getHeight()-wordPanel.getHeight()) { //y의 위치가 게임 패널을 나갔으면
						word.setText(""); //단어를 지운다
						boolean gaming=scorePanel.misMatch();
						if(gaming ==true) //게임이 진행중이면 (life가 0이 되지 않으면)
							gamePanel.startGame(index); //새로운 단어를 생성
						break; 
					}
				
					wordPanel.setLocation(wordPanel.getX(), y); //범위안이면 이동
					Component cm=c.getParent();
					cm.repaint(); //다시 그린다.
				
			
			} catch (InterruptedException e) {
				return; 
				}
			}
		}
	
	}