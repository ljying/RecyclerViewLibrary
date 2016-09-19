package ljying.github.io.expandablecheckrecyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ljying.github.io.expandablecheckrecyclerview.listeners.OnCheckChildClickListener;
import ljying.github.io.expandablecheckrecyclerview.listeners.OnChildCheckChangedListener;
import ljying.github.io.expandablecheckrecyclerview.listeners.OnChildrenCheckStateChangedListener;
import ljying.github.io.expandablecheckrecyclerview.models.CheckedExpandableGroup;
import ljying.github.io.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder;
import ljying.github.io.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import ljying.github.io.expandablerecyclerview.models.ExpandableGroup;
import ljying.github.io.expandablerecyclerview.models.ExpandableListPosition;
import ljying.github.io.expandablerecyclerview.viewholders.GroupViewHolder;


public abstract class CheckableChildRecyclerViewAdapter<GVH extends GroupViewHolder, CCVH extends CheckableChildViewHolder>
    extends ExpandableRecyclerViewAdapter<GVH, CCVH>
    implements OnChildCheckChangedListener, OnChildrenCheckStateChangedListener {

  private static final String CHECKED_STATE_MAP = "child_check_controller_checked_state_map";

  private ChildCheckController childCheckController;
  private OnCheckChildClickListener childClickListener;

  public CheckableChildRecyclerViewAdapter(List<? extends CheckedExpandableGroup> groups) {
    super(groups);
    childCheckController = new ChildCheckController(expandableList, this);
  }

  @Override
  public CCVH onCreateChildViewHolder(ViewGroup parent, int viewType) {
    CCVH CCVH = onCreateCheckChildViewHolder(parent, viewType);
    CCVH.setOnChildCheckedListener(this);
    return CCVH;
  }

  @Override
  public void onBindChildViewHolder(CCVH holder, int flatPosition, ExpandableGroup group,
      int childIndex) {
    ExpandableListPosition listPosition = expandableList.getUnflattenedPosition(flatPosition);
    holder.onBindViewHolder(flatPosition, childCheckController.isChildChecked(listPosition));
    onBindCheckChildViewHolder(holder, flatPosition, (CheckedExpandableGroup) group, childIndex);
  }

  @Override
  public void onChildCheckChanged(View view, boolean checked, int flatPos) {
    ExpandableListPosition listPos = expandableList.getUnflattenedPosition(flatPos);
    childCheckController.onChildCheckChanged(checked, listPos);
    if (childClickListener != null) {
      childClickListener.onCheckChildCLick(view, checked,
          (CheckedExpandableGroup) expandableList.getExpandableGroup(listPos), listPos.childPos);
    }
  }

  @Override
  public void updateChildrenCheckState(int firstChildFlattenedIndex, int numChildren) {
    notifyItemRangeChanged(firstChildFlattenedIndex, numChildren);
  }

  public void setChildClickListener(OnCheckChildClickListener listener) {
    childClickListener = listener;
  }

  /**
   * Stores the checked state map across state loss.
   * <p>
   * Should be called from whatever {@link Activity} that hosts the RecyclerView that {@link
   * CheckableChildRecyclerViewAdapter} is attached to.
   * <p>
   * This will make sure to add the checked state map as an extra to the
   * instance state bundle to be used in {@link #onRestoreInstanceState(Bundle)}.
   *
   * @param outState The {@code Bundle} into which to store the
   * chekced state map
   */
  @Override
  public void onSaveInstanceState(Bundle outState) {
    outState.putParcelableArrayList(CHECKED_STATE_MAP,
        (ArrayList<? extends Parcelable>) expandableList.groups);
    super.onSaveInstanceState(outState);
  }

  /**
   * Fetches the checked state map from the saved instance state {@link Bundle}
   * and restores the checked states of all of the child list items.
   * <p>
   * Should be called from {@link Activity#onRestoreInstanceState(Bundle)}  in
   * the {@link Activity} that hosts the RecyclerView that this
   * {@link CheckableChildRecyclerViewAdapter} is attached to.
   * <p>
   *
   * @param savedInstanceState The {@code Bundle} from which the expanded
   * state map is loaded
   */
  @Override
  public void onRestoreInstanceState(Bundle savedInstanceState) {
    if (savedInstanceState == null || !savedInstanceState.containsKey(CHECKED_STATE_MAP)) {
      return;
    }
    expandableList.groups = savedInstanceState.getParcelableArrayList(CHECKED_STATE_MAP);
    super.onRestoreInstanceState(savedInstanceState);
  }

  /**
   * Clear any choices previously checked
   */
  public void clearChoices() {
    childCheckController.clearCheckStates();
    notifyDataSetChanged();
  }

  /**
   * Called from #onCreateViewHolder(ViewGroup, int) when the list item created is a child
   *
   * @param parent the {@link ViewGroup} in the list for which a {@link CCVH}  is being created
   * @return A {@link CCVH} corresponding to child list item with the  {@code ViewGroup} parent
   */
  public abstract CCVH onCreateCheckChildViewHolder(ViewGroup parent, int viewType);

  /**
   * Called from onBindViewHolder(RecyclerView.ViewHolder, int) when the list item
   * bound to is a  child.
   * <p>
   * Bind data to the {@link CCVH} here.
   *
   * @param holder The {@code CCVH} to bind data to
   * @param flatPosition the flat position (raw index) in the list at which to bind the child
   * @param group The {@link CheckedExpandableGroup} that the the child list item belongs to
   * @param childIndex the index of this child within it's {@link CheckedExpandableGroup}
   */
  public abstract void onBindCheckChildViewHolder(CCVH holder, int flatPosition,
                                                  CheckedExpandableGroup group, int childIndex);
}
