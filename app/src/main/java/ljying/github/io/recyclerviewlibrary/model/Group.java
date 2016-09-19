package ljying.github.io.recyclerviewlibrary.model;

import java.util.List;

import ljying.github.io.expandablerecyclerview.models.ExpandableGroup;


public class Group extends ExpandableGroup<Child> {

  public Group(String title, List<Child> items) {
    super(title, items);
  }
}
