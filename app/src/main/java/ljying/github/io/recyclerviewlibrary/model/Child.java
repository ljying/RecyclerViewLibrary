package ljying.github.io.recyclerviewlibrary.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Child implements Parcelable {

  private String name;
  private boolean isTopHit;

  public Child(String name, boolean isTopHit) {
    this.name = name;
    this.isTopHit = isTopHit;
  }

  public String getName() {
    return name;
  }

  public boolean isTopHit() {
    return isTopHit;
  }

  protected Child(Parcel in) {
    name = in.readString();
    isTopHit = in.readByte() != 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
    dest.writeByte((byte) (isTopHit ? 1 : 0));
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<Child> CREATOR = new Creator<Child>() {
    @Override
    public Child createFromParcel(Parcel in) {
      return new Child(in);
    }

    @Override
    public Child[] newArray(int size) {
      return new Child[size];
    }
  };
}
