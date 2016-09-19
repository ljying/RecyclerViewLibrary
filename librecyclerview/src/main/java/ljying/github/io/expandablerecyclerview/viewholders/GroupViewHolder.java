package ljying.github.io.expandablerecyclerview.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;

import ljying.github.io.expandablerecyclerview.listeners.OnGroupClickListener;
import ljying.github.io.expandablerecyclerview.models.ExpandableGroup;


/**
 * ViewHolder for the {@link ExpandableGroup#title} in a {@link ExpandableGroup}
 *
 * The current implementation does now allow for sub {@link View} of the parent view to trigger
 * a collapse / expand. *Only* click events on the parent {@link View} will trigger a collapse or
 * expand
 */
public abstract class GroupViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

  private OnGroupClickListener listener;

  public GroupViewHolder(View itemView) {
    super(itemView);
    itemView.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    if (listener != null) {
      if (listener.onGroupClick(getAdapterPosition())) {
        expand();
      } else {
        collapse();
      }
    }
  }

  public void setOnGroupClickListener(OnGroupClickListener listener) {
    this.listener = listener;
  }

  public void expand() {}

  public void collapse() {}

}
