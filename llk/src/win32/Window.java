package win32;


import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

public class Window {

	private static User32 user32 = User32.INSTANCE;

	// 取窗口句柄
	public static int getHwnd(String title) {
		return user32.FindWindowA(null, title);
	}

	// 取窗口矩形
	public static Rect getRect(int hwnd) {
		Rect r = new Rect();
		user32.GetWindowRect(hwnd, r);
		return r;
	}
	
	//窗口截图
	public static BufferedImage getImage(int hwnd){
		Rect r = Window.getRect(hwnd);
		Rectangle rg = new Rectangle(r.left, r.top, r.right-r.left, r.bottom-r.top);
		try {
			return new Robot().createScreenCapture(rg);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
