package vv.z;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import org.lwjgl.glfw.GLFW;

public class Main implements ClientModInitializer {

    final int keybind = GLFW.GLFW_KEY_F7;

    // bad keybind logic because no need for extra mixin lol
    private boolean state = false;

    @Override
    public void onInitializeClient() {
        // create da tick listener
        registerListener();
    }

    void registerListener() {
        ClientTickEvents.START_CLIENT_TICK.register(mc -> {
            if (mc.player == null) return;
            boolean pressed = GLFW.glfwGetKey(mc.getWindow().getHandle(), keybind) == 1;
            if (pressed && !state) {
                // slot can be anything < 0 or > 9
                mc.player.networkHandler.getConnection().send(new UpdateSelectedSlotC2SPacket(-100));
            }
            state = pressed;
        });
    }
}
