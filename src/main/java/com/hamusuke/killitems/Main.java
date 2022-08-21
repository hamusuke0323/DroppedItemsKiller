package com.hamusuke.killitems;

import com.hamusuke.killitems.command.KillDroppedItemsCommand;
import emu.grasscutter.plugin.Plugin;

public final class Main extends Plugin {
    private static Main INSTANCE;

    @Override
    public void onLoad() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        this.getHandle().registerCommand(new KillDroppedItemsCommand());
        this.getLogger().info("DroppedItemsKiller has been enabled.");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("DroppedItemsKiller has been disabled.");
    }

    public static Main getInstance() {
        return INSTANCE;
    }
}
