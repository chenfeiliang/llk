package llk;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author 神
 * 
 */
public class BMP {

	private int width;
	private int height;
	private byte[] data;

	public BMP() {

	}

	public BMP(String src) {
		this.read(src);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	//将字节通过与运算   和 移位运算  拼接为对应值  18---21字节存图片宽度  22---25字节存图片高度   详细介绍请看    http://itindex.net/detail/50025-java-bmp-图片   
	public static int b2i(byte[] b, int s) {
		int ret = 0;
		for (int i = 0; i < 4; i++) {
			int temp = b[s + i] & 0xff;
			ret += temp << (8 * i);
		}
		return ret;
	}

	/** * 读取图片文件 * @param src 文件路径 */
	public void read(String src) {
		width = 0;
		height = 0;
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(src));
			byte[] b = new byte[1024 * 1024];
			int len = 0;
			while ((len = in.read(b)) != -1) {
				bs.write(b, 0, len);
				bs.flush();
			}
			data = bs.toByteArray();   //       详细介绍请看    http://itindex.net/detail/50025-java-bmp-图片 
			width = b2i(data, 18);     //18---21字节存图片宽度           注意是以像素为单位  
			height = b2i(data, 22);    //22---25字节存图片高度
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bs.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 这方法捣鼓了我好久才弄出来
	public int getColor(int x, int y) {
		// BMP图要求每行字节数为4的倍数,不够则填充1-3个无用字节  
		
		int lineW = 0;//每行字节数
		
		switch ((width * 3) % 4) {  //BMP图片width是以像素为单位，而一个像素3个字节
		case 0:
			lineW = width * 3;
			break;
		case 1:
			lineW = width * 3 + 3;
			break;
		case 2:
			lineW = width * 3 + 2;
			break;
		case 3:
			lineW = width * 3 + 1;
		}
		int i = 54 + (height - y - 1) * lineW + 3 * x;  //通过一个公式得到该点的像素位置  共有三个字节，每个字节表示一个颜色 r g b  
		int r = data[i + 2] & 0xff;
		int g = data[i + 1] & 0xff;
		int b = data[i] & 0xff;
		return r + (g << 8) + (b << 16);
	}

	public void setColor(int x, int y, int v) {
		int lineW = 0;
		switch ((width * 3) % 4) {
		case 0:
			lineW = width * 3;
			break;
		case 1:
			lineW = width * 3 + 3;
			break;
		case 2:
			lineW = width * 3 + 2;
			break;
		case 3:
			lineW = width * 3 + 1;
		}
		int i = 54 + (height - y - 1) * lineW + 3 * x;
		data[i + 2] = (byte) ((v >> 16) & 0xff);
		data[i + 1] = (byte) ((v >> 8) & 0xff);
		data[i] = (byte) (v & 0xff);
	}
	

	// 取矩形颜色数据
	public byte[] getData(int x, int y, int w, int h) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(w * h);
		try {
			for (int i = x; i < x + w; i++) {
				for (int j = y; j < y + h; j++) {
				//	System.out.println(i + "," + j + " 颜色为：" + getColor(i, j));
					bos.write(getColor(i, j));
					bos.flush();
				}
			}
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bos.toByteArray();
	}

}




/**
 * 读取BMP文件的方法(BMP24位)
 *//*

public int[][] readFile(String path) {

	try {
		// 创建读取文件的字节流
		FileInputStream fis = new FileInputStream(path);
		BufferedInputStream bis = new BufferedInputStream(fis);
		// 读取时丢掉前面的18位，
		// 读取图片的18~21的宽度
		bis.skip(18);
		byte[] b = new byte[4];
		bis.read(b);
		// 读取图片的高度22~25
		byte[] b2 = new byte[4];
		bis.read(b2);

		// 得到图片的高度和宽度
		int width = byte2Int(b);
		int heigth = byte2Int(b2);
		// 使用数组保存得图片的高度和宽度
		int[][] date = new int[heigth][width];

		int skipnum = 0;
		if (width * 3 / 4 != 0) {
			skipnum = 4 - width * 3 % 4;
		}
		// 读取位图中的数据，位图中数据时从54位开始的，在读取数据前要丢掉前面的数据
		bis.skip(28);
		for (int i = 0; i < date.length; i++) {
			for (int j = 0; j < date[i].length; j++) {
				// bmp的图片在window里面世3个byte为一个像素
				int blue = bis.read();
				int green = bis.read();
				int red = bis.read();
				// 创建一个Color对象，将rgb作为参数传入其中
				Color c = new Color(red, green, blue);
				// Color c = new Color(blue,green,red);
				// 将得到的像素保存到date数组中
				date[i][j] = c.getRGB();
			}
			// 如果补0的个数不为0，则需要跳过这些补上的0
			if (skipnum != 0) {
				bis.skip(skipnum);
			}
		}
		return date;
	} catch (Exception e) {
		e.printStackTrace();

	}
	return null;

}

// 将四个byte拼接成一个int
public int byte2Int(byte[] by) {
	int t1 = by[3] & 0xff;
	int t2 = by[2] & 0xff;
	int t3 = by[1] & 0xff;
	int t4 = by[0] & 0xff;
	int num = t1 << 24 | t2 << 16 | t3 << 8 | t4;
	return num;

}*/
