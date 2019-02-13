package beer.unacceptable.unacceptablehealth.Repositories;

import com.unacceptable.unacceptablelibrary.Repositories.RepositoryCallback;

public interface IRepository {
    public void LoadDailyLog(String sStringID, RepositoryCallback callback);
}
