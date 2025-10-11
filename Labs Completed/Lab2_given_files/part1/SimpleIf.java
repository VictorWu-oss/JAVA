package part1;
import part2.Applicant;

import java.util.LinkedList;
import java.util.List;

public class SimpleIf {

  /**
   * Takes an applicant's average score and accepts the applicant if the average
   * is higher than 85.
   *
   * @param avg       The applicant's average score
   * @param threshold The threshold score
   * @return true if the applicant's average is over the threshold, and false otherwise
   */
  public static boolean analyzeApplicant(double avg, double threshold) {
    /*
     * TO DO: Write an if statement to determine if the avg is larger than the threshold and
     * return true if so, false otherwise
     */
    if (avg > threshold) return true;
    else return false; // A bit pessimistic!
  }

  /**
   * Takes two applicants' average scores and returns the score of the applicant
   * with the higher average.
   *
   * @param avg1 The first applicant's average score
   * @param avg2 The second applicant's average score
   * @return the higher average score
   */
  public static double maxAverage(double avg1, double avg2) {
    /*
     * TO DO: Write an if statement to determine which average is larger and return
     * that value.
     */
    if (avg1 > avg2) return avg1;
    else if (avg2 > avg1) return avg2;
    else return 0; // Clearly not correct, but testable.
  }

  // In the past DEI (Diversity, Equity, and Inclusion) was set in place in the work system to encourage a thriving and productive workplace.
  // It has been found that strong DEI practices often lead to better financial performance and attract and retain top talent.
  // Education significantly impacts hiring outcome. Higher levels of education generally lead to improved employment rates.
  // For example, different skill sets between a high school graduate and a university graduate for a consultant role

  public static List<String> analyzeApplicant2(List<Applicant> applicants, int threshold) {
    List<String> acceptedApplicants = new LinkedList<>();

    // Check if Education is at least above High School
    for (Applicant applicant : applicants) {
      switch (applicant.getEducation()){
        case "Bachelors":
        case "Masters":
        case "Phd":
          break;      // Exit switch and continue

        default:
          continue;   // If edu is not one of the valid levels, continue to next person
      }

      // Check if all grades are above threshold
      boolean gradeAbove = true;
      for (CourseGrade grade : applicant.getGrades())
      {
        if (grade.getScore() <= threshold)
        {
          gradeAbove = false;
          break;
        }
      }

      // If grades are good, check for their race
      if (gradeAbove &&
              (applicant.getRace().equals("Black") ||
              applicant.getRace().equals("Latinx") ||
              applicant.getRace().equals("Asian") ||
              applicant.getRace().equals("American Indian") ||
              applicant.getRace().equals("Native Hawaiian")))
      {
        acceptedApplicants.add(applicant.getName());
      }
    }
    return acceptedApplicants;
  }
}


