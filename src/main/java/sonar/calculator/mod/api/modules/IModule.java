package sonar.calculator.mod.api.modules;

import sonar.core.api.IRegistryObject;

/**
 * all modules must implement this
 */
public interface IModule extends IRegistryObject {
	
    String getClientName();
}
