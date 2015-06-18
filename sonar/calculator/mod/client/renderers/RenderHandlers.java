package sonar.calculator.mod.client.renderers;

import sonar.calculator.mod.client.models.ModelAtomicMultiplier;
import sonar.calculator.mod.client.models.ModelConductorMast;
import sonar.calculator.mod.client.models.ModelGenerator;
import sonar.calculator.mod.client.models.ModelScarecrow;
import sonar.core.client.renderers.SonarTERender;

public class RenderHandlers {

	public static class AtomicMultiplier extends SonarTERender {
		public AtomicMultiplier() {
			super(new ModelAtomicMultiplier(),
					"Calculator:textures/model/atomicmultiplier.png");
		}

	}
	public static class ConductorMast extends SonarTERender {
		public ConductorMast() {
			super(new ModelConductorMast(),
					"Calculator:textures/model/conductormask.png");
		}

	}
	public static class Scarecrow extends SonarTERender {
		public Scarecrow(){
			super(new ModelScarecrow(),"Calculator:textures/model/scarecrow.png");
		}	
	}
	public static class StarchExtractor extends SonarTERender {
		public StarchExtractor(){
			super(new ModelGenerator(),"Calculator:textures/model/starchextractor.png");
		}			
	}
	public static class RedstoneExtractor extends SonarTERender {
		public RedstoneExtractor(){
			super(new ModelGenerator(),"Calculator:textures/model/redstoneextractor.png");
		}			
	}
	public static class GlowstoneExtractor extends SonarTERender {
		public GlowstoneExtractor(){
			super(new ModelGenerator(),"Calculator:textures/model/glowstoneextractor.png");
		}			
	}
	
}
