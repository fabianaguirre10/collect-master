package org.odk.collect.android.architecture;

import android.arch.lifecycle.ViewModelProvider;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class MVVMActivity_MembersInjector<V extends MVVMViewModel>
    implements MembersInjector<MVVMActivity<V>> {
  private final Provider<ViewModelProvider.Factory> viewModelFactoryProvider;

  public MVVMActivity_MembersInjector(
      Provider<ViewModelProvider.Factory> viewModelFactoryProvider) {
    assert viewModelFactoryProvider != null;
    this.viewModelFactoryProvider = viewModelFactoryProvider;
  }

  public static <V extends MVVMViewModel> MembersInjector<MVVMActivity<V>> create(
      Provider<ViewModelProvider.Factory> viewModelFactoryProvider) {
    return new MVVMActivity_MembersInjector<V>(viewModelFactoryProvider);
  }

  @Override
  public void injectMembers(MVVMActivity<V> instance) {
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    instance.viewModelFactory = viewModelFactoryProvider.get();
  }

  public static <V extends MVVMViewModel> void injectViewModelFactory(
      MVVMActivity<V> instance, Provider<ViewModelProvider.Factory> viewModelFactoryProvider) {
    instance.viewModelFactory = viewModelFactoryProvider.get();
  }
}
