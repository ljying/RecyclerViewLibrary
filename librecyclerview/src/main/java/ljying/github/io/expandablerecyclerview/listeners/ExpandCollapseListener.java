package ljying.github.io.expandablerecyclerview.listeners;


import ljying.github.io.expandablerecyclerview.models.ExpandableGroup;

public interface ExpandCollapseListener {

  /**
   * Called when a group is expanded
   *
   * @param positionStart the flat position of the first child in the {@link ExpandableGroup}
   * @param itemCount the total number of children in the {@link ExpandableGroup}
   */
  void onGroupExpanded(int positionStart, int itemCount);

  /**
   * Called when a group is collapsed
   *
   * @param positionStart the flat position of the first child in the {@link ExpandableGroup}
   * @param itemCount the total number of children in the {@link ExpandableGroup}
   */
  void onGroupCollapsed(int positionStart, int itemCount);
}
