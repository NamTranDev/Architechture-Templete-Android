package nam.tran.domain.di;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import nam.tran.domain.IRepository;
import nam.tran.domain.Repository;
import nam.tran.domain.executor.AppSchedulerProvider;
import nam.tran.domain.executor.SchedulerProvider;
import nam.tran.domain.interactor.TeamUseCase;
import nam.tran.domain.interactor.usecase.ITeamUseCase;
import nam.tran.flatform.di.PreferenceModule;
import nam.tran.domain.interactor.SoccerSeasonUseCase;
import nam.tran.domain.interactor.usecase.ISoccerSeasonUseCase;
import nam.tran.flatform.di.DbModule;
import nam.tran.flatform.di.NetModule;


@SuppressWarnings("unused")
@Module(includes = {NetModule.class, DbModule.class, PreferenceModule.class})
public abstract class DataModule {

    @Binds
    @Singleton
    abstract IRepository provideRepository(Repository repository);

    @Binds
    @Singleton
    abstract SchedulerProvider provideSchedulerProvider(AppSchedulerProvider schedulerProvider);
    
    @Binds
    @Singleton
    abstract ISoccerSeasonUseCase provideSoccerUseCase(SoccerSeasonUseCase soccerSeasonUseCase);

    @Binds
    @Singleton
    abstract ITeamUseCase provideTeamUseCase(TeamUseCase teamUseCase);
}
