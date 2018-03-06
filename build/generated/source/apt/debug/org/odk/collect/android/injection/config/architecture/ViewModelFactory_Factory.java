package org.odk.collect.android.injection.config.architecture;

import android.arch.lifecycle.ViewModel;
import dagger.internal.Factory;
import java.util.Map;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ViewModelFactory_Factory implements Factory<ViewModelFactory> {
  private final Provider<Map<Class<? extends ViewModel>, Provider<ViewModel>>> creatorsProvider;

  public ViewModelFactory_Factory(
      Provider<Map<Class<? extends ViewModel>, Provider<ViewModel>>> creatorsProvider) {
    assert creatorsProvider != null;
    this.creatorsProvider = creatorsProvider;
  }

  @Override
  public ViewModelFactory get() {
    return new ViewModelFactory(creatorsProvider.get());
  }

  public static Factory<ViewModelFactory> create(
      Provider<Map<Class<? extends ViewModel>, Provider<ViewModel>>> creatorsProvider) {
    return new ViewModelFactory_Factory(creatorsProvider);
  }

  /** Proxies {@link ViewModelFactory#ViewModelFactory(Map)}. */
  public static ViewModelFactory newViewModelFactory(
      Map<Class<? extends ViewModel>, Provider<ViewModel>> creators) {
    return new ViewModelFactory(creators);
  }
}
