package org.odk.collect.android.injection.config.architecture;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v4.app.FragmentActivity;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ActivityViewModelProvider_Factory implements Factory<ActivityViewModelProvider> {
  private final Provider<FragmentActivity> activityProvider;

  private final Provider<ViewModelProvider.Factory> factoryProvider;

  public ActivityViewModelProvider_Factory(
      Provider<FragmentActivity> activityProvider,
      Provider<ViewModelProvider.Factory> factoryProvider) {
    assert activityProvider != null;
    this.activityProvider = activityProvider;
    assert factoryProvider != null;
    this.factoryProvider = factoryProvider;
  }

  @Override
  public ActivityViewModelProvider get() {
    return new ActivityViewModelProvider(activityProvider.get(), factoryProvider.get());
  }

  public static Factory<ActivityViewModelProvider> create(
      Provider<FragmentActivity> activityProvider,
      Provider<ViewModelProvider.Factory> factoryProvider) {
    return new ActivityViewModelProvider_Factory(activityProvider, factoryProvider);
  }

  /**
   * Proxies {@link ActivityViewModelProvider#ActivityViewModelProvider(FragmentActivity,
   * ViewModelProvider.Factory)}.
   */
  public static ActivityViewModelProvider newActivityViewModelProvider(
      FragmentActivity activity, ViewModelProvider.Factory factory) {
    return new ActivityViewModelProvider(activity, factory);
  }
}
