package eci.arsw.covidanalyzer.persistence.impl;

import eci.arsw.covidanalyzer.exceptions.CovidAggregateException;
import eci.arsw.covidanalyzer.model.Result;
import eci.arsw.covidanalyzer.model.ResultType;
import eci.arsw.covidanalyzer.persistence.ICovidAggregatePersistence;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CovidAggregatePersistenceImpl implements ICovidAggregatePersistence {

    List<Result> resultados;
    public  CovidAggregatePersistenceImpl(){
        resultados = new ArrayList<Result>();
        Result result = new Result(UUID.randomUUID(),"germancho","mejia","Apache", "g@mail.es", "jaja", "2020", false,0.3);
        result.setResultType(ResultType.FALSE_NEGATIVE);
        resultados.add(result);
    }

    @Override
    public boolean aggregateResult(Result result, ResultType type) throws CovidAggregateException {
        result.setResultType(type);
        boolean flag = false;

        if(flag){
            throw new CovidAggregateException("Resultado ya existe");
        }
        resultados.add(result);
        return true;


    }

    @Override
    public List<Result> getResult(ResultType type) {
        ArrayList<Result> aux = new ArrayList<Result>();
        for (Result r: resultados) {
            if(r.getResultType().equals(type)){
                aux.add(r);
            }

        }
        return aux;
    }

    @Override
    public void upsertPersonWithMultipleTests(UUID id, ResultType type) {
        boolean flag = true;
        for (Result r: resultados) {
            if(r.getId().equals(id)){
                flag = false;
                r.setResultType(type);
            }

        }
        if(flag){
            Result result = new Result(id,null,null,null,null,null,null,false,0);
            result.setResultType(type);
            resultados.add(result);
        }



    }
}
