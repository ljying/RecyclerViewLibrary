package ljying.github.io.recyclerviewlibrary.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import ljying.github.io.expandablerecyclerview.viewholders.ChildViewHolder;
import ljying.github.io.recyclerviewlibrary.R;


public class TopHitViewHolder extends ChildViewHolder {

  private TextView topHitName;

  public TopHitViewHolder(View itemView) {
    super(itemView);
    topHitName = (TextView) itemView.findViewById(R.id.list_item_top_hit_name);
  }

  public void setSongName(String name) {
    topHitName.setText("Top Hit: " + name);
  }

}
