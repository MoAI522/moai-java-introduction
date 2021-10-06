public class Practice4 {
  public static void main(String[] args) {
    ISStudents is2 = new ISStudents(95, 2);

    is2.registerStudent(1234567, "坊っちゃん");
    is2.registerStudent(7654321, "Madonna-chan");

    System.out.println("登録者数:" + is2.getNumOfRegisterdStudents() + "人");

    int[] targets = { 7654321, 1234567, 6316001 };
    for (int id : targets) {
      System.out.println("学籍番号" + id + "の学生は " + is2.getNameFromId(id));
    }
  }
}
