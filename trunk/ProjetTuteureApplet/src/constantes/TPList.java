package constantes;


import java.util.ArrayList;

import exploration.Teleporter;

public class TPList extends ArrayList<Teleporter>{
	private static final long serialVersionUID = -2707622405578972370L;

	public TPList() {
		super();
		this.add(new Teleporter(578, 69, 78, 400, "01", "02", 65, 272));
		this.add(new Teleporter(1, 129, 47, 243, "02", "01", 560, 260));
	}

}
