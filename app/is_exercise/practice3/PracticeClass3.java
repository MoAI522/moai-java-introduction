public class PracticeClass3 {
  public static void main(String[] args) {
    Student st = new Student("坊っちゃん", 1234567);

    System.out.println("氏名:" + "\t\t" + st.getName());
    System.out.println("学籍番号:" + "\t" + st.getId());
  }
}

class Student {
  private String name;
  private int id;

  Student(String name, int id) {
    this.name = name;
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setId(int id) {
    this.id = id;
  }
}
