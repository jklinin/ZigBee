package org.project.mw.gui;

import java.awt.KeyEventDispatcher;

import java.awt.event.KeyEvent;

public class MyDispatcher implements KeyEventDispatcher {

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		if (e.getID() == KeyEvent.KEY_PRESSED) {
			if (e.getKeyCode() == 82) {
				EditorWindow.rotEnbledKey = true;
			}
			if (e.getKeyCode() == 68) {
				EditorWindow.removeEnbKey = true;
			}
		}

		if (e.getID() == KeyEvent.KEY_RELEASED) {
			if (e.getKeyCode() == 82) {
				EditorWindow.rotEnbledKey = false;
			}
			if (e.getKeyCode() == 68) {
				EditorWindow.removeEnbKey = false;
			}
		}
		return false;
	}
}
