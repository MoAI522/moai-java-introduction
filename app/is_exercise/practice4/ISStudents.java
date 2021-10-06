public class ISStudents {
  private int grade;
  private int numOfStudents;

  private Student[] students;
  private int numOfRegisterdStudents;

  ISStudents(int num, int grade) {
    this.numOfStudents = num;
    this.grade = grade;
    this.students = new Student[num];
    this.numOfRegisterdStudents = 0;
  }

  public void setGrade(int grade) {
    this.grade = grade;
  }

  public void setNumOfStudents(int numOfStudents) {
    this.numOfStudents = numOfStudents;
  }

  public int getGrade() {
    return grade;
  }

  public int getNumOfStudents() {
    return numOfStudents;
  }

  public int getNumOfRegisterdStudents() {
    return numOfRegisterdStudents;
  }

  public void registerStudent(int id, String name) {
    if (numOfRegisterdStudents >= numOfStudents)
      return;
    students[numOfRegisterdStudents] = new Student(name, id);
    numOfRegisterdStudents++;
  }

  public String getNameFromId(int id) {
    String targetName = "Shiranai kodane.";
    for (int i = 0; i < numOfRegisterdStudents; i++) {
      if (students[i].getId() != id)
        continue;
      targetName = students[i].getName();
      break;
    }

    return targetName;
  }
}