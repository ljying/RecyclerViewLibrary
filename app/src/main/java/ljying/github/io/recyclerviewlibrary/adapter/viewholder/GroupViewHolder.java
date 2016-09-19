package ljying.github.io.recyclerviewlibrary.adapter.viewholder;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import ljying.github.io.expandablerecyclerview.models.ExpandableGroup;
import ljying.github.io.recyclerviewlibrary.R;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class GroupViewHolder extends ljying.github.io.expandablerecyclerview.viewholders.GroupViewHolder {

  private TextView bandName;
  private ImageView arrow;

  public GroupViewHolder(View itemView) {
    super(itemView);
    bandName = (TextView) itemView.findViewById(R.id.list_item_band_name);
    arrow = (ImageView) itemView.findViewById(R.id.list_item_band_arrow);
  }

  public void setBandName(ExpandableGroup group) {
    bandName.setText(group.getTitle());
  }

  @Override
  public void expand() {
    animateExpand();
  }

  @Override
  public void collapse() {
    animateCollapse();
  }

  private void animateCollapse() {
    RotateAnimation rotate =
        new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
    rotate.setDuration(300);
    rotate.setFillAfter(true);
    arrow.setAnimation(rotate);
  }

  private void animateExpand() {
    RotateAnimation rotate =
        new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
    rotate.setDuration(300);
    rotate.setFillAfter(true);
    arrow.setAnimation(rotate);
  }
}
