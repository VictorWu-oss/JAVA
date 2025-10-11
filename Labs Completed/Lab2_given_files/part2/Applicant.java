package part2;

import part1.CourseGrade;
import java.util.List;

public class Applicant {
    private String name;
    private List<CourseGrade> grades;
    private String education;      // highestEducationlevel
    private String race;           // DEI

    // Constructor
    public Applicant(String name, List<CourseGrade> grades, String education, String race) {
        this.name = name;
        this.grades = grades;
        this.education = education;
        this.race = race;
    }

    // Returns applicant's name
    public String getName() {
        return name;
    }

    // Returns applicant's list of scores
    public List<CourseGrade> getGrades() {
        return grades;
    }

    public String getRace(){
        return race;
    }

    public String getEducation(){
        return education;
    }

    // Returns the score that was asked for
    public CourseGrade getGradeFor(String courseName) {
        for (CourseGrade grade : grades) {
            if (grade.getCourseName().equals(courseName)) {
                return grade;
            }
        }
        return null; // Course not found
    }
}
