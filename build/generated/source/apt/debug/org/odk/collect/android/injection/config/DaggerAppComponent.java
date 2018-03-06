package org.odk.collect.android.injection.config;

import android.app.Activity;
import android.app.Application;
import dagger.MembersInjector;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.DispatchingAndroidInjector_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.MapProviderFactory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.odk.collect.android.application.Collect;
import org.odk.collect.android.application.Collect_MembersInjector;
import org.opendatakit.httpclientandroidlib.client.CookieStore;
import org.opendatakit.httpclientandroidlib.client.CredentialsProvider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerAppComponent implements AppComponent {
  private Provider<CookieStore> provideCookieStoreProvider;

  private Provider<CredentialsProvider> provideCredentialsProvider;

  private Provider<DispatchingAndroidInjector<Activity>> dispatchingAndroidInjectorProvider;

  private MembersInjector<Collect> collectMembersInjector;

  private DaggerAppComponent(Builder builder) {
    assert builder != null;
    initialize(builder);
  }

  public static AppComponent.Builder builder() {
    return new Builder();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {

    this.provideCookieStoreProvider =
        DoubleCheck.provider(AppModule_ProvideCookieStoreFactory.create(builder.appModule));

    this.provideCredentialsProvider =
        DoubleCheck.provider(AppModule_ProvideCredentialsProviderFactory.create(builder.appModule));

    this.dispatchingAndroidInjectorProvider =
        DispatchingAndroidInjector_Factory.create(
            MapProviderFactory
                .<Class<? extends Activity>, AndroidInjector.Factory<? extends Activity>>empty());

    this.collectMembersInjector =
        Collect_MembersInjector.create(
            provideCookieStoreProvider,
            provideCredentialsProvider,
            dispatchingAndroidInjectorProvider);
  }

  @Override
  public void inject(Collect collect) {
    collectMembersInjector.injectMembers(collect);
  }

  private static final class Builder implements AppComponent.Builder {
    private AppModule appModule;

    private Application application;

    @Override
    public AppComponent build() {
      if (appModule == null) {
        this.appModule = new AppModule();
      }
      if (application == null) {
        throw new IllegalStateException(Application.class.getCanonicalName() + " must be set");
      }
      return new DaggerAppComponent(this);
    }

    @Override
    public Builder application(Application application) {
      this.application = Preconditions.checkNotNull(application);
      return this;
    }
  }
}
