import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

class Index extends JFrame{
	
	public Index(){
		
		setTitle("쌍자 암호"); //타이틀바 문자열
		setLocation(0, 0);//시작위치 (x,y)
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//종료 버튼 눌렀을때 행동
		setLocation(300, 0);
		setPreferredSize(new Dimension(600, 1000));
	}
	
}

public class Main extends JPanel{
	
	ImageIcon startbtn = new ImageIcon("start_btn.png");
    ImageIcon howtobtn = new ImageIcon("howto_btn.png");
    
    JButton encryption = new JButton(startbtn); //암호화
    JButton decryption = new JButton(howtobtn); //복호화
    
    JTextField key_text = new JTextField(); //가상의 키
    JTextField message_text = new JTextField(); //평문 입력
    JTextField password_text = new JTextField(); //암호화된 문자 보여줄 텍스트박스
    static JTextField messageTwo_text = new JTextField(); //두개씩 나눠진 평문
    JTextField messageShow_text = new JTextField(); //복호화된 문자 보여줄 텍스트 박스
    
    JLabel key_label = new JLabel("가상의 키 입력 : ");
    JLabel message_label = new JLabel("평문 입력 : ");
    JLabel password_label = new JLabel("암호문 : ");
    JLabel messageShow_label = new JLabel("복호문 : ");
    
    JLabel Setboard = new JLabel();
    
    
    public static String zChk ="";
    public static char alphabetBoard[][] = new char[5][5];
    public static boolean oddFlag = false;
    
    public static String message = "";
    public static String key="";
    String blankCheck="";
    String strEncryption = "";
	
