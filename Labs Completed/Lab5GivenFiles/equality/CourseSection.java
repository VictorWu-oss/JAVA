import java.time.LocalTime;
import java.util.Objects;

class CourseSection
{
   private final String prefix;
   private final String number;
   private final int enrollment;
   private final LocalTime startTime;
   private final LocalTime endTime;

   public CourseSection(final String prefix, final String number,
      final int enrollment, final LocalTime startTime, final LocalTime endTime)
   {
      this.prefix = prefix;
      this.number = number;
      this.enrollment = enrollment;
      this.startTime = startTime;
      this.endTime = endTime;
   }

   // implement equals (Check if 2 objects are equals)
   @Override
   public boolean equals(Object o){
      if (o == null) return false;                       // If it doesn't exist return false
      if(this == o) return true;                         // If objects are the same, 2 references to the same object in MEMORY LOCATION, return true
      if (!(o instanceof CourseSection)) return false;   // Check if the object is an instance of the same class, is it the correct type, if not same class return false
      CourseSection courses = (CourseSection) o;         // If so then Cast object to CourseSection to access fields

      // Compare their fields
      // int are primitive so use ==
      // String and classes are of reference type bc they refer to an Object, calls methods to perform operations
      // HANDLE NULL VALUES FOR OBJECT AND EVERY ATTRIBUTE

      // PREFIX
      if (this.prefix == null){                       // Check if current object is null prefix
         if (courses.prefix != null) return false;    // If one is null, the other is not so they are not equal
      }
      else if(!this.prefix.equals(courses.prefix)){   // If both are null continue (meaning equality)
         return false;                                // Call .equals on this.prefix bc it's not null
      }

      // NUMBER
      if (this.number == null){
         if (courses.number != null) return false;
      }
      else if(!this.number.equals(courses.number)){
         return false;
      }

      // ENROLLMENT
      if(this.enrollment != courses.enrollment){
         return false;
      }

      // START TIME
      if (this.startTime == null){
         if (courses.startTime != null) return false;
      }
      else if(!this.startTime.equals(courses.startTime)){
         return false;
      }

      // END TIME
      if (this.endTime == null){
         if (courses.endTime != null) return false;
      }
      else if(!this.endTime.equals(courses.endTime)){
         return false;
      }

      return true;
   }

   //hashCode (return hash code used for collections) for appropriate objects LONG WAY
   // Formula for int hash code is hash = 31 * hash + i;
   @Override
   public int hashCode(){
      int hash = 17;

      // PREFIX
      int prefixHash;
      if (prefix == null){
         prefixHash = 0;
      }
      else {
         prefixHash = prefix.hashCode();  // Objects hashCode
      }
      hash = 31 * hash + prefixHash;      // + the has and prime number make this hash unique

      // NUMBER
      int numberHash;
      if (number == null){
         numberHash = 0;
      }
      else {
         numberHash = number.hashCode();
      }
      hash = 31 * hash + numberHash;

      // ENROLLMENT
      hash = 31 * hash + enrollment;

      // STARTTIME
      int stHash;
      if (startTime == null){
         stHash = 0;
      }
      else {
         stHash = startTime.hashCode();
      }
      hash = 31 * hash + stHash;

      // ENDTIME
      int etHash;
      if (endTime == null){
         etHash = 0;
      }
      else {
         etHash = endTime.hashCode();
      }
      hash = 31 * hash + etHash;

     return hash;
   }

   // additional likely methods not defined since they are not needed for testing
}
