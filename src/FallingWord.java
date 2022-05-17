import java.awt.Component;
import java.awt.Container;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class FallingWord extends Thread{ //�������� �ܾ ���� Thread
		//�ʿ��� Panel�� ����Ű������ ���۷��� ����
		private GamePanel gamePanel=null;
		private JLabel word=null;
		private JPanel wordPanel=null;
		private ScorePanel scorePanel=null;

		private int delay;//�������� �ӵ�
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
			
					sleep(delay); //delay��ŭ �ڰ�
					Container c=wordPanel.getParent();
					int y = wordPanel.getY() + 5;  //�ܾ��� y���� ��� 5��ŭ �̵�
					if(y >= c.getHeight()-wordPanel.getHeight()) { //y�� ��ġ�� ���� �г��� ��������
						word.setText(""); //�ܾ �����
						boolean gaming=scorePanel.misMatch();
						if(gaming ==true) //������ �������̸� (life�� 0�� ���� ������)
							gamePanel.startGame(index); //���ο� �ܾ ����
						break; 
					}
				
					wordPanel.setLocation(wordPanel.getX(), y); //�������̸� �̵�
					Component cm=c.getParent();
					cm.repaint(); //�ٽ� �׸���.
				
			
			} catch (InterruptedException e) {
				return; 
				}
			}
		}
	
	}