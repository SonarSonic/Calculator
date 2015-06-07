package sonar.calculator.mod.api;

/**Used for synchronising Tile Entities with client*/
public class SyncData {
	public boolean used;
	public int data;

	public SyncData(boolean used, int data) {
		this.used = used;
		this.data = data;
	}
}