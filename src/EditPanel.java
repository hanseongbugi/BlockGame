import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.awt.event.ActionEvent;
public class EditPanel extends JPanel{//단어를 입력하고 파일에 저장하는 Panel
	private TextSource textSource=null; //단어 파일이 있는 class
	private Vector<String> words=new Vector<String>(5); //입력할 단어를 기록할 Vector
	private JTextField edit=new JTextField(20);//저장할 단어를 입력하는 TextField
	private JButton addButton=new JButton("add"); //add button
	private JButton saveButton=new JButton("save"); //save button
	public EditPanel(TextSource textSource) {
		this.textSource=textSource;
		setBackground(Color.CYAN); //배경은 CYAN색
		setLayout(new FlowLayout()); //배치관리자는 flow layout으로 한다
		//textField와 button을 부착
		add(edit);
		add(addButton);
		add(saveButton);
		
		//버튼에 actionListenr 부착
		addButton.addActionListener(new ButtonAction());
		saveButton.addActionListener(new ButtonAction());
	}
	
	class ButtonAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button=(JButton)e.getSource(); //어떤 버튼을 눌렀는지 얻고
			String key=button.getText(); //버튼이 무슨 버튼인지 text를 얻는다.
			if(key.equals("add")) { //add이면 입력한 단어를 vector에 추가
				String word=edit.getText();
				if (word.length()==0)return;
				words.add(word);
				edit.setText("");
			}
			else { //save면 단어를 기록한다.
				textSource.saveWord(words);
				//벡터를 초기화
				words.clear();
			}
		}
	}
}
