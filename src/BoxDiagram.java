
import acm.graphics.*;
import acm.program.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class BoxDiagram extends GraphicsProgram {

	private static final double BOX_WIDTH = 120;
	private static final double BOX_HEIGHT = 50;
	private static final int NAME_SIZE = 25;
	private static JTextField textField;
	private static JButton add;
	private static JButton remove;
	private static JButton clear;
	private HashMap<String, GObject> list;
	private GPoint last;
	private GObject currentObject;

	public void init() {
		list = new HashMap<String, GObject>();
		textField = new JTextField(NAME_SIZE);
		textField.addActionListener(this);
		add = new JButton("Add");
		remove = new JButton("Remove");
		clear = new JButton("Clear");
		add(new JLabel("Name"), SOUTH);
		add(textField, SOUTH);
		add(add, SOUTH);
		add(remove, SOUTH);
		add(clear, SOUTH);
		addActionListeners();
		addMouseListeners();

	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == add || source == textField) {
			addBox(textField.getText());
		} else if (source == remove) {
			removeBox(textField.getText());
		} else if (source == clear) {
			removeContents();
		}
	}

	private void removeBox(String name) {
		GObject obj = list.get(name);
		if (obj != null) {
			remove(obj);
		}
	}

	private void removeContents() {
		Iterator<String> iterator = list.keySet().iterator();
		while (iterator.hasNext()) {
			removeBox(iterator.next());
		}
		list.clear();
	}

	public void mousePressed(MouseEvent e) {
		last = new GPoint(e.getPoint());
		currentObject = getElementAt(last);
	}

	public void mouseDragged(MouseEvent e) {
		if (currentObject != null) {
			currentObject.move(e.getX() - last.getX(), e.getY() - last.getY());
			last = new GPoint(e.getPoint());
		}
	}

	public void mouseClicked(MouseEvent e) {
		if (currentObject != null)
			currentObject.sendToFront();
	}

	public void addBox(String text) {
		GCompound box = new GCompound();
		GRect outline = new GRect(BOX_WIDTH, BOX_HEIGHT);
		GLabel label = new GLabel(text);
		box.add(outline, -BOX_WIDTH / 2, -BOX_HEIGHT / 2);
		box.add(label, -label.getWidth() / 2, label.getAscent() / 2);
		add(box, getWidth() / 2, getHeight() / 2);
		list.put(text, box);
	}
}