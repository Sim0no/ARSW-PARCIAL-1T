package eci.arsw.covidanalyzer.persistence;

import eci.arsw.covidanalyzer.exceptions.CovidAggregateException;
import eci.arsw.covidanalyzer.model.Result;
import eci.arsw.covidanalyzer.model.ResultType;

import java.util.List;
import java.util.UUID;

public interface ICovidAggregatePersistence {
    /**
     * Add a new result into the specified result type storage.
     *
     * @param result
     * @param type
     * @return
     */
    boolean aggregateResult(Result result, ResultType type) throws CovidAggregateException;

    /**
     * Get all the results for the specified result type.
     *
     * @param type
     * @return
     */
    List<Result> getResult(ResultType type);

    /**
     *
     * @param id
     * @param type
     */
    void upsertPersonWithMultipleTests(UUID id, ResultType type);
}
