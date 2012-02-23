package exploration;

import org.newdawn.slick.geom.Rectangle;

public class Teleporter extends Rectangle{
	private String idMapDepart;
	private String idMapDestination;
	private int destinationX;
	private int destinationY;
	
	public Teleporter(float x, float y, float width, float height,
			String idMapDepart, String idMapDestination, int destinationX,
			int destinationY) {
		super(x, y, width, height);
		this.idMapDepart = idMapDepart;
		this.idMapDestination = idMapDestination;
		this.destinationX = destinationX;
		this.destinationY = destinationY;
	}

	public String getIdMapDepart() {
		return idMapDepart;
	}

	public String getIdMapDestination() {
		return idMapDestination;
	}

	public int getDestinationX() {
		return destinationX;
	}

	public int getDestinationY() {
		return destinationY;
	}
	
	
	
}
