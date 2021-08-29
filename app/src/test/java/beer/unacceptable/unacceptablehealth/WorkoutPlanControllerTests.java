package beer.unacceptable.unacceptablehealth;

import static org.mockito.Mockito.mock;

import com.unacceptable.unacceptablelibrary.Repositories.ILibraryRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import beer.unacceptable.unacceptablehealth.Controllers.WorkoutPlanController;
import beer.unacceptable.unacceptablehealth.Repositories.IRepository;

public class WorkoutPlanControllerTests {

    String sWorkoutPlanData = "{\"WorkoutPlan\":{\"idString\":\"5cad619054eaba3fa6788242\",\"name\":\"Main Arm Workout\",\"WorkoutType\":{\"idString\":\"5c70d3e72406ff7cdb0313a5\",\"name\":\"Arms\"},\"ExercisePlans\":[{\"idString\":null,\"name\":\"Bent Over Row\",\"Exercise\":{\"idString\":\"5cad657654eaba3fa6788245\",\"name\":\"Bent Over Row\",\"Muscles\":[{\"idString\":\"5cad656954eaba3fa6788244\",\"name\":\"Back\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":null,\"GPSTracking\":false},\"Order\":0,\"Reps\":8,\"Sets\":4,\"Weight\":20.0,\"Seconds\":0},{\"idString\":null,\"name\":\"Lateral Dumbell Raise\",\"Exercise\":{\"idString\":\"5caa543479f5dc4dfbe2fd2b\",\"name\":\"Lateral Dumbell Raise\",\"Muscles\":[{\"idString\":\"5ca694f06aae603b7206de91\",\"name\":\"Shoulder\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":null,\"GPSTracking\":false},\"Order\":0,\"Reps\":12,\"Sets\":4,\"Weight\":10.0,\"Seconds\":0},{\"idString\":null,\"name\":\"Zottoman curl\",\"Exercise\":{\"idString\":\"5e8e1530ee5d221046fd514b\",\"name\":\"Zottoman curl\",\"Muscles\":[{\"idString\":\"5ca694cd6aae603b7206de8e\",\"name\":\"Biceps\"},{\"idString\":\"5ca694d86aae603b7206de90\",\"name\":\"Forearm\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"Rotate a bicep curl from underhanded to overhanded at the top.\",\"GPSTracking\":false},\"Order\":0,\"Reps\":10,\"Sets\":4,\"Weight\":17.5,\"Seconds\":0},{\"idString\":null,\"name\":\"Dumbell Overhead Tricep Extensions \",\"Exercise\":{\"idString\":\"5cad654b54eaba3fa6788243\",\"name\":\"Dumbell Overhead Tricep Extensions \",\"Muscles\":[{\"idString\":\"5ca694d36aae603b7206de8f\",\"name\":\"Triceps\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":null,\"GPSTracking\":false},\"Order\":0,\"Reps\":8,\"Sets\":4,\"Weight\":40.0,\"Seconds\":0},{\"idString\":null,\"name\":\"Chest Fly\",\"Exercise\":{\"idString\":\"5caa588279f5dc4dfbe2fd2c\",\"name\":\"Chest Fly\",\"Muscles\":[{\"idString\":\"5ca697236aae603b7206de92\",\"name\":\"Chest \"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":null,\"GPSTracking\":false},\"Order\":0,\"Reps\":10,\"Sets\":4,\"Weight\":20.0,\"Seconds\":0},{\"idString\":null,\"name\":\"Arnold Press\",\"Exercise\":{\"idString\":\"60ae5ea582b3fa5055217925\",\"name\":\"Arnold Press\",\"Muscles\":[{\"idString\":\"5ca694f06aae603b7206de91\",\"name\":\"Shoulder\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"Curl the dumbbells up and bring them in front of you so your palms face you. Press the weights above your head until your arms are straight, twisting them as you go so your palms end up facing forwards. Reverse the movement so that the weights are in front of your chest again. That’s one rep.\",\"GPSTracking\":false},\"Order\":0,\"Reps\":8,\"Sets\":4,\"Weight\":12.5,\"Seconds\":0},{\"idString\":null,\"name\":\"Dumbbell Press\",\"Exercise\":{\"idString\":\"5ebabb3aee5d221046fd515f\",\"name\":\"Dumbbell Press\",\"Muscles\":[{\"idString\":\"5ca697236aae603b7206de92\",\"name\":\"Chest \"},{\"idString\":\"5ca694d36aae603b7206de8f\",\"name\":\"Triceps\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"Lay on floor with arms to your side, holding two dumbbells towards the ceiling. Raising them up and above your chest\",\"GPSTracking\":false},\"Order\":0,\"Reps\":8,\"Sets\":4,\"Weight\":22.5,\"Seconds\":0}],\"CalorieLogs\":null,\"TotalCalories\":0,\"LastUsed\":\"2001-01-01T00:00:00Z\",\"Id\":\"5cad619054eaba3fa6788242\"},\"Exercises\":[{\"idString\":\"5caa4a7f79f5dc4dfbe2fd29\",\"name\":\"Dumbbell Curl\",\"Muscles\":[{\"idString\":\"5ca694cd6aae603b7206de8e\",\"name\":\"Biceps\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":null,\"GPSTracking\":false,\"Id\":\"5caa4a7f79f5dc4dfbe2fd29\"},{\"idString\":\"5caa4b5979f5dc4dfbe2fd2a\",\"name\":\"Push Up\",\"Muscles\":[{\"idString\":\"5ca697236aae603b7206de92\",\"name\":\"Chest \"}],\"ShowWeight\":false,\"ShowTime\":false,\"ShowReps\":true,\"Description\":null,\"GPSTracking\":false,\"Id\":\"5caa4b5979f5dc4dfbe2fd2a\"},{\"idString\":\"5caa543479f5dc4dfbe2fd2b\",\"name\":\"Lateral Dumbell Raise\",\"Muscles\":[{\"idString\":\"5ca694f06aae603b7206de91\",\"name\":\"Shoulder\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":null,\"GPSTracking\":false,\"Id\":\"5caa543479f5dc4dfbe2fd2b\"},{\"idString\":\"5caa588279f5dc4dfbe2fd2c\",\"name\":\"Chest Fly\",\"Muscles\":[{\"idString\":\"5ca697236aae603b7206de92\",\"name\":\"Chest \"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":null,\"GPSTracking\":false,\"Id\":\"5caa588279f5dc4dfbe2fd2c\"},{\"idString\":\"5cad654b54eaba3fa6788243\",\"name\":\"Dumbell Overhead Tricep Extensions \",\"Muscles\":[{\"idString\":\"5ca694d36aae603b7206de8f\",\"name\":\"Triceps\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":null,\"GPSTracking\":false,\"Id\":\"5cad654b54eaba3fa6788243\"},{\"idString\":\"5cad657654eaba3fa6788245\",\"name\":\"Bent Over Row\",\"Muscles\":[{\"idString\":\"5cad656954eaba3fa6788244\",\"name\":\"Back\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":null,\"GPSTracking\":false,\"Id\":\"5cad657654eaba3fa6788245\"},{\"idString\":\"5cad658d54eaba3fa6788246\",\"name\":\"Reverse Curl\",\"Muscles\":[{\"idString\":\"5ca694d86aae603b7206de90\",\"name\":\"Forearm\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":null,\"GPSTracking\":false,\"Id\":\"5cad658d54eaba3fa6788246\"},{\"idString\":\"5cad65a354eaba3fa6788247\",\"name\":\"Body Weight Tricep Dip\",\"Muscles\":[{\"idString\":\"5ca694d36aae603b7206de8f\",\"name\":\"Triceps\"}],\"ShowWeight\":false,\"ShowTime\":false,\"ShowReps\":true,\"Description\":null,\"GPSTracking\":false,\"Id\":\"5cad65a354eaba3fa6788247\"},{\"idString\":\"5cadefe454eaba3fa678824a\",\"name\":\"Sit Up\",\"Muscles\":[{\"idString\":\"5cadefd754eaba3fa6788249\",\"name\":\"Abs\"},{\"idString\":\"5cb351c96d178c28ea8f19d1\",\"name\":\"Rectus Abdominal\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":null,\"GPSTracking\":false,\"Id\":\"5cadefe454eaba3fa678824a\"},{\"idString\":\"5cadf00454eaba3fa678824b\",\"name\":\"Bicycle Abs\",\"Muscles\":[{\"idString\":\"5cadefd754eaba3fa6788249\",\"name\":\"Abs\"},{\"idString\":\"5cb351da6d178c28ea8f19d2\",\"name\":\"External Obliques\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":null,\"GPSTracking\":false,\"Id\":\"5cadf00454eaba3fa678824b\"},{\"idString\":\"5cadf01154eaba3fa678824c\",\"name\":\"Dumbbell Side Bend\",\"Muscles\":[{\"idString\":\"5cadefd754eaba3fa6788249\",\"name\":\"Abs\"},{\"idString\":\"5cb351e56d178c28ea8f19d3\",\"name\":\"Internal Obliques\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"Stand straight up, holding one dumbell in one hand at the side of your body. Put the other hand on the other hip. Slowly bend as far down as possible with the dumbell side, hold for a second, and return back up.\",\"GPSTracking\":false,\"Id\":\"5cadf01154eaba3fa678824c\"},{\"idString\":\"5cadf02254eaba3fa678824d\",\"name\":\"Hip Raise\",\"Muscles\":[{\"idString\":\"5cadefd754eaba3fa6788249\",\"name\":\"Abs\"},{\"idString\":\"5cb352056d178c28ea8f19d5\",\"name\":\"Hip Flexor\"}],\"ShowWeight\":false,\"ShowTime\":false,\"ShowReps\":true,\"Description\":null,\"GPSTracking\":false,\"Id\":\"5cadf02254eaba3fa678824d\"},{\"idString\":\"5cadf02e54eaba3fa678824e\",\"name\":\"Russian Side Twist\",\"Muscles\":[{\"idString\":\"5cadefd754eaba3fa6788249\",\"name\":\"Abs\"},{\"idString\":\"5cb351e56d178c28ea8f19d3\",\"name\":\"Internal Obliques\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":null,\"GPSTracking\":false,\"Id\":\"5cadf02e54eaba3fa678824e\"},{\"idString\":\"5cadf03e54eaba3fa678824f\",\"name\":\"Plank\",\"Muscles\":[{\"idString\":\"5cadefd754eaba3fa6788249\",\"name\":\"Abs\"}],\"ShowWeight\":false,\"ShowTime\":true,\"ShowReps\":false,\"Description\":null,\"GPSTracking\":false,\"Id\":\"5cadf03e54eaba3fa678824f\"},{\"idString\":\"5cb1e1bc6d178c28ea8f19ce\",\"name\":\"Pull Up\",\"Muscles\":[{\"idString\":\"5ca697236aae603b7206de92\",\"name\":\"Chest \"},{\"idString\":\"5ca694cd6aae603b7206de8e\",\"name\":\"Biceps\"},{\"idString\":\"5cad656954eaba3fa6788244\",\"name\":\"Back\"},{\"idString\":\"5ca694d86aae603b7206de90\",\"name\":\"Forearm\"}],\"ShowWeight\":false,\"ShowTime\":false,\"ShowReps\":true,\"Description\":null,\"GPSTracking\":false,\"Id\":\"5cb1e1bc6d178c28ea8f19ce\"},{\"idString\":\"5cb1e2666d178c28ea8f19cf\",\"name\":\"Push Up\",\"Muscles\":[{\"idString\":\"5ca697236aae603b7206de92\",\"name\":\"Chest \"},{\"idString\":\"5ca694f06aae603b7206de91\",\"name\":\"Shoulder\"},{\"idString\":\"5ca694d36aae603b7206de8f\",\"name\":\"Triceps\"},{\"idString\":\"5cadefd754eaba3fa6788249\",\"name\":\"Abs\"}],\"ShowWeight\":false,\"ShowTime\":false,\"ShowReps\":true,\"Description\":null,\"GPSTracking\":false,\"Id\":\"5cb1e2666d178c28ea8f19cf\"},{\"idString\":\"5cb361de6d178c28ea8f19d6\",\"name\":\"Leg Raises\",\"Muscles\":[{\"idString\":\"5cb351c96d178c28ea8f19d1\",\"name\":\"Rectus Abdominal\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":null,\"GPSTracking\":false,\"Id\":\"5cb361de6d178c28ea8f19d6\"},{\"idString\":\"5cb362cb6d178c28ea8f19d8\",\"name\":\"Side Plank\",\"Muscles\":[{\"idString\":\"5cadefd754eaba3fa6788249\",\"name\":\"Abs\"},{\"idString\":\"5cb351da6d178c28ea8f19d2\",\"name\":\"External Obliques\"}],\"ShowWeight\":false,\"ShowTime\":true,\"ShowReps\":false,\"Description\":null,\"GPSTracking\":false,\"Id\":\"5cb362cb6d178c28ea8f19d8\"},{\"idString\":\"5cb364176d178c28ea8f19da\",\"name\":\"Bird Dog Crunch \",\"Muscles\":[{\"idString\":\"5cadefd754eaba3fa6788249\",\"name\":\"Abs\"},{\"idString\":\"5cb351f96d178c28ea8f19d4\",\"name\":\"Transverse Abdominal\"}],\"ShowWeight\":false,\"ShowTime\":false,\"ShowReps\":true,\"Description\":null,\"GPSTracking\":false,\"Id\":\"5cb364176d178c28ea8f19da\"},{\"idString\":\"5cb364276d178c28ea8f19db\",\"name\":\"Forearm Plank\",\"Muscles\":[{\"idString\":\"5cb351f96d178c28ea8f19d4\",\"name\":\"Transverse Abdominal\"}],\"ShowWeight\":false,\"ShowTime\":true,\"ShowReps\":false,\"Description\":null,\"GPSTracking\":false,\"Id\":\"5cb364276d178c28ea8f19db\"},{\"idString\":\"5cb364b06d178c28ea8f19dc\",\"name\":\"Hip Flexion \",\"Muscles\":[{\"idString\":\"5cb352056d178c28ea8f19d5\",\"name\":\"Hip Flexor\"}],\"ShowWeight\":false,\"ShowTime\":true,\"ShowReps\":false,\"Description\":null,\"GPSTracking\":false,\"Id\":\"5cb364b06d178c28ea8f19dc\"},{\"idString\":\"5cb8c4b0f7cb5a79682da50f\",\"name\":\"Run - Treadmill\",\"Muscles\":[{\"idString\":\"5cb8c496f7cb5a79682da50e\",\"name\":\"Legs\"}],\"ShowWeight\":false,\"ShowTime\":false,\"ShowReps\":false,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5cb8c4b0f7cb5a79682da50f\"},{\"idString\":\"5cca1381f108bd754ed86a07\",\"name\":\"Chest Fly Machine\",\"Muscles\":[{\"idString\":\"5ca697236aae603b7206de92\",\"name\":\"Chest \"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5cca1381f108bd754ed86a07\"},{\"idString\":\"5cca152ef108bd754ed86a09\",\"name\":\"Hammer Strength High Row\",\"Muscles\":[{\"idString\":\"5ca694cd6aae603b7206de8e\",\"name\":\"Biceps\"},{\"idString\":\"5cad656954eaba3fa6788244\",\"name\":\"Back\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5cca152ef108bd754ed86a09\"},{\"idString\":\"5cca1687f108bd754ed86a0b\",\"name\":\"Back Extension\",\"Muscles\":[{\"idString\":\"5cca1646f108bd754ed86a0a\",\"name\":\"Lower Back\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5cca1687f108bd754ed86a0b\"},{\"idString\":\"5cca1787f108bd754ed86a0d\",\"name\":\"Tricep Extension Machine\",\"Muscles\":[{\"idString\":\"5ca694d36aae603b7206de8f\",\"name\":\"Triceps\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5cca1787f108bd754ed86a0d\"},{\"idString\":\"5cca1927f108bd754ed86a0f\",\"name\":\"Lateral Raise Machine\",\"Muscles\":[{\"idString\":\"5cca18ebf108bd754ed86a0e\",\"name\":\"Deltoids\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5cca1927f108bd754ed86a0f\"},{\"idString\":\"5cca19f6f108bd754ed86a10\",\"name\":\"Bicep Curl Machine\",\"Muscles\":[{\"idString\":\"5ca694cd6aae603b7206de8e\",\"name\":\"Biceps\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5cca19f6f108bd754ed86a10\"},{\"idString\":\"5cca1abbf108bd754ed86a11\",\"name\":\"Bicep Curl Machine - Forearms\",\"Muscles\":[{\"idString\":\"5ca694d86aae603b7206de90\",\"name\":\"Forearm\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5cca1abbf108bd754ed86a11\"},{\"idString\":\"5cca1ceef108bd754ed86a12\",\"name\":\"Shoulder Press Machine\",\"Muscles\":[{\"idString\":\"5ca694f06aae603b7206de91\",\"name\":\"Shoulder\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5cca1ceef108bd754ed86a12\"},{\"idString\":\"5cca1df3f108bd754ed86a13\",\"name\":\"Row Machine\",\"Muscles\":[{\"idString\":\"5cad656954eaba3fa6788244\",\"name\":\"Back\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5cca1df3f108bd754ed86a13\"},{\"idString\":\"5cca1f4ef108bd754ed86a14\",\"name\":\"Chest Press Machine\",\"Muscles\":[{\"idString\":\"5ca697236aae603b7206de92\",\"name\":\"Chest \"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5cca1f4ef108bd754ed86a14\"},{\"idString\":\"5cca53ecf108bd754ed86a15\",\"name\":\"Crunch Machine\",\"Muscles\":[{\"idString\":\"5cb351c96d178c28ea8f19d1\",\"name\":\"Rectus Abdominal\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5cca53ecf108bd754ed86a15\"},{\"idString\":\"5ccb1640f108bd754ed86a18\",\"name\":\"Hanging Leg Twist\",\"Muscles\":[{\"idString\":\"5cb351da6d178c28ea8f19d2\",\"name\":\"External Obliques\"},{\"idString\":\"5cb351e56d178c28ea8f19d3\",\"name\":\"Internal Obliques\"}],\"ShowWeight\":false,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5ccb1640f108bd754ed86a18\"},{\"idString\":\"5cccb43bf108bd754ed86a1c\",\"name\":\"Hip Abduction\",\"Muscles\":[{\"idString\":\"5cccb412f108bd754ed86a1b\",\"name\":\"Hip Abductors\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5cccb43bf108bd754ed86a1c\"},{\"idString\":\"5cccb4f8f108bd754ed86a1f\",\"name\":\"Leg Extensions\",\"Muscles\":[{\"idString\":\"5cccb4ccf108bd754ed86a1e\",\"name\":\"Quadriceps\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5cccb4f8f108bd754ed86a1f\"},{\"idString\":\"5cccb612f108bd754ed86a22\",\"name\":\"Seated Leg Curl\",\"Muscles\":[{\"idString\":\"5cccb5cef108bd754ed86a20\",\"name\":\"Glutes\"},{\"idString\":\"5cccb5d4f108bd754ed86a21\",\"name\":\"Hamstring\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5cccb612f108bd754ed86a22\"},{\"idString\":\"5cccb794f108bd754ed86a23\",\"name\":\"Seated Leg Press\",\"Muscles\":[{\"idString\":\"5cccb4ccf108bd754ed86a1e\",\"name\":\"Quadriceps\"},{\"idString\":\"5cccb5cef108bd754ed86a20\",\"name\":\"Glutes\"},{\"idString\":\"5cccb5d4f108bd754ed86a21\",\"name\":\"Hamstring\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5cccb794f108bd754ed86a23\"},{\"idString\":\"5cccb8f7f108bd754ed86a26\",\"name\":\"Standing Calfs\",\"Muscles\":[{\"idString\":\"5cccb8b9f108bd754ed86a24\",\"name\":\"Soleus\"},{\"idString\":\"5cccb8c0f108bd754ed86a25\",\"name\":\"Gastrocnemius\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5cccb8f7f108bd754ed86a26\"},{\"idString\":\"5cccba21f108bd754ed86a27\",\"name\":\"Hip Abduction 2\",\"Muscles\":[{\"idString\":\"5cccb412f108bd754ed86a1b\",\"name\":\"Hip Abductors\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"Machine 2, bring knees together\",\"GPSTracking\":false,\"Id\":\"5cccba21f108bd754ed86a27\"},{\"idString\":\"5cd59d8fcce9715e8bd01e82\",\"name\":\"Glutes Machine\",\"Muscles\":[{\"idString\":\"5cccb5cef108bd754ed86a20\",\"name\":\"Glutes\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"This is the one behind the stairs that you stand on and push back.\",\"GPSTracking\":false,\"Id\":\"5cd59d8fcce9715e8bd01e82\"},{\"idString\":\"5cdc8c9dcce9715e8bd01e89\",\"name\":\"Dead Bug\",\"Muscles\":[{\"idString\":\"5cb351c96d178c28ea8f19d1\",\"name\":\"Rectus Abdominal\"},{\"idString\":\"5cb351da6d178c28ea8f19d2\",\"name\":\"External Obliques\"},{\"idString\":\"5cb351e56d178c28ea8f19d3\",\"name\":\"Internal Obliques\"},{\"idString\":\"5cb352056d178c28ea8f19d5\",\"name\":\"Hip Flexor\"},{\"idString\":\"5ca694f06aae603b7206de91\",\"name\":\"Shoulder\"},{\"idString\":\"5cca1646f108bd754ed86a0a\",\"name\":\"Lower Back\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"Lay on back with legs up bent at 90. Hold weight above you. Alternate lowering legs while lowering weight above head.\",\"GPSTracking\":false,\"Id\":\"5cdc8c9dcce9715e8bd01e89\"},{\"idString\":\"5cdf2a1ccce9715e8bd01e8e\",\"name\":\"Stair Climber\",\"Muscles\":[{\"idString\":\"5cb8c496f7cb5a79682da50e\",\"name\":\"Legs\"}],\"ShowWeight\":false,\"ShowTime\":false,\"ShowReps\":false,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5cdf2a1ccce9715e8bd01e8e\"},{\"idString\":\"5ce71482cce9715e8bd01e9f\",\"name\":\"Kettle Bell Swing\",\"Muscles\":[{\"idString\":\"5ca694d86aae603b7206de90\",\"name\":\"Forearm\"},{\"idString\":\"5cb351c96d178c28ea8f19d1\",\"name\":\"Rectus Abdominal\"},{\"idString\":\"5cb8c496f7cb5a79682da50e\",\"name\":\"Legs\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5ce71482cce9715e8bd01e9f\"},{\"idString\":\"5ce7149ccce9715e8bd01ea0\",\"name\":\"Kettle Bell One Arm Dead Drop\",\"Muscles\":[{\"idString\":\"5cadefd754eaba3fa6788249\",\"name\":\"Abs\"},{\"idString\":\"5cb8c496f7cb5a79682da50e\",\"name\":\"Legs\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5ce7149ccce9715e8bd01ea0\"},{\"idString\":\"5cfad9cdcce9715e8bd01eb2\",\"name\":\"Scissor Legs\",\"Muscles\":[{\"idString\":\"5cadefd754eaba3fa6788249\",\"name\":\"Abs\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5cfad9cdcce9715e8bd01eb2\"},{\"idString\":\"5d1d28af1379f819bcea6af7\",\"name\":\"Hammer Strength Chest Press\",\"Muscles\":[{\"idString\":\"5ca697236aae603b7206de92\",\"name\":\"Chest \"},{\"idString\":\"5ca694f06aae603b7206de91\",\"name\":\"Shoulder\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5d1d28af1379f819bcea6af7\"},{\"idString\":\"5d251ee31379f819bcea6afd\",\"name\":\"Wrist Curls\",\"Muscles\":[{\"idString\":\"5ca694d86aae603b7206de90\",\"name\":\"Forearm\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5d251ee31379f819bcea6afd\"},{\"idString\":\"5d6a8e2fee5d221046fd50b7\",\"name\":\"Rowing Machine\",\"Muscles\":[{\"idString\":\"5d6a8e1cee5d221046fd50b6\",\"name\":\"Full Body\"}],\"ShowWeight\":false,\"ShowTime\":false,\"ShowReps\":false,\"Description\":\"Set drag factor to 115-125\",\"GPSTracking\":false,\"Id\":\"5d6a8e2fee5d221046fd50b7\"},{\"idString\":\"5d6becaaee5d221046fd50ba\",\"name\":\"Tricep Press\",\"Muscles\":[{\"idString\":\"5ca694d36aae603b7206de8f\",\"name\":\"Triceps\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5d6becaaee5d221046fd50ba\"},{\"idString\":\"5d6bedaeee5d221046fd50bb\",\"name\":\"Bicep Curl Machine (By Stairs)\",\"Muscles\":[{\"idString\":\"5ca694cd6aae603b7206de8e\",\"name\":\"Biceps\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5d6bedaeee5d221046fd50bb\"},{\"idString\":\"5d6beed4ee5d221046fd50bc\",\"name\":\"Seated Row\",\"Muscles\":[{\"idString\":\"5ca694cd6aae603b7206de8e\",\"name\":\"Biceps\"},{\"idString\":\"5cad656954eaba3fa6788244\",\"name\":\"Back\"},{\"idString\":\"5cca1646f108bd754ed86a0a\",\"name\":\"Lower Back\"},{\"idString\":\"5cca18ebf108bd754ed86a0e\",\"name\":\"Deltoids\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5d6beed4ee5d221046fd50bc\"},{\"idString\":\"5dc70d62ee5d221046fd510c\",\"name\":\"Side Plank Leg Lift\",\"Muscles\":[{\"idString\":\"5cadefd754eaba3fa6788249\",\"name\":\"Abs\"},{\"idString\":\"5ca694f06aae603b7206de91\",\"name\":\"Shoulder\"},{\"idString\":\"5cad656954eaba3fa6788244\",\"name\":\"Back\"},{\"idString\":\"5cccb5cef108bd754ed86a20\",\"name\":\"Glutes\"}],\"ShowWeight\":false,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5dc70d62ee5d221046fd510c\"},{\"idString\":\"5e87263bee5d221046fd513e\",\"name\":\"Squat\",\"Muscles\":[{\"idString\":\"5cccb4ccf108bd754ed86a1e\",\"name\":\"Quadriceps\"},{\"idString\":\"5cccb5cef108bd754ed86a20\",\"name\":\"Glutes\"},{\"idString\":\"5cccb5d4f108bd754ed86a21\",\"name\":\"Hamstring\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5e87263bee5d221046fd513e\"},{\"idString\":\"5e8a465bee5d221046fd5140\",\"name\":\"Clamshell\",\"Muscles\":[{\"idString\":\"5cccb412f108bd754ed86a1b\",\"name\":\"Hip Abductors\"}],\"ShowWeight\":false,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5e8a465bee5d221046fd5140\"},{\"idString\":\"5e8a4721ee5d221046fd5141\",\"name\":\"Marching Hip Lift\",\"Muscles\":[{\"idString\":\"5cccb5cef108bd754ed86a20\",\"name\":\"Glutes\"}],\"ShowWeight\":false,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"Lay on back and raise hip all the way. Then lift one leg at a time so it becomes inline with your body.\",\"GPSTracking\":false,\"Id\":\"5e8a4721ee5d221046fd5141\"},{\"idString\":\"5e8a47e0ee5d221046fd5142\",\"name\":\"Wall Sit\",\"Muscles\":[{\"idString\":\"5cccb4ccf108bd754ed86a1e\",\"name\":\"Quadriceps\"}],\"ShowWeight\":false,\"ShowTime\":true,\"ShowReps\":false,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5e8a47e0ee5d221046fd5142\"},{\"idString\":\"5e8a4961ee5d221046fd5143\",\"name\":\"Dumbbell Donkey Kick\",\"Muscles\":[{\"idString\":\"5cccb5d4f108bd754ed86a21\",\"name\":\"Hamstring\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"Get on all fours doggy style. Put weight on backside of knee. Lift leg backwards until even with your body.\",\"GPSTracking\":false,\"Id\":\"5e8a4961ee5d221046fd5143\"},{\"idString\":\"5e8a4a3cee5d221046fd5144\",\"name\":\"Standing Heel Raise\",\"Muscles\":[{\"idString\":\"5cccb8b9f108bd754ed86a24\",\"name\":\"Soleus\"},{\"idString\":\"5cccb8c0f108bd754ed86a25\",\"name\":\"Gastrocnemius\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5e8a4a3cee5d221046fd5144\"},{\"idString\":\"5e8e1530ee5d221046fd514b\",\"name\":\"Zottoman curl\",\"Muscles\":[{\"idString\":\"5ca694cd6aae603b7206de8e\",\"name\":\"Biceps\"},{\"idString\":\"5ca694d86aae603b7206de90\",\"name\":\"Forearm\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"Rotate a bicep curl from underhanded to overhanded at the top.\",\"GPSTracking\":false,\"Id\":\"5e8e1530ee5d221046fd514b\"},{\"idString\":\"5ebab937ee5d221046fd515c\",\"name\":\"Palms-In Shoulder Press\",\"Muscles\":[{\"idString\":\"5ca694f06aae603b7206de91\",\"name\":\"Shoulder\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5ebab937ee5d221046fd515c\"},{\"idString\":\"5ebaba07ee5d221046fd515e\",\"name\":\"Tricep Kickback\",\"Muscles\":[{\"idString\":\"5ca694d36aae603b7206de8f\",\"name\":\"Triceps\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5ebaba07ee5d221046fd515e\"},{\"idString\":\"5ebabb3aee5d221046fd515f\",\"name\":\"Dumbbell Press\",\"Muscles\":[{\"idString\":\"5ca697236aae603b7206de92\",\"name\":\"Chest \"},{\"idString\":\"5ca694d36aae603b7206de8f\",\"name\":\"Triceps\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"Lay on floor with arms to your side, holding two dumbbells towards the ceiling. Raising them up and above your chest\",\"GPSTracking\":false,\"Id\":\"5ebabb3aee5d221046fd515f\"},{\"idString\":\"5ebabcb8ee5d221046fd5160\",\"name\":\"Renegade Row\",\"Muscles\":[{\"idString\":\"5ca697236aae603b7206de92\",\"name\":\"Chest \"},{\"idString\":\"5ca694d36aae603b7206de8f\",\"name\":\"Triceps\"},{\"idString\":\"5cadefd754eaba3fa6788249\",\"name\":\"Abs\"},{\"idString\":\"5cad656954eaba3fa6788244\",\"name\":\"Back\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"Get two dumbbells to do a push up with. When you come up, lift up one dumbbell. Then do another push up and lift up the other. That's one rep\",\"GPSTracking\":false,\"Id\":\"5ebabcb8ee5d221046fd5160\"},{\"idString\":\"5ec2a813ee5d221046fd5164\",\"name\":\"Farmers Walk\",\"Muscles\":[{\"idString\":\"5ca694d86aae603b7206de90\",\"name\":\"Forearm\"}],\"ShowWeight\":true,\"ShowTime\":true,\"ShowReps\":false,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5ec2a813ee5d221046fd5164\"},{\"idString\":\"5ed7b7c4565b0d04d7452e2a\",\"name\":\"Hammer Curls\",\"Muscles\":[{\"idString\":\"5ca694cd6aae603b7206de8e\",\"name\":\"Biceps\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5ed7b7c4565b0d04d7452e2a\"},{\"idString\":\"5ed7b89d565b0d04d7452e2b\",\"name\":\"Farmers Carry\",\"Muscles\":[{\"idString\":\"5ca694cd6aae603b7206de8e\",\"name\":\"Biceps\"},{\"idString\":\"5d6a8e1cee5d221046fd50b6\",\"name\":\"Full Body\"}],\"ShowWeight\":true,\"ShowTime\":true,\"ShowReps\":false,\"Description\":\"Hold your arms directly out and bend your elbow at 90° upwards, holding the dumbbells up and walk around.\",\"GPSTracking\":false,\"Id\":\"5ed7b89d565b0d04d7452e2b\"},{\"idString\":\"5ed7b962565b0d04d7452e2c\",\"name\":\"Tricep Gravity Press\",\"Muscles\":[{\"idString\":\"5ca694d36aae603b7206de8f\",\"name\":\"Triceps\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"Lay on ground. Hold arms straight up with forearms parallel to ground. Move arms over your head then back down to chest, keeping Forearm's parallel the whole time.\",\"GPSTracking\":false,\"Id\":\"5ed7b962565b0d04d7452e2c\"},{\"idString\":\"5ee14fec565b0d04d7452e2e\",\"name\":\"Palms Down Wrist Curl\",\"Muscles\":[{\"idString\":\"5ca694d86aae603b7206de90\",\"name\":\"Forearm\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"\",\"GPSTracking\":false,\"Id\":\"5ee14fec565b0d04d7452e2e\"},{\"idString\":\"5ee15097565b0d04d7452e2f\",\"name\":\"Reverse Fly\",\"Muscles\":[{\"idString\":\"5ca694f06aae603b7206de91\",\"name\":\"Shoulder\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"Hold dumbbell in each hand, bend knees slightly, lean 45° forward, do a similar motion to chest fly\",\"GPSTracking\":false,\"Id\":\"5ee15097565b0d04d7452e2f\"},{\"idString\":\"5f765e8cec515328d86f611d\",\"name\":\"Rucking\",\"Muscles\":[{\"idString\":\"5ca694f06aae603b7206de91\",\"name\":\"Shoulder\"},{\"idString\":\"5cad656954eaba3fa6788244\",\"name\":\"Back\"},{\"idString\":\"5cccb5cef108bd754ed86a20\",\"name\":\"Glutes\"},{\"idString\":\"5cb8c496f7cb5a79682da50e\",\"name\":\"Legs\"},{\"idString\":\"5cb351c96d178c28ea8f19d1\",\"name\":\"Rectus Abdominal\"},{\"idString\":\"5cb352056d178c28ea8f19d5\",\"name\":\"Hip Flexor\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":false,\"Description\":\"\",\"GPSTracking\":true,\"Id\":\"5f765e8cec515328d86f611d\"},{\"idString\":\"5f862ee490b140471b8864ee\",\"name\":\"Walking\",\"Muscles\":[{\"idString\":\"5cb8c496f7cb5a79682da50e\",\"name\":\"Legs\"}],\"ShowWeight\":false,\"ShowTime\":false,\"ShowReps\":false,\"Description\":\"\",\"GPSTracking\":true,\"Id\":\"5f862ee490b140471b8864ee\"},{\"idString\":\"5ff0a70e82b3fa50552178c9\",\"name\":\"Goblet Squat\",\"Muscles\":[{\"idString\":\"5cccb4ccf108bd754ed86a1e\",\"name\":\"Quadriceps\"},{\"idString\":\"5cb8c496f7cb5a79682da50e\",\"name\":\"Legs\"},{\"idString\":\"5cadefd754eaba3fa6788249\",\"name\":\"Abs\"},{\"idString\":\"5ca694d86aae603b7206de90\",\"name\":\"Forearm\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"Stand with feet shoulder width apart and hold one side of dumbell in hands infront of your crotch area. Squat and repeat.\",\"GPSTracking\":false,\"Id\":\"5ff0a70e82b3fa50552178c9\"},{\"idString\":\"5ff0a7bf82b3fa50552178cb\",\"name\":\"Two Arm Dumbell Stiff Legged Dead Lift\",\"Muscles\":[{\"idString\":\"5cccb5d4f108bd754ed86a21\",\"name\":\"Hamstring\"},{\"idString\":\"5ff0a72c82b3fa50552178ca\",\"name\":\"Spine Erectors\"},{\"idString\":\"5cad656954eaba3fa6788244\",\"name\":\"Back\"},{\"idString\":\"5cccb5cef108bd754ed86a20\",\"name\":\"Glutes\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"Hold two dumbells, palm towards you and arms down. Lower dumbells to the top of your feet, then return to starting position. Do the whole thing slowly.\",\"GPSTracking\":false,\"Id\":\"5ff0a7bf82b3fa50552178cb\"},{\"idString\":\"5ff0a87b82b3fa50552178cc\",\"name\":\"Cross Body Hammer Curl\",\"Muscles\":[{\"idString\":\"5ca694cd6aae603b7206de8e\",\"name\":\"Biceps\"},{\"idString\":\"5ca694d86aae603b7206de90\",\"name\":\"Forearm\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"Hold two dumbells palm in down by your side. One at a time, curl each weight up towards your opposing shoulder. Return and do the other side.\",\"GPSTracking\":false,\"Id\":\"5ff0a87b82b3fa50552178cc\"},{\"idString\":\"5ff0a90782b3fa50552178cd\",\"name\":\"Dumbell Scaption\",\"Muscles\":[{\"idString\":\"5cad656954eaba3fa6788244\",\"name\":\"Back\"},{\"idString\":\"5ca694f06aae603b7206de91\",\"name\":\"Shoulder\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"Hold two dumbells on your sides, palms forward. Arc the weights up to your sides keeping your arms straight until your shoulder stretches. Return to start position and repeat.\",\"GPSTracking\":false,\"Id\":\"5ff0a90782b3fa50552178cd\"},{\"idString\":\"5ff0a98482b3fa50552178ce\",\"name\":\"Dumbell Lunge\",\"Muscles\":[{\"idString\":\"5cb8c496f7cb5a79682da50e\",\"name\":\"Legs\"},{\"idString\":\"5cadefd754eaba3fa6788249\",\"name\":\"Abs\"},{\"idString\":\"5cca1646f108bd754ed86a0a\",\"name\":\"Lower Back\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"Dumbells at your side, palms facing your body. Lunge forward as far as you can with your right leg, bending your trailling knee so it almost touches the floor. Use the heel of your right foot to push your upper body back to the starting position. Repeat with opposite leg.\",\"GPSTracking\":false,\"Id\":\"5ff0a98482b3fa50552178ce\"},{\"idString\":\"5ff0aa0a82b3fa50552178cf\",\"name\":\"Single Dumbell Shoulder Raise\",\"Muscles\":[{\"idString\":\"5cca18ebf108bd754ed86a0e\",\"name\":\"Deltoids\"},{\"idString\":\"5ca694d36aae603b7206de8f\",\"name\":\"Triceps\"},{\"idString\":\"5ca697236aae603b7206de92\",\"name\":\"Chest \"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"Grab one dumbell with a hand on each side, hold it in front of you arms down. Lift the dumbell directly above your head, then lower it back down and repeat.\",\"GPSTracking\":false,\"Id\":\"5ff0aa0a82b3fa50552178cf\"},{\"idString\":\"60ae5ea582b3fa5055217925\",\"name\":\"Arnold Press\",\"Muscles\":[{\"idString\":\"5ca694f06aae603b7206de91\",\"name\":\"Shoulder\"}],\"ShowWeight\":true,\"ShowTime\":false,\"ShowReps\":true,\"Description\":\"Curl the dumbbells up and bring them in front of you so your palms face you. Press the weights above your head until your arms are straight, twisting them as you go so your palms end up facing forwards. Reverse the movement so that the weights are in front of your chest again. That’s one rep.\",\"GPSTracking\":false,\"Id\":\"60ae5ea582b3fa5055217925\"}],\"WorkoutTypes\":[{\"Id\":\"5c70d3e02406ff7cdb0313a4\",\"idString\":\"5c70d3e02406ff7cdb0313a4\",\"name\":\"Run\"},{\"Id\":\"5c70d3e72406ff7cdb0313a5\",\"idString\":\"5c70d3e72406ff7cdb0313a5\",\"name\":\"Arms\"},{\"Id\":\"5c70d3ec2406ff7cdb0313a6\",\"idString\":\"5c70d3ec2406ff7cdb0313a6\",\"name\":\"Abs\"},{\"Id\":\"5c736c142406ff7cdb0313a9\",\"idString\":\"5c736c142406ff7cdb0313a9\",\"name\":\"Rest\"},{\"Id\":\"5caa7f8479f5dc4dfbe2fd2e\",\"idString\":\"5caa7f8479f5dc4dfbe2fd2e\",\"name\":\"Legs\"},{\"Id\":\"5cd598b4cce9715e8bd01e80\",\"idString\":\"5cd598b4cce9715e8bd01e80\",\"name\":\"Full Body\"},{\"Id\":\"5d696eefee5d221046fd50b1\",\"idString\":\"5d696eefee5d221046fd50b1\",\"name\":\"Cardio\"},{\"Id\":\"5ed693fc565b0d04d7452e25\",\"idString\":\"5ed693fc565b0d04d7452e25\",\"name\":\"Bi's and Tri's\"},{\"Id\":\"5ed69405565b0d04d7452e26\",\"idString\":\"5ed69405565b0d04d7452e26\",\"name\":\"Shoulder's and Forearm's\"},{\"Id\":\"5ed69421565b0d04d7452e27\",\"idString\":\"5ed69421565b0d04d7452e27\",\"name\":\"Chest and Back\"},{\"Id\":\"5ff363c482b3fa50552178d3\",\"idString\":\"5ff363c482b3fa50552178d3\",\"name\":\"Compound Workout\"},{\"Id\":\"5ff363ce82b3fa50552178d4\",\"idString\":\"5ff363ce82b3fa50552178d4\",\"name\":\"Compound Workout 2\"}]}";

    WorkoutPlanController m_Controller;
    IRepository repo;
    ILibraryRepository LibraryRepo;
    WorkoutPlanController.View view;

    @Before
    public void Setup() {
        repo = mock(IRepository.class);
        LibraryRepo = mock(ILibraryRepository.class);
        view = mock(WorkoutPlanController.View.class);

        m_Controller = new WorkoutPlanController(repo, LibraryRepo);
        m_Controller.attachView(view);
    }

    @Test
    public void Test_Create_MuscleListSummary() {
        Assert.assertTrue(true);
    }

}
