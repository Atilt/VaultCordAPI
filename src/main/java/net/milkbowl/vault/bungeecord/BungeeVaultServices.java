package net.milkbowl.vault.bungeecord;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class BungeeVaultServices {

    private static final Map<Class<?>, ProviderWrapper<?>> services = new ConcurrentHashMap<>();

    private BungeeVaultServices() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static FluentServices services() {
        return new FluentServices();
    }

    public static class FluentServices {

        public <T> FluentServices register(Class<T> serviceClass, T provider) {
            if (serviceClass == null || provider == null) {
                throw new IllegalArgumentException("Service class and provider cannot be null");
            }
            if (!serviceClass.isInstance(provider)) {
                throw new IllegalArgumentException("Provider does not implement the service class");
            }
            services.put(serviceClass, new ProviderWrapper<>(provider));
            return this;
        }

        public <T> FluentServices then(Runnable callback) {
            callback.run();
            return this;
        }

        @SuppressWarnings("unchecked")
        public <T> Optional<T> get(Class<T> serviceClass) {
            ProviderWrapper<?> wrapper = services.get(serviceClass);
            if (wrapper != null) {
                return Optional.of((T) wrapper.provider);
            }
            return Optional.empty();
        }

        public boolean isRegistered(Class<?> serviceClass) {
            return services.containsKey(serviceClass);
        }

        public FluentServices unregister(Class<?> serviceClass) {
            services.remove(serviceClass);
            return this;
        }

        public Optional<Economy> economy() {
            return get(Economy.class);
        }

        public Optional<Permission> permission() {
            return get(Permission.class);
        }

        public Optional<Chat> chat() {
            return get(Chat.class);
        }
    }

    private static class ProviderWrapper<T> {
        final T provider;
        final int priority;

        ProviderWrapper(T provider) {
            this(provider, 0);
        }

        ProviderWrapper(T provider, int priority) {
            this.provider = provider;
            this.priority = priority;
        }
    }
}
