package beer.unacceptable.unacceptablehealth.Controllers;

import java.util.Date;

public class DateLogic implements IDateLogic {
    public Date getTodaysDate() {
        return new Date();
    }
}

