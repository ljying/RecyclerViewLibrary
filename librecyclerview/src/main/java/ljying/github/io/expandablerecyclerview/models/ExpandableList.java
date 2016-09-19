package ljying.github.io.expandablerecyclerview.models;

import android.util.SparseBooleanArray;

import java.util.List;

public class ExpandableList {

    public List<? extends ExpandableGroup> groups;
    //各组的展开折叠状态
    public SparseBooleanArray expandedGroupIndexes;

    public ExpandableList(List<? extends ExpandableGroup> groups) {
        this.groups = groups;

        expandedGroupIndexes = new SparseBooleanArray();
        for (int i = 0; i < groups.size(); i++) {
            expandedGroupIndexes.put(i, false);
        }
    }

    /**
     * 获取指定组下可见条目数
     *
     * @param group 组集合中组的下标
     * @return 给定下标组中展开条目数，若组折叠返回1 ，反之返回该组下的条目数
     */
    private int numberOfVisibleItemsInGroup(int group) {
        if (expandedGroupIndexes.get(group)) {
            return groups.get(group).getItemCount() + 1;
        } else {
            return 1;
        }
    }

    /**
     * @return 所有可见条目数
     */
    public int getVisibleItemCount() {
        int count = 0;
        for (int i = 0; i < groups.size(); i++) {
            count += numberOfVisibleItemsInGroup(i);
        }
        return count;
    }

    /**
     * Translates a flat list position (the raw position of an item (child or group) in the list) to
     * either a) group pos if the specified flat list position corresponds to a group, or b) child
     * pos if it corresponds to a child.  Performs a binary search on the expanded
     * groups list to find the flat list pos if it is an exp group, otherwise
     * finds where the flat list pos fits in between the exp groups.
     *
     * @param flPos the flat list position to be translated
     * @return the group position or child position of the specified flat list
     * position encompassed in a {@link ExpandableListPosition} object
     * that contains additional useful info for insertion, etc.
     */
    public ExpandableListPosition getUnflattenedPosition(int flPos) {
        int groupItemCount;
        int adapted = flPos;
        for (int i = 0; i < groups.size(); i++) {
            groupItemCount = numberOfVisibleItemsInGroup(i);
            if (adapted == 0) {
                return ExpandableListPosition.obtain(ExpandableListPosition.GROUP, i, -1, flPos);
            } else if (adapted < groupItemCount) {
                return ExpandableListPosition.obtain(ExpandableListPosition.CHILD, i, adapted - 1, flPos);
            }
            adapted -= groupItemCount;
        }
        throw new RuntimeException("Unknown state");
    }

    /**
     * @param listPosition  孩子或者组的listPosition
     * @return 组在所有可见条目中的位置
     */
    public int getFlattenedGroupIndex(ExpandableListPosition listPosition) {
        int groupIndex = listPosition.groupPos;
        int runningTotal = 0;

        for (int i = 0; i < groupIndex; i++) {
            runningTotal += numberOfVisibleItemsInGroup(i);
        }
        return runningTotal;
    }

    /**
     * Converts a child position to a flat list position.
     *
     * @param packedPosition The child positions to be converted in it's
     *                       packed position representation.
     * @return The flat list position for the given child
     */
    public int getFlattenedChildIndex(long packedPosition) {
        ExpandableListPosition listPosition = ExpandableListPosition.obtainPosition(packedPosition);
        return getFlattenedChildIndex(listPosition);
    }

    /**
     * Converts a child position to a flat list position.
     *
     * @param listPosition The child positions to be converted in it's
     *                     {@link ExpandableListPosition} representation.
     * @return The flat list position for the given child
     */
    public int getFlattenedChildIndex(ExpandableListPosition listPosition) {
        int groupIndex = listPosition.groupPos;
        int childIndex = listPosition.childPos;
        int runningTotal = 0;

        for (int i = 0; i < groupIndex; i++) {
            runningTotal += numberOfVisibleItemsInGroup(i);
        }
        return runningTotal + childIndex + 1;
    }

    /**
     * @param listPosition The child positions to be converted in it's
     *                     {@link ExpandableListPosition} representation.
     * @return The flat list position for the first child in a group
     */
    public int getFlattenedFirstChildIndex(ExpandableListPosition listPosition) {
        return getFlattenedGroupIndex(listPosition) + 1;
    }

    /**
     * @param listPosition An {@link ExpandableListPosition} representing either a child or group
     * @return the total number of children within the group associated with the @param listPosition
     */
    public int getExpandableGroupItemCount(ExpandableListPosition listPosition) {
        return groups.get(listPosition.groupPos).getItemCount();
    }

    /**
     * Translates either a group pos or a child pos to an {@link ExpandableGroup}.
     * If the {@link ExpandableListPosition} is a child position, it returns the {@link
     * ExpandableGroup} it belongs to
     *
     * @param listPosition a {@link ExpandableListPosition} representing either a group position
     *                     or child position
     * @return the {@link ExpandableGroup} object that contains the listPosition
     */
    public ExpandableGroup getExpandableGroup(ExpandableListPosition listPosition) {
        return groups.get(listPosition.groupPos);
    }
}
