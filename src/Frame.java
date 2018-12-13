import java.util.ArrayList;

import javafx.scene.Group;

public class Frame extends Group {
	
	ArrayList<Canvas> undoList = new ArrayList<Canvas>();
	
	
	
	
	public Frame() {
		
	}
	
	public Frame(ArrayList<Canvas> otherList) {		
		for (Canvas c : otherList) {
			undoList.add(new Canvas(c));
			System.out.println(otherList.size());	
			}
			otherList.clear();
				otherList.add(new Canvas(Main.canvas));
		}

		
	
	
	
	
	

}
