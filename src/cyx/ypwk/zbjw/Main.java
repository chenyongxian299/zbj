package cyx.ypwk.zbjw;

import cyx.ypwk.zbjw.thread.GetShopList;
import cyx.ypwk.zbjw.thread.MoveData_Thread;
import cyx.ypwk.zbjw.tools.AnalyticData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Main extends JFrame {

	private JPanel contentPane;
	public static JTextField textField;
	public static JTextField textField_1;
	public static Button button;
	public static Button button_1;
	
	public static boolean ISSTART=false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 318, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		button = new Button();
		/*button.setFont(new Font("宋体",Font.ITALIC,20));*/
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button.setLocation(10, 125);
		button.setSize(100, 50);
		button.setLabel("start");
		ISSTART=false;
		button.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(!ISSTART){	
					ISSTART=true;
					button.setLabel("stop");
					String _Path = textField.getText();// "http://www.zhubajie.com/uisheji/p.html";
					GetShopList GML = new GetShopList(_Path);
					AnalyticData.setCategory(_Path);
					GML.start();
				}else{
					ISSTART=false;
					button.setEnabled(false);
				}
			}
		});
		contentPane.add(button);

		button_1 = new Button("exprot to excel");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MoveData_Thread m = new MoveData_Thread();
				m.start();
			}
		});
		button_1.setBounds(176, 125, 100, 50);
		contentPane.add(button_1);

		textField = new JTextField();
		textField.setText("http://");
		textField.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				/*if (textField.getText().equals("http://")) {
					textField.setText("");
				}*/
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textField.setText("http://www.zhubajie.com/uisheji/p.html");

		textField.setBounds(10, 90, 264, 21);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setEnabled(false);
		textField_1.setVisible(false);
		textField_1.setText("");
		textField_1.setBackground(Color.WHITE);
		textField_1.setBounds(10, 23, 124, 21);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
	}
}
