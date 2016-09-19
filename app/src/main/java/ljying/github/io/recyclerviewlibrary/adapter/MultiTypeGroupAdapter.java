package ljying.github.io.recyclerviewlibrary.adapter;

import android.view.View;
import android.view.ViewGroup;


import java.util.List;


import ljying.github.io.expandablerecyclerview.MultiTypeExpandableRecyclerViewAdapter;
import ljying.github.io.expandablerecyclerview.models.ExpandableGroup;
import ljying.github.io.expandablerecyclerview.models.ExpandableListPosition;
import ljying.github.io.recyclerviewlibrary.R;
import ljying.github.io.recyclerviewlibrary.adapter.viewholder.GroupViewHolder;
import ljying.github.io.recyclerviewlibrary.adapter.viewholder.ChildViewHolder;
import ljying.github.io.recyclerviewlibrary.model.Group;
import ljying.github.io.recyclerviewlibrary.model.Child;
import ljying.github.io.recyclerviewlibrary.adapter.viewholder.TopHitViewHolder;

import static android.view.LayoutInflater.from;

public class MultiTypeGroupAdapter
    extends MultiTypeExpandableRecyclerViewAdapter<GroupViewHolder, ljying.github.io.expandablerecyclerview.viewholders.ChildViewHolder> {

  public static final int TOP_HIT_VIEW_TYPE = 3;
  public static final int SONG_VIEW_TYPE = 4;

  public MultiTypeGroupAdapter(List<Group> groups) {
    super(groups);
  }

  @Override
  public GroupViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
    View view = from(parent.getContext())
        .inflate(R.layout.list_item_band, parent, false);
    return new GroupViewHolder(view);
  }

  @Override
  public ljying.github.io.expandablerecyclerview.viewholders.ChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
      case SONG_VIEW_TYPE:
        View song = from(parent.getContext()).inflate(R.layout.list_item_song, parent, false);
        return new ChildViewHolder(song);
      case TOP_HIT_VIEW_TYPE:
        View topHit = from(parent.getContext()).inflate(R.layout.list_item_top_hit, parent, false);
        return new TopHitViewHolder(topHit);
      default:
        throw new IllegalArgumentException("Invalid viewType");
    }
  }

  @Override
  public void onBindChildViewHolder(ljying.github.io.expandablerecyclerview.viewholders.ChildViewHolder holder, int flatPosition, ExpandableGroup group,
                                    int childIndex) {
    int viewType = getItemViewType(flatPosition);
    Child child = ((Group) group).getItems().get(childIndex);
    switch (viewType) {
      case SONG_VIEW_TYPE:
        ((ChildViewHolder) holder).setSongName(child.getName());
        break;
      case TOP_HIT_VIEW_TYPE:
        ((TopHitViewHolder) holder).setSongName(child.getName());
    }
  }

  @Override
  public void onBindGroupViewHolder(GroupViewHolder holder, int flatPosition,
                                    ExpandableGroup group) {
    holder.setBandName(group);
  }

  @Override
  public int getChildViewType(int position, ExpandableGroup group, int childIndex) {
    if (((Group) group).getItems().get(childIndex).isTopHit()) {
      return TOP_HIT_VIEW_TYPE;
    } else {
      return SONG_VIEW_TYPE;
    }
  }

  @Override
  public boolean isGroup(int viewType) {
    return viewType == ExpandableListPosition.GROUP;
  }

  @Override
  public boolean isChild(int viewType) {
    return viewType == TOP_HIT_VIEW_TYPE || viewType == SONG_VIEW_TYPE;
  }
}
