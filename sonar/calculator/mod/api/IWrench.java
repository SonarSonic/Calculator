package sonar.calculator.mod.api;

/**used in blocks or Tile Entities to check if they can be dropped with a Calculator Wrench*/
public abstract interface IWrench
{
	/**@return if the block/tile can be dropped*/
	public abstract boolean canWrench();
}
