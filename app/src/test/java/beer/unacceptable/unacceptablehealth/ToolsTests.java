package beer.unacceptable.unacceptablehealth;

import com.unacceptable.unacceptablelibrary.Models.ListableObject;
import com.unacceptable.unacceptablelibrary.Tools.Tools;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import beer.unacceptable.unacceptablehealth.Models.Muscle;

public class ToolsTests {


    @Test
    public void twoObjectsInArray_LookingForSecondOne_ReturnsPosition2() {
        Muscle[] array = new Muscle[2];
        Muscle one;
        Muscle two;
        one = new Muscle();
        one.idString = "5ca692cf81005e41b045712f";
        one.name = "Bicep";
        two = new Muscle();
        two.idString = "5ca6c912f8d26a41943b9186";
        two.name = "Tricep";
        array[0] = one;
        array[1] = two;

        Muscle other = new Muscle();
        other.idString = "5ca6c912f8d26a41943b9186";
        other.name = "Tricep";

        int i = Tools.FindPositionInArray(array, other);
        Assert.assertEquals(1, i);

    }

    @Test
    public void emptyListableObject_ComparesCorrectly() {
        ArrayList<Muscle> arrayList = new ArrayList<>();
        Muscle m = new Muscle();
        arrayList.add(m);

        int i = arrayList.indexOf(m);
        Assert.assertEquals(0, i);
    }
}
