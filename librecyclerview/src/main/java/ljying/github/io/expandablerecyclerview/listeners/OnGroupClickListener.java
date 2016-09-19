package ljying.github.io.expandablerecyclerview.listeners;

public interface OnGroupClickListener {

  /**
   * @param flatPos the flat position (raw index within the list of visible items in the
   * RecyclerView of a GroupViewHolder)
   * @return true if click expanded group, false if click collapsed group
   */
  boolean onGroupClick(int flatPos);
}