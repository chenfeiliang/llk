package llk;

//·½¿éÊý¾Ý
public class Fangkuai {

	private byte[] b;

	public Fangkuai(byte[] b) {
		this.b = b;
	}

	public byte[] getBytes() {
		return b;
	}

	public boolean same(Fangkuai fk) {
		byte[] a = fk.getBytes();
		for (int i = 0; i < b.length; i++) {
			
		//	System.out.println("a"+i+" is "+a[i]);
		//	System.out.println("b"+i+" is "+b[i]);
			
			if (a[i] != b[i]) {
				return false;
			}
		}
		return true;
	}
}
