package beer.unacceptable.unacceptablehealth.Models;

import com.google.gson.annotations.Expose;
import com.unacceptable.unacceptablelibrary.Models.ListableObject;

public class ExercisePlan extends ListableObject {
    @Expose
    public Exercise Exercise;
    @Expose
    int Order; //TODO: ??? What was this?
    //TODO: For Order above... Now that I'm doing the PerformWorkout, I think this was the order to do the exercises...
    //TODO: Maybe I can add the ability to move them in the workout plan editor.


    @Expose
    public int Reps;
    @Expose
    public int Sets;
    @Expose
    public double Weight;
    //Time to do a rep. Stored in seconds
    @Expose
    public int Seconds;

    //Below isn't saved, just used in the app to keep track as you go
    public int CompletedSets;
    public boolean Completed;

    public int SetsRemaining() {
        return Sets - CompletedSets;
    }
}
