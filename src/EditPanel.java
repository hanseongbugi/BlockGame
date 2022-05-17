import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.awt.event.ActionEvent;
public class EditPanel extends JPanel{//�ܾ �Է��ϰ� ���Ͽ� �����ϴ� Panel
	private TextSource textSource=null; //�ܾ� ������ �ִ� class
	private Vector<String> words=new Vector<String>(5); //�Է��� �ܾ ����� Vector
	private JTextField edit=new JTextField(20);//������ �ܾ �Է��ϴ� TextField
	private JButton addButton=new JButton("add"); //add button
	private JButton saveButton=new JButton("save"); //save button
	public EditPanel(TextSource textSource) {
		this.textSource=textSource;
		setBackground(Color.CYAN); //����� CYAN��
		setLayout(new FlowLayout()); //��ġ�����ڴ� flow layout���� �Ѵ�
		//textField�� button�� ����
		add(edit);
		add(addButton);
		add(saveButton);
		
		//��ư�� actionListenr ����
		addButton.addActionListener(new ButtonAction());
		saveButton.addActionListener(new ButtonAction());
	}
	
	class ButtonAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button=(JButton)e.getSource(); //� ��ư�� �������� ���
			String key=button.getText(); //��ư�� ���� ��ư���� text�� ��´�.
			if(key.equals("add")) { //add�̸� �Է��� �ܾ vector�� �߰�
				String word=edit.getText();
				if (word.length()==0)return;
				words.add(word);
				edit.setText("");
			}
			else { //save�� �ܾ ����Ѵ�.
				textSource.saveWord(words);
				//���͸� �ʱ�ȭ
				words.clear();
			}
		}
	}
}
