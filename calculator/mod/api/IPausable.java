package sonar.calculator.mod.api;

/**for machines which can be paused or stopped altogether*/
public interface IPausable {

	/**when the machine is paused/resumed*/
	public void onPause();
	/**if the machine is running*/
	public boolean isActive();
	/**if the machine is paused*/
	public boolean isPaused();
}
