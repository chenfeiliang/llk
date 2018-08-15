package win32;

public class Mouse {

	private static User32 user32 = User32.INSTANCE;

	//Ä£ÄâÊó±ê×ó¼üµ¥»÷
	public static void click(int hwnd, int x, int y) {
		int v = x + (y << 16);
		user32.PostMessageA(hwnd, 513, 1, v);
		user32.PostMessageA(hwnd, 514, 0, v);
	}

}
