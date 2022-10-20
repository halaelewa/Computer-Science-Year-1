
public class Shape {

	private int numSides;
	private String colour;
	
	public Shape (String name, String colour) {
		
		if (name.equals("circle")) {
			numSides = 1;
		} else if (name.equals("triangle")) {
			numSides = 3;
		} else if (name.equals("square")) {
			numSides = 4;
		} else if (name.equals("star")) {
			numSides = 5;
		} else {
			// Should never get in here!
			numSides = 0;
		}
		
		this.colour = colour;
	}
	
	public Shape (int numSides, String colour) {
		this.numSides = numSides;
		this.colour = colour;
	}
	
	public int getNumSides () {
		return numSides;
	}
	
	public String getColour () {
		return colour;
	}

	public String toString () {
		return numSides + " " + colour;
	}

}
