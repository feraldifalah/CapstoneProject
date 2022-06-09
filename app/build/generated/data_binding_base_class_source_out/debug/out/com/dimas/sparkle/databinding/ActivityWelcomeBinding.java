// Generated by view binder compiler. Do not edit!
package com.dimas.sparkle.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.dimas.sparkle.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityWelcomeBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button BottonA;

  @NonNull
  public final Button BottonB;

  @NonNull
  public final TextView bye;

  @NonNull
  public final CardView cardView;

  @NonNull
  public final ImageView imageView;

  @NonNull
  public final TextView statusA;

  @NonNull
  public final TextView statusB;

  private ActivityWelcomeBinding(@NonNull ConstraintLayout rootView, @NonNull Button BottonA,
      @NonNull Button BottonB, @NonNull TextView bye, @NonNull CardView cardView,
      @NonNull ImageView imageView, @NonNull TextView statusA, @NonNull TextView statusB) {
    this.rootView = rootView;
    this.BottonA = BottonA;
    this.BottonB = BottonB;
    this.bye = bye;
    this.cardView = cardView;
    this.imageView = imageView;
    this.statusA = statusA;
    this.statusB = statusB;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityWelcomeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityWelcomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_welcome, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityWelcomeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.BottonA;
      Button BottonA = ViewBindings.findChildViewById(rootView, id);
      if (BottonA == null) {
        break missingId;
      }

      id = R.id.BottonB;
      Button BottonB = ViewBindings.findChildViewById(rootView, id);
      if (BottonB == null) {
        break missingId;
      }

      id = R.id.bye;
      TextView bye = ViewBindings.findChildViewById(rootView, id);
      if (bye == null) {
        break missingId;
      }

      id = R.id.cardView;
      CardView cardView = ViewBindings.findChildViewById(rootView, id);
      if (cardView == null) {
        break missingId;
      }

      id = R.id.imageView;
      ImageView imageView = ViewBindings.findChildViewById(rootView, id);
      if (imageView == null) {
        break missingId;
      }

      id = R.id.statusA;
      TextView statusA = ViewBindings.findChildViewById(rootView, id);
      if (statusA == null) {
        break missingId;
      }

      id = R.id.statusB;
      TextView statusB = ViewBindings.findChildViewById(rootView, id);
      if (statusB == null) {
        break missingId;
      }

      return new ActivityWelcomeBinding((ConstraintLayout) rootView, BottonA, BottonB, bye,
          cardView, imageView, statusA, statusB);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}