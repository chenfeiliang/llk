package llk;

import java.util.ArrayList;
import java.util.List;

//用来保存不同种类的方块。如果是同一种，就不重复保存了
public class FkSet {

	private List<Fangkuai> list = new ArrayList<Fangkuai>();

	public int size(){
		return list.size();
	}
	//返回种类编号
	public int add(Fangkuai fk) {
		for (int i = 0; i < list.size(); i++) {
			Fangkuai f = list.get(i);
			if (fk.same(f)) {
				return i + 1;   //返回数学意义上的编号
			}
		}
		list.add(fk);
		return list.size();
	}

}
