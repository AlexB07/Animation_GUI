import java.util.ArrayList;

import javafx.scene.Group;

public class Frame extends Group {
	
	 private ArrayList<Canvas> undoList = new ArrayList<Canvas>();
	
	
	
	
	public Frame(Canvas other) {
		this.addToList(other);
	}
	
	public void addToList(Canvas other) {
		undoList.add(new Canvas(other));
	}
	
	
	public Frame(ArrayList<Canvas> otherList) {		
		for (Canvas c : otherList) {
			undoList.add(new Canvas(c));
			System.out.println(otherList.size());	
			}
			otherList.clear();
				otherList.add(new Canvas(Main.canvas));
		}
	
	public ArrayList<Canvas> getUndoList(){
		return undoList;
	}

		
	public Canvas getFrame() {
		return undoList.get(undoList.size()-1);
	}
	
	public int getSizeOfList() {
		return undoList.size();
	}
	
	
	
	
	
	
	
	
	

}
