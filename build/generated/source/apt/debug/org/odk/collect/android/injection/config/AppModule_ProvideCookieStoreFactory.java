package org.odk.collect.android.injection.config;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import org.opendatakit.httpclientandroidlib.client.CookieStore;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppModule_ProvideCookieStoreFactory implements Factory<CookieStore> {
  private final AppModule module;

  public AppModule_ProvideCookieStoreFactory(AppModule module) {
    assert module != null;
    this.module = module;
  }

  @Override
  public CookieStore get() {
    return Preconditions.checkNotNull(
        module.provideCookieStore(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<CookieStore> create(AppModule module) {
    return new AppModule_ProvideCookieStoreFactory(module);
  }

  /** Proxies {@link AppModule#provideCookieStore()}. */
  public static CookieStore proxyProvideCookieStore(AppModule instance) {
    return instance.provideCookieStore();
  }
}
