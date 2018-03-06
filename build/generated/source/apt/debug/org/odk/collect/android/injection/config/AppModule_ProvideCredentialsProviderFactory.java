package org.odk.collect.android.injection.config;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import org.opendatakit.httpclientandroidlib.client.CredentialsProvider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppModule_ProvideCredentialsProviderFactory
    implements Factory<CredentialsProvider> {
  private final AppModule module;

  public AppModule_ProvideCredentialsProviderFactory(AppModule module) {
    assert module != null;
    this.module = module;
  }

  @Override
  public CredentialsProvider get() {
    return Preconditions.checkNotNull(
        module.provideCredentialsProvider(),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<CredentialsProvider> create(AppModule module) {
    return new AppModule_ProvideCredentialsProviderFactory(module);
  }

  /** Proxies {@link AppModule#provideCredentialsProvider()}. */
  public static CredentialsProvider proxyProvideCredentialsProvider(AppModule instance) {
    return instance.provideCredentialsProvider();
  }
}
