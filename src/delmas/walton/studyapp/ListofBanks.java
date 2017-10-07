package delmas.walton.studyapp;

import java.io.Serializable;
import java.util.ArrayList;

public class ListofBanks implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1554421568867838164L;
	private ArrayList<QuestionBank> list;
	
	public ListofBanks() {
		this.list = new ArrayList<QuestionBank>();
	}
	
	public QuestionBank getBank(int index) {
		if (index < this.list.size()) {
			return this.list.get(index);
		} else {
			return null;
		}
	}
	
	public void setBank(QuestionBank bank) {
		if(bank != null) {
			this.list.add(bank);
		}
	}
	
}
