package eci.arsw.covidanalyzer.service;

import eci.arsw.covidanalyzer.exceptions.CovidAggregateException;
import eci.arsw.covidanalyzer.model.Result;
import eci.arsw.covidanalyzer.model.ResultType;
import eci.arsw.covidanalyzer.persistence.ICovidAggregatePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CovidAggregateServiceStub implements ICovidAggregateService{
    @Autowired
    private ICovidAggregatePersistence persistencia;

    @Override
    public boolean aggregateResult(Result result, ResultType type) throws CovidAggregateException {
        persistencia.aggregateResult(result, type);
        return false;
    }

    @Override
    public List<Result> getResult(ResultType type) {
        return persistencia.getResult(type);
    }

    @Override
    public void upsertPersonWithMultipleTests(UUID id, ResultType type) {
        persistencia.upsertPersonWithMultipleTests(id, type);

    }
}
