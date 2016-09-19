package ljying.github.io.recyclerviewlibrary.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ljying.github.io.expandablecheckrecyclerview.CheckableChildRecyclerViewAdapter;
import ljying.github.io.expandablecheckrecyclerview.models.CheckedExpandableGroup;
import ljying.github.io.expandablerecyclerview.models.ExpandableGroup;
import ljying.github.io.recyclerviewlibrary.R;
import ljying.github.io.recyclerviewlibrary.adapter.viewholder.GroupViewHolder;
import ljying.github.io.recyclerviewlibrary.model.Child;
import ljying.github.io.recyclerviewlibrary.model.MultiCheckGroup;
import ljying.github.io.recyclerviewlibrary.adapter.viewholder.MultiCheckChildViewHolder;


public class MultiCheckGroupAdapter extends
        CheckableChildRecyclerViewAdapter<GroupViewHolder, MultiCheckChildViewHolder> {

  public MultiCheckGroupAdapter(List<MultiCheckGroup> groups) {
    super(groups);
  }

  @Override
  public MultiCheckChildViewHolder onCreateCheckChildViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.list_item_multicheck_song, parent, false);
    return new MultiCheckChildViewHolder(view);
  }

  @Override
  public void onBindCheckChildViewHolder(MultiCheckChildViewHolder holder, int position,
                                         CheckedExpandableGroup group, int childIndex) {
    final Child child = (Child) group.getItems().get(childIndex);
    holder.setSongName(child.getName());
  }

  @Override
  public GroupViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.list_item_band, parent, false);
    return new GroupViewHolder(view);
  }

  @Override
  public void onBindGroupViewHolder(GroupViewHolder holder, int flatPosition,
                                    ExpandableGroup group) {
    holder.setBandName(group);
  }
}
