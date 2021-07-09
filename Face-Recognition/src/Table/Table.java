package Table;

import java.awt.*;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.*;
import javax.swing.table.*;
public class Table extends JFrame implements MouseListener{
	Timer timer;
	String filePath = "data.txt";
	int col = 5;
	int row = 0;
	String data[][] = new String[100000][col];
	String DATA[][] = new String[100000][col];
	String infoSV[] = new String[3];
	Vector vD = new Vector();
	Vector vT = new Vector();
	JScrollPane jsc;
	DefaultTableModel model;
	JTable tb;
	Container cn;
	int selectRow = -1;
	int selectCol = -1;
	JLabel lb[] = new JLabel[4];
	JPanel pn[] = new JPanel[4];
	JPanel PN = new JPanel();
	boolean First = true;
	int indexSv = -1;
	int count = 0;
	public Table() {
		super("Face-Recognition");
		cn = this.getContentPane();
		vT.clear();
		vD.clear();
		vT.add("Số thứ tự");
		vT.add("Công nhân");
		vT.add("Họ và tên");
		vT.add("Đơn vị");
		vT.add("Trạng thái");
		int N = 0;
			
		model = new DefaultTableModel(vD, vT);
        tb = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                if (!isRowSelected(row)) {
                    if (tb.getColumnCount() >= 0) {
                        String type = (String)getModel().getValueAt(row, 4);
                        if (type.equalsIgnoreCase("Có mặt")) {
                            c.setBackground(Color.GREEN);

                        }
                        if (type.equalsIgnoreCase("Vắng")) {
                            c.setBackground(Color.red);
                        }
                    }
                }
                if (isRowSelected(row) && isColumnSelected(column)) {
                    ((JComponent)c).setBorder(new LineBorder(Color.red));
                }
                return c;
            }
        };
        tb.setRowHeight(50);
		tb.setFont(new Font("Serif", Font.BOLD, 15));
		tb.addMouseListener(this);
		jsc = new JScrollPane(tb);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		for (int i = 0; i < col; i++) {
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment( JLabel.CENTER );
			tb.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
		}
		tb.getColumnModel().getColumn(0).setPreferredWidth(10);
		tb.setRowHeight(1, 15);
		tb.getTableHeader().setBackground(Color.cyan);
		
		cn.add(jsc,"North");
		
		PN = new JPanel();
		PN.setLayout(new GridLayout(4, 1));
		for (int i = 0; i < pn.length; i++) {
			lb[i] = new JLabel();
			lb[i].setFont(new Font("Serif", Font.BOLD, 15));
			lb[i].setForeground(Color.green);
			pn[i] = new JPanel();
			pn[i].setBackground(Color.black);
			pn[i].setLayout(new FlowLayout());
			pn[i].add(lb[i]);
			PN.add(pn[i]);
		}
		
		lb[0].setForeground(Color.white);
		cn.add(PN,"South");
		timer = new Timer(500, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				readData();
				if (count-- > 0) {
					lb[0].setText("Công Nhân Vừa Chấm Công");
					lb[1].setText("Mã Công Nhân: " + infoSV[0]);
					lb[2].setText("Họ và tên: " + infoSV[1]);
					lb[3].setText("Đơn Vị: " + infoSV[2]);
				} else {
					for (int i = 0; i < lb.length; i++)
						lb[i].setText("");
				}
			}
		});
		readData();
		timer.start();
		
		this.setVisible(true);
		this.setSize(800, 580);
		this.setLocationRelativeTo(null);
	}
	
	public void readData() {
		vD.clear();
		int stt = 0;
		row = 0;
    	File file = new File(filePath);
        InputStream inputStream;
        try (
        		FileInputStream fis = new FileInputStream(file);
        		InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        		BufferedReader reader = new BufferedReader(isr)
        	){
				String line;
				while ((line = reader.readLine()) != null) {
					String s[] = line.split(" @#@ ");
					Vector vo = new Vector();
					vo.add(++stt);
					data[row][0] = stt + "";
					for (int i = 0; i < s.length; i++) {
						if (i == s.length - 1) {
							if (Integer.parseInt(s[s.length - 1]) == 1) 
								vo.add("Có mặt");
							else
								vo.add("Vắng");
						} else
							vo.add(s[i]);
						data[row][i + 1] = s[i];
					}
					row++;
					vD.add(vo);
				}
				if (First) {
					for (int i = 0; i < row; i++)
						for (int j = 0; j < col; j++)
							DATA[i][j] = data[i][j];
					First = false;
							
				} else {
					indexSv = -1;
					for (int i = 0; i < row; i++)
						if (Integer.parseInt(data[i][4]) != Integer.parseInt(DATA[i][4]) && Integer.parseInt(data[i][4]) == 1) {
							indexSv = i;
							for (int j = 0; j < infoSV.length; j++)
								infoSV[j] = data[i][j + 1];
							DATA[i][4] = "1";
							System.out.println(infoSV[0]);
							count = 100;
						}
				}
				model.fireTableDataChanged();

			} catch (IOException e) {
				e.getLocalizedMessage();
			}
	}
	
	public void writeData() throws IOException {
		File file = new File(filePath);
		try (FileOutputStream fos = new FileOutputStream(file);
	             OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
	             BufferedWriter writer = new BufferedWriter(osw)
	        ) {
			
			for (int i = 0; i < row; i++) {
				for (int j = 1; j < col - 1; j++)
					writer.append(data[i][j] + " @#@ ");
				writer.append(data[i][col - 1]);
				if (i < row - 1)
					writer.newLine();
			}
			
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		selectRow = tb.getSelectedRow();
		selectCol = tb.getSelectedColumn();
		
//		System.out.println(selectRow + " " + selectCol);
		if (selectRow >= 0 && selectRow < row && selectCol == col - 1) {
			data[selectRow][selectCol] = (1 - Integer.parseInt(data[selectRow][selectCol])) + "";
			try {
				writeData();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public static void main(String[] args) {
		new Table();
	}
}