	public Main() {
		
	    add(key_text);
	    key_text.setBounds(260,150,220,40);
	    
	    add(message_text);
	    message_text.setBounds(260,210,220,40);
	    
	    add(messageTwo_text);
	    messageTwo_text.setBounds(260,270,220,40);
	    
	    add(password_text);
	    password_text.setBounds(260,330,220,40);
	    
	    add(messageShow_text);
	    messageShow_text.setBounds(260,390,220,40);
	    
	    add(key_label);
	    key_label.setBounds(100, 150, 220, 40);
	    
	    add(message_label);
	    message_label.setBounds(100, 210, 220, 40);
	    
	    add(password_label);
	    password_label.setBounds(100, 330, 220, 40);
	    
	    add(messageShow_label);
	    messageShow_label.setBounds(100, 390, 220, 40);

		add(encryption);
		encryption.setText("암호화");
		encryption.setBounds(130, 500, 150, 40); //앞에 두개는 위치, 뒤에 두개는 크기
		
	    add(decryption);
	    decryption.setText("복호화");
	    decryption.setBounds(320, 500, 150, 40);
	    
	    add(Setboard);
		Setboard.setBounds(285, 550, 220, 40);
		
	    setLayout(null);
	    
	    String boardSet[] = {"","","","",""};
	    String showBoard[][] = new String[5][5];

	    
	    encryption.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) { 

				String key = key_text.getText();
				
				message = message_text.getText();
				
				message = chk(message);
				
				String board = board(key);
				
				Setboard.setText("암호판");
				
				strEncryption = strEncryption(key, message);
				
				password_text.setText(strEncryption.toLowerCase());
				
				for( int i = 0 ; i < alphabetBoard.length ; i++ )
				{
					for( int j = 0; j <alphabetBoard[i].length ; j++ )
					{
						showBoard[i][j] = String.valueOf(alphabetBoard[i][j]);
					}
				}	
				
				JTable table = new JTable(showBoard, boardSet);
				table.setBounds(200, 600, 200, 100);
				add(table);
				
			}
		});
	    
	    decryption.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				for( int i = 0 ; i < strEncryption.length() ; i++ ) 
				{
					if(strEncryption.charAt(i)==' ') //공백제거
						strEncryption = strEncryption.substring(0,i)+strEncryption.substring(i+1,strEncryption.length());
				}
				
				String strDecryption = strDecryption(key, strEncryption, zChk);
				
				for( int i = 0 ; i < strDecryption.length() ; i++)
				{
					if(blankCheck.charAt(i)=='1')
					{
						strDecryption = strDecryption.substring(0,i)+" "+strDecryption.substring(i,strDecryption.length());
					}
				}
				
				messageShow_text.setText(strDecryption);
				
			}
	    });
		
	}

	//z를 q로 치환하는 메서드
	public String chk(String message) {
		
		//message = message.replace(" ",""); //공백 제거
		
		
		
		for(int i = 0; i < message.length(); i++) {
			
			if(message.charAt(i)==' ') //공백제거
			{
				message = message.substring(0,i)+message.substring(i+1,message.length());
				blankCheck+=10;
			}
			else
			{
				blankCheck+=0;
			}
			
			if(message.charAt(i)=='z') //z를 q로 바꿔줘서 처리함.
			{
				message = message.substring(0,i)+'q'+message.substring(i+1,message.length());
				zChk+=1;
				
			}
			else {
				zChk+=0;
			}
		}
		return message.toLowerCase();
    	
    };
    
    public String board(String key) {
    	String keySet = "";					// 중복된 문자가 제거된 문자열을 저장할 문자열.
		boolean duplicationFlag = false;		// 문자 중복을 체크하기 위한 flag 변수.
		int keyLengthCount = 0;					// alphabetBoard에 keyForSet을 넣기 위한 count변수.
		
		
		key = key.toLowerCase();
		
		key += "abcdefghijklmnopqrstuvwxy"; 	// 키에 모든 알파벳을 추가.

		
		// 중복처리
		for( int i = 0 ; i < key.length() ; i++ ) 
		{
			if(key.charAt(i)=='z') {
				key = key.substring(0,i)+'q'+key.substring(i+1,key.length());
			}
			
			for( int j = 0 ; j < keySet.length() ; j++ )
			{
				
				if(key.charAt(i)==keySet.charAt(j))
				{
					duplicationFlag = true;
					break;
				}
			}
			
			/*if(key.charAt(i)=='z') {
				key = key.substring(0,i)+'q'+key.substring(i+1,key.length());
			}*/
			
			if(!(duplicationFlag)) keySet += key.charAt(i);
			duplicationFlag = false;
		}
		
		
		//배열에 대입
		for( int i = 0 ; i < alphabetBoard.length ; i++ )
		{
			for( int j = 0; j <alphabetBoard[i].length ; j++ )
			{
				alphabetBoard[i][j] = keySet.charAt(keyLengthCount++);
			}
		}
		
		return keySet.toLowerCase();
	
    }
    
    private static String strEncryption(String key, String str){
		ArrayList<char[]> playFair = new ArrayList<char[]>();
		ArrayList<char[]> encPlayFair = new ArrayList<char[]>();
		int x1 = 0 , x2 = 0 , y1 = 0, y2 = 0;
		String encStr ="";
		String play = "";
		
		
		
		for( int i = 0 ; i < str.length() ; i+=2 ) // arraylist 세팅
		{
			char[] tmpArr = new char[2];
			tmpArr[0] = str.charAt(i);
			try{
				if( str.charAt(i) == str.charAt(i+1)) //글이 반복되면 x추가
				{
					tmpArr[1] = 'x';
					i--;
				}else{
					tmpArr[1] = str.charAt(i+1);
				}
			}catch(StringIndexOutOfBoundsException e)
			{
				tmpArr[1] = 'x'; 
				oddFlag = true;
			}
			playFair.add(tmpArr);
		}
		
		for(int i = 0 ; i < playFair.size() ; i++ )
		{
			System.out.print(playFair.get(i)[0]+""+playFair.get(i)[1]+" ");
			play += playFair.get(i)[0]+""+playFair.get(i)[1]+" ";
		}
		
		messageTwo_text.setText(play);
		
		System.out.println();
		
		for(int i = 0 ; i < playFair.size() ; i++ )
		{

			char[] tmpArr = new char[2];
			for( int j = 0 ; j < alphabetBoard.length ; j++ ) //쌍자암호의 각각 위치체크
			{
				for( int k = 0 ; k < alphabetBoard[j].length ; k++ )
				{
					if(alphabetBoard[j][k] == playFair.get(i)[0])
					{
						x1 = j;
						y1 = k;
					}
					if(alphabetBoard[j][k] == playFair.get(i)[1])
					{
						x2 = j;
						y2 = k;
					}
				}
			}
			
			if(x1==x2) //행이 같은경우
			{
				tmpArr[0] = alphabetBoard[x1][(y1+1)%5];
				tmpArr[1] = alphabetBoard[x2][(y2+1)%5];
			}
			else if(y1==y2) //열이 같은 경우
			{
				tmpArr[0] = alphabetBoard[(x1+1)%5][y1];
				tmpArr[1] = alphabetBoard[(x2+1)%5][y2];
			} 
			else //행, 열 모두 다른경우
			{
				tmpArr[0] = alphabetBoard[x2][y1];
				tmpArr[1] = alphabetBoard[x1][y2];
			}
			
			encPlayFair.add(tmpArr);
			
		}
		
		for(int i = 0 ; i < encPlayFair.size() ; i++)
		{
			encStr += encPlayFair.get(i)[0]+""+encPlayFair.get(i)[1]+" ";
		}
		
		
		return encStr.toLowerCase();
	}
    
    
    private static String strDecryption(String key, String str, String zCheck) {
		ArrayList<char[]> playFair = new ArrayList<char[]>(); //바꾸기 전 쌍자암호를 저장할 곳
		ArrayList<char[]> decPlayFair = new ArrayList<char[]>(); //바꾼 후의 쌍자암호 저장할 곳
		int x1 = 0 , x2 = 0 , y1 = 0, y2 = 0; //쌍자 암호 두 글자의 각각의 행,열 값
		String decStr ="";

		int lengthOddFlag = 1;
		
		
		for( int i = 0 ; i < str.length() ; i+=2 )
		{
			char[] tmpArr = new char[2];
			tmpArr[0] = str.charAt(i);
			tmpArr[1] = str.charAt(i+1);
			playFair.add(tmpArr);
			
		}
		
		
		for(int i = 0 ; i < playFair.size() ; i++ )
		{
			char[] tmpArr = new char[2];
			for( int j = 0 ; j < alphabetBoard.length ; j++ )
			{
				for( int k = 0 ; k < alphabetBoard[j].length ; k++ )
				{
					if(alphabetBoard[j][k] == playFair.get(i)[0])
					{
						x1 = j;
						y1 = k;
					}
					if(alphabetBoard[j][k] == playFair.get(i)[1])
					{
						x2 = j;
						y2 = k;
					}
				}
			}
			
			if(x1==x2) //행이 같은 경우 각각 바로 아래열 대입
			{
				tmpArr[0] = alphabetBoard[x1][(y1+4)%5];
				tmpArr[1] = alphabetBoard[x2][(y2+4)%5];
			}
			else if(y1==y2) //열이 같은 경우 각각 바로 옆 열 대입
			{
				tmpArr[0] = alphabetBoard[(x1+4)%5][y1];
				tmpArr[1] = alphabetBoard[(x2+4)%5][y2];
			}
			else //행, 열 다른경우 각자 대각선에 있는 곳.
			{
				tmpArr[0] = alphabetBoard[x2][y1];
				tmpArr[1] = alphabetBoard[x1][y2];
			}
			
			decPlayFair.add(tmpArr);
			
		}
		System.out.println("decStr1: " + decStr);
		
		for(int i = 0 ; i < decPlayFair.size() ; i++) //중복 문자열 돌려놓음
		{
			if(i!=decPlayFair.size()-1 && decPlayFair.get(i)[1]=='x' 
					&& decPlayFair.get(i)[0]==decPlayFair.get(i+1)[0])
			{	
				decStr += decPlayFair.get(i)[0];
			}
			else
			{
				decStr += decPlayFair.get(i)[0]+""+decPlayFair.get(i)[1];
			}
		}
		System.out.println("decStr2: " + decStr);
		
		
		for(int i = 0 ; i < zCheck.length() ; i++ )//z위치 찾아서 q로 돌려놓음
		{
			if( zCheck.charAt(i) == '1' ) 
				decStr = decStr.substring(0,i)+'z'+decStr.substring(i+1,decStr.length());
			
		}
		
		
		if(oddFlag) decStr = decStr.substring(0,decStr.length()-1);
		
		return decStr;
	}

	public static void main(String[] args) {
		
		Index frame = new Index();
		Main panel = new Main();
		
		frame.add(panel);
		frame.pack();
	  	frame.setVisible(true);//창을 보이게 함

	}

}
