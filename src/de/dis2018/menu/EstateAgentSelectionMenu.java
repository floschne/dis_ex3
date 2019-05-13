package de.dis2018.menu;

import java.util.Iterator;
import java.util.List;

import de.dis2018.data.EstateAgent;

/**
 *  A small menu showing all estate agents from a set for selection
 */
public class EstateAgentSelectionMenu extends Menu {
	public static final int BACK = -1;
	
	public EstateAgentSelectionMenu(String title, List<EstateAgent> list) {
		super(title);
		
		Iterator<EstateAgent> it = list.iterator();
		while(it.hasNext()) {
			EstateAgent m = it.next();
			addEntry(m.getName(), m.getId());
		}
		addEntry("Back", BACK);
	}
}
