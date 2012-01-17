package constantes;

import ingame.Teleporter;

import java.util.ArrayList;
import java.util.Collection;

public class TPList extends ArrayList<Teleporter>{

	public TPList() {
		super();
		this.add(new Teleporter(578, 69, 78, 273, "01", "02", 75, 272, false));
		this.add(new Teleporter(1, 129, 47, 243, "02", "01", 560, 260, true));
	}

}
