package ljying.github.io.recyclerviewlibrary.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ljying.github.io.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import ljying.github.io.expandablerecyclerview.models.ExpandableGroup;
import ljying.github.io.recyclerviewlibrary.R;
import ljying.github.io.recyclerviewlibrary.adapter.viewholder.GroupViewHolder;
import ljying.github.io.recyclerviewlibrary.adapter.viewholder.ChildViewHolder;
import ljying.github.io.recyclerviewlibrary.model.Group;
import ljying.github.io.recyclerviewlibrary.model.Child;


public class GroupAdapter extends ExpandableRecyclerViewAdapter<GroupViewHolder, ChildViewHolder> {

  public GroupAdapter(List<? extends ExpandableGroup> groups) {
    super(groups);
  }

  @Override
  public GroupViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.list_item_band, parent, false);
    return new GroupViewHolder(view);
  }

  @Override
  public ChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.list_item_song, parent, false);
    return new ChildViewHolder(view);
  }

  @Override
  public void onBindChildViewHolder(ChildViewHolder holder, int flatPosition,
                                    ExpandableGroup group, int childIndex) {

    final Child child = ((Group) group).getItems().get(childIndex);
    holder.setSongName(child.getName());
  }

  @Override
  public void onBindGroupViewHolder(GroupViewHolder holder, int flatPosition, ExpandableGroup group) {

    holder.setBandName(group);
  }
}
