package beer.unacceptable.unacceptablehealth.Repositories;

import com.unacceptable.unacceptablelibrary.Repositories.RepositoryCallback;

import java.util.Date;

public interface IRepository {
    void LoadDailyLog(String sStringID, RepositoryCallback callback);
    void LoadDailyLogByDate(String sDate, RepositoryCallback callback);
    void LoadAllWorkoutTypes(RepositoryCallback callback);
}
