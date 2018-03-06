package org.odk.collect.android.location;

import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class GeoViewModel_Factory implements Factory<GeoViewModel> {
  private final MembersInjector<GeoViewModel> geoViewModelMembersInjector;

  public GeoViewModel_Factory(MembersInjector<GeoViewModel> geoViewModelMembersInjector) {
    assert geoViewModelMembersInjector != null;
    this.geoViewModelMembersInjector = geoViewModelMembersInjector;
  }

  @Override
  public GeoViewModel get() {
    return MembersInjectors.injectMembers(geoViewModelMembersInjector, new GeoViewModel());
  }

  public static Factory<GeoViewModel> create(
      MembersInjector<GeoViewModel> geoViewModelMembersInjector) {
    return new GeoViewModel_Factory(geoViewModelMembersInjector);
  }
}
