// Generated code from Butter Knife. Do not modify!
package org.odk.collect.android.location;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import org.odk.collect.android.R;

public class GeoActivity_ViewBinding implements Unbinder {
  private GeoActivity target;

  private View view2131361820;

  private View view2131362094;

  private View view2131362028;

  private View view2131362060;

  private View view2131361960;

  private View view2131361868;

  @UiThread
  public GeoActivity_ViewBinding(GeoActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public GeoActivity_ViewBinding(final GeoActivity target, View source) {
    this.target = target;

    View view;
    target.locationInfoText = Utils.findRequiredViewAsType(source, R.id.location_info, "field 'locationInfoText'", TextView.class);
    target.locationStatusText = Utils.findRequiredViewAsType(source, R.id.location_status, "field 'locationStatusText'", TextView.class);
    target.mapContainer = Utils.findRequiredViewAsType(source, R.id.map_container, "field 'mapContainer'", FrameLayout.class);
    view = Utils.findRequiredView(source, R.id.add_button, "field 'addButton' and method 'onAddClick'");
    target.addButton = Utils.castView(view, R.id.add_button, "field 'addButton'", ImageButton.class);
    view2131361820 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onAddClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.show_button, "field 'showButton' and method 'onShowClick'");
    target.showButton = Utils.castView(view, R.id.show_button, "field 'showButton'", ImageButton.class);
    view2131362094 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onShowClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.pause_button, "field 'pauseButton' and method 'onPauseClick'");
    target.pauseButton = Utils.castView(view, R.id.pause_button, "field 'pauseButton'", ImageButton.class);
    view2131362028 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onPauseClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.save_button, "field 'saveButton' and method 'onSaveClick'");
    target.saveButton = Utils.castView(view, R.id.save_button, "field 'saveButton'", ImageButton.class);
    view2131362060 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSaveClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.layers_button, "method 'onLayersClick'");
    view2131361960 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onLayersClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.clear_button, "method 'onClearClick'");
    view2131361868 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClearClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    GeoActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.locationInfoText = null;
    target.locationStatusText = null;
    target.mapContainer = null;
    target.addButton = null;
    target.showButton = null;
    target.pauseButton = null;
    target.saveButton = null;

    view2131361820.setOnClickListener(null);
    view2131361820 = null;
    view2131362094.setOnClickListener(null);
    view2131362094 = null;
    view2131362028.setOnClickListener(null);
    view2131362028 = null;
    view2131362060.setOnClickListener(null);
    view2131362060 = null;
    view2131361960.setOnClickListener(null);
    view2131361960 = null;
    view2131361868.setOnClickListener(null);
    view2131361868 = null;
  }
}
