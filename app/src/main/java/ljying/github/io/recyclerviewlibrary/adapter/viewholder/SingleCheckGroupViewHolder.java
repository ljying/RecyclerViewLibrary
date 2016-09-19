package ljying.github.io.recyclerviewlibrary.adapter.viewholder;

import android.view.View;
import android.widget.Checkable;
import android.widget.CheckedTextView;

import ljying.github.io.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder;
import ljying.github.io.recyclerviewlibrary.R;


public class SingleCheckGroupViewHolder extends CheckableChildViewHolder {

  private CheckedTextView childCheckedTextView;

  public SingleCheckGroupViewHolder(View itemView) {
    super(itemView);
    childCheckedTextView =
        (CheckedTextView) itemView.findViewById(R.id.list_item_singlecheck_song_name);
  }

  @Override
  public Checkable getCheckable() {
    return childCheckedTextView;
  }

  public void setSongName(String songName) {
    childCheckedTextView.setText(songName);
  }
}
