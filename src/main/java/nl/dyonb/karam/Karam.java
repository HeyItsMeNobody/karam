package nl.dyonb.karam;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import nl.dyonb.karam.registry.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Karam implements ModInitializer {

	public static final String LOG_ID = "Karam";
	public static final String MOD_ID = "karam";

	public static final Logger LOGGER = LogManager.getLogger(LOG_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.log(Level.INFO, "Hello Karam world!");

		KaramConfig.initialize();
		KaramItemGroups.initialize();
		KaramItems.initialize();
		KaramBlockEntityTypes.initialize();
		KaramBlocks.initialize();
		KaramServerPackets.initialize();
		KaramScreenHandlers.initialize();
	}

	public static Identifier identifier(String name) {
		return new Identifier(MOD_ID, name);
	}

}
