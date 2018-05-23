package MainWin;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class ToDoListViewer {

	private String DEFAULT_XML_URL = "./file/ToDoList.xml";
	private JFrame frame;
	private JPanel mainPanel;
	private JCheckBox itemBuilder;
	private ArrayList<JCheckBox> checkBoxList;
	private ArrayList<JCheckBox> exitCheckBox;
	private ArrayList<ToDoList> toDoList;
	private ToDoList toDo;
	private Iterator<ToDoList> listIterator;
	private String time;
	private int ONE_SECOND = 1000;
	private String DEFAULT_TIME_FORMAT = "HH:mm:ss";
	private JLabel displayArea;
	private JLabel timeLabel;
	private JButton newButton;
	
	public static void main(String[] args) {
		ToDoListViewer list = new ToDoListViewer();
		list.go();
	}
	
	public void go() {
		
		//����gui
		frame = new JFrame("ToDoList Viewer");
		mainPanel = new JPanel();
		JPanel centrePanel = new JPanel();
		JPanel topPanel = new JPanel();
		checkBoxList = new ArrayList<JCheckBox>();
		exitCheckBox = new ArrayList<JCheckBox>();
		
		//��ȡ��Ļ�߶ȺͿ�������ô��ڳ�ʼλ��
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int)screensize.getWidth();
		//int screenHeight = (int)screensize.getHeight();
		
		mainPanel.setLayout(new BorderLayout());
		
		timeLabel = new JLabel("CurrentTime: ");
		displayArea = new JLabel();
		newButton = new JButton("new");
		itemBuilder = new JCheckBox("<html><font color='blue'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Build_Item</font><html>");
		
		configTimeArea();
		//��ȡXML�ļ������浽toDoList��
		getItem();
		//���������˳���ѡ��
		setExit();
		
		topPanel.add(timeLabel);
		topPanel.add(displayArea);
		
		centrePanel.setLayout(new BoxLayout(centrePanel, BoxLayout.Y_AXIS));
		
		ActionListener listener = new OpenButtonListener(centrePanel);
		newButton.addActionListener(listener);
		//��ʼ��ʱ�½���Ŀ��ѡ��������˳���ѡ��
		for(JCheckBox item:setItem()) {
			checkBoxList.add(item);
			centrePanel.add(item);
		}
		
		centrePanel.add(exitCheckBox.get(0));
		centrePanel.add(exitCheckBox.get(1));
		centrePanel.add(itemBuilder);
		
		
		mainPanel.add(new JLabel("        "), BorderLayout.WEST);
		mainPanel.add(new JLabel("        "), BorderLayout.EAST);
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(centrePanel, BorderLayout.CENTER);
		mainPanel.add(newButton, BorderLayout.SOUTH);
		frame.add(mainPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(0);
		//frame.setSize(new Dimension(280,95));
		//��ʼ��ʱʹ������Ӧȫ�������С
		frame.pack();
		frame.setMinimumSize(new Dimension(200, 95));
		frame.setLocation(screenWidth - (int)(frame.getSize().getWidth() * 1.5), (int)(frame.getSize().getHeight() * 0.5));
		//ʹ��ʼ��ʱ�������
		//frame.setLocationRelativeTo(null);
		//ʹ�������촰��
		//frame.setResizable(false);
		//ʹ�ö�
		frame.setAlwaysOnTop(true);
		frame.setVisible(true); 
		
		//JOptionPane.showMessageDialog(null, checkBoxList.get(1).getText(), "Title", JOptionPane.ERROR_MESSAGE); 
		
		//test();
		
	}
	
	protected class JLabelTimerTask extends TimerTask{
		//����ʱ��ĸ�ʽ
		SimpleDateFormat dateFormatter = new SimpleDateFormat(DEFAULT_TIME_FORMAT);
		@Override
		public void run() {
			time = dateFormatter.format(Calendar.getInstance().getTime());
			displayArea.setText(time);
		 }
	}
		
	public class OpenButtonListener implements ActionListener {
	   	//�����ִ�еĲ���
		JPanel centrePanel;
		SAXReader reader = new SAXReader();
		
		public OpenButtonListener (JPanel centrePanel) {
			this.centrePanel = centrePanel;
		}
		
	    public void actionPerformed(ActionEvent ev) { 
	    	//�����ж��Ƿ�ͬʱѡ���������˳���ѡ��������񵯳�ѡ��Ի���ȷ���û���ͼ
	    	if(exitCheckBox.get(0).isSelected() && exitCheckBox.get(1).isSelected()) {
	    		int ifExit = JOptionPane.showConfirmDialog(null, "�˳���", "ע��",JOptionPane.YES_NO_OPTION); 
	    		if(ifExit == JOptionPane.YES_OPTION) {
	    			System.exit(0);
	    		}
	    	//���û��ͬʱѡ�������˳���ѡ����ִ����һ��
	    	}else if(itemBuilder.isSelected()){
	    		//ItemBuilder itemBuilder = new ItemBuilder();
	    		new ItemBuilder().go();
	    		itemBuilder.setSelected(false);
	    	}else {
	    		
	    		try {
		    		Document document = reader.read(new File(DEFAULT_XML_URL));
		    		Element toDoListXml = document.getRootElement();
		    		int count = 0;
		    		//Element item = toDoListXml.elements("todo").get(0).element("item");
		    		
		    		//������checkbox�Ƿ�ѡ��
		    		for(int i = 0; i < checkBoxList.size(); i++) {
		    			
		    			JCheckBox jc = (JCheckBox) checkBoxList.get(i);
		    			if(jc.isSelected()) {
		    				//��ѡ�ϵĻ���ɾ����checkbox��Ӧ��ͬһ��todo��Ŀ��ע��todo��Ŀÿɾ��һ����λ������һ��������Ҫ��count����λ׼ȷλ�ã�i-countΪ��ȷλ��
		    				Element todo = toDoListXml.elements("todo").get(i - count);
		    				//JOptionPane.showMessageDialog(null, todo.element("item").getText(), "Title", JOptionPane.ERROR_MESSAGE); 
		    				toDoListXml.remove(todo);
		    				count ++;//�ǵ�ÿ�Ƴ�һ����Ҫ����һ��count
		    			}
		    			
		    		}
		    		//��д����
		    		OutputFormat format = OutputFormat.createPrettyPrint();
					format.setEncoding("utf-8");
					XMLWriter xmlWriter = new XMLWriter(
							new FileOutputStream(DEFAULT_XML_URL),format);
					xmlWriter.write(document);
					xmlWriter.close();
		    		
		    	}catch (DocumentException | IOException e) {
		    		e.printStackTrace();
		    	}
		    	
	    		//�����������ȫ��checkbox�����¶�ȡxml�ʹ����µ�checkbox
		    	centrePanel.removeAll();
		    	getItem();
		    	checkBoxList.clear();
		    	for(JCheckBox item:setItem()) {
		    		checkBoxList.add(item);
					centrePanel.add(item);
				}
		    	
		    	//����µ��˳���鸴ѡ��
		    	centrePanel.add(exitCheckBox.get(0));
				centrePanel.add(exitCheckBox.get(1));
				centrePanel.add(itemBuilder);
		    	mainPanel.repaint();
		    	frame.pack();
		    	//JOptionPane.showMessageDialog(null, checkBoxList.get(4).getText(), "Title", JOptionPane.ERROR_MESSAGE); 
		    	//JOptionPane.showMessageDialog(null, toDo.getItem(), "s", JOptionPane.ERROR_MESSAGE); 
	    		
	    	}
	
	    }
	}

	/*
	 * ���Գ���
	 * 
	private void test() {
			
		SAXReader reader = new SAXReader();
		
		try {
    		Document document = reader.read(new File(DEFAULT_XML_URL));
    		Element toDoListXml = document.getRootElement();
    		Element item = toDoListXml.elements("todo").get(0).element("item");
    		String str = checkBoxList.get(0).getText();
    		str = str.substring(2, 2 + item.getText().length());
    		
    		
    		if(item.getText().equals(str)) {
    			JOptionPane.showMessageDialog(null, str+str.length()+" and "+item.getText().length(), "Title"+str, JOptionPane.ERROR_MESSAGE); 
    		}else {
    			//JOptionPane.showMessageDialog(null, str+str.length()+" and "+item.getText().length(), "Title"+str, JOptionPane.ERROR_MESSAGE); 
    		}

    	}catch (DocumentException e) {
    		e.printStackTrace();
    	}

	}
	*/
	
	private void getItem() {
		
		toDoList = new ArrayList<ToDoList>();
    	SAXReader reader = new SAXReader();
    	
    	try {
    		Document document = reader.read(new File(DEFAULT_XML_URL));
    		Element toDoListXml = document.getRootElement();
    		Iterator<Element> toDoListIterator = toDoListXml.elementIterator();
    		
    		//�ֱ��ȡÿ��todo����item��ֵ����toDo��Ȼ����뵽toDoList��
    		while (toDoListIterator.hasNext()) {
    			Element todo = (Element) toDoListIterator.next();
    			String item = todo.getStringValue();
    			makeList(item);
    		}
    	}catch (DocumentException e) {
    		e.printStackTrace();
    	}
    	//����toDoList�ĵ�����
    	listIterator = toDoList.iterator();
	}
	
	private void makeList (String item) {
	     
        ToDoList todo = new ToDoList(item);
        toDoList.add(todo);
  }
	
	private JCheckBox[] setItem() {
		
		final int listSize = toDoList.size();
		JCheckBox checkArray[] = new JCheckBox[listSize];
		int i = 0;
		
		//��toDoList��ֵ�ֱ𸳸�checkbox���鲢��������
		while(listIterator.hasNext()) {
			toDo = (ToDoList) listIterator.next();
			checkArray[i] = new JCheckBox(toDo.getItem());
			i++;
		}
		
		return checkArray;
		
	}

	private void setExit() {
		//String str = "EXIT_1";
		exitCheckBox.add(new JCheckBox("<html><font color='red'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;EXIT_1</font><html>"));
		exitCheckBox.add(new JCheckBox("<html><font color='red'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;EXIT_2</font><html>"));
		
	}
	
	private void configTimeArea() {
		Timer tmr = new Timer();
		tmr.scheduleAtFixedRate(new JLabelTimerTask(),new Date(), ONE_SECOND);
		}
	
}
