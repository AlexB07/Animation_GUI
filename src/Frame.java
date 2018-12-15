import java.util.ArrayList;

import javafx.scene.Group;

public class Frame extends Group {
	/*Saves canvas, for the undo of each frame*/
	private ArrayList<Canvas> undoList = new ArrayList<Canvas>();

	/*Creates a frame*/
	public Frame(Canvas other) {
		this.addToList(other);
	}
	/*adds canvas to the frame undolist*/
	public void addToList(Canvas other) {
		undoList.add(new Canvas(other));
	}
	/* Returns the undolist of this frame */
	public ArrayList<Canvas> getUndoList() {
		return undoList;
	}
	/*Retuns the last canvas in the undolist*/
	public Canvas getFrame() {
		return undoList.get(undoList.size() - 1);
	}
	/*return the size of the arraylist*/
	public int getSizeOfList() {
		return undoList.size();
	}

}
