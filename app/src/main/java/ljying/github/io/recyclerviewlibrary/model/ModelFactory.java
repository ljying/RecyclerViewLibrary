package ljying.github.io.recyclerviewlibrary.model;


import java.util.ArrayList;
import java.util.List;

public class ModelFactory {

    public static List<Group> makeGroups() {
        ArrayList list = new ArrayList();
        for (int i = 0; i < 6; i++) {
            list.add(new Group("分组：" + (i + 1), makeChild()));
        }
        return list;
    }

    public static List<Child> makeChild() {
        ArrayList list = new ArrayList();
        for (int i = 0; i < 3; i++) {
            list.add(new Child("子条目: " + (i + 1), i % 2 == 0));
        }
        return list;
    }

    public static List<MultiCheckGroup> makeMultiCheckGroups() {
        ArrayList list = new ArrayList();
        for (int i = 0; i < 6; i++) {
            list.add(new MultiCheckGroup("分组：" + (i + 1), makeChild()));
        }
        return list;
    }

    public static List<SingleCheckGroup> makeSingleCheckGroups() {
        ArrayList list = new ArrayList();
        for (int i = 0; i < 6; i++) {
            list.add(new SingleCheckGroup("分组: " + (i + 1), makeChild()));
        }
        return list;
    }
}
