package beer.unacceptable.unacceptablehealth.Models.CustomReturns;

import com.google.gson.annotations.Expose;

import beer.unacceptable.unacceptablehealth.Models.Exercise;
import beer.unacceptable.unacceptablehealth.Models.Muscle;

public class ExerciseWithMuscleList {
    public Exercise Exercise;
    public Muscle[] Muscles;
}
