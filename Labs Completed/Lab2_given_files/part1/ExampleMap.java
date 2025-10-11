package part1;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ExampleMap {

  /**
   * Return a list of "high scoring" students --- High scoring students are
   * students who have all grades strictly greater than the given threshold.
   *
   * @param scoresByApplicantName A map of applicant names to applicant scores
   * @param scoreThreshold        The score threshold
   * @return The list of high-scoring applicant names
   */
  public static List<String> highScoringStudents(Map<String, List<CourseGrade>> scoresByApplicantName, int scoreThreshold) {
    // new list to hold the names of high scoring students, you have to add to this
    List<String> highScoringStudents = new LinkedList<>();

    //Build a list of the names of applicants who have scores strictly greater than the given threshold.
    //loops through each entry/student and get their list of Course grades values
    for (Map.Entry<String, List<CourseGrade>> current : scoresByApplicantName.entrySet()) {
      List<CourseGrade> scores = current.getValue();

      // at first make all entries true
      boolean add = true;

      // Loop through each value/score in the CourseGrade
      for (CourseGrade grade : scores) {
        if (grade.getScore() <= scoreThreshold) {
          add = false;
          break;
        }
      }
      if (add) {
        highScoringStudents.add(current.getKey());
      }
    }
    return highScoringStudents;
  }
}
