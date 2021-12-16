package me.maxitros.legends.skills.keys;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class KeybindsRegister {
    private static final String catergory = "Magic api";
    public static final KeyBinding
            USEMAGIC_KEY = new KeyBinding("key.usemagic", Keyboard.KEY_G, catergory),
            NEXTSKILL_KEY = new KeyBinding("key.nextskill", Keyboard.KEY_H, catergory),
            PREVSKILL_KEY = new KeyBinding("key.prervskill", Keyboard.KEY_J, catergory);
    public static void register()
    {
        setRegister(USEMAGIC_KEY);
        setRegister(NEXTSKILL_KEY);
        setRegister(PREVSKILL_KEY);
    }

    private static void setRegister(KeyBinding binding)
    {
        ClientRegistry.registerKeyBinding(binding);
    }
}
