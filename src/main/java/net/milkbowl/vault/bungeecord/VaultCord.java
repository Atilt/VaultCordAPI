package net.milkbowl.vault.bungeecord;

import net.milkbowl.vault.economy.Economy;

/**
 * VaultCord entry point for registering the BungeeVaultEconomy provider.
 */
public final class VaultCord {

    private VaultCord() {
        // Prevent instantiation
    }

    /**
     * Registers the given BungeeVaultEconomy instance with the service registry.
     *
     * @param economyProvider The economy provider instance
     */
    public static void registerEconomy(BungeeVaultEconomy economyProvider) {
        if (economyProvider == null) {
            throw new IllegalArgumentException("Economy provider cannot be null");
        }
        // Register in BungeeVaultServices
        BungeeVaultServices.services().register(Economy.class, economyProvider);
    }

    /**
     * Convenience method to retrieve the registered economy provider.
     *
     * @return The registered economy provider, if any
     */
    public static Economy getEconomy() {
        return BungeeVaultServices.services().economy()
                .orElseThrow(() -> new IllegalStateException("No Economy provider registered in VaultCord"));
    }
}
