package win32;

import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;

public interface User32 extends StdCallLibrary{

	User32 INSTANCE = (User32)Native.loadLibrary("User32",User32.class);
	
	int PostMessageA(int a,int b,int c,int d);
	
	int FindWindowA(String a,String b);
	
	int GetWindowRect(int hwnd,Rect r);
	
}
//__cdecl 手动清栈，不严格要求参数数量
//_stdcall 自动清栈，严格要求参数数量