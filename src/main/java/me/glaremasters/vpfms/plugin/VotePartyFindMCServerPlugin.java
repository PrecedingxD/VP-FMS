package me.glaremasters.vpfms.plugin;

import me.glaremasters.vpfms.VotePartyFindMCServer;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

public final class VotePartyFindMCServerPlugin extends JavaPlugin {

    private VotePartyFindMCServer findMCServer;

    @Override
    public void onEnable() {
        this.findMCServer = new VotePartyFindMCServer(this);
        this.findMCServer.load();

        getServer().getServicesManager().register(VotePartyFindMCServer.class, this.findMCServer, this, ServicePriority.Normal);
    }

    @Override
    public void onDisable() {
        if (this.findMCServer != null) {
            this.findMCServer.kill();
        }

        this.findMCServer = null;

        getServer().getServicesManager().unregisterAll(this);
    }

    @Nullable
    public VotePartyFindMCServer findMCServer() {
        return findMCServer;
    }
}
