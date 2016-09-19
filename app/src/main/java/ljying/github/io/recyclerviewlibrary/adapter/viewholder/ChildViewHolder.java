package ljying.github.io.recyclerviewlibrary.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import ljying.github.io.recyclerviewlibrary.R;


public class ChildViewHolder extends ljying.github.io.expandablerecyclerview.viewholders.ChildViewHolder {

  private TextView childTextView;

  public ChildViewHolder(View itemView) {
    super(itemView);
    childTextView = (TextView) itemView.findViewById(R.id.list_item_song_name);
  }

  public void setSongName(String name) {
    childTextView.setText(name);
  }
}
