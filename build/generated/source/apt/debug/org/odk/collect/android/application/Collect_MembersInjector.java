package org.odk.collect.android.application;

import android.app.Activity;
import dagger.MembersInjector;
import dagger.android.DispatchingAndroidInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.opendatakit.httpclientandroidlib.client.CookieStore;
import org.opendatakit.httpclientandroidlib.client.CredentialsProvider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class Collect_MembersInjector implements MembersInjector<Collect> {
  private final Provider<CookieStore> cookieStoreProvider;

  private final Provider<CredentialsProvider> credsProvider;

  private final Provider<DispatchingAndroidInjector<Activity>> androidInjectorProvider;

  public Collect_MembersInjector(
      Provider<CookieStore> cookieStoreProvider,
      Provider<CredentialsProvider> credsProvider,
      Provider<DispatchingAndroidInjector<Activity>> androidInjectorProvider) {
    assert cookieStoreProvider != null;
    this.cookieStoreProvider = cookieStoreProvider;
    assert credsProvider != null;
    this.credsProvider = credsProvider;
    assert androidInjectorProvider != null;
    this.androidInjectorProvider = androidInjectorProvider;
  }

  public static MembersInjector<Collect> create(
      Provider<CookieStore> cookieStoreProvider,
      Provider<CredentialsProvider> credsProvider,
      Provider<DispatchingAndroidInjector<Activity>> androidInjectorProvider) {
    return new Collect_MembersInjector(cookieStoreProvider, credsProvider, androidInjectorProvider);
  }

  @Override
  public void injectMembers(Collect instance) {
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    instance.cookieStore = cookieStoreProvider.get();
    instance.credsProvider = credsProvider.get();
    instance.androidInjector = androidInjectorProvider.get();
  }

  public static void injectCookieStore(
      Collect instance, Provider<CookieStore> cookieStoreProvider) {
    instance.cookieStore = cookieStoreProvider.get();
  }

  public static void injectCredsProvider(
      Collect instance, Provider<CredentialsProvider> credsProvider) {
    instance.credsProvider = credsProvider.get();
  }

  public static void injectAndroidInjector(
      Collect instance, Provider<DispatchingAndroidInjector<Activity>> androidInjectorProvider) {
    instance.androidInjector = androidInjectorProvider.get();
  }
}
