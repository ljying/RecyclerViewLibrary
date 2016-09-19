package ljying.github.io.expandablerecyclerview.models;

import android.widget.ExpandableListView;

import java.util.ArrayList;

/**
 * 由于android.widget.ExpandableListPosition 的包作用域的问题，因此创建了该控件的副本即ExpandableListPosition
 * <p>
 * ExpandableListPosition可以引用组的位置或孩子的位置。
 * 引用的是孩子的位置时，需要该孩子所在的组的位置和孩子在该组内的位置
 * <p>
 * 创建对象,使用obtainChildPosition(int,int)或者obtainGroupPosition(int)
 */

public class ExpandableListPosition {

    private static final int MAX_POOL_SIZE = 5;
    private static ArrayList<ExpandableListPosition> sPool =
            new ArrayList<ExpandableListPosition>(MAX_POOL_SIZE);

    /**
     * 类型：孩子的位置
     */
    public final static int CHILD = 1;

    /**
     * 类型组位置
     */
    public final static int GROUP = 2;

    /**
     * 被引用组的位置或者被引用孩子所在组的位置
     */
    public int groupPos;

    /**
     * 孩子在组内的位置
     */
    public int childPos;

    /**
     * 平铺列表中项的位置（可）
     */
    int flatListPos;

    /**
     * ExpandableListPosition代表的位置类型 CHILD or GROUP
     */
    public int type;

    private void resetState() {
        groupPos = 0;
        childPos = 0;
        flatListPos = 0;
        type = 0;
    }

    private ExpandableListPosition() {
    }

    public long getPackedPosition() {
        if (type == CHILD) {
            return ExpandableListView.getPackedPositionForChild(groupPos, childPos);
        } else {
            return ExpandableListView.getPackedPositionForGroup(groupPos);
        }
    }

    static ExpandableListPosition obtainGroupPosition(int groupPosition) {
        return obtain(GROUP, groupPosition, 0, 0);
    }

    static ExpandableListPosition obtainChildPosition(int groupPosition, int childPosition) {
        return obtain(CHILD, groupPosition, childPosition, 0);
    }

    static ExpandableListPosition obtainPosition(long packedPosition) {
        if (packedPosition == ExpandableListView.PACKED_POSITION_VALUE_NULL) {
            return null;
        }

        ExpandableListPosition elp = getRecycledOrCreate();
        elp.groupPos = ExpandableListView.getPackedPositionGroup(packedPosition);
        if (ExpandableListView.getPackedPositionType(packedPosition) ==
                ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
            elp.type = CHILD;
            elp.childPos = ExpandableListView.getPackedPositionChild(packedPosition);
        } else {
            elp.type = GROUP;
        }
        return elp;
    }

    public static ExpandableListPosition obtain(int type, int groupPos, int childPos,
                                                int flatListPos) {
        ExpandableListPosition elp = getRecycledOrCreate();
        elp.type = type;
        elp.groupPos = groupPos;
        elp.childPos = childPos;
        elp.flatListPos = flatListPos;
        return elp;
    }

    private static ExpandableListPosition getRecycledOrCreate() {
        ExpandableListPosition elp;
        synchronized (sPool) {
            if (sPool.size() > 0) {
                elp = sPool.remove(0);
            } else {
                return new ExpandableListPosition();
            }
        }
        elp.resetState();
        return elp;
    }

    /**
     * 只有通过ExpandableListPosition.obtain()创建的才可以调用，PositionMetadata将会处理自己孩子的回收
     */
    public void recycle() {
        synchronized (sPool) {
            if (sPool.size() < MAX_POOL_SIZE) {
                sPool.add(this);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExpandableListPosition that = (ExpandableListPosition) o;

        if (groupPos != that.groupPos) return false;
        if (childPos != that.childPos) return false;
        if (flatListPos != that.flatListPos) return false;
        return type == that.type;

    }

    @Override
    public int hashCode() {
        int result = groupPos;
        result = 31 * result + childPos;
        result = 31 * result + flatListPos;
        result = 31 * result + type;
        return result;
    }

    @Override
    public String toString() {
        return "ExpandableListPosition{" +
                "groupPos=" + groupPos +
                ", childPos=" + childPos +
                ", flatListPos=" + flatListPos +
                ", type=" + type +
                '}';
    }
}

