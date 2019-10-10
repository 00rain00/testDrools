package util;
import core.Game;
import manager.DisplayManager;



/**
 * FightingICEのメインメソッドを持つクラス．
 */
public class Main {

	/**
	 * 起動時に入力した引数に応じて起動情報を設定し, それを基にゲームを開始する．<br>
	 * このメソッドはFightingICEのメインメソッドである．
	 *
	 * @param options
	 *            起動時に入力した全ての引数を格納した配列
	 */
	public static void main(String[] options) {
		Game game = new Game();
		
		game.setOptions(options);
		DisplayManager displayManager = new DisplayManager();

		// ゲームの開始
		displayManager.start(game);
		 // load up the knowledge base
       
//        Message message = new Message();
//        message.setMessage("Hello World");
//        message.setStatus(Message.HELLO);
//        System.out.println(message.getMessage());
       
		
		
	}
	
	
	public static class Message {
		 
	    public static final int HELLO = 0;
	    public static final int GOODBYE = 1;

	    private String message;

	    private int status;

	    public String getMessage() {
	        return this.message;
	    }

	    public void setMessage(String message) {
	        this.message = message;
	    }

	    public int getStatus() {
	        return this.status;
	    }

	    public void setStatus(int status) {
	        this.status = status;
	    }

	}


}