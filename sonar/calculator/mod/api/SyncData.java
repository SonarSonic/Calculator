package sonar.calculator.mod.api;

/**Used for synchronising Tile Entities with client*/
public class SyncData {
	public boolean used;
	public Object data;

	public SyncData(boolean used, Object data) {
		this.used = used;
		this.data = data;
	}
}