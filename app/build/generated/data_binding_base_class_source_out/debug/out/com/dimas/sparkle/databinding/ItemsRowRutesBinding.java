// Generated by view binder compiler. Do not edit!
package com.dimas.sparkle.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.dimas.sparkle.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemsRowRutesBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final CardView cardView;

  @NonNull
  public final TextView tvItemDescription;

  @NonNull
  public final TextView tvItemName;

  private ItemsRowRutesBinding(@NonNull CardView rootView, @NonNull CardView cardView,
      @NonNull TextView tvItemDescription, @NonNull TextView tvItemName) {
    this.rootView = rootView;
    this.cardView = cardView;
    this.tvItemDescription = tvItemDescription;
    this.tvItemName = tvItemName;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemsRowRutesBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemsRowRutesBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.items_row_rutes, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemsRowRutesBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      CardView cardView = (CardView) rootView;

      id = R.id.tv_item_description;
      TextView tvItemDescription = ViewBindings.findChildViewById(rootView, id);
      if (tvItemDescription == null) {
        break missingId;
      }

      id = R.id.tv_item_name;
      TextView tvItemName = ViewBindings.findChildViewById(rootView, id);
      if (tvItemName == null) {
        break missingId;
      }

      return new ItemsRowRutesBinding((CardView) rootView, cardView, tvItemDescription, tvItemName);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
