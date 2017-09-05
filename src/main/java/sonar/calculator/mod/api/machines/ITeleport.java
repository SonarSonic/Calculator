package sonar.calculator.mod.api.machines;

import sonar.core.api.utils.BlockCoords;

public interface ITeleport {

    /**
     * assigned frequency
     */
    int teleporterID();

    /**
     * user assigned name
     */
    String name();

    /**
     * teleporter coordinates
     */
    BlockCoords getCoords();
}
